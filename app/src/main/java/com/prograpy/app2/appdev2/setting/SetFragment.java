package com.prograpy.app2.appdev2.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prograpy.app2.appdev2.R;

/**
 * Created by samsung on 2018-04-03.
 */

public class SetFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_set,container, false);


        return view;
    }

    public static SetFragment newInstance(){

        Bundle bundle = new Bundle();

        SetFragment one = new SetFragment();
        one.setArguments(bundle);

        return one;
    }
}



