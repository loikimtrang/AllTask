package com.example.listmarkerproject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listmarkerproject.sharePreferenes.DataLocalManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAddList;
    private RecyclerView rcvListMarker;
    private MarkerAdapter markerAdapter;
    private MarkerViewModel markerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DataLocalManager.init(this);

        fabAddList = findViewById(R.id.fabAddList);
        updateMarkerList();
        fabAddList.setOnClickListener(v -> openListMarkerDialog());
    }

    private void updateMarkerList() {
        rcvListMarker = findViewById(R.id.rcvListMarker);

        markerAdapter = new MarkerAdapter(this);
        rcvListMarker.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        rcvListMarker.setAdapter(markerAdapter);

        markerViewModel = new ViewModelProvider(this).get(MarkerViewModel.class);
        markerViewModel.getMarkerList().observe(this, markers -> markerAdapter.setData(markers));

    }
    private void openListMarkerDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_marker);

        Window window = dialog.getWindow();
        if (window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;

        EditText edtNameList = dialog.findViewById(R.id.edtNameList);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnCreate = dialog.findViewById(R.id.btnCreate);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnCreate.setOnClickListener(v -> {
            String name = edtNameList.getText().toString().trim();
            if (!name.isEmpty()) {
                int id = markerViewModel.getMarkerList().getValue() == null ? 1 : markerViewModel.getMarkerList().getValue().size() + 1;
                Marker newMarker = new Marker(String.valueOf(id), name);
                markerViewModel.addMarker(newMarker);

                Toast.makeText(this, "Đã thêm: " + name, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Vui lòng nhập tên danh sách!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}

