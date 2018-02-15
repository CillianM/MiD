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

/**
 * Created by Cillian on 02/02/2018.
 */

public class RequestsListAdapter  extends BaseAdapter {

    private Context context;
    private List<ViewableRequest> requests ;
    private String serverId;

    public RequestsListAdapter(Context context,List<ViewableRequest> requests, String serverId) {
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
            view = inflater.inflate(R.layout.setting_item_card_small, null);
        } else {
            view = convertView;
        }

        TextView titleView = view.findViewById(R.id.text_area);
        TextView subtitleView = view.findViewById(R.id.sub_text_area);
        ImageView iconView = view.findViewById(R.id.dataImage);

        Drawable drawable;
        String title;
        if (requests.get(position).getSender().equals(serverId)) {
            drawable = context.getResources().getDrawable(R.drawable.ic_arrow_forward_black_24dp);
            title = "Request to " + requests.get(position).getSenderReceiverName();
        } else {
            drawable = context.getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            title = "Request from " + requests.get(position).getSenderReceiverName();
        }
        iconView.setImageDrawable(drawable);
        titleView.setText(title);
        subtitleView.setText(requests.get(position).getStatus());

        return view;
    }
}
