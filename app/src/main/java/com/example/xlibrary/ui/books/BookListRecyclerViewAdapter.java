package com.example.xlibrary.ui.books;

import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xlibrary.R;
import com.example.xlibrary.databinding.FragmentBookListBinding;
import com.example.xlibrary.ui.books.placeholder.PlaceholderContent.PlaceholderItem;
//import com.example.xlibrary.ui.books.databinding.FragmentBookListBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class BookListRecyclerViewAdapter extends RecyclerView.Adapter<BookListRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;
    private final NavController navController;
    public BookListRecyclerViewAdapter(List<PlaceholderItem> items, NavController navController) {
        mValues = items;
        this.navController = navController;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder( FragmentBookListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
        holder.mBookCardView.setOnClickListener(click -> {
           System.out.println("CLICKKKKKKK");
            navController.navigate(R.id.action_navigation_library_to_navigation_book_detail);
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView mBookCardView;
        public final TextView mIdView;
        public final TextView mContentView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentBookListBinding binding) {
            super(binding.getRoot());
//            this.mBookCardView = mBookCardView;
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            mBookCardView = binding.bookCardView;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}