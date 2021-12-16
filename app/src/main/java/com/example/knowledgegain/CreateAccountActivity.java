package com.example.knowledgegain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.knowledgegain.databinding.ActivityCreateAccountBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateAccountActivity extends AppCompatActivity {

    ActivityCreateAccountBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        dialog = new ProgressDialog(this);

        binding.submitBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, password, name, refer_Code;

                email = binding.createEmailId.getText().toString();
                password = binding.createPasswordId.getText().toString();
                name = binding.createNameId.getText().toString();
                refer_Code = binding.createReferCodeId.getText().toString();

                User user = new User(name,email,password,refer_Code);

                dialog.show();
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(CreateAccountActivity.this, "Successful" ,Toast.LENGTH_LONG).show();

                            String uid = task.getResult().getUser().getUid();

                            database
                                    .collection("User")
                                    .document(uid)
                                    .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    dialog.dismiss();
                                    if (task.isSuccessful()){
                                        startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
                                    }else {
                                        Toast.makeText(CreateAccountActivity.this, task.getException().getLocalizedMessage() ,Toast.LENGTH_LONG).show();
                                    }
                                }
                            });


                        }else {
                            dialog.dismiss();
                            Toast.makeText(CreateAccountActivity.this, task.getException().getLocalizedMessage() ,Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        binding.AlreadyAccountBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateAccountActivity.this,LoginActivity.class));
            }
        });
    }
}