package com.logistic.logic.e_saloon.CustomerChat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.logistic.logic.e_saloon.R;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {

    RecyclerView recyclerView;
    Context context;
    List<ChatMessage> chatMessageList=new ArrayList<>();

    public ChatMessageAdapter(RecyclerView recyclerView, Context context
            , List<ChatMessage> chatMessageList) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.chatMessageList = chatMessageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater
                .from(context)
                .inflate(R.layout.custom_chat_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ChatMessage chatMessage=chatMessageList.get(i);
        if (chatMessage.getMessageType().equalsIgnoreCase(chatMessage.MSG_TYPE_SENT)){
            viewHolder.rightTextView.setText(chatMessage.getMessageContent());
            viewHolder.leftLayout.setVisibility(View.GONE);
        }
        else {
                viewHolder.leftTextView.setText(chatMessage.getMessageContent());
                viewHolder.rightLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftLayout,rightLayout;
        TextView leftTextView,rightTextView;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        leftLayout=itemView.findViewById(R.id.left_Chat_Message_Layout);
        leftTextView=itemView.findViewById(R.id.left_Chat_Message);
        rightTextView=itemView.findViewById(R.id.right_Chat_Message);
        rightLayout=itemView.findViewById(R.id.right_Chat_Message_Layout);
    }
}
}
