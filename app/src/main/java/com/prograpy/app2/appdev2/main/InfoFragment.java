package com.prograpy.app2.appdev2.main;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prograpy.app2.appdev2.R;

/**
 * Created by samsung on 2018-03-25.
 */

public class InfoFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info,container,false);
//        이미지뷰.셋팅이미지(전달받은이미지)
        return view;
    }

//    public static InfoFragment newInstance(string imagurl, string profile){
//        Bundle bundle = new Bundle();
//
//        InfoFragment one = new InfoFragment();
//        one.setArguments(bundle);
//
//        return one;
//    }

    public static InfoFragment newInstance(){
        Bundle bundle = new Bundle();

        InfoFragment one = new InfoFragment();
        one.setArguments(bundle);

        return one;
    }


}

