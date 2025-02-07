package com.example.listmarkerproject;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listmarkerproject.adapter.MarkerAdapter;
import com.example.listmarkerproject.adapter.MarkerDetailAdapter;
import com.example.listmarkerproject.model.Marker;
import com.example.listmarkerproject.model.MarkerDetail;
import com.example.listmarkerproject.sharePreferenes.DataLocalManager;
import com.example.listmarkerproject.viewModel.MarkerDetailViewModel;
import com.example.listmarkerproject.viewModel.MarkerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private FloatingActionButton fabAddList;
    private RecyclerView rcvListMarkerDetail;
    private MarkerDetailAdapter markerDetailAdapter;
    private TextView tvMarkerDetail;

    private MarkerDetailViewModel markerDetailViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);

        fabAddList = findViewById(R.id.fabAddList);

        tvMarkerDetail = findViewById(R.id.tvMarkerDetail);
        Intent intent = getIntent();
        Marker marker = intent.getParcelableExtra("marker");
        tvMarkerDetail.setText(marker.getName());
        updateMarkerList();
        fabAddList.setOnClickListener(v -> openListMarkerDialog());
    }
    private void updateMarkerList() {
        Intent intent = getIntent();
        Marker marker = intent.getParcelableExtra("marker");
        rcvListMarkerDetail = findViewById(R.id.rcvListMarkerDetail);

        markerDetailAdapter = new MarkerDetailAdapter(this, marker.getId());
        rcvListMarkerDetail.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rcvListMarkerDetail.setAdapter(markerDetailAdapter);

        markerDetailViewModel = new ViewModelProvider(this).get(MarkerDetailViewModel.class);
        markerDetailViewModel.getMarkerDetailList().observe(this, markers -> markerDetailAdapter.setData(markers));
    }
    private void openListMarkerDialog() {
        Intent intent = getIntent();
        Marker marker = intent.getParcelableExtra("marker");
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_marker);

        Window window = dialog.getWindow();
        if (window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;

        EditText edtNameList = dialog.findViewById(R.id.edtNameList);
        Button btnCreate = dialog.findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(v -> {
            String name = edtNameList.getText().toString().trim();
            if (!name.isEmpty()) {
                MarkerDetail newMarkerDetail = new MarkerDetail(marker.getId(), name);
                markerDetailViewModel.addMarkerDetail(newMarkerDetail);
                Toast.makeText(this, "Đã thêm: " + name, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Vui lòng nhập tên danh sách!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}