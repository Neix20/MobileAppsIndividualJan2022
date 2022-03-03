package my.edu.utar.neixpasswordmanager.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import my.edu.utar.neixpasswordmanager.databinding.FragmentChangeMasPwdBinding;

public class ChangeMasPwdFragment extends Fragment {

    private FragmentChangeMasPwdBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChangeMasPwdBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView textView = binding.textChangeMasPwd;
        textView.setText("Change Master Password Fragment");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
