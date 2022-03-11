package my.edu.utar.neixpasswordmanager.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import my.edu.utar.neixpasswordmanager.R;
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

    private SearchView searchBar;
    private ImageView sort_btn;

    private FloatingActionButton fab;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPwdListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mRecyclerView = binding.pwdRecView;
        searchBar = binding.searchBar;
        sort_btn = binding.sortBtn;
        fab = binding.fab;

        fab.setOnClickListener(v -> nAddPwd(v));

        viewModel = ViewModelProviders.of(this.getActivity()).get(PwdListViewModel.class);

        mAdapter = new PwdListAdapter(this.getActivity());

        mRecyclerView.setAdapter(mAdapter);

        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.getActivity(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        // Updates Password List
        viewModel.getPwdList().observe(this, pwdList -> {
            mAdapter.updateList(pwdList);
        });

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        sort_btn.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(this.getActivity(), sort_btn);
            popup.getMenuInflater().inflate(R.menu.menu_sort, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if(id == R.id.option_a_to_z){
                    viewModel.getPwdList().observe(this, pwdList -> mAdapter.updateList(pwdList));
                } else if(id == R.id.option_z_to_a){
                    viewModel.getPwdListDesc().observe(this, pwdList -> mAdapter.updateList(pwdList));
                } else if(id == R.id.option_new_to_old){
                    viewModel.getPwdListDate().observe(this, pwdList -> mAdapter.updateList(pwdList));
                } else if(id == R.id.option_old_to_new){
                    viewModel.getPwdListDateDesc().observe(this, pwdList -> mAdapter.updateList(pwdList));
                }
                return true;
            });

            popup.show();//showing popup menu
        });

        return root;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int pos = item.getOrder();

        // Alert Dialog
        new AlertDialog.Builder(this.getContext())
                .setMessage("Are you sure you want to delete?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    PasswordElem pwd = null;
                    try {
                        pwd = viewModel.getPassword((long) pos);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    viewModel.deletePassword(pwd);

                    viewModel.getPwdList().observe(this, pwdList -> {
                        mAdapter.updateList(pwdList);
                    });
                })
                .setNegativeButton("No", (dialog, id) -> dialog.cancel())
                .show();

        return super.onContextItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getPwdList().observe(this, pwdList -> {
            mAdapter.updateList(pwdList);
        });
    }

    public void nAddPwd(View v){
        Intent intent = new Intent(v.getContext(), AddPwdActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}