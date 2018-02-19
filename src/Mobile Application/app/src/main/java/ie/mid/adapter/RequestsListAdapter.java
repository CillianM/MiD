package ie.mid.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ie.mid.R;
import ie.mid.model.ViewableRequest;
import ie.mid.pojo.Request;
import ie.mid.util.TimeUtil;

/**
 * Created by Cillian on 02/02/2018.
 */

public class RequestsListAdapter  extends BaseAdapter {

    private Context context;
    private List<Request> requests ;
    private String serverId;

    public RequestsListAdapter(Context context, List<Request> requests, String serverId) {
        this.context = context;
        this.requests = requests;
        this.serverId = serverId;
    }

    @Override
    public int getCount() {
        return requests.size();
    }

    @Override
    public Object getItem(int position) {
        return requests.get(position);
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

        Drawable drawable;
        String title;
        if (requests.get(position).getSenderId().equals(serverId)) {
            drawable = context.getResources().getDrawable(R.drawable.ic_arrow_forward_black_24dp);
            title = "Request to " + cutName(requests.get(position).getRecipientName());
        } else {
            drawable = context.getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            title = "Request from " + cutName(requests.get(position).getSenderName());
        }
        iconView.setImageDrawable(drawable);
        titleView.setText(title);
        subtitleView.setText(requests.get(position).getStatus());
        dateView.setText(TimeUtil.getTimeSince(requests.get(position).getCreatedAt()));

        return view;
    }

    private String cutName(String name){
        if (name.length() > 10) return name.substring(0,10) + "..";
        else return name;
    }
}
