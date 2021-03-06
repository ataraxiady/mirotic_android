package com.prograpy.app2.appdev2.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prograpy.app2.appdev2.R;

/**
 * Created by samsung on 2018-04-03.
 */

public class SetFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting,container, false);

        TextView send = (TextView) view.findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendmail = new Intent(getContext(), SendMail.class);
                startActivity(sendmail);
            }
        });


        return view;
    }

    public static SetFragment createFragment(){


        Bundle bundle = new Bundle();

        SetFragment one = new SetFragment();
        one.setArguments(bundle);

        return one;
    }
}



