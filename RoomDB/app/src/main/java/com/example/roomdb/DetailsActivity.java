package com.example.roomdb;

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

import com.example.roomdb.adapter.MarkerDetailAdapter;
import com.example.roomdb.databinding.ActivityDetailsBinding;
import com.example.roomdb.databinding.LayoutDialogMarkerBinding;
import com.example.roomdb.model.MarkerDetail;
import com.example.roomdb.viewModel.MarkerDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.roomdb.model.Marker;
public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding mActivityDetailsBinding;

    private FloatingActionButton fab;
    private MarkerDetailAdapter markerDetailAdapter;

    private TextView tvMarkerDetail;

    private MarkerDetailViewModel markerDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDetailsBinding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(mActivityDetailsBinding.getRoot());
        EdgeToEdge.enable(this);

        Intent intent = getIntent();
        Marker marker = intent.getParcelableExtra("marker");
        mActivityDetailsBinding.tvMarkerDetail.setText(marker.getName());
        updateMarkerList();

        fab = findViewById(R.id.fabAddList);

        fab.setOnClickListener(v -> openListMarkerDialog());
    }
    private void updateMarkerList() {
        Intent intent = getIntent();
        Marker marker = intent.getParcelableExtra("marker");

        markerDetailAdapter = new MarkerDetailAdapter(this, marker.getId());
        mActivityDetailsBinding.rcvListMarkerDetail.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mActivityDetailsBinding.rcvListMarkerDetail.setAdapter(markerDetailAdapter);

        markerDetailViewModel = new ViewModelProvider(this).get(MarkerDetailViewModel.class);
        markerDetailViewModel.getMarkerDetailList().observe(this, markers -> markerDetailAdapter.setData(markers));
    }
    private void openListMarkerDialog() {
        Intent intent = getIntent();
        Marker marker = intent.getParcelableExtra("marker");
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutDialogMarkerBinding dialogBinding = LayoutDialogMarkerBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogBinding.getRoot());

        Window window = dialog.getWindow();
        if (window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;

        dialogBinding.btnCreate.setOnClickListener(v -> {
            String name = dialogBinding.edtNameList.getText().toString().trim();
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