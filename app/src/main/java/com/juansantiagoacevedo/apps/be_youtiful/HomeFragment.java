package com.juansantiagoacevedo.apps.be_youtiful;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
