package com.prograpy.app2.appdev2.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.prograpy.app2.appdev2.R;

/**
 * Created by samsung on 2018-03-25.
 */

public class InfoFragment extends Fragment{

    Button fail;
    OnClickListener listener;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_info, container, false);
        fail = (Button)view.findViewById(R.id.failBtn);
        fail.setOnClickListener(listener);
        return view;
    }



    public static InfoFragment newInstance(){

        Bundle bundle = new Bundle();

        InfoFragment one = new InfoFragment();
        one.setArguments(bundle);

        return one;
    }

    public void listener(OnClickListener listener){
        this.listener = listener;
    }


}
