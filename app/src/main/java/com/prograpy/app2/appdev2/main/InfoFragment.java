package com.prograpy.app2.appdev2.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prograpy.app2.appdev2.R;

/**
 * Created by samsung on 2018-03-25.
 */

public class InfoFragment extends Fragment{
    private static final String NAME = "name";
    private static final String AGE = "age";
    ImageView fprofileImage;
    TextView fname;
    TextView fage;
    TextView fgender;
    TextView farea;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            fname.setText(getArguments().getString(NAME));
            fage.setText(getArguments().getInt(AGE));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info,container,false);
//        이미지뷰.셋팅이미지(전달받은이미지)

        return view;
    }


    public static InfoFragment newInstance(String name, String gender, int age, String area){
        InfoFragment fragment = new InfoFragment();
        Bundle bundle = new Bundle();

        bundle.putString(NAME,name);
        bundle.putString(gender,gender);
        bundle.putInt(String.valueOf(age),age);
        bundle.putString(area,area);

        fragment.setArguments(bundle);

        return fragment;
    }


}

