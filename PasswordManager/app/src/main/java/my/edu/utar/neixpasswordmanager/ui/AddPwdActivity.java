package my.edu.utar.neixpasswordmanager.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import my.edu.utar.neixpasswordmanager.R;
import my.edu.utar.neixpasswordmanager.Utils.util;
import my.edu.utar.neixpasswordmanager.adapter.PwdListViewModel;
import my.edu.utar.neixpasswordmanager.data.PasswordElem;
import my.edu.utar.neixpasswordmanager.databinding.ActivityAddPwdBinding;

public class AddPwdActivity extends AppCompatActivity {

    public static final String TAG = AddPwdActivity.class.getSimpleName();

    private ActivityAddPwdBinding binding;

    private PwdListViewModel viewModel;

    private EditText title_txt;
    private EditText name_txt;
    private EditText pwd_txt;
    private EditText website_txt;

    private ImageView show_pass_btn;

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddPwdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Binding Java Element to XML
        title_txt = binding.titleTxt;
        name_txt = binding.nameTxt;
        pwd_txt = binding.pwdTxt;
        website_txt = binding.websiteTxt;

        show_pass_btn = binding.showPassBtn;

        pref = this.getSharedPreferences("mySharedPreferences", MODE_PRIVATE);

        // Initialize Objects
        viewModel = ViewModelProviders.of(AddPwdActivity.this).get(PwdListViewModel.class);

        // Add Back Button at ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        show_pass_btn.setOnClickListener(v -> ShowHidePass(v));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.option_save) {

            String pwd_str = pwd_txt.getText().toString();
            String key = pref.getString("decrypt_key", "");

            Log.e(TAG, key);
            Log.e(TAG, pwd_str);

            // Encrypt Password using AES Encryption
            try {
                pwd_str = util.encrypt(pwd_str, key);
                Log.e(TAG, pwd_str);
            } catch (Exception e) {
                Log.e(TAG, String.valueOf(e));
                e.printStackTrace();
            }

            String[] txt_arr = {
                    title_txt.getText().toString(),
                    name_txt.getText().toString(),
                    pwd_str,
                    website_txt.getText().toString()
            };

            // Validation => Check to ensure Edit Text Entry are not empty
            // If There is Empty Text Field
            if(util.checkIfAnyEmpty(txt_arr)) {
                Toast.makeText(this, "Please ensure that all textFields are not empty!", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }

            PasswordElem pwd = new PasswordElem();
            pwd.updateValue(txt_arr);
            viewModel.insertPassword(pwd);
            Toast.makeText(this, "Successfully Save Record!", Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
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


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
