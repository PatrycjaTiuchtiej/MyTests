package com.example.mytests.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytests.databinding.FragmentResultTestBinding;
import com.example.mytests.model.ResultModel;

import java.util.List;

public class ResultTestAdapter extends RecyclerView.Adapter<ResultTestAdapter.ResultViewHolder> {


    private List<ResultModel> resultModels;
    private OnItemClickedListener onItemClickedListener;


    public void setResultModels(List<ResultModel> resultModels) {
        this.resultModels = resultModels;
    }

    public ResultTestAdapter(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentResultTestBinding binding = FragmentResultTestBinding.inflate(inflater, parent, false);
        return new ResultViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {

        ResultModel model = resultModels.get(position);
        holder.binding.resultStudentList.setText(model.getStudentEmail());
        //Log.d("Score", String.valueOf(model.getScore()));
        holder.binding.resultScoreList.setText(String.valueOf(model.getScore())+"/3");
        //Glide.with(holder.itemView)
        //        .load(model.getImage())
        //        .into(holder.binding.quizImageList);


    }

    @Override
    public int getItemCount() {

        if (resultModels == null)
            return 0;
        else
            return resultModels.size();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FragmentResultTestBinding binding;

        public ResultViewHolder(FragmentResultTestBinding binding) {
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
