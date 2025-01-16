package com.example.suzette.virtualassistant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suzette.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int getItemViewType(int position) {
        // Determine view type based on message sender
        Message message = messageList.get(position);
        return message.getSentBy().equals(Message.SENT_BY_ME) ? 1 : 0;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView;
        // Inflate layout based on view type
        if (viewType == 1) {
            chatView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_user, parent, false);
        } else {
            chatView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_bot, parent, false);
        }
        return new MyViewHolder(chatView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messageList.get(position);

        // Bind appropriate text views based on view type
        if (message.getSentBy().equals(Message.SENT_BY_ME)) {
            if (holder.leftTextView != null) {
                holder.leftTextView.setText(message.getMessage());
            }
        } else {
            if (holder.rightTextView != null) {
                holder.rightTextView.setText(message.getMessage());
            }
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView leftTextView, rightTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize text views if they exist
            leftTextView = itemView.findViewById(R.id.text_message_user);
            rightTextView = itemView.findViewById(R.id.text_message_bot);
        }
    }
}