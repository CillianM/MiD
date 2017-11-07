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

import java.util.ArrayList;
import java.util.List;

import ie.mid.CardSelectActivity;
import ie.mid.ProfileSelectionActivity;
import ie.mid.R;
import ie.mid.enums.CardStatus;
import ie.mid.enums.DataType;
import ie.mid.model.CardData;
import ie.mid.model.CardType;

/**
 * Created by Cillian on 21/10/2017.
 */

public class CardFragment extends Fragment  {

    private MaterialViewPager mViewPager;
    private List<CardType> cardTypes;
    private CardType currentCardType;

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
            case R.id.new_card:
                intent = new Intent(getActivity(), CardSelectActivity.class);
                startActivity(intent);
                break;
            case R.id.edit_card:
                Toast.makeText(getActivity(), currentCardType.getTitle(), Toast.LENGTH_SHORT)
                        .show();
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
        CardData data1 = new CardData("Test Data 1", DataType.PRIMARY.toString());
        data1.setDataIcon(R.drawable.ic_vpn_key_black_48dp);
        CardData data2 = new CardData("Test Data 2", DataType.SECONDARY.toString());
        data2.setDataIcon(R.drawable.ic_person_black_48dp);
        CardData data3 = new CardData("Test Data 3", DataType.TERTIARY.toString());
        data3.setDataIcon(R.drawable.ic_cake_black_48dp);
        CardData data4 = new CardData("Test Data 4", DataType.TERTIARY.toString());
        data4.setDataIcon(R.drawable.ic_home_black_48dp);
        CardData data5 = new CardData("Test Data 5", DataType.TERTIARY.toString());
        data5.setDataIcon(R.drawable.ic_home_black_48dp);
        ArrayList<CardData> listOfData = new ArrayList<>();
        listOfData.add(data1);
        listOfData.add(data2);
        listOfData.add(data3);
        listOfData.add(data4);
        listOfData.add(data5);
        CardType a = new CardType();
        a.setTitle("Drivers Licence");
        a.setDescription("This is your drivers license. It should be carried with you at all times when on the road." +
                "\nFor more information on your card or any other queries please visit www.ndls.ie");
        a.setDefaultColor(R.color.blue);
        a.setImageUrl("http://student.computing.dcu.ie/~mcneilc2/ndls.jpg");
        a.setDataList(listOfData);
        a.setStatus(CardStatus.ACCEPTED.toString());
        CardType b = new CardType();
        b.setTitle("Passport");
        b.setDescription("This is a Passport");
        b.setDefaultColor(R.color.red);
        b.setImageUrl("http://student.computing.dcu.ie/~mcneilc2/passport.jpg");
        b.setDataList(listOfData);
        b.setStatus(CardStatus.PENDING.toString());
        CardType c = new CardType();
        c.setTitle("Public Services Card");
        c.setDescription("This is a Public Services Card");
        c.setDefaultColor(R.color.cyan);
        c.setImageUrl("http://student.computing.dcu.ie/~mcneilc2/psc.png");
        c.setStatus(CardStatus.REJECTED.toString());
        c.setDataList(listOfData);
        ArrayList<CardType> listOfCards = new ArrayList<>();
        listOfCards.add(a);
        listOfCards.add(b);
        listOfCards.add(c);
        return listOfCards;
    }
}
