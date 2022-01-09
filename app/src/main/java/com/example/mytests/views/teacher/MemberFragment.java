package com.example.mytests.views.teacher;

import android.annotation.SuppressLint;
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
import com.example.mytests.adapter.MemberAdapter;
import com.example.mytests.databinding.FragmentMemberListBinding;
import com.example.mytests.model.UserModel;
import com.example.mytests.viewmodel.MemberViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MemberFragment extends Fragment implements MemberAdapter.OnItemClickedListener {

    private FragmentMemberListBinding binding;
    private NavController navController;
    private MemberViewModel viewModel;
    private MemberAdapter adapter;
    private String subjectId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentMemberListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        subjectId = (TestFragmentArgs.fromBundle(getArguments()).getSubjectId());
        Log.e("subjectId ", subjectId);

        binding.addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //viewModel.setSubjectId(subjectId);
                new AddMemberDialogFragment(subjectId).show(getChildFragmentManager(),"ADD_MEMBER");
            }
        });


        binding.recyclerListMember.setHasFixedSize(true);
        binding.recyclerListMember.setLayoutManager(new GridLayoutManager(getContext(), 1));

        adapter = new MemberAdapter(this);
        binding.recyclerListMember.setAdapter(adapter);

        List <String> members = new ArrayList<String>();
        members.add("pdKnT3JHKTRgE8l6eijpUUUra9J2");
        members.add("ygm9e6hI4wXfcpnR4fEia3Gzjqx2");
        members.add("pareGvqb25Xt2Iaza8YzXq3Vup73");
        members.add("eD6bh2oa8bdwW48M0aprkbN1Icm2");
        members.add("YUeWz9u13BZrxphznhtNuzdDEK53");

        viewModel.getMemberLiveData(members).observe(getViewLifecycleOwner(), new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> memberModels) {
                binding.progressMemberList.setVisibility(View.GONE);
                adapter.setMemberModels(memberModels);
                adapter.notifyDataSetChanged();
            }
        });



        SwipeHelper swipeHelper = new SwipeHelper(getContext(), binding.recyclerListMember) {
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
                                new DeleteMemberDialogFragment().show(getChildFragmentManager(),"TAG");
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
                .getInstance(getActivity().getApplication())).get(MemberViewModel.class);
    }

    @Override
    public void onItemClick(int position) {

        MemberFragmentDirections.ActionMemberFragmentToResultStudentFragment action =
                MemberFragmentDirections.actionMemberFragmentToResultStudentFragment();
        action.setPosition(position);
        navController.navigate(action);
    }
}
