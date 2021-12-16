package com.example.knowledgegain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.knowledgegain.databinding.FragmentLeaderBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class LeaderFragment extends Fragment {



    public LeaderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentLeaderBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLeaderBinding.inflate(getLayoutInflater(),container,false);
        FirebaseFirestore database = FirebaseFirestore.getInstance();

        ArrayList<User> users = new ArrayList<>();
        LeaderAdapter adapter = new LeaderAdapter(getContext(),users);

        binding.leaderRecyclerId.setAdapter(adapter);
        binding.leaderRecyclerId.setLayoutManager(new LinearLayoutManager(getContext()));

        database.collection("User")
                .orderBy("coins", Query.Direction.DESCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (DocumentSnapshot snapshots : queryDocumentSnapshots ){
                    User user = snapshots.toObject(User.class);
                    users.add(user);
                }
                adapter.notifyDataSetChanged();
            }
        });

        return binding.getRoot();
    }
}