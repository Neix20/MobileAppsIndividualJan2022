package my.edu.utar.neixpasswordmanager.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import my.edu.utar.neixpasswordmanager.databinding.FragmentPwdListBinding;

public class PwdListFragment extends Fragment {

    private FragmentPwdListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPwdListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView textView = binding.textPwdList;
        textView.setText("Welcome to Password List Fragment");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}