package com.example.mytests.views.student;

import android.annotation.SuppressLint;
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

import com.example.mytests.adapter.SubjectAdapter;
import com.example.mytests.databinding.FragmentStudentSubjectListBinding;
import com.example.mytests.model.SubjectModel;
import com.example.mytests.viewmodel.SubjectViewModel;

import java.util.List;

public class StudentSubjectFragment extends Fragment implements SubjectAdapter.OnItemClickedListener {

    private FragmentStudentSubjectListBinding binding;
    private NavController navController;
    private SubjectViewModel viewModel;
    private SubjectAdapter adapter;
    private String subjectId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStudentSubjectListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        binding.recyclerListSubject.setHasFixedSize(true);
        binding.recyclerListSubject.setLayoutManager(new GridLayoutManager(getContext(), 1));

        adapter = new SubjectAdapter(this);
        binding.recyclerListSubject.setAdapter(adapter);

        viewModel.getSubjectLiveData().observe(getViewLifecycleOwner(), new Observer<List<SubjectModel>>() {
            @Override
            public void onChanged(List<SubjectModel> subjectModels) {
                binding.progressSubjectList.setVisibility(View.GONE);
                adapter.setSubjectModels(subjectModels);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(SubjectViewModel.class);
    }

    @Override
    public void onItemClick(int position) {

        viewModel.getSubjectLiveData().observe(getViewLifecycleOwner(), new Observer<List<SubjectModel>>() {
            @Override
            public void onChanged(List<SubjectModel> subjectModels) {
                SubjectModel subject = subjectModels.get(position);
                subjectId = subject.getSubjectId();
                Log.e("subjectId ", subjectId);
            }
        });
        StudentSubjectFragmentDirections.ActionStudentSubjectFragmentToStudentTestFragment action =
                StudentSubjectFragmentDirections.actionStudentSubjectFragmentToStudentTestFragment();
        action.setSubjectId(subjectId);
        action.setPosition(position);
        navController.navigate(action);
        //navController.navigate(R.id.action_subjectFragment_to_testFragment);
    }
}
