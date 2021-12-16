package com.example.knowledgegain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.knowledgegain.databinding.ActivityQuizBinding;
import com.example.knowledgegain.databinding.ActivityResultBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultActivity extends AppCompatActivity {

    ActivityResultBinding binding;
    long POINT = 10;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int correctAns , totalQ;


        correctAns = getIntent().getIntExtra("correctAns",0);
        totalQ = getIntent().getIntExtra("totalQ",0);

        long point = correctAns*POINT;

        binding.showResultId.setText(String.format("%d/%d",correctAns,totalQ));
        binding.earnCoinsId.setText((String.valueOf(point)));

        database = FirebaseFirestore.getInstance();
        database.collection("User")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(point));

    }
}