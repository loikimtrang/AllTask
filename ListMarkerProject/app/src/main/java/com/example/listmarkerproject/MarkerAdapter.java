package com.example.listmarkerproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MarkerAdapter extends RecyclerView.Adapter<MarkerAdapter.MarkerViewHolder> {

    private Context context;

    private List<Marker> markers;

    public MarkerAdapter(Context context) {
        this.context = context;
    }

    public void setData (List<Marker> list) {
        this.markers = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MarkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marker, parent, false);
        return new MarkerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarkerViewHolder holder, int position) {
        Marker marker = markers.get(position);
        if (marker == null) return;

        holder.tvCount.setText(String.valueOf(position + 1));
        holder.tvNameMarker.setText(marker.getName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("marker", marker);
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        if (markers != null) return markers.size();
        return 0;
    }

    public class MarkerViewHolder extends RecyclerView.ViewHolder {

        private TextView tvNameMarker, tvCount;
        public MarkerViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameMarker = itemView.findViewById(R.id.tvNameMarker);
            tvCount = itemView.findViewById(R.id.tvCount);

        }
    }
}
