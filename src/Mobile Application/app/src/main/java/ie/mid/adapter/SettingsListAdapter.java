package ie.mid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ie.mid.R;
import ie.mid.model.SettingItem;

public class SettingsListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<SettingItem> settingItems;

    public SettingsListAdapter(Context context, ArrayList<SettingItem> settingItems) {
        mContext = context;
        this.settingItems = settingItems;
    }

    @Override
    public int getCount() {
        return settingItems.size();
    }

    @Override
    public Object getItem(int position) {
        return settingItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.setting_item_card_small, null);
        } else {
            view = convertView;
        }

        TextView titleView = view.findViewById(R.id.text_area);
        TextView subtitleView = view.findViewById(R.id.sub_text_area);
        ImageView iconView = view.findViewById(R.id.dataImage);

        titleView.setText(settingItems.get(position).getTitle());
        subtitleView.setText(settingItems.get(position).getSubtitle());
        iconView.setImageResource(settingItems.get(position).getIcon());

        return view;
    }
}
