package my.edu.utar.neixpasswordmanager.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;

import my.edu.utar.neixpasswordmanager.Utils.util;
import my.edu.utar.neixpasswordmanager.databinding.FragmentGenPwdBinding;

public class GenPwdFragment extends Fragment {

    private FragmentGenPwdBinding binding;

    private TextView pwd_txtView;
    private TextView pwd_len_txtView;

    private ImageView reset_pwd_btn;
    private ImageView copy_pwd_btn;

    private Slider pwd_slider;

    private SwitchMaterial uppercase_switch;
    private SwitchMaterial lowercase_switch;
    private SwitchMaterial digit_switch;
    private SwitchMaterial symbol_switch;

    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGenPwdBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        context = getContext();

        // Bind Java Object to XML Element
        pwd_txtView = binding.pwdTxtView;
        pwd_len_txtView = binding.pwdLenTxtView;

        reset_pwd_btn = binding.resetPwdBtn;
        copy_pwd_btn = binding.copyPwdBtn;

        pwd_slider = binding.pwdSlider;

        uppercase_switch = binding.uppercaseSwitch;
        lowercase_switch = binding.lowercaseSwitch;
        digit_switch = binding.digitSwitch;
        symbol_switch = binding.symbolSwitch;

        // Set Default Password
        String password = util.genPassword(4, true, true, true, true);
        pwd_txtView.setText(password);

        // Update & Set Password Length
        pwd_slider.addOnChangeListener((slider, value, fromUser) -> {
            int pwd_len = (int) value;
            updatePwdAndLength(pwd_len);
        });

        uppercase_switch.setOnClickListener(v -> updatePwd());
        lowercase_switch.setOnClickListener(v -> updatePwd());
        digit_switch.setOnClickListener(v -> updatePwd());
        symbol_switch.setOnClickListener(v -> updatePwd());

        // Check if at least one option is selected
        reset_pwd_btn.setOnClickListener(v -> updatePwd());

        // Copy to Clipboard
        copy_pwd_btn.setOnClickListener(v -> util.copyCodeInClipBoard(v.getContext(), "Password has been copied to clipboard.", pwd_txtView.getText().toString()));

        return root;
    }

    public boolean checkOptions(boolean needUpperCase, boolean needLowerCase, boolean needDigit, boolean needSymbol){
        return needUpperCase || needLowerCase || needDigit || needSymbol;
    }

    // Get Password Length
    public void updatePwdAndLength(int pwd_len){
        boolean needUpper = uppercase_switch.isChecked();
        boolean needLower = lowercase_switch.isChecked();
        boolean needDigit = digit_switch.isChecked();
        boolean needSymbol = symbol_switch.isChecked();

        // Check if there's at least one Option
        if(!checkOptions(needUpper, needLower, needDigit, needSymbol)){
            pwd_txtView.setText("Not enough usable characters defined");
            return;
        }

        // Update Password Length
        pwd_len_txtView.setText(String.format("Password length: %d", pwd_len));

        // Also generates New Password
        String password = util.genPassword(pwd_len, needUpper, needLower, needDigit, needSymbol);
        pwd_txtView.setText(password);
    }

    // Reset
    public void updatePwd(){
        boolean needUpper = uppercase_switch.isChecked();
        boolean needLower = lowercase_switch.isChecked();
        boolean needDigit = digit_switch.isChecked();
        boolean needSymbol = symbol_switch.isChecked();

        if(!checkOptions(needUpper, needLower, needDigit, needSymbol)){
            pwd_txtView.setText("Not enough usable characters defined");
            return;
        }

        int pwd_len = (int) pwd_slider.getValue();

        String password = util.genPassword(pwd_len, needUpper, needLower, needDigit, needSymbol);
        pwd_txtView.setText(password);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}