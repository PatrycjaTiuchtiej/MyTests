package com.example.mytests.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytests.databinding.FragmentSubjectBinding;
import com.example.mytests.model.SubjectModel;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {


    private List<SubjectModel> subjectModels;
    private OnItemClickedListener onItemClickedListener;


    public void setSubjectModels(List<SubjectModel> subjectModels) {
        this.subjectModels = subjectModels;
    }

    public SubjectAdapter(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentSubjectBinding binding = FragmentSubjectBinding.inflate(inflater, parent, false);
        return new SubjectViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {

        SubjectModel model = subjectModels.get(position);
        holder.binding.subjectTitleList.setText(model.getName());
        //Glide.with(holder.itemView)
        //        .load(model.getImage())
        //        .into(holder.binding.quizImageList);


    }

    @Override
    public int getItemCount() {

        if (subjectModels == null)
            return 0;
        else
            return subjectModels.size();
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FragmentSubjectBinding binding;

        public SubjectViewHolder(FragmentSubjectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.constrainLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            onItemClickedListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickedListener{
        void onItemClick(int position);
        //void onItemSwipe(int position);
    }
}

