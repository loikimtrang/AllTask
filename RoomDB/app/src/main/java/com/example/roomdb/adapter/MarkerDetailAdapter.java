package com.example.roomdb.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.roomdb.databinding.ItemMarkerDetailBinding;
import com.example.roomdb.model.Marker;
import com.example.roomdb.model.MarkerDetail;

import java.util.ArrayList;
import java.util.List;

public class MarkerDetailAdapter extends RecyclerView.Adapter<MarkerDetailAdapter.MarkerDetailViewHolder> {

    private Context context;
    private List<MarkerDetail> markerDetailList;

    private IClickItemMarkerDetail iClickItemMarkerDetail;
    public interface IClickItemMarkerDetail {
        void updateMarkerDetail(MarkerDetail markerDetail);
        void deleteMarkerDetail(MarkerDetail markerDetail);
    }

    private int idMarker;

    public MarkerDetailAdapter(Context context, int idMarker, IClickItemMarkerDetail iClickItemMarkerDetail) {
        this.context = context;
        this.idMarker = idMarker;
        this.iClickItemMarkerDetail = iClickItemMarkerDetail;
    }
    public void setData(List<MarkerDetail> listMarkerDetail) {
        List<MarkerDetail> list = new ArrayList<>();
        for (MarkerDetail detail : listMarkerDetail) {
            if (detail.getIdMarker() == idMarker) {
                list.add(detail);
            }
        }
        this.markerDetailList = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MarkerDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemMarkerDetailBinding binding = ItemMarkerDetailBinding.inflate(inflater, parent, false);
        return new MarkerDetailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MarkerDetailViewHolder holder, int position) {
        MarkerDetail markerDetail = markerDetailList.get(position);
        holder.binding.tvNameMarkerDetail.setText(markerDetail.getName());

        holder.binding.btnEditMarkerDetail.setOnClickListener(v -> { iClickItemMarkerDetail.updateMarkerDetail(markerDetail);});
        holder.itemView.setOnLongClickListener(v -> {
            iClickItemMarkerDetail.deleteMarkerDetail(markerDetail);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return markerDetailList != null ? markerDetailList.size() : 0;
    }

    public class MarkerDetailViewHolder extends RecyclerView.ViewHolder {

        private final ItemMarkerDetailBinding binding;
        public MarkerDetailViewHolder(@NonNull ItemMarkerDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
