package ie.mid.fragments;

import android.content.Intent;
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

import ie.mid.CardCreateActivity;
import ie.mid.ProfileSelectionActivity;
import ie.mid.R;
import ie.mid.handler.DatabaseHandler;
import ie.mid.model.CardType;

/**
 * Created by Cillian on 21/10/2017.
 */

public class CardFragment extends Fragment  {

    private MaterialViewPager mViewPager;
    private List<CardType> cardTypes;
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
        cardTypes = getCards();
        mViewPager = view.findViewById(R.id.materialViewPager);
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

    public List<CardType> getCards(){
        DatabaseHandler handler = new DatabaseHandler(getActivity().getApplicationContext());
        handler.open();
        List<CardType> listOfCards = handler.getUserCards(userId);
        handler.close();
        return listOfCards;
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
}
