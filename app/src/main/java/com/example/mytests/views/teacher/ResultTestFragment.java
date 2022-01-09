package com.example.mytests.views.teacher;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.mytests.adapter.ResultTestAdapter;
import com.example.mytests.databinding.FragmentResultTestListBinding;
import com.example.mytests.model.ResultModel;
import com.example.mytests.viewmodel.ResultViewModel;

import java.util.List;

public class ResultTestFragment extends Fragment implements ResultTestAdapter.OnItemClickedListener {

    private FragmentResultTestListBinding binding;
    private NavController navController;
    private ResultViewModel viewModel;
    private ResultTestAdapter adapter;
    //private String subjectId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentResultTestListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        //subjectId = (TestFragmentArgs.fromBundle(getArguments()).getSubjectId());
        //Log.e("subjectId ", subjectId);

        binding.recyclerListResults.setHasFixedSize(true);
        binding.recyclerListResults.setLayoutManager(new GridLayoutManager(getContext(), 1));

        adapter = new ResultTestAdapter(this);
        binding.recyclerListResults.setAdapter(adapter);

        viewModel.getResultLiveData().observe(getViewLifecycleOwner(), new Observer<List<ResultModel>>() {
            @Override
            public void onChanged(List<ResultModel> resultModels) {
                binding.progressResultsList.setVisibility(View.GONE);
                adapter.setResultModels(resultModels);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(ResultViewModel.class);
    }

    @Override
    public void onItemClick(int position) {

        //TeacherMemberFragmentDirections.ActionMemberFragmentToMemberDetailFragment action =
        //        TeacherMemberFragmentDirections.actionMemberFragmentToMemberDetailFragment();
        //action.setMemberId("Member0");
        //action.setPosition(position);
        //navController.navigate(action);
        //navController.navigate(R.id.action_memberFragment_to_testFragment);
    }
}