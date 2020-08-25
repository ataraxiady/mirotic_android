package com.prograpy.app2.appdev2.chatList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prograpy.app2.appdev2.R;
import com.prograpy.app2.appdev2.network.response.data.MatchUserData;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by samsung on 2018-04-04.
 */

public class ChatListRecyclerViewAdapter extends RecyclerView.Adapter<ChatListRecyclerViewAdapter.ChatRecyclerViewAdapterViewHolder> {

    private ArrayList<MatchUserData> userData = new ArrayList<MatchUserData>();
    private View.OnClickListener clickListener;

    private Context context;

    @Override
    public ChatListRecyclerViewAdapter.ChatRecyclerViewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerviewholder_chat, parent, false);
        context = parent.getContext();
        return new ChatListRecyclerViewAdapter.ChatRecyclerViewAdapterViewHolder(view);
    }


    public void setUserData(ArrayList<MatchUserData> userData){
        this.userData = userData;
    }


    public void setItemClickListener(View.OnClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(ChatListRecyclerViewAdapter.ChatRecyclerViewAdapterViewHolder holder, int position) {


        Glide.with(context).load(userData.get(position).getMatchImage()).into(holder.imageView);

        holder.tvName.setText(userData.get(position).getMatchName());

        holder.itemView.setTag(userData.get(position));
        holder.itemView.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return userData.size();
    }

    public class ChatRecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private CircleImageView imageView;
        private LinearLayout parentView;

        public ChatRecyclerViewAdapterViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.chat_name);
            imageView = (CircleImageView) itemView.findViewById(R.id.photo);
        }

    }


}
