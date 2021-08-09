package com.example.chattingapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chattingapp.Adapter.UsersAdapter;
import com.example.chattingapp.Models.Users;
import com.example.chattingapp.databinding.FragmentChatBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class ChatFragment extends Fragment {



    public ChatFragment() {
        // Required empty public constructor
    }


    FragmentChatBinding binding;
    ArrayList<Users> list= new ArrayList<>();
    FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentChatBinding.inflate(inflater, container, false);

        database=FirebaseDatabase.getInstance();

        UsersAdapter adapter= new UsersAdapter(list,getContext());
        binding.chatRecyClerView.setAdapter(adapter);

        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext());
        binding.chatRecyClerView.setLayoutManager(layoutManager);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                list.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Users users= dataSnapshot.getValue(Users.class);
                    users.setUserId(dataSnapshot.getKey());
                    list.add(users);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        return binding.getRoot();
    }
}