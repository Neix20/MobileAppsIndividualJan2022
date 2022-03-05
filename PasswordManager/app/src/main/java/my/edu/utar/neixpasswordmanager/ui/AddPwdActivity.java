package my.edu.utar.neixpasswordmanager.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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

    private EditText title_txt;
    private EditText name_txt;
    private EditText pwd_txt;
    private EditText website_txt;

    private PwdListViewModel viewModel;

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

        // Initialize Objects
        viewModel = ViewModelProviders.of(this).get(PwdListViewModel.class);

        // Add Back Button at ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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

            String[] txt_arr = {
                    title_txt.getText().toString(),
                    name_txt.getText().toString(),
                    pwd_txt.getText().toString(),
                    website_txt.getText().toString()
            };

            // If There is Empty Text Field
            if(util.checkIfAnyEmpty(txt_arr)) {
                Toast.makeText(this, "Please ensure that all textFields are not Empty!", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }

            PasswordElem pwd = new PasswordElem(txt_arr[0], txt_)

            // Validation => Check to ensure Edit Text Entry are not empty
            Toast.makeText(this, "Successfully Save Record", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
