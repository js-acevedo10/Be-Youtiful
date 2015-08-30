package com.juansantiagoacevedo.apps.be_youtiful;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;
import com.vlonjatg.progressactivity.ProgressActivity;


public class FacebookFragment extends Fragment implements View.OnClickListener{
    public final static String FACEBOOK_URL = "https://www.facebook.com/BeYoutifulSalon22/";
    public View rootView;
    public WebView facebookWebView;
    public ProgressActivity progressActivity;

    public FacebookFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_facebook, container, false);
        progressActivity = (ProgressActivity) rootView.findViewById(R.id.FacebookProgressFragment);
        progressActivity.showLoading();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareLayout();
            }
        }, 400);
        return rootView;
    }

    public void prepareLayout() {

        facebookWebView = (WebView) rootView.findViewById(R.id.facebook_webview);
        ConnectivityManager conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        final Drawable emptyDrawable = new IconDrawable(rootView.getContext(), Iconify.IconValue.zmdi_wifi_off)
                .colorRes(android.R.color.white);
        if(i == null || !i.isConnected() || !i.isAvailable()) {
            progressActivity.showError(emptyDrawable, "Error", "Al parecer hubo un error con tu conexión a Internet.", "Intentar de Nuevo", FacebookFragment.this);
        } else {
            facebookWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    progressActivity.showContent();
                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    progressActivity.showError(emptyDrawable, "Error", "Al parecer hubo un error con tu conexión a Internet.", "Intentar de Nuevo", FacebookFragment.this);
                }

                @Override
                public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                    progressActivity.showError(emptyDrawable, "Error", "Al parecer hubo un error con tu conexión a Internet.", "Intentar de Nuevo", FacebookFragment.this);
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    progressActivity.showError(emptyDrawable, "Error", "Al parecer hubo un error con tu conexión a Internet.", "Intentar de Nuevo", FacebookFragment.this);
                }
            });
            facebookWebView.getSettings().setJavaScriptEnabled(true);
            facebookWebView.loadUrl(FACEBOOK_URL);
        }
    }

    public boolean canDoBack() {
        return facebookWebView.canGoBack();
    }

    public void doBack() {
        facebookWebView.goBack();
    }

    @Override
    public void onClick(View view) {
        progressActivity.showLoading();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareLayout();
            }
        }, 400);
    }
}