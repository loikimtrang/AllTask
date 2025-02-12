package com.example.roomdb;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdb.adapter.MarkerAdapter;
import com.example.roomdb.databinding.ActivityMainBinding;
import com.example.roomdb.databinding.LayoutDialogMarkerBinding;
import com.example.roomdb.databinding.LayoutFabCreateMarkerBinding;
import com.example.roomdb.model.Marker;
import com.example.roomdb.viewModel.MarkerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mActivityMainBinding;

    private FloatingActionButton fab;
    private MarkerAdapter markerAdapter;
    private MarkerViewModel markerViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mActivityMainBinding.getRoot());

        EdgeToEdge.enable(this);

        fab = findViewById(R.id.fabAddList);

        updateMarkerList();

        fab.setOnClickListener(v -> openListMarkerDialog());
    }

    private void updateMarkerList() {

        markerAdapter = new MarkerAdapter(this);
        mActivityMainBinding.rcvListMarker.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mActivityMainBinding.rcvListMarker.setAdapter(markerAdapter);

        markerViewModel = new ViewModelProvider(this).get(MarkerViewModel.class);
        markerViewModel.getMarkerList().observe(this, markers -> markerAdapter.setData(markers));

    }
    private void openListMarkerDialog() {
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