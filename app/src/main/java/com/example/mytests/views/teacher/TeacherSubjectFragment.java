package com.example.mytests.views.teacher;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytests.SwipeHelper;
import com.example.mytests.adapter.SubjectAdapter;
import com.example.mytests.databinding.FragmentTeacherSubjectListBinding;
import com.example.mytests.model.SubjectModel;
import com.example.mytests.viewmodel.SubjectViewModel;

import java.util.List;

public class TeacherSubjectFragment extends Fragment implements SubjectAdapter.OnItemClickedListener {

    private FragmentTeacherSubjectListBinding binding;
    private NavController navController;
    private SubjectViewModel viewModel;
    private SubjectAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentTeacherSubjectListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        binding.addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                new AddSubjectDialogFragment().show(getChildFragmentManager(),"ADD_SUBJECT");
            }
        });


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



        SwipeHelper swipeHelper = new SwipeHelper(getContext(), binding.recyclerListSubject) {
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
                                // nie wiem skąd wziąć subjectId

                                String subjectId = "biologia";
                                new DeleteSubjectDialogFragment(subjectId).show(getChildFragmentManager(),"TAG");
                            }
                        }
                ));

                /*underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Transfer",
                        0,
                        Color.parseColor("#FF9502"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: OnTransfer
                            }
                        }
                ));
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Unshare",
                        0,
                        Color.parseColor("#C7C7CB"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: OnUnshare
                            }
                        }
                ));*/
            }
        };


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(SubjectViewModel.class);
    }

    @Override
    public void onItemClick(int position) {

        TeacherSubjectFragmentDirections.ActionSubjectFragmentToSubjectDetailFragment action =
                TeacherSubjectFragmentDirections.actionSubjectFragmentToSubjectDetailFragment();
        //action.setSubjectId("Subject0");
        action.setPosition(position);
        navController.navigate(action);
        //navController.navigate(R.id.action_subjectFragment_to_testFragment);
    }
}
