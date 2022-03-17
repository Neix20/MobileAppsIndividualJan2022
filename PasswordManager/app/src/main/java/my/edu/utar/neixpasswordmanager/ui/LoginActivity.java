package my.edu.utar.neixpasswordmanager.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import my.edu.utar.neixpasswordmanager.R;
import my.edu.utar.neixpasswordmanager.Utils.util;
import my.edu.utar.neixpasswordmanager.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    private ActivityLoginBinding binding;

    private EditText pwd_txt;
    private Button submit_btn;
    private ImageView show_pass_btn;

    private SharedPreferences pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Bind Java Elements to XML Objects
        pwd_txt = binding.pwdTxt;
        submit_btn = binding.submitBtn;
        show_pass_btn = binding.showPassBtn;

        // Check if Saved Preference Exits
        pref = getSharedPreferences("mySharedPreferences", MODE_PRIVATE);

        // If Master Password Does Not Exist
        if(pref.getString("master_password", null) == null){
            SharedPreferences.Editor prefEditor = pref.edit();
            prefEditor.putString("master_password", "root");
            prefEditor.commit();
        }

        // If Decryption Key Does not Exist
        if(pref.getString("decrypt_key", null) == null){
            SharedPreferences.Editor prefEditor = pref.edit();
            String key = util.genPassword(16, true, true, true, false);
            prefEditor.putString("decrypt_key", key);
            prefEditor.commit();
        }

        show_pass_btn.setOnClickListener(v -> ShowHidePass(v));

        // When Submit Button is clicked, the password is parsed to check if its similar or something
        submit_btn.setOnClickListener(v -> submitPassword(v));
    }

    public void submitPassword(View v){
        String pwd = pwd_txt.getText().toString();

        String master_pwd = pref.getString("master_password", null);

        if(pwd.equals(master_pwd)){
            pwd_txt.setText("");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else{
            pwd_txt.setText("");
            Toast.makeText(this, String.format("Password is incorrect!\nPlease try again."), Toast.LENGTH_SHORT).show();
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
}
