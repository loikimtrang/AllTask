package com.example.roomdb;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdb.adapter.MarkerAdapter;
import com.example.roomdb.model.Marker;
import com.example.roomdb.viewModel.MarkerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;

        EditText edtNameList = dialog.findViewById(R.id.edtNameList);
        Button btnCreate = dialog.findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(v -> {
            String name = edtNameList.getText().toString().trim();
            if (!name.isEmpty()) {
                Marker newMarker = new Marker(name);
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