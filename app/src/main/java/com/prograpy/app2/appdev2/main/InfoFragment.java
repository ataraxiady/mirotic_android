package com.prograpy.app2.appdev2.main;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prograpy.app2.appdev2.R;

/**
 * Created by samsung on 2018-03-25.
 */

public class InfoFragment extends Fragment {
    private static final String NAME = "name";
    private static final String AGE = "age";
    private static final String GENDER = "gender";
    private static final String AREA = "area";
    private static final String IMAGE = "profileImage";


    ImageView fprofileImage;
    TextView fname;
    TextView fage;
    TextView fgender;
    TextView farea;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);


        fname = (TextView) view.findViewById(R.id.infoName);
        fage = (TextView) view.findViewById(R.id.infoAge);
        fgender = (TextView) view.findViewById(R.id.infoGender);
        farea = (TextView) view.findViewById(R.id.infoArea);
        fprofileImage = (ImageView) view.findViewById(R.id.infoPic);
        fprofileImage.setBackground(new ShapeDrawable(new OvalShape()));
        fprofileImage.setClipToOutline(true);

        if (getArguments() != null) {
            fname.setText(getArguments().getString(NAME));
            fage.setText(getArguments().getString(AGE));
            fgender.setText(getArguments().getString(GENDER));
            farea.setText(getArguments().getString(AREA));

            Glide.with(container.getContext()).load(getArguments().getString(IMAGE)).into(fprofileImage);
        }

        return view;
    }


    public static InfoFragment newInstance(String name, String gender, int age, String area, String profileImage) {
        InfoFragment fragment = new InfoFragment();
        Bundle bundle = new Bundle();

        bundle.putString(NAME, name);
        bundle.putString(GENDER, gender);
        bundle.putInt(AGE, age);
        bundle.putString(AREA, area);
        bundle.putString(IMAGE, profileImage);
        fragment.setArguments(bundle);

        return fragment;
    }


}

