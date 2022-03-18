package my.edu.utar.neixpasswordmanager.ui;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import my.edu.utar.neixpasswordmanager.R;
import my.edu.utar.neixpasswordmanager.databinding.FragmentChangeMasPwdBinding;

public class ChangeMasPwdFragment extends Fragment {

    private FragmentChangeMasPwdBinding binding;

    private EditText pwd_txt;
    private EditText con_pwd_txt;

    private ImageView show_pass_btn;
    private ImageView show_con_pass_btn;

    private Button submit_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChangeMasPwdBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Bind Jave Elements to XML
        pwd_txt = binding.pwdTxt;
        con_pwd_txt = binding.conPwdTxt;

        show_pass_btn = binding.showPassBtn;
        show_con_pass_btn = binding.showConPassBtn;

        submit_btn = binding.submitBtn;

        show_pass_btn.setOnClickListener(v -> ShowHidePass(v));
        show_con_pass_btn.setOnClickListener(v -> ShowConHidePass(v));

        submit_btn.setOnClickListener(v -> submitPassword(v));

        return root;
    }

    public void submitPassword(View v){
        String pwd = pwd_txt.getText().toString();
        String con_pwd = con_pwd_txt.getText().toString();

        if(pwd.equals(con_pwd)){
            pwd_txt.setText("");
            con_pwd_txt.setText("");

            SharedPreferences pref = v.getContext().getSharedPreferences("mySharedPreferences", MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = pref.edit();

            prefEditor.putString("master_password", pwd);
            prefEditor.commit();

            Toast.makeText(v.getContext(), String.format("Master Password Successfully Updated!"), Toast.LENGTH_SHORT).show();
        } else{
            pwd_txt.setText("");
            con_pwd_txt.setText("");
            Toast.makeText(v.getContext(), String.format("Both passwords are not the same!\nPlease try again."), Toast.LENGTH_SHORT).show();
        }
    }

    public void ShowHidePass(View view){
        if(pwd_txt.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
            show_pass_btn.setImageResource(R.drawable.ic_baseline_visibility_24);
            //Show Password
            pwd_txt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else{
            show_pass_btn.setImageResource(R.drawable.ic_baseline_visibility_off_24);
            //Hide Password
            pwd_txt.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    public void ShowConHidePass(View view){
        if(con_pwd_txt.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
            show_con_pass_btn.setImageResource(R.drawable.ic_baseline_visibility_24);
            //Show Password
            con_pwd_txt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else{
            show_con_pass_btn.setImageResource(R.drawable.ic_baseline_visibility_off_24);
            //Hide Password
            con_pwd_txt.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
