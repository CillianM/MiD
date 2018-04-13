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
import ie.mid.enums.CardStatus;
import ie.mid.enums.DataType;
import ie.mid.model.CardField;
import ie.mid.model.SubmissionField;

public class FieldListAdapter extends BaseAdapter {

    private Context context;
    private List<SubmissionField> cardFields;

    public FieldListAdapter(Context context, List<SubmissionField> cardFields) {
        this.context = context;
        this.cardFields = cardFields;
    }

    @Override
    public int getCount() {
        return cardFields.size();
    }

    @Override
    public Object getItem(int position) {
        return cardFields.get(position);
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

        titleView.setText(cardFields.get(position).getFieldTitle());
        subtitleView.setText(cardFields.get(position).getFieldEntry());
        Drawable drawable = context.getResources().getDrawable(R.drawable.ic_person_black_48dp);
        if (cardFields.get(position).getFieldType().equals(DataType.KEY.toString())) {
            drawable = context.getResources().getDrawable(R.drawable.ic_vpn_key_black_48dp);
        } else if (cardFields.get(position).getFieldType().equals(DataType.EXPIRY.toString())) {
            drawable = context.getResources().getDrawable(R.drawable.ic_date_range_black_24dp);
        }  else if (cardFields.get(position).getFieldType().equals(DataType.ADDRESS.toString())) {
            drawable = context.getResources().getDrawable(R.drawable.ic_home_black_48dp);
        } else if (cardFields.get(position).getFieldType().equals(DataType.BIRTHDAY.toString())) {
            drawable = context.getResources().getDrawable(R.drawable.ic_cake_black_48dp);
        }
        iconView.setImageDrawable(drawable);

        return view;
    }
}
