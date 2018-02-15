package ie.mid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ie.mid.R;
import ie.mid.model.Field;

/**
 * Created by Cillian on 13/02/2018.
 */

public class IdentityRequestAdapter extends BaseAdapter {

    private Context mContext;
    private List<Field> fieldItems;
    private List<Boolean> checks;

    public IdentityRequestAdapter(Context context, List<Field> fieldItems) {
        mContext = context;
        this.fieldItems = fieldItems;
        this.checks = new ArrayList<>();
        for (int i = 0; i < this.fieldItems.size(); i++) {
            this.checks.add(false);
        }
    }

    public List<Boolean> getBooleanList(){
        return checks;
    }

    @Override
    public int getCount() {
        return fieldItems.size();
    }

    @Override
    public Object getItem(int position) {
        return fieldItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_checkbox, null);
        } else {
            view = convertView;
        }

        TextView titleView = view.findViewById(R.id.text_area);
        titleView.setText(fieldItems.get(position).getName());
        final CheckBox checkBox = view.findViewById(R.id.checkbox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                checks.set(position,checkBox.isChecked());
            }
        });

        return view;
    }
}
