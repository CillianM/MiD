package ie.mid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ie.mid.CardSelectActivity;
import ie.mid.CreateRequestActivity;
import ie.mid.ProfileViewActivity;
import ie.mid.R;
import ie.mid.SettingsActivity;
import ie.mid.adapter.SettingsListAdapter;
import ie.mid.model.SettingItem;

public class MoreFragment extends Fragment {

    ListView settingsList;
    ArrayList<SettingItem> settingItems = new ArrayList<>();
    String userId;

    public static MoreFragment newInstance() {
        return new MoreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userId = getArguments().getString("userId");
        return inflater.inflate(R.layout.fragment_more, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        settingsList = view.findViewById(R.id.settings_list);
        getSettingsList();
        SettingsListAdapter adapter = new SettingsListAdapter(getActivity().getApplicationContext(), settingItems);
        settingsList.setAdapter(adapter);

        // Drawer Item click listeners
        settingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(getActivity(), ProfileViewActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), CardSelectActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getActivity(), CreateRequestActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getActivity(), SettingsActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                        break;
                }
            }
        });
    }


    public void getSettingsList() {
        settingItems.add(new SettingItem("Profile", "Manage Profile Settings", R.drawable.ic_face_black_24dp));
        settingItems.add(new SettingItem("Add Card", "Add a new card", R.drawable.ic_credit_card_black_24dp));
        settingItems.add(new SettingItem("Request Information", "Request information from a user", R.drawable.ic_person_black_24dp));
        settingItems.add(new SettingItem("Settings", "Manage Application Settings", R.drawable.ic_settings_black_24dp));
    }
}
