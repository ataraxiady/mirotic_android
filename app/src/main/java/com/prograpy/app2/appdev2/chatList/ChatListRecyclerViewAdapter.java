package com.prograpy.app2.appdev2.chatList;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prograpy.app2.appdev2.R;

/**
 * Created by samsung on 2018-04-04.
 */

public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatRecyclerViewAdapterViewHolder>{

    @Override
    public ChatListRecyclerViewAdapter.ChatRecyclerViewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewholder_chat, parent,false);
        return new ChatListRecyclerViewAdapter.ChatRecyclerViewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatListRecyclerViewAdapter.ChatRecyclerViewAdapterViewHolder holder, int position) {
        switch (position){
            case 0:

                holder.setText("첫번째 채팅");

                break;

            case 1:

                holder.setText("두번째 채팅");

                break;

            case 2:

                holder.setText("세번째 채팅");

                break;


            case 3:

                holder.setText("네번째 채팅");

                break;

            case 4:

                holder.setText("다섯번째 채팅");

                break;

            case 5:

                holder.setText("여섯번째 채팅");

                break;

            case 6:

                holder.setText("일곱번째 채팅");

                break;

            case 7:

                holder.setText("여덟번째 채팅");

                break;

            case 8:

                holder.setText("아홉번째 채팅");

                break;

            case 9:

                holder.setText("열번째 채팅");

                break;
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ChatRecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ChatRecyclerViewAdapterViewHolder(View itemView) {
            super(itemView);

            textView = (TextView)itemView.findViewById(R.id.viewholder_text);
        }
        public void setText(String text){textView.setText(text);}
    }


}
