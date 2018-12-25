package com.example.kirill.cookbook;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CaptionedImagesAdapter extends
        RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    private ArrayList<String> captions;
    private ArrayList<Integer> imageIds;
    private Listener listener;

    interface Listener {
        public void onClick(int position);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, parent, false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;

        ImageView imageView = (ImageView) cardView.findViewById(R.id.image_info);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imageIds.get(position));
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions.get(position));

        TextView textView = (TextView) cardView.findViewById(R.id.name_info);
        textView.setText(captions.get(position));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return captions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

    }

    CaptionedImagesAdapter(ArrayList<String> captions, ArrayList<Integer> imageIds) {
        this.captions = captions;
        this.imageIds = imageIds;
    }

    public void setSearchOperation(ArrayList<String> newCaptions, ArrayList<Integer> newImageIds) {
        captions = new ArrayList<>();
        imageIds = new ArrayList<>();

        captions.addAll(newCaptions);
        imageIds.addAll(newImageIds);

        notifyDataSetChanged();
    }
}
