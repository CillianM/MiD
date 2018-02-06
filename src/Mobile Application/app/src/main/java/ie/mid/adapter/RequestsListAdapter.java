package ie.mid.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ie.mid.R;
import ie.mid.enums.CardStatus;
import ie.mid.pojo.Request;
import ie.mid.pojo.Submission;

/**
 * Created by Cillian on 02/02/2018.
 */

public class RequestsListAdapter  extends BaseAdapter {

    Context context;
    List<Submission> submissions ;
    List<Request> requests ;
    int code;

    public RequestsListAdapter(Context context) {
        this.context = context;
    }

    public void setSubmissions(List<Submission> submissions) {
        this.submissions = submissions;
        this.code = 0;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
        this.code = 1;
    }

    @Override
    public int getCount() {
        if(code == 0) return submissions.size();
        else return requests.size();
    }

    @Override
    public Object getItem(int position) {
        if(code ==0)return submissions.get(position);
        else return requests.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.setting_item_card_small, null);
        } else {
            view = convertView;
        }

        TextView titleView = view.findViewById(R.id.text_area);
        TextView subtitleView = view.findViewById(R.id.sub_text_area);
        ImageView iconView = view.findViewById(R.id.dataImage);

        if(code == 0) {

            titleView.setText(submissions.get(position).getStatus());
            subtitleView.setText(submissions.get(position).getDate());
            Drawable drawable = context.getResources().getDrawable(R.drawable.ic_assignment_late_black_24dp);
            if (submissions.get(position).getStatus().equals(CardStatus.ACCEPTED.toString())) {
                drawable = context.getResources().getDrawable(R.drawable.ic_assignment_turned_in_black_24dp);
            } else if (submissions.get(position).getStatus().equals(CardStatus.PENDING.toString())) {
                drawable = context.getResources().getDrawable(R.drawable.ic_assignment_return_black_24dp);
            }
            iconView.setImageDrawable(drawable);
        }
        else{
            titleView.setText(requests.get(position).getStatus());
            subtitleView.setText(requests.get(position).getSender());
        }

        return view;
    }
}
