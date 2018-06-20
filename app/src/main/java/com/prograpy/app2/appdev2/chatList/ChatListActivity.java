package com.prograpy.app2.appdev2.chatList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.chat.ChatMainActivity;

/**
 * Created by samsung on 2018-04-01.
 */

public class ChatListActivity extends AppCompatActivity{

    private RecyclerView chatRecyclerView;
    private ChatListRecyclerViewAdapter chatRecyclerViewAdapter;

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
        chatRecyclerViewAdapter = new ChatListRecyclerViewAdapter();
        chatRecyclerViewAdapter.setItemClickListener(listener);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        chatRecyclerView.setLayoutManager(linearLayoutManager);
        chatRecyclerView.setAdapter(chatRecyclerViewAdapter);

    }





}
