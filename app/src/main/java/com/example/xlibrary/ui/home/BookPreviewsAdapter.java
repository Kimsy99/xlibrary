package com.example.xlibrary.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xlibrary.R;
import com.example.xlibrary.model.BookPreviewModel;

import java.util.ArrayList;
import java.util.List;

public class BookPreviewsAdapter extends RecyclerView.Adapter<BookPreviewsAdapter.BookPreviewCardHolder> {
    private ArrayList<BookPreviewModel> bookPreviewCardList;
    private OnCardListener onCardListener;

    public BookPreviewsAdapter(ArrayList<BookPreviewModel> bookPreviewCardList, OnCardListener onCardListener){
        this.bookPreviewCardList = bookPreviewCardList;
        this.onCardListener = onCardListener;
    }

    @NonNull
    @Override
    public BookPreviewsAdapter.BookPreviewCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_book_preview, parent, false);
        return new BookPreviewCardHolder(view, onCardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull  BookPreviewsAdapter.BookPreviewCardHolder holder, int position) {
        final BookPreviewModel card = bookPreviewCardList.get(position);
        System.out.println("card: " + card);
        holder.title.setText(card.title);
        holder.category.setText(card.category);
    }

    @Override
    public int getItemCount() {
        return bookPreviewCardList.size();
    }
    static class BookPreviewCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView title, category;
        public ImageView image;
        private LinearLayout announcementCardItemLayout;
        OnCardListener onCardListener;
        private boolean isAnnouncement;

        public BookPreviewCardHolder(@NonNull View itemView, OnCardListener onCardListener)
        {
            super(itemView);
            title = itemView.findViewById(R.id.book_preview_title);
            category = itemView.findViewById(R.id.book_preview_category);
//            image = itemView.findViewById(R.id.card_book_preview_image);
            announcementCardItemLayout = itemView.findViewById(R.id.book_card_preview_view);
            this.onCardListener = onCardListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
//            onCardListener.onCardClick(getAbsoluteAdapterPosition(), isAnnouncement);
        }
    }
}
interface OnCardListener
{
    void onCardClick(int position, boolean isAnnouncement);
}
