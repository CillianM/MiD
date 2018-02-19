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
import ie.mid.model.ViewableRequest;
import ie.mid.pojo.Request;
import ie.mid.pojo.Submission;
import ie.mid.util.TimeUtil;

public class SubmissionListAdapter  extends BaseAdapter {

    private Context context;
    private List<Submission> submissions ;

    public SubmissionListAdapter(Context context,List<Submission> submissions) {
        this.context = context;
        this.submissions = submissions;
    }

    @Override
    public int getCount() {
        return submissions.size();
    }

    @Override
    public Object getItem(int position) {
        return submissions.get(position);
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
            view = inflater.inflate(R.layout.request_item_card, null);
        } else {
            view = convertView;
        }

        TextView titleView = view.findViewById(R.id.text_area);
        TextView subtitleView = view.findViewById(R.id.sub_text_area);
        TextView dateView = view.findViewById(R.id.date_area);
        ImageView iconView = view.findViewById(R.id.dataImage);

        String title = "Submission to " + cutName(submissions.get(position).getPartyName());
        titleView.setText(title);
        subtitleView.setText(submissions.get(position).getStatus());
        dateView.setText(TimeUtil.getTimeSince(submissions.get(position).getDate()));
        Drawable drawable = context.getResources().getDrawable(R.drawable.ic_assignment_late_black_24dp);
        if (submissions.get(position).getStatus().equals(CardStatus.ACCEPTED.toString())) {
            drawable = context.getResources().getDrawable(R.drawable.ic_assignment_turned_in_black_24dp);
        } else if (submissions.get(position).getStatus().equals(CardStatus.PENDING.toString())) {
            drawable = context.getResources().getDrawable(R.drawable.ic_assignment_return_black_24dp);
        }
        iconView.setImageDrawable(drawable);


        return view;
    }

    private String cutName(String name){
        if (name.length() > 10) return name.substring(0,10) + "..";
        else return name;
    }
}
