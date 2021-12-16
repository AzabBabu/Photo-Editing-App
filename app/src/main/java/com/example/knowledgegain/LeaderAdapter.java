package com.example.knowledgegain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.knowledgegain.databinding.RowLeaderLayoutBinding;

import java.util.ArrayList;

public class LeaderAdapter extends RecyclerView.Adapter<LeaderAdapter.LeaderViewHolder>{

    Context context;
    ArrayList<User> users;

    public LeaderAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public LeaderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_leader_layout,parent,false);
        return new LeaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderViewHolder holder, int position) {

        User user = users.get(position);
        holder.binding.nameId.setText(user.getName());
        holder.binding.indexId.setText(String.format("#%d", position+1));
        holder.binding.coidid.setText(String.valueOf(user.getCoins()));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class LeaderViewHolder extends RecyclerView.ViewHolder {

        RowLeaderLayoutBinding binding;

        public LeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = RowLeaderLayoutBinding.bind(itemView);
        }
    }
}
