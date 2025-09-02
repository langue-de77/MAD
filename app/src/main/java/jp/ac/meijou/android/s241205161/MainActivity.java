package jp.ac.meijou.android.s241205161;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import jp.ac.meijou.android.s241205161.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.changeButton.setOnClickListener(view -> {
            var color = (-1 * (binding.colorText.getCurrentTextColor())+1600000) % 16777216;
            if (color > 0)
                color = color * -1;
            binding.colorText.setText(String.valueOf(color));
            binding.colorText.setTextColor(color);
        });
        binding.applyButton.setOnClickListener(view -> {
            binding.text.setText(binding.editText.getText().toString() + ": Applied");
        });
        binding.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                binding.text.setText(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        binding.text.setText(R.string.author_lastName);
        binding.text.setTextColor(binding.text.getCurrentTextColor()+100);
    }
}