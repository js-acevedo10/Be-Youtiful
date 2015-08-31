package com.juansantiagoacevedo.apps.be_youtiful;


import android.app.Fragment;
import android.app.ProgressDialog;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class InstagramFragment extends Fragment implements View.OnClickListener{

    public final static String INSTAGRAM_URL = "https://instagram.com/beyoutiful_salon/";
    public View rootView;
    public WebView instagramWebView;
    public ProgressActivity progressActivity;

    public InstagramFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_instagram, container, false);
        progressActivity = (ProgressActivity) rootView.findViewById(R.id.InstagramProgressFragment);
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

        instagramWebView = (WebView) rootView.findViewById(R.id.instagram_webview);
        ConnectivityManager conMgr = null;
        if(getActivity() != null) {
            conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        final Drawable emptyDrawable = new IconDrawable(rootView.getContext(), Iconify.IconValue.zmdi_wifi_off)
                .colorRes(android.R.color.white);
        if(i == null || !i.isConnected() || !i.isAvailable()) {
            progressActivity.showError(emptyDrawable, "Error", "Al parecer hubo un error con tu conexión a Internet.", "Intentar de Nuevo", InstagramFragment.this);
        } else {
            instagramWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    progressActivity.showContent();
                }

                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    progressActivity.showError(emptyDrawable, "Error", "Al parecer hubo un error con tu conexión a Internet.", "Intentar de Nuevo", InstagramFragment.this);
                }

                @Override
                public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                    progressActivity.showError(emptyDrawable, "Error", "Al parecer hubo un error con tu conexión a Internet.", "Intentar de Nuevo", InstagramFragment.this);
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    progressActivity.showError(emptyDrawable, "Error", "Al parecer hubo un error con tu conexión a Internet.", "Intentar de Nuevo", InstagramFragment.this);
                }
            });
            instagramWebView.getSettings().setJavaScriptEnabled(true);
            instagramWebView.loadUrl(INSTAGRAM_URL);
        }
    }

    public boolean canDoBack() {
        return instagramWebView.canGoBack();
    }

    public void doBack() {
        instagramWebView.goBack();
    }

    public void activarLoad() {
        if(!progressActivity.isError()) {
            progressActivity.showLoading();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkMe();
                }
            }, 600);
        }
    }

    public void checkMe() {
        String x = "name";
        if(x.equals("name")) x = "namee";
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
