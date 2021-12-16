package com.example.knowledgegain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.knowledgegain.databinding.ActivityQuizBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    FirebaseFirestore database;
    ActivityQuizBinding binding;
    ArrayList<Question> questions;
    int index = 0 ;
    Question question;
    boolean ableNext= false;
    CountDownTimer timer;
    int correctAns = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Random random = new Random();
        final int rand = random.nextInt(12);

        database = FirebaseFirestore.getInstance();
        final String uId = FirebaseAuth.getInstance().getUid();

        final String cateId = getIntent().getStringExtra("cateId");


        questions = new ArrayList<>( );

        database.collection("Categories")
                .document(cateId)
                .collection("questions")
                .whereGreaterThanOrEqualTo("index",rand)
                .orderBy("index")
                .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.size() < 5){
                    database.collection("Categories")
                            .document(cateId)
                            .collection("questions")
                            .whereLessThanOrEqualTo("index",rand)
                            .orderBy("index")
                            .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                                Question question = snapshot.toObject(Question.class);
                                questions.add(question);
                            }
                            Log.d("TAG", "onSuccess: 2 ");

                            nextQues();

                        }
                    });
                }else {
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots){
                        Question question = snapshot.toObject(Question.class);
                        questions.add(question);
                    }
                    Log.d("TAG", "onSuccess: 1 ");
                    nextQues();
                }
            }
        });


    }

    void resetTimer(){
        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long l) {
                 binding.timerId.setText(String.valueOf(l/1000));
            }

            @Override
            public void onFinish() {

            }
        };
    }

    void nextQues(){

        try {
            if (index < questions.size()){

                Log.d("TAG", "onSuccess: 4 ");

                ableNext = false;
                resetOption();
                clickableOption();
                binding.questionCounterId.setText(String.format("%d/%d", (index+1), questions.size()));
                resetTimer();
                timer.start();

                question =questions.get(index);
                binding.questionId.setText(question.getQuestion());
                binding.option1.setText(question.getOption1());
                binding.option2.setText(question.getOption2());
                binding.option3.setText(question.getOption3());
                binding.option4.setText(question.getOption4());
            }
        }catch (Exception e){

            Log.e("TAG", "nextQues: ",e);

        }
    }
    void checkAns(TextView view){
        String selectedOption = view.getText().toString();
        ableNext = true;
        if (selectedOption.equals(question.ans)){
            correctAns++;
            view.setBackground(getDrawable(R.drawable.correct_background));
            unclickableOption();
        }else {
            view.setBackground(getDrawable(R.drawable.wrong_background));
            showAns();
            unclickableOption();

        }
    }
    void showAns(){
        if (question.ans.equals(binding.option1.getText().toString())){
            binding.option1.setBackground(getDrawable(R.drawable.correct_background));
        }else if (question.ans.equals(binding.option2.getText().toString())){
            binding.option2.setBackground(getDrawable(R.drawable.correct_background));
        }else if (question.ans.equals(binding.option3.getText().toString())){
            binding.option3.setBackground(getDrawable(R.drawable.correct_background));
        }else if (question.ans.equals(binding.option4.getText().toString())){
            binding.option4.setBackground(getDrawable(R.drawable.correct_background));
        }
    }
    void unclickableOption(){
        binding.option1.setClickable(false);
        binding.option2.setClickable(false);
        binding.option3.setClickable(false);
        binding.option4.setClickable(false);
    }
    void clickableOption(){
        binding.option1.setClickable(true);
        binding.option2.setClickable(true);
        binding.option3.setClickable(true);
        binding.option4.setClickable(true);
    }

    void resetOption(){
        binding.option1.setBackground(getDrawable(R.drawable.unselect_background));
        binding.option2.setBackground(getDrawable(R.drawable.unselect_background));
        binding.option3.setBackground(getDrawable(R.drawable.unselect_background));
        binding.option4.setBackground(getDrawable(R.drawable.unselect_background));
    }

    public void onClick(View view){
        TextView selected;
        switch (view.getId()){
            case R.id.option1:
            case R.id.option2:
            case R.id.option3:
            case R.id.option4:
                if (timer !=null){
                    timer.cancel();
                }
                selected = (TextView) view;
                checkAns(selected);

                break;
            case R.id.nextBtnId:
                    if (ableNext == true){
                        if (index <= questions.size()){
                            index ++;
                            nextQues();
                        }else {
                            //Toast.makeText(this,"Question Finish",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                            intent.putExtra("correctAns", correctAns);
                            intent.putExtra("totalQ",questions.size());
                            startActivity(intent);
                            finish();
                        }
                    }else {
                        Toast.makeText(this,"Option is null",Toast.LENGTH_LONG).show();

                    }
                break;
        }
    }
}