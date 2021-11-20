package com.example.mytests.views;

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
import com.example.mytests.databinding.FragmentSubjectListBinding;
import com.example.mytests.model.SubjectModel;
import com.example.mytests.viewmodel.SubjectViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SubjectFragment extends Fragment implements SubjectAdapter.OnItemClickedListener {

    private FragmentSubjectListBinding binding;
    private NavController navController;
    private SubjectViewModel viewModel;
    private SubjectAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentSubjectListBinding.inflate(inflater, container, false);
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
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                new AddSubjectDialogFragment().show(getChildFragmentManager(),"ADD_SUBJECT");
            }
        });

        //SwipeLayout swipeLayout =  (SwipeLayout)findViewById(R.id.sample1);

        //new MyGestureDetector(binding.recyclerListSubject);

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
                //binding.recyclerListSubject.setOnTouchListener(new OnSwipeTouchListener(getActivity(),binding.recyclerListSubject));
            }
        });



        SwipeHelper swipeHelper = new SwipeHelper(getContext(), binding.recyclerListSubject) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#1C1B1B"/*"#FF3C30"*/),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: onDelete
                                new DeleteSubjectDialogFragment().show(getChildFragmentManager(),"TAG");
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

        SubjectFragmentDirections.ActionSubjectFragmentToSubjectDetailFragment action =
                SubjectFragmentDirections.actionSubjectFragmentToSubjectDetailFragment();
        //action.setSubjectId("Subject0");
        action.setPosition(position);
        navController.navigate(action);
        //navController.navigate(R.id.action_subjectFragment_to_testFragment);
    }
}

/*
public class SubjectFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;


    public SubjectFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SubjectFragment newInstance(int columnCount) {
        SubjectFragment fragment = new SubjectFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //recyclerView.setAdapter(new SubjectAdapter(PlaceholderContent.ITEMS));
        }
        return view;
    }
}
*/
