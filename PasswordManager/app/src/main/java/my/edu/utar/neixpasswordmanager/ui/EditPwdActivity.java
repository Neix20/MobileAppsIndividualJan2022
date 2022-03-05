package my.edu.utar.neixpasswordmanager.ui;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
        pwd_txt.setText(curPwd.getPassword());
        website_txt.setText(curPwd.getWebsite());

        show_pass_btn.setOnClickListener(v -> ShowHidePass(v));

        title_copy_btn.setOnClickListener(v -> util.copyCodeInClipBoard(v.getContext(), "Title", title_txt.getText().toString()));
        name_copy_btn.setOnClickListener(v -> util.copyCodeInClipBoard(v.getContext(), "Name", name_txt.getText().toString()));
        pwd_copy_btn.setOnClickListener(v -> util.copyCodeInClipBoard(v.getContext(), "Password", pwd_txt.getText().toString()));
        website_copy_btn.setOnClickListener(v -> util.copyCodeInClipBoard(v.getContext(), "Website", website_txt.getText().toString()));
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

            String[] txt_arr = {
                    title_txt.getText().toString(),
                    name_txt.getText().toString(),
                    pwd_txt.getText().toString(),
                    website_txt.getText().toString()
            };

            // Validation => Check to ensure Edit Text Entry are not empty
            // If There is Empty Text Field
            if(util.checkIfAnyEmpty(txt_arr)) {
                Toast.makeText(this, "Please ensure that all textFields are not empty!", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }

            curPwd.updateValue(txt_arr);
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
