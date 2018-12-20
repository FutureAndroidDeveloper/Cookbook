package com.example.kirill.cookbook;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CaptionedImagesAdapter extends
        RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    private ArrayList<String> captions;
    private ArrayList<Integer> imageIds;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, parent, false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;

        ImageView imageView = (ImageView) cardView.findViewById(R.id.image_info);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imageIds.get(position));
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(captions.get(position));

        TextView textView = (TextView) cardView.findViewById(R.id.name_info);
        textView.setText(captions.get(position));
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
}
