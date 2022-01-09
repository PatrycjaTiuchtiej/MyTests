package com.example.mytests.views.teacher;

import android.graphics.Color;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytests.SwipeHelper;
import com.example.mytests.adapter.TestAdapter;
import com.example.mytests.databinding.FragmentTestListBinding;
import com.example.mytests.model.TestModel;
import com.example.mytests.viewmodel.TestViewModel;
import com.google.android.material.snackbar.Snackbar;

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

        binding.addTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //viewModel.setSubjectId(subjectId);
                //new AddTestDialogFragment(subjectId).show(getChildFragmentManager(),"ADD_TEST");
                TestFragmentDirections.ActionTestFragmentToAddTestDialogFragment action =
                        TestFragmentDirections.actionTestFragmentToAddTestDialogFragment();

                //action.setPosition(position);
                navController.navigate(action);
            }
        });

        binding.recyclerListTest.setHasFixedSize(true);
        binding.recyclerListTest.setLayoutManager(new GridLayoutManager(getContext(), 1));

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

        SwipeHelper swipeHelper = new SwipeHelper(getContext(), binding.recyclerListTest) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "DELETE",
                        0,
                        Color.parseColor("#1C1B1B"/*"#FF3C30"*/),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: onDelete
                                new DeleteTestDialogFragment().show(getChildFragmentManager(),"TAG");
                            }
                        }
                ));
            }
        };
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(TestViewModel.class);
    }

    @Override
    public void onItemClick(int position) {

        TestFragmentDirections.ActionTestFragmentToResultTestFragment action =
                TestFragmentDirections.actionTestFragmentToResultTestFragment();

        action.setPosition(position);
        navController.navigate(action);
    }
}