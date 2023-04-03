package com.devdroiddev.realtimechat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private Context context;
    private List<MessageModel> messageModelList;

    public MessageAdapter(Context context) {
        this.context = context;

        // TODO: Initialize UserModelList Object Here
        messageModelList = new ArrayList<>();
    }

    // TODO: Make a Method to Add the Data

    public void add(MessageModel messageModel) {
        if (messageModel != null) {
            messageModelList.add(messageModel);
            notifyDataSetChanged();
        }
    }

    // TODO: Make a Method to Clear the Data From the List
    public void clear() {
        messageModelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MessageModel messageModel = messageModelList.get(position);
       holder.msg.setText(messageModel.getMessage());
       if (messageModel.getSenderId().equals(FirebaseAuth.getInstance().getUid()))
       {
           holder.main.setBackgroundColor(context.getResources().getColor(R.color.teal_700));
           holder.msg.setTextColor(context.getResources().getColor(R.color.white));
       }
       else {
           holder.main.setBackgroundColor(context.getResources().getColor(R.color.black));
           holder.msg.setTextColor(context.getResources().getColor(R.color.white));
       }
    }


    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    // TODO: Make Nested ViewHolder Class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView msg;
        private LinearLayout main;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.mainMessage);
            main = itemView.findViewById(R.id.mainMessageLayout);
        }
    }
}
