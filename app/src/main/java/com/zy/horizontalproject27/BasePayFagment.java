package com.zy.horizontalproject27;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BasePayFagment extends Fragment {
    private String TAG = BasePayFagment.class.getSimpleName();
    private int faceType = 0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_base, null);
        Bundle arguments = getArguments();
        if (arguments != null) {
            faceType = arguments.getInt(Common.GUN_TYPE, 0);
        }

        return v;
    }
}
