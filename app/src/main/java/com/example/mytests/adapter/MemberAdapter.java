package com.example.mytests.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytests.databinding.FragmentMemberBinding;
import com.example.mytests.model.UserModel;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {


    private List<UserModel> memberModels;
    private OnItemClickedListener onItemClickedListener;

    public void setMemberModels(List<UserModel> memberModels) {
        this.memberModels = memberModels;
    }

    public MemberAdapter(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentMemberBinding binding = FragmentMemberBinding.inflate(inflater, parent, false);
        return new MemberViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {

        UserModel model = memberModels.get(position);

        //holder.binding.content.setText(model.getName());
        holder.binding.memberUsernameList.setText(model.getEmail());
        //Glide.with(holder.itemView)
        //        .load(model.getImage())
        //        .into(holder.binding.quizImageList);


    }

    @Override
    public int getItemCount() {

        if (memberModels == null)
            return 0;
        else
            return memberModels.size();
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FragmentMemberBinding binding;

        public MemberViewHolder(FragmentMemberBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            // ????????????
            binding.constrainLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            onItemClickedListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickedListener{
        void onItemClick(int position);
    }
}
