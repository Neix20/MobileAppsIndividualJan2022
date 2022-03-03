package my.edu.utar.neixpasswordmanager.ui;

import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import my.edu.utar.neixpasswordmanager.R;
import my.edu.utar.neixpasswordmanager.databinding.ActivityAddPwdBinding;

public class AddPwdActivity extends AppCompatActivity{

    private ActivityAddPwdBinding binding;

    private EditText title_txt;
    private EditText name_txt;
    private EditText pwd_txt;
    private EditText website_txt;

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

        // Add Back Button at ActionBar
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
