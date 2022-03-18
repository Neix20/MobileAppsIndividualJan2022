package my.edu.utar.neixpasswordmanager.ui;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import java.util.concurrent.ExecutionException;

import my.edu.utar.neixpasswordmanager.R;
import my.edu.utar.neixpasswordmanager.Utils.util;
import my.edu.utar.neixpasswordmanager.adapter.PwdListViewModel;
import my.edu.utar.neixpasswordmanager.data.PasswordElem;
import my.edu.utar.neixpasswordmanager.databinding.ActivityEditPwdBinding;

public class EditPwdActivity extends AppCompatActivity {
    public static final String TAG = EditPwdActivity.class.getSimpleName();

    private ActivityEditPwdBinding binding;

    private PwdListViewModel viewModel;

    private PasswordElem curPwd;

    private EditText title_txt;
    private EditText name_txt;
    private EditText pwd_txt;
    private EditText website_txt;

    private ImageView show_pass_btn;

    private ImageView title_copy_btn;
    private ImageView name_copy_btn;
    private ImageView pwd_copy_btn;
    private ImageView website_copy_btn;

    // Optional Elements
    private EditText pinNo_txt;
    private TextView secQes1_txtView;
    private TextView secQes2_txtView;
    private TextView secQes3_txtView;
    private EditText secAns1_txt;
    private EditText secAns2_txt;
    private EditText secAns3_txt;

    private ImageView pinNo_copy_btn;
    private ImageView secAns1_copy_btn;
    private ImageView secAns2_copy_btn;
    private ImageView secAns3_copy_btn;

    private LinearLayout pin_ll;
    private LinearLayout sec_1_ll;
    private LinearLayout sec_2_ll;
    private LinearLayout sec_3_ll;

    private SharedPreferences pref;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditPwdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Add Back Button at ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Initialize Objects
        viewModel = ViewModelProviders.of(EditPwdActivity.this).get(PwdListViewModel.class);

        // Binding Java Element to XML
        title_txt = binding.titleTxt;
        name_txt = binding.nameTxt;
        pwd_txt = binding.pwdTxt;
        website_txt = binding.websiteTxt;

        show_pass_btn = binding.showPassBtn;

        title_copy_btn = binding.titleCopyBtn;
        name_copy_btn = binding.nameCopyBtn;
        pwd_copy_btn = binding.pwdCopyBtn;
        website_copy_btn = binding.websiteCopyBtn;

        // Optional
        pinNo_txt = binding.pinNoTxt;

        secQes1_txtView = binding.secQes1TxtView;
        secQes2_txtView = binding.secQes2TxtView;
        secQes3_txtView = binding.secQes3TxtView;

        secAns1_txt = binding.secAns1Txt;
        secAns2_txt = binding.secAns2Txt;
        secAns3_txt = binding.secAns3Txt;

        pinNo_copy_btn = binding.pinNoCopyBtn;
        secAns1_copy_btn = binding.secAns1CopyBtn;
        secAns2_copy_btn = binding.secAns2CopyBtn;
        secAns3_copy_btn = binding.secAns3CopyBtn;

        pin_ll = binding.pinLl;
        sec_1_ll = binding.sec1Ll;
        sec_2_ll = binding.sec2Ll;
        sec_3_ll = binding.sec3Ll;

        pref = this.getSharedPreferences("mySharedPreferences", MODE_PRIVATE);

        Intent mIntent = getIntent();
        Long ind = (long) mIntent.getLongExtra("id", 0);

