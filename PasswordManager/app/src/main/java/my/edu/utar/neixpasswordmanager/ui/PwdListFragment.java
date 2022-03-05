package my.edu.utar.neixpasswordmanager.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import my.edu.utar.neixpasswordmanager.adapter.PwdListAdapter;
import my.edu.utar.neixpasswordmanager.adapter.PwdListViewModel;
import my.edu.utar.neixpasswordmanager.data.PasswordElem;
import my.edu.utar.neixpasswordmanager.databinding.FragmentPwdListBinding;

public class PwdListFragment extends Fragment {

    public static final String TAG = PwdListFragment.class.getSimpleName();

    private FragmentPwdListBinding binding;

    private RecyclerView mRecyclerView;

    private PwdListViewModel viewModel;
    private PwdListAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPwdListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mRecyclerView = binding.pwdRecView;

        viewModel = ViewModelProviders.of(this.getActivity()).get(PwdListViewModel.class);

        mAdapter = new PwdListAdapter(this.getActivity());

        mRecyclerView.setAdapter(mAdapter);

        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        // Updates Password List
        viewModel.getPwdList().observe(this, pwdList -> {
            mAdapter.updateList(pwdList);
        });

        return root;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int pos = item.getOrder();

        // Alert Dialog

        PasswordElem pwd = viewModel.getPwdList().getValue().get(pos);

        viewModel.deletePassword(pwd);

        viewModel.getPwdList().observe(this, pwdList -> {
            mAdapter.updateList(pwdList);
        });

        return super.onContextItemSelected(item);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}