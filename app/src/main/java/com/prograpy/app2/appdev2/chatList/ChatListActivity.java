package com.prograpy.app2.appdev2.chatList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.prograpy.app2.appdev2.R;

/**
 * Created by samsung on 2018-04-01.
 */

public class ChatListActivity extends AppCompatActivity{

    private RecyclerView chatRecyclerView;
    private ChatListRecyclerViewAdapter chatRecyclerViewAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        chatRecyclerViewAdapter = new ChatListRecyclerViewAdapter();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        chatRecyclerView.setLayoutManager(linearLayoutManager);
        chatRecyclerView.setAdapter(chatRecyclerViewAdapter);

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };





}
