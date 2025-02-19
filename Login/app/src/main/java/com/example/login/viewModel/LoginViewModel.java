package com.example.login.viewModel;

import android.content.Context;
import android.widget.Toast;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    public ObservableField<String> shopId = new ObservableField<>();
    public ObservableField<String> username = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();

    private final Context context;

    public LoginViewModel(Context context) {
        this.context = context;
    }

    public void onLoginClick() {
        if (shopId.get() == null || username.get() == null || password.get() == null) {
            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "Đăng nhập với: " + username.get(), Toast.LENGTH_SHORT).show();
            shopId.set(null);
            username.set(null);
            password.set(null);
        }
    }
}