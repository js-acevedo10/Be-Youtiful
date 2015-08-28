package com.juansantiagoacevedo.apps.be_youtiful;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class FacebookFragment extends Fragment {
    public final static String FACEBOOK_URL = "https://www.facebook.com/BeYoutifulSalon22/";
    public View rootView;
    public WebView facebookWebView;
    public Activity activity;

    public FacebookFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_facebook, container, false);
        prepareLayout();
        return rootView;
    }

    public void prepareLayout() {
        facebookWebView = (WebView) rootView.findViewById(R.id.facebook_webview);
        facebookWebView.setWebViewClient(new WebViewClient());
        facebookWebView.getSettings().setJavaScriptEnabled(true);
        facebookWebView.loadUrl(FACEBOOK_URL);
    }

    public boolean canDoBack() {
        return facebookWebView.canGoBack();
    }

    public void doBack() {
        facebookWebView.goBack();
    }
}