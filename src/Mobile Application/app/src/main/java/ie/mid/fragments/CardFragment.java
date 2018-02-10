package ie.mid.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;

import java.util.List;
import java.util.Objects;

import ie.mid.CardCreateActivity;
import ie.mid.ProfileSelectionActivity;
import ie.mid.R;
import ie.mid.backend.IdentityTypeService;
import ie.mid.backend.SubmissionService;
import ie.mid.enums.CardStatus;
import ie.mid.handler.DatabaseHandler;
import ie.mid.interfaces.CardTaskCompleted;
import ie.mid.interfaces.IdentityTaskCompleted;
import ie.mid.model.CardType;
import ie.mid.pojo.IdentityType;
import ie.mid.pojo.Submission;
import ie.mid.util.InternetUtil;

/**
 * Created by Cillian on 21/10/2017.
 */

public class CardFragment extends Fragment implements CardTaskCompleted {

    private List<CardType> cardTypes;
    private List<CardType> localCardTypes;
    private CardType currentCardType;
    String userId;

    public static CardFragment newInstance() {
        return new CardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        userId = getArguments().getString("userId");
        return inflater.inflate(R.layout.fragment_card, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_card, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.edit_card:
                if(!currentCardType.getStatus().equals("PENDING")) {
                    intent = new Intent(getActivity(), CardCreateActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("isUpdate", true);
                    intent.putExtra("cardId", currentCardType.getId());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(),"Cannot Update a pending card",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.delete_card:
                deleteCard();
                break;
            default:
                intent = new Intent(getActivity(), ProfileSelectionActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

        return true;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getCards();
    }

    public void getCards(){
        DatabaseHandler handler = new DatabaseHandler(getActivity().getApplicationContext());
        handler.open();
        List<CardType> listOfCards = handler.getUserCards(userId);
        localCardTypes = handler.getUserCards(userId);
        handler.close();
        if(InternetUtil.isNetworkAvailable(getActivity().getApplicationContext())) {
            new CardRunner(
                    this,
                    new IdentityTypeService(getActivity().getApplicationContext()),
                    new SubmissionService(getActivity().getApplicationContext()),
                    listOfCards
            ).execute();
        }
        else {
            cardTypes = localCardTypes;
            setupViewPager();
            Toast.makeText(getActivity().getApplicationContext(),"No Internet Connection, Using Local Cards",Toast.LENGTH_LONG).show();
        }
    }

    private void setupViewPager(){
        MaterialViewPager mViewPager = getView().findViewById(R.id.materialViewPager);
        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                ListFragment listFragment = ListFragment.newInstance();
                listFragment.setCardType(cardTypes.get(position));
                return listFragment;
            }

            @Override
            public int getCount() {
                return cardTypes.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return cardTypes.get(position).getTitle();
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                currentCardType = cardTypes.get(page);
                return HeaderDesign.fromColorResAndUrl(
                        cardTypes.get(page).getDefaultColor(),
                        cardTypes.get(page).getImageUrl());
            }
        });

        Toolbar toolbar = mViewPager.getToolbar();
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
    }

    private void deleteCard(){
        DatabaseHandler handler = new DatabaseHandler(getActivity().getApplicationContext());
        handler.open();
        handler.removeCard(currentCardType.getId());
        handler.close();
        Intent intent = getActivity().getIntent();
        getActivity().finish();
        startActivity(intent);
    }

    @Override
    public void onTaskComplete(List<CardType> cardTypes) {
        if(cardTypes != null && !cardTypes.isEmpty()){
            this.cardTypes = cardTypes;
            updateDB();
            setupViewPager();
        }
        else{
            this.cardTypes = localCardTypes;
            setupViewPager();
        }
    }

    private void updateDB(){
        DatabaseHandler handler = new DatabaseHandler(getActivity().getApplicationContext());
        handler.open();
        for(int i = 0; i < cardTypes.size(); i++){
            if(!cardTypes.get(i).getStatus().equals(localCardTypes.get(i).getStatus())){
                handler.updateCardStatus(cardTypes.get(i).getId(),cardTypes.get(i).getStatus());
            }
        }
        handler.close();
    }

    private static class CardRunner extends AsyncTask<Void, Void, List<CardType>> {

        private CardTaskCompleted callBack;
        private SubmissionService submissionService;
        private List<CardType> cardTypes;
        List<IdentityType> identityTypes;
        private IdentityTypeService identityTypeService;

        CardRunner(CardTaskCompleted callBack, IdentityTypeService identityTypeService, SubmissionService submissionService,List<CardType> cardTypes){
            this.callBack = callBack;
            this.identityTypeService = identityTypeService;
            this.submissionService = submissionService;
            this.cardTypes = cardTypes;
        }

        @Override
        protected List<CardType> doInBackground(Void... voids) {
            identityTypes = identityTypeService.getIdentityTypes();
            if(identityTypes != null){
                for(CardType cardType:cardTypes){
                    if(cardType.getSubmissionId() != null && !Objects.equals(cardType.getSubmissionId(), CardStatus.NOT_VERIFIED.toString())){
                        Submission submission = submissionService.getSubmission(cardType.getSubmissionId());
                        if (submission != null) {
                            cardType.setStatus(submission.getStatus());
                        }
                        IdentityType identityType = getIdentityById(cardType.getCardId());
                        if(identityType == null) {
                            cardType.setStatus(CardStatus.DELETED.toString());
                        }
                    }
                }
                return cardTypes;
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<CardType> result) {
            callBack.onTaskComplete(result);
        }

        @Override
        protected void onPreExecute() {
        }

        private IdentityType getIdentityById(String id){
            for(IdentityType identityType: identityTypes){
                if(identityType.getId().equals(id))
                    return identityType;
            }
            return null;
        }

    }
}
