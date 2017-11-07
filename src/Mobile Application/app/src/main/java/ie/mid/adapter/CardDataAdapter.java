package ie.mid.adapter;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ie.mid.R;
import ie.mid.enums.CardStatus;
import ie.mid.model.CardType;


public class CardDataAdapter extends RecyclerView.Adapter<CardDataAdapter.ViewHolder> {

    private CardType contents;
    private Context context;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CELL = 1;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView dataText;
        TextView descriptionText;
        ImageView dataIcon;

        ViewHolder(View view) {
            super(view);
            dataText = (TextView) view.findViewById(R.id.text_area);
            descriptionText = (TextView) view.findViewById(R.id.desciption_text);
            dataIcon = (ImageView) view.findViewById(R.id.dataImage);
        }
    }

    public CardDataAdapter(Context context,CardType contents) {
        this.context = context;
        this.contents = contents;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return contents.getDataList().size() + 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big,parent, false);
                return new ViewHolder(view);
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small,parent, false);
                return new ViewHolder(view);
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String cardData;
        if (position == 1) {
            cardData = contents.getStatus();
            holder.dataText.setText(cardData);
            Drawable drawable = context.getResources().getDrawable(R.drawable.ic_assignment_late_black_24dp);
            if (contents.getStatus().equals(CardStatus.ACCEPTED.toString())) {
                drawable = context.getResources().getDrawable(R.drawable.ic_assignment_turned_in_black_24dp);
            } else if (contents.getStatus().equals(CardStatus.PENDING.toString())) {
                drawable = context.getResources().getDrawable(R.drawable.ic_assignment_return_black_24dp);
            }
            holder.dataIcon.setImageDrawable(drawable);
            return;
        }
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                cardData = contents.getDescription();
                holder.descriptionText.setText(cardData);
                break;
            case TYPE_CELL:
                cardData = contents.getDataList().get(position - 2).getData();
                holder.dataText.setText(cardData);
                Drawable drawable = context.getResources().getDrawable(contents.getDataList().get(position - 2).getDataIcon());
                holder.dataIcon.setImageDrawable(drawable);
                break;
        }
    }
}