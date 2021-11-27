package com.example.mytests.views;

import android.os.Bundle;
import android.util.Log;
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

import com.example.mytests.adapter.TestAdapter;
import com.example.mytests.databinding.FragmentTestListBinding;
import com.example.mytests.model.TestModel;
import com.example.mytests.viewmodel.TestViewModel;

import java.util.List;

public class TestFragment extends Fragment implements TestAdapter.OnItemClickedListener {

    private FragmentTestListBinding binding;
    private NavController navController;
    private TestViewModel viewModel;
    private TestAdapter adapter;
    private String subjectId;
    //private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentTestListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        //position = TestFragmentArgs.fromBundle(getArguments()).getPosition();
        subjectId = (TestFragmentArgs.fromBundle(getArguments()).getSubjectId());
        Log.e("subjectId ", subjectId);
        //subjectId = "angielski";
        //viewModel.setSubjectId(subjectId);

        binding.recyclerListTest.setHasFixedSize(true);
        binding.recyclerListTest.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new TestAdapter(this);
        binding.recyclerListTest.setAdapter(adapter);

        viewModel.getTestLiveData(subjectId).observe(getViewLifecycleOwner(), new Observer<List<TestModel>>() {
            @Override
            public void onChanged(List<TestModel> testModels) {
                binding.progressTestList.setVisibility(View.GONE);
                adapter.setTestModels(testModels);
                adapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(TestViewModel.class);
    }

    @Override
    public void onItemClick(int position) {

        TestFragmentDirections.ActionTestFragmentToDetailFragment action =
                TestFragmentDirections.actionTestFragmentToDetailFragment();

        action.setPosition(position);
        navController.navigate(action);
    }
}