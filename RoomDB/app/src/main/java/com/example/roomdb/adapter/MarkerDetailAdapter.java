package com.example.roomdb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdb.R;
import com.example.roomdb.model.MarkerDetail;

import java.util.ArrayList;
import java.util.List;

public class MarkerDetailAdapter extends RecyclerView.Adapter<MarkerDetailAdapter.MarkerDetailViewHolder> {

    private Context context;
    private List<MarkerDetail> markerDetailList;

    private int idMarker;

    public MarkerDetailAdapter(Context context, int idMarker) {
        this.context = context;
        this.idMarker = idMarker;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marker_detail, parent, false);
        return new MarkerDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarkerDetailViewHolder holder, int position) {
        MarkerDetail markerDetail = markerDetailList.get(position);
        holder.tvNameMarkerDetail.setText(markerDetail.getName());
    }

    @Override
    public int getItemCount() {
        return markerDetailList != null ? markerDetailList.size() : 0;
    }

    public class MarkerDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNameMarkerDetail;
        public MarkerDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameMarkerDetail = itemView.findViewById(R.id.tvNameMarkerDetail);
        }
    }
}
