package com.prograpy.app2.appdev2.chatList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.chat.ChatMainActivity;
import com.prograpy.app2.appdev2.network.response.ApiValue;
import com.prograpy.app2.appdev2.network.response.data.MatchUserData;
import com.prograpy.app2.appdev2.network.response.data.UserData;
import com.prograpy.app2.appdev2.network.response.result.MatchResult;
import com.prograpy.app2.appdev2.task.MatchListTask;
import com.prograpy.app2.appdev2.utils.PreferenceData;

import java.util.ArrayList;

/**
 * Created by samsung on 2018-04-01.
 */

public class ChatListActivity extends AppCompatActivity{

    private ArrayList<MatchUserData> userDataList = new ArrayList<MatchUserData>();

    private RecyclerView chatRecyclerView;
    private ChatListRecyclerViewAdapter chatRecyclerViewAdapter;

    private TextView emptyView;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ChatListActivity.this, ChatMainActivity.class);
            startActivity(intent);
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        emptyView = (TextView) findViewById(R.id.empty_text);

        chatRecyclerViewAdapter = new ChatListRecyclerViewAdapter();
        chatRecyclerViewAdapter.setItemClickListener(listener);
        chatRecyclerViewAdapter.setUserData(userDataList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        chatRecyclerView.setLayoutManager(linearLayoutManager);
        chatRecyclerView.setAdapter(chatRecyclerViewAdapter);


        MatchListTask matchListTask = new MatchListTask(new MatchListTask.TaskResultHandler() {
            @Override
            public void onSuccessTask(MatchResult result) {

                if(result.isSuccess()){

                    if(result.getMatchUsers() != null && result.getMatchUsers().size() > 0){

                        emptyView.setVisibility(View.GONE);
                        chatRecyclerView.setVisibility(View.VISIBLE);

                        userDataList = result.getMatchUsers();

                        chatRecyclerViewAdapter.notifyDataSetChanged();

                    }else{
                        emptyView.setVisibility(View.VISIBLE);
                        chatRecyclerView.setVisibility(View.GONE);
                    }



                }else{
                    Toast.makeText(ChatListActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();

                    emptyView.setVisibility(View.VISIBLE);
                    chatRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailTask() {
                emptyView.setVisibility(View.VISIBLE);
                chatRecyclerView.setVisibility(View.GONE);

                Toast.makeText(ChatListActivity.this, "서버 통신에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelTask() {
                emptyView.setVisibility(View.VISIBLE);
                chatRecyclerView.setVisibility(View.GONE);
            }
        });

        matchListTask.execute(ApiValue.API_GET_MATCH_LIST, PreferenceData.getKeyUserId());
    }





}
