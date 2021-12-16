package com.example.knowledgegain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.knowledgegain.databinding.ActivityMainBinding;
import com.iammert.library.readablebottombar.ReadableBottomBar;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contentBoxId,new HomeFragment());
        transaction.commit();

        binding.tabsId.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
            @Override
            public void onItemSelected(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (i){
                    case 0:transaction.replace(R.id.contentBoxId,new HomeFragment());

                        break;
                    case 1:transaction.replace(R.id.contentBoxId,new LeaderFragment());

                        break;
                    case 2:transaction.replace(R.id.contentBoxId,new WalletFragment());

                        break;
                    case 3:transaction.replace(R.id.contentBoxId,new ProfileFragment());

                        break;
                }
                transaction.commit();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.home_wallet){
            Toast.makeText(this,"Money is here.",Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);

    }
}