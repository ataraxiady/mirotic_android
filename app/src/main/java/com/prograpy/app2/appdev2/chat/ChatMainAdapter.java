package com.prograpy.app2.appdev2.chat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.network.response.data.ChatData;

import java.util.ArrayList;

public class ChatMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ChatData> chatLists = new ArrayList<ChatData>();

    private static final int MY_CHAT = 0;
    private static final int MATCH_CHAT = 1;


    public void setChatLists(ArrayList<ChatData> chatLists){
        this.chatLists = chatLists;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        if(viewType == MY_CHAT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_chat, parent, false);
            return new MyChatViewHolder(view);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_match_chat, parent, false);
            return new MatchChatViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof MyChatViewHolder){
            ((MyChatViewHolder)holder).myChat.setText(chatLists.get(position).getChatMsg());
        }else{
            ((MatchChatViewHolder)holder).matchChat.setText(chatLists.get(position).getChatMsg());
        }

    }


    @Override
    public int getItemViewType(int position) {
        if(position % 2 == 0)
            return MY_CHAT;
        else
            return MATCH_CHAT;
    }

    @Override
    public int getItemCount() {
        return chatLists.size();
    }




    public class MyChatViewHolder extends RecyclerView.ViewHolder{

        private TextView myChat;

        public MyChatViewHolder(View itemView) {
            super(itemView);

            myChat = (TextView) itemView.findViewById(R.id.tv_mychat);
        }
    }


    public class MatchChatViewHolder extends RecyclerView.ViewHolder{

        private TextView matchChat;

        public MatchChatViewHolder(View itemView) {
            super(itemView);

            matchChat = (TextView) itemView.findViewById(R.id.tv_matchchat);
        }
    }
}
