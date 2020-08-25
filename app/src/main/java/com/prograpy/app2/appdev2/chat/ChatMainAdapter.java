package com.prograpy.app2.appdev2.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.network.response.data.ChatData;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ChatData> chatLists = new ArrayList<ChatData>();

    private static final int MY_CHAT = 0;
    private static final int MATCH_CHAT = 1;

    private Context context;


    public void setChatLists(ArrayList<ChatData> chatLists){
        this.chatLists = chatLists;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        context = parent.getContext();

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

            Glide.with(context).load(chatLists.get(position).getChatImage()).into(((MyChatViewHolder)holder).myImg);
        }else{
            ((MatchChatViewHolder)holder).matchChat.setText(chatLists.get(position).getChatMsg());
            Glide.with(context).load(chatLists.get(position).getChatImage()).into(((MatchChatViewHolder)holder).matchImg);
        }

    }


    @Override
    public int getItemViewType(int position) {

        if(chatLists != null && chatLists.size() > 0){
            if(chatLists.get(position).getChatType() == 0)
                return MY_CHAT;
            else
                return MATCH_CHAT;
        }else{
            return MY_CHAT;
        }

    }

    @Override
    public int getItemCount() {
        return chatLists.size();
    }




    public class MyChatViewHolder extends RecyclerView.ViewHolder{

        private TextView myChat;
        private CircleImageView myImg;

        public MyChatViewHolder(View itemView) {
            super(itemView);

            myChat = (TextView) itemView.findViewById(R.id.tv_mychat);
            myImg = (CircleImageView) itemView.findViewById(R.id.my_img);
        }
    }


    public class MatchChatViewHolder extends RecyclerView.ViewHolder{

        private TextView matchChat;
        private CircleImageView matchImg;


        public MatchChatViewHolder(View itemView) {
            super(itemView);

            matchChat = (TextView) itemView.findViewById(R.id.tv_matchchat);
            matchImg = (CircleImageView) itemView.findViewById(R.id.match_img);
        }
    }
}
