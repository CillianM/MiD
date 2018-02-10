package ie.mid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import ie.mid.CardCreateActivity;
import ie.mid.CardSubmissionActivity;
import ie.mid.R;
import ie.mid.adapter.CardDataAdapter;
import ie.mid.enums.CardStatus;
import ie.mid.interfaces.ItemClickListener;
import ie.mid.model.CardType;

public class ListFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CardType cardType;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    public void setCardType(CardType cardType){
        this.cardType = cardType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setHasFixedSize(true);

        //Use this now
        recyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        recyclerView.setAdapter(new CardDataAdapter(getContext(),cardType, new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if(position == 0 && Objects.equals(cardType.getStatus(), CardStatus.NOT_VERIFIED.toString())) {
                    Intent intent = new Intent(getActivity(), CardSubmissionActivity.class);
                    intent.putExtra("userId",cardType.getOwnerId());
                    intent.putExtra("cardId",cardType.getId());
                    startActivity(intent);
                }
                if(position == 0 && Objects.equals(cardType.getStatus(), CardStatus.DELETED.toString())) {
                    Toast.makeText(getActivity().getApplicationContext(), cardType.getTitle() + " has been upgraded/deleted from the server", Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }
}