        try {
            curPwd = viewModel.getPassword(ind);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        title_txt.setText(curPwd.getTitle());
        name_txt.setText(curPwd.getUsername());

        // Decrypt Password
        String pwd_str = curPwd.getPassword();
        String key = pref.getString("decrypt_key", "");

        // Encrypt Password using AES Encryption
        try {
            pwd_str = util.decrypt(pwd_str, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        pwd_txt.setText(pwd_str);
        website_txt.setText(curPwd.getWebsite());

        pinNo_txt.setText(curPwd.getPin_number());
        if(curPwd.getPin_number().isEmpty()){
            pin_ll.setVisibility(View.GONE);
        }

        secQes1_txtView.setText(curPwd.getSecurity_question_1());
        secAns1_txt.setText(curPwd.getSecurity_answer_1());
        if(curPwd.getSecurity_question_1().isEmpty()){
            sec_1_ll.setVisibility(View.GONE);
        }

        secQes2_txtView.setText(curPwd.getSecurity_question_2());
        secAns2_txt.setText(curPwd.getSecurity_answer_2());
        if(curPwd.getSecurity_question_2().isEmpty()){
            sec_2_ll.setVisibility(View.GONE);
        }

        secQes3_txtView.setText(curPwd.getSecurity_question_3());
        secAns3_txt.setText(curPwd.getSecurity_answer_3());
        if(curPwd.getSecurity_question_3().isEmpty()){
            sec_3_ll.setVisibility(View.GONE);
        }

        show_pass_btn.setOnClickListener(v -> ShowHidePass(v));

        // Copy to Clipboard
        title_copy_btn.setOnClickListener(v -> util.copyCodeInClipBoard(
                v.getContext(), "Title has been copied to clipboard.", title_txt.getText().toString()));
        name_copy_btn.setOnClickListener(v -> util.copyCodeInClipBoard(
                v.getContext(), "Username has been copied to clipboard.", name_txt.getText().toString()));
        pwd_copy_btn.setOnClickListener(v -> util.copyCodeInClipBoard(
                v.getContext(), "Password has been copied to clipboard.", pwd_txt.getText().toString()));
        website_copy_btn.setOnClickListener(v -> util.copyCodeInClipBoard(
                v.getContext(), "URL has been copied to clipboard.", website_txt.getText().toString()));
        pinNo_copy_btn.setOnClickListener(v -> util.copyCodeInClipBoard(
                v.getContext(), "PIN Number has been copied to clipboard.", pinNo_txt.getText().toString()));
        secAns1_copy_btn.setOnClickListener(v -> util.copyCodeInClipBoard(
                v.getContext(), "Security Answer has been copied to clipboard.", secAns1_txt.getText().toString()));
        secAns2_copy_btn.setOnClickListener(v -> util.copyCodeInClipBoard(
                v.getContext(), "Security Answer has been copied to clipboard.", secAns2_txt.getText().toString()));
        secAns3_copy_btn.setOnClickListener(v -> util.copyCodeInClipBoard(
                v.getContext(), "Security Answer has been copied to clipboard.", secAns3_txt.getText().toString()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.option_save) {

            String pwd_str = pwd_txt.getText().toString();
            String key = pref.getString("decrypt_key", "");

            // Encrypt Password using AES Encryption
            try {
                pwd_str = util.encrypt(pwd_str, key);
            } catch (Exception e) {
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

            String[] txt_optional_arr = {
                    pinNo_txt.getText().toString().isEmpty() ? "" : pinNo_txt.getText().toString(),
                    secQes1_txtView.getText().toString().isEmpty() ? "" : secQes1_txtView.getText().toString(),
                    secAns1_txt.getText().toString().isEmpty() ? "" : secAns1_txt.getText().toString(),
                    secQes2_txtView.getText().toString().isEmpty() ? "" : secQes2_txtView.getText().toString(),
                    secAns2_txt.getText().toString().isEmpty() ? "" : secAns2_txt.getText().toString(),
                    secQes3_txtView.getText().toString().isEmpty() ? "" : secQes3_txtView.getText().toString(),
                    secAns3_txt.getText().toString().isEmpty() ? "" : secAns3_txt.getText().toString()
            };

            curPwd.updateValue(txt_arr);
            curPwd.updateOptionalValue(txt_optional_arr);

            viewModel.updatePassword(curPwd);
            Toast.makeText(this, "Successfully Edited Record!", Toast.LENGTH_SHORT).show();
            finish();
        } else if(id == R.id.option_delete){
            // Alert Box
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, ind) -> {
                        viewModel.deletePassword(curPwd);
                        Toast.makeText(this, "Successfully Deleted Record!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .setNegativeButton("No", (dialog, ind) -> dialog.cancel())
                    .show();
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
}
