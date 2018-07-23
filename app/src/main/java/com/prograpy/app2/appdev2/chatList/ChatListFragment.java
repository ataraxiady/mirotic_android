package com.prograpy.app2.appdev2.chatList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.chat.ChatMainActivity;
import com.prograpy.app2.appdev2.network.NetworkProgressDialog;
import com.prograpy.app2.appdev2.network.response.ApiValue;
import com.prograpy.app2.appdev2.network.response.data.MatchUserData;
import com.prograpy.app2.appdev2.network.response.result.MatchResult;
import com.prograpy.app2.appdev2.task.MatchListTask;
import com.prograpy.app2.appdev2.utils.PreferenceData;

import java.util.ArrayList;

/**
 * Created by samsung on 2018-04-01.
 */

public class ChatListFragment extends Fragment{

    private ArrayList<MatchUserData> userDataList = new ArrayList<MatchUserData>();

    private RecyclerView chatRecyclerView;
    private ChatListRecyclerViewAdapter chatRecyclerViewAdapter;

    private NetworkProgressDialog dialog;

    private TextView emptyView;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            MatchUserData data = (MatchUserData) view.getTag();
            Intent intent = new Intent(getContext(), ChatMainActivity.class);
            intent.putExtra("matchId", data.getMatchId());
            startActivity(intent);
        }
    };



    public static ChatListFragment createFragment(){

        Bundle bundle = new Bundle();

        ChatListFragment fragment = new ChatListFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_match, container, false);

        chatRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        emptyView = (TextView) view.findViewById(R.id.empty_text);

        dialog = new NetworkProgressDialog(getContext());

        chatRecyclerViewAdapter = new ChatListRecyclerViewAdapter();
        chatRecyclerViewAdapter.setItemClickListener(listener);
        chatRecyclerViewAdapter.setUserData(userDataList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        chatRecyclerView.setLayoutManager(linearLayoutManager);
        chatRecyclerView.setAdapter(chatRecyclerViewAdapter);



        MatchListTask matchListTask = new MatchListTask(new MatchListTask.TaskResultHandler() {
            @Override
            public void onSuccessTask(MatchResult result) {

                dialog.dismiss();

                if(result.isSuccess()){

                    if(result.getMatchUsers() != null && result.getMatchUsers().size() > 0){

                        emptyView.setVisibility(View.GONE);
                        chatRecyclerView.setVisibility(View.VISIBLE);

                        userDataList = result.getMatchUsers();

                        chatRecyclerViewAdapter.setUserData(userDataList);
                        chatRecyclerViewAdapter.notifyDataSetChanged();

                    }else{
                        emptyView.setVisibility(View.VISIBLE);
                        chatRecyclerView.setVisibility(View.GONE);
                    }



                }else{
                    Toast.makeText(getContext(), result.getMsg(), Toast.LENGTH_SHORT).show();

                    emptyView.setVisibility(View.VISIBLE);
                    chatRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailTask() {
                dialog.dismiss();

                emptyView.setVisibility(View.VISIBLE);
                chatRecyclerView.setVisibility(View.GONE);

                Toast.makeText(getContext(), "서버 통신에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelTask() {
                dialog.dismiss();

                emptyView.setVisibility(View.VISIBLE);
                chatRecyclerView.setVisibility(View.GONE);
            }
        });

        dialog.show();
        matchListTask.execute(ApiValue.API_GET_MATCH_LIST, PreferenceData.getKeyUserId());

        return view;
    }




}
