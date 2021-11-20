package com.example.mytests.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytests.databinding.FragmentTestBinding;
import com.example.mytests.model.TestModel;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {


    private List<TestModel> testModels;
    private OnItemClickedListener onItemClickedListener;

    public void setTestModels(List<TestModel> testModels) {
        this.testModels = testModels;
    }

    public TestAdapter(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FragmentTestBinding binding = FragmentTestBinding.inflate(inflater, parent, false);
        return new TestViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {

        TestModel model = testModels.get(position);
        //holder.binding.content.setText(model.getName());
        holder.binding.testTitleList.setText(model.getTitle());
        //Glide.with(holder.itemView)
        //        .load(model.getImage())
        //        .into(holder.binding.quizImageList);


    }

    @Override
    public int getItemCount() {

        if (testModels == null)
            return 0;
        else
            return testModels.size();
    }

    public class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FragmentTestBinding binding;

        public TestViewHolder(FragmentTestBinding binding) {
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
