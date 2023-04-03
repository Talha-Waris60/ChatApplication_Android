package com.devdroiddev.realtimechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.devdroiddev.realtimechat.databinding.ActivityChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    String receiverId;
    DatabaseReference databaseReferenceSender, databaseReferenceReceiver;
    String senderRoom, receiverRoom;
    MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        receiverId = getIntent().getStringExtra("id");
        senderRoom = FirebaseAuth.getInstance().getUid()+receiverId;
        receiverRoom = receiverId+FirebaseAuth.getInstance().getUid();

        // TODO: Set Adapter Here
        messageAdapter = new MessageAdapter(this);
        binding.recycleChat.setAdapter(messageAdapter);
        binding.recycleChat.setLayoutManager(new LinearLayoutManager(this));




        databaseReferenceSender = FirebaseDatabase.getInstance().getReference("Chats").child(senderRoom);
        databaseReferenceReceiver = FirebaseDatabase.getInstance().getReference("Chats").child(receiverRoom);
        // TODO: Click Event for Sender
        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageAdapter.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                    messageAdapter.add(messageModel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // TODO: Click Event for receiver


        // TODO: To send the Message set Click Listener
        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.writeMessage.getText().toString();
                if (!message.trim().isEmpty()) {
                    sendMessage(message);
                } else {
                    Toast.makeText(ChatActivity.this, "Cannot send an empty message", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // TODO: Method that send the message
    private void sendMessage(String message) {
        try {
            String messageId = UUID.randomUUID().toString();
            MessageModel messageModel = new MessageModel(messageId,FirebaseAuth.getInstance().getUid(),message);
            messageAdapter.add(messageModel);
            databaseReferenceSender
                    .child(messageId)
                    .setValue(messageModel);

            databaseReferenceReceiver
                    .child(messageId)
                    .setValue(messageModel);
        } catch (Exception e) {
            Log.e("CHAT_ACTIVITY", "Error sending message: " + e.getMessage());
            Toast.makeText(ChatActivity.this, "Error sending message", Toast.LENGTH_SHORT).show();
        }
    }
}