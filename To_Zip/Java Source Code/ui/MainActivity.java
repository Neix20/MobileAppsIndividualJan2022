package my.edu.utar.neixpasswordmanager.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

import my.edu.utar.neixpasswordmanager.R;
import my.edu.utar.neixpasswordmanager.adapter.PwdListViewModel;
import my.edu.utar.neixpasswordmanager.data.PasswordElem;
import my.edu.utar.neixpasswordmanager.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private PwdListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProviders.of(this).get(PwdListViewModel.class);

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.option_export) {
                FileOutputStream fos;

                String fileName = "PwdDb.txt";

                try {
                    fos = openFileOutput(fileName, MODE_PRIVATE);
                    viewModel.getPwdList().observe(this, pwdList -> {
                        for (PasswordElem elem : pwdList) {
                            try {
                                fos.write(String.format("%s\n", elem.toString()).getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(this, "PwdDb.txt was successfully generated!", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_pwd_list, R.id.nav_gen_pwd, R.id.nav_change_mas_pwd)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_exit) {
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialog, id) -> finish())
                        .setNegativeButton("No", (dialog, id) -> dialog.cancel())
                        .show();
            } else {
                NavigationUI.onNavDestinationSelected(item, navController);
                drawer.closeDrawers();
            }
            return false;
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> finishAffinity())
                .setNegativeButton("No", (dialog, id) -> dialog.cancel())
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    public boolean onOptionsItemSelected(MenuItem item){
//        int id = item.getItemId();
//        if(id == R.id.option_export){
//            FileOutputStream fos;
//
//            String fileName = "PwdDb.txt";
//
//            try {
//                fos = openFileOutput(fileName, MODE_PRIVATE);
//                viewModel.getPwdList().observe(this, pwdList -> {
//                    for(PasswordElem elem : pwdList){
//                        try {
//                            fos.write(String.format("%s\n", elem.toString()).getBytes());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Toast.makeText(this, "PwdDb.txt was successfully generated!", Toast.LENGTH_SHORT).show();
//        }
//        return true;
//    }

}