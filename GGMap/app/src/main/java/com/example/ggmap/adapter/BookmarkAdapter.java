package com.example.ggmap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ggmap.databinding.ItemPlaceBinding;
import com.example.ggmap.model.Bookmark;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

    private Context mContext;
    private List<Bookmark> bookmarks;

    private IClickItemBookmark iClickItemBookmark;

    public interface IClickItemBookmark {
        void showBookmark(Bookmark bookmark);
    }

    public BookmarkAdapter (Context mContext, IClickItemBookmark iClickItemBookmark) {
        this.mContext = mContext;
        this.iClickItemBookmark = iClickItemBookmark;
    }

    public void setData(List<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        ItemPlaceBinding binding = ItemPlaceBinding.inflate(inflater, parent, false);
        return new BookmarkViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        Bookmark bookmark = bookmarks.get(position);

        holder.binding.tvName.setText(bookmark.getPlaceId());
        holder.itemView.setOnClickListener(view -> iClickItemBookmark.showBookmark(bookmark));
    }

    @Override
    public int getItemCount() {
        return (bookmarks != null) ? bookmarks.size() : 0;
    }

    public static class BookmarkViewHolder extends RecyclerView.ViewHolder {
        private ItemPlaceBinding binding;
        public BookmarkViewHolder(@NonNull ItemPlaceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
