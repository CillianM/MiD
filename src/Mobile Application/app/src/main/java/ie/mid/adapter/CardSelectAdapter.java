package ie.mid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ie.mid.R;
import ie.mid.model.AvailableCard;
import jp.wasabeef.picasso.transformations.BlurTransformation;

/**
 * Created by Cillian on 27/10/2017.
 */

public class CardSelectAdapter extends BaseAdapter{

    private Context context;
    private final List<AvailableCard> gridValues;

    public CardSelectAdapter(Context context, List<AvailableCard> gridValues) {

        this.context = context;
        this.gridValues = gridValues;
    }

    @Override
    public int getCount() {

        // Number of times getView method call depends upon gridValues.length
        return gridValues.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null) {
            gridView = new View(context);
            gridView = inflater.inflate( R.layout.card_grid_item , null);
            TextView textView = (TextView) gridView
                    .findViewById(R.id.item_title);
            textView.setText(gridValues.get(position).getTitle());
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.icon);
            BlurTransformation blur = new BlurTransformation(context,10);
            Picasso.with(context).load(gridValues.get(position).getIconImgUrl()).into(imageView);
        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }
}
