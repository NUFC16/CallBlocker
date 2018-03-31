package com.example.arcibald160.callblocker.tools;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arcibald160.callblocker.R;

import java.lang.reflect.Method;

/**
 * Created by arcibald160 on 27.03.18..
 */

public class Tab1Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment, container, false);

        return view;
    }


}
