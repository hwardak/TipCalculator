package com.example.hwardak.tipcalculator;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tgk.integrationwithfragment.R;

/**
 * Created by HWardak on 16-04-20.
 */
public class HelpFragment extends Fragment {


    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return LayoutInflator object.
     * This method will call upon the help_fragment to fill its designated container.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.help_fragment, container,  false);
    }
}


