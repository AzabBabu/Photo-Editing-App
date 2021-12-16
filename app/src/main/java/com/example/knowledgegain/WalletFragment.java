package com.example.knowledgegain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.knowledgegain.databinding.FragmentWalletBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;


public class WalletFragment extends Fragment {


    public WalletFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentWalletBinding binding;
    FirebaseFirestore database;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWalletBinding.inflate(inflater,container,false);
        database = FirebaseFirestore.getInstance();

        try {

            database.collection("User").document(FirebaseAuth.getInstance().getUid())
            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    try {

                        user = documentSnapshot.toObject(User.class);
                        binding.showCoinsId.setText(String.valueOf(user.getCoins()));

                    }catch (Exception e){
                        Log.e("TAG", "onSuccess: ", e );
                    }
                }
            });

        }catch (Exception e){
            Log.d("TAg", e.toString());
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
        }

        binding.sentRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (user.getCoins() > 50000){

                    String uid = FirebaseAuth.getInstance().getUid();
                    String bkashNo =binding.bkashNo.getText().toString();
                    WithdrawsRequest request = new WithdrawsRequest( uid,bkashNo, user.getName());

                    database.collection("Withdraws")
                            .document(FirebaseAuth.getInstance().getUid())
                            .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "Your Request Is Successful.",Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    Toast.makeText(getContext(), "You Need More Coins Earn.",Toast.LENGTH_LONG).show();
                }

            }
        });

        return binding.getRoot();
    }
}