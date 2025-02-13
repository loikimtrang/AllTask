package com.example.roomdb.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdb.DetailsActivity;
import com.example.roomdb.databinding.ItemMarkerBinding;
import com.example.roomdb.model.Marker;
import com.example.roomdb.model.MarkerDetail;

import java.util.List;

public class MarkerAdapter extends RecyclerView.Adapter<MarkerAdapter.MarkerViewHolder> {

    private Context context;
    private List<Marker> markers;

    private IClickItemMarker iClickItemMarker;
    public interface IClickItemMarker {
        void updateMarker(Marker marker);
        void deleteMarker(Marker marker);
    }

    public MarkerAdapter(Context context ,IClickItemMarker iClickItemMarker) {
        this.context = context;
        this.iClickItemMarker = iClickItemMarker;
    }

    public void setData(List<Marker> list) {
        this.markers = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MarkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemMarkerBinding binding = ItemMarkerBinding.inflate(inflater, parent, false);
        return new MarkerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MarkerViewHolder holder, int position) {
        Marker marker = markers.get(position);
        if (marker == null) return;

        holder.binding.tvCount.setText(String.valueOf(position + 1));
        holder.binding.tvNameMarker.setText(marker.getName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("marker", marker);
            context.startActivity(intent);
        });

        holder.binding.btnEditMarker.setOnClickListener(view -> iClickItemMarker.updateMarker(marker));

        holder.itemView.setOnLongClickListener(v -> {
            iClickItemMarker.deleteMarker(marker);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return (markers != null) ? markers.size() : 0;
    }

    public static class MarkerViewHolder extends RecyclerView.ViewHolder {
        private final ItemMarkerBinding binding;

        public MarkerViewHolder(@NonNull ItemMarkerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
