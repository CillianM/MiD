package ie.mid.fragments;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import ie.mid.R;
import ie.mid.RequestListActivity;
import ie.mid.SubmissionListActivity;
import ie.mid.view.RoundedImageView;

public class RequestsFragment extends Fragment {
    String userId;

    public static RequestsFragment newInstance() {
        return new RequestsFragment();
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
        return inflater.inflate(R.layout.fragment_requests, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RoundedImageView submissionImage = view.findViewById(R.id.submission_image);
        submissionImage.setColorFilter(R.color.colorPrimary, PorterDuff.Mode.MULTIPLY);
        RoundedImageView requestImage = view.findViewById(R.id.request_image);
        requestImage.setColorFilter(R.color.colorPrimary, PorterDuff.Mode.MULTIPLY);

        RelativeLayout submissionText = view.findViewById(R.id.submission_layout);
        submissionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSubmissionList();
            }
        });
        RelativeLayout requestText = view.findViewById(R.id.request_layout);
        requestText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoRequestList();
            }
        });

    }

    private void gotoSubmissionList(){
        Intent intent = new Intent(getActivity(), SubmissionListActivity.class);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }

    private void gotoRequestList(){
        Intent intent = new Intent(getActivity(), RequestListActivity.class);
        intent.putExtra("userId",userId);
        startActivity(intent);
    }
}

