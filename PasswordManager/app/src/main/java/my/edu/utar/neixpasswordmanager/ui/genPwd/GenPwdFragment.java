package my.edu.utar.neixpasswordmanager.ui.genPwd;

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

import my.edu.utar.neixpasswordmanager.databinding.FragmentGenPwdBinding;


public class GenPwdFragment extends Fragment {

    private FragmentGenPwdBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGenPwdBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView textView = binding.textGenPwd;
        textView.setText("Generate Password Fragment");
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}