package com.plguerra.f1simengineer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SimpleDash extends Fragment {
    static TextView gearView;
    private static String Tag;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dash_simple,container,false);
        gearView = (TextView) view.findViewById(R.id.Gear);

//        updateSimpleFragment();
        return view;
    }


    public static void updateSimpleFragment() {
//        gearView.setText("5");
        Log.d(Tag,"Called Properly" );
    }
}
