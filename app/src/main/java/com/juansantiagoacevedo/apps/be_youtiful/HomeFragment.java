package com.juansantiagoacevedo.apps.be_youtiful;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class HomeFragment extends Fragment{

    public Activity activity;
    private View rootView;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        prepareLayout();
        return rootView;
    }

    private void prepareLayout() {

    }

    public interface homeFragmentListener {
        public void onButtonClick(int position);
    }
}
