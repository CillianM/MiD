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
import ie.mid.interfaces.ItemClickListener;
import ie.mid.model.CardType;


public class CardDataAdapter extends RecyclerView.Adapter<CardDataAdapter.ViewHolder> {

    private CardType contents;
    private Context context;
    private ItemClickListener listener;

    private static final int TYPE_CELL = 1;
    private static final int TYPE_HEAD = 0;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView dataText;
        ImageView dataIcon;

        ViewHolder(View view) {
            super(view);
            dataText = view.findViewById(R.id.text_area);
            dataIcon = view.findViewById(R.id.dataImage);
        }
    }

    public CardDataAdapter(Context context,CardType contents, ItemClickListener listener) {
        this.context = context;
        this.contents = contents;
        this.listener = listener;
    }

    @Override
    public CardDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card_small, parent, false);
        final ViewHolder mViewHolder = new ViewHolder(mView);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getLayoutPosition());
            }
        });
        return mViewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){ return TYPE_HEAD;}
        return TYPE_CELL;
    }

    @Override
    public int getItemCount() {
        return contents.getDataList().size() + 1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String cardData;
        if (position == 0) {
            cardData = CardStatus.enumToString(contents.getStatus());
            holder.dataText.setText(cardData);
            Drawable drawable = context.getResources().getDrawable(R.drawable.ic_assignment_late_black_24dp);
            if (contents.getStatus().equals(CardStatus.ACCEPTED.toString())) {
                drawable = context.getResources().getDrawable(R.drawable.ic_assignment_turned_in_black_24dp);
            } else if (contents.getStatus().equals(CardStatus.PENDING.toString())) {
                drawable = context.getResources().getDrawable(R.drawable.ic_assignment_return_black_24dp);
            } else if (contents.getStatus().equals(CardStatus.DELETED.toString())) {
                drawable = context.getResources().getDrawable(R.drawable.ic_error_black_24dp);
            }
            holder.dataIcon.setImageDrawable(drawable);
        } else {
            cardData = contents.getDataList().get(position - 1).getFieldEntry();
            holder.dataText.setText(cardData);
            Drawable drawable = context.getResources().getDrawable(contents.getDataList().get(position - 1).getDataIcon());
            holder.dataIcon.setImageDrawable(drawable);
        }
    }
}