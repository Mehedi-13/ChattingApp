package com.example.chattingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.chattingapp.Adapter.ChatAdapter;
import com.example.chattingapp.Models.MessageModel;
import com.example.chattingapp.databinding.ActivityChatDetailsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailsActivity extends AppCompatActivity {

    ActivityChatDetailsBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityChatDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        database= FirebaseDatabase.getInstance();
        auth= FirebaseAuth.getInstance();

      final   String sendetId=auth.getUid();
        String reciveId= getIntent().getStringExtra("userId");
        String userName= getIntent().getStringExtra("name");
        String profilePic= getIntent().getStringExtra("profile");


        binding.userName.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.ic_user).into(binding.profileImageChatDetails);

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ChatDetailsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        final ArrayList<MessageModel> messageModels= new ArrayList<>();

        final ChatAdapter chatAdapter= new ChatAdapter(messageModels,this);
        binding.chatRecylerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        binding.chatRecylerView.setLayoutManager(layoutManager);


        final String senderRoom = sendetId + reciveId;
        final String receverRoom= reciveId+ senderRoom;
//For Chat

         database.getReference().child("chats")
                 .child(senderRoom)
                 .addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                         messageModels.clear();

                         for (DataSnapshot snapshot1: snapshot.getChildren()){
                             MessageModel model=snapshot1.getValue(MessageModel.class);

                             messageModels.add(model);
                         }

                         chatAdapter.notifyDataSetChanged();
                     }

                     @Override
                     public void onCancelled(@NonNull @NotNull DatabaseError error) {

                     }
                 });







        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message= binding.etMessage.getText().toString();
                final  MessageModel model= new MessageModel(sendetId,message);
                model.setTimestamp(new Date().getTime());
                binding.etMessage.setText("");

                database.getReference().child("chats")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("chats")
                                .child(receverRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });
            }
        });


    }
}