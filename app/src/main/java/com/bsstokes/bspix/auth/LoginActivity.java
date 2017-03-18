package com.bsstokes.bspix.auth;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bsstokes.bspix.BsPixApplication;
import com.bsstokes.bspix.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.HttpUrl;

public class LoginActivity extends AppCompatActivity implements LoginController.View {

    @BindView(R.id.web_view) WebView webView;
    @Inject Account account;
    private LoginController loginController;

    @NonNull
    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        BsPixApplication.getBsPixApplication(this).getAppComponent().inject(this);

        loginController = new LoginController(this, account);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                loginController.onLoadPage(url);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginController.start();
    }

    @Override
    public void loadUrl(@NonNull HttpUrl authorizeUrl) {
        webView.loadUrl(authorizeUrl.toString());
    }
}
