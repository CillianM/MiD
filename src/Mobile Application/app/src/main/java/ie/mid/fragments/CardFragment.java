package ie.mid.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AlertDialog;
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

import ie.mid.CardCreateActivity;
import ie.mid.ProfileSelectionActivity;
import ie.mid.R;
import ie.mid.async.CardGetter;
import ie.mid.backend.IdentityTypeService;
import ie.mid.backend.SubmissionService;
import ie.mid.handler.DatabaseHandler;
import ie.mid.interfaces.CardTaskCompleted;
import ie.mid.model.CardType;
import ie.mid.model.Profile;
import ie.mid.util.InternetUtil;

/**
 * Created by Cillian on 21/10/2017.
 */

public class CardFragment extends Fragment implements CardTaskCompleted {

    private List<CardType> cardTypes;
    private List<CardType> localCardTypes;
    private CardType currentCardType;
    String userId;
    Profile profile;

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
        DatabaseHandler handler = new DatabaseHandler(getActivity().getApplicationContext());
        handler.open();
        profile = handler.getProfile(userId);
        handler.close();
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
            case R.id.delete_card:
                confirmDelete();
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
        showLoading();
        if(InternetUtil.isNetworkAvailable(getActivity().getApplicationContext())) {
            new CardGetter(
                    getActivity().getApplicationContext(),
                    this,
                    new IdentityTypeService(getActivity().getApplicationContext()),
                    new SubmissionService(getActivity().getApplicationContext()),
                    profile,
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

    private void confirmDelete(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        deleteCard();

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.AlertDialog);
        builder.setMessage("Do you want to delete \"" + currentCardType.getTitle() + "\" from your cards?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

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

    private void showLoading(){
        getView().findViewById(R.id.materialViewPager).setVisibility(View.GONE);
        getView().findViewById(R.id.card_progress).setVisibility(View.VISIBLE);

    }

    private void hideLoading(){
        getView().findViewById(R.id.materialViewPager).setVisibility(View.VISIBLE);
        getView().findViewById(R.id.card_progress).setVisibility(View.GONE);
    }

    @Override
    public void onTaskComplete(List<CardType> cardTypes) {
        hideLoading();
        if(cardTypes != null && !cardTypes.isEmpty()){
            this.cardTypes = cardTypes;
            updateDB();
            setupViewPager();
        }
        else{
            Toast.makeText(getActivity().getApplicationContext(),"Could not contact server Using Local Cards",Toast.LENGTH_LONG).show();
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
}
