package com.juansantiagoacevedo.apps.be_youtiful;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class InstagramFragment extends Fragment {

    public final static String INSTAGRAM_URL = "https://instagram.com/beyoutiful_salon/";
    public View rootView;
    public WebView instagramWebView;

    public InstagramFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_instagram, container, false);
        prepareLayout();
        return rootView;
    }

    public void prepareLayout() {
        instagramWebView = (WebView) rootView.findViewById(R.id.instagram_webview);
        instagramWebView.setWebViewClient(new WebViewClient());
        instagramWebView.getSettings().setJavaScriptEnabled(true);
        instagramWebView.loadUrl(INSTAGRAM_URL);
    }

    public boolean canDoBack() {
        return instagramWebView.canGoBack();
    }

    public void doBack() {
        instagramWebView.goBack();
    }
}
