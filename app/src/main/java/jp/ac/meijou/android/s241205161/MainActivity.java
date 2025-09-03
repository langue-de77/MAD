package jp.ac.meijou.android.s241205161;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import jp.ac.meijou.android.s241205161.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PrefDataStore prefDataStore;

    @Override
    protected void onStart(){
        super.onStart();
        prefDataStore.get("name",String.class).ifPresent(name -> binding.text.setText(name));
        binding.colorText.setText(getIntent().getStringExtra("cipher"));
    }

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

        prefDataStore = PrefDataStore.getInstance(this);

        binding.changeButton.setOnClickListener(view -> {
            var color = (-1 * (binding.colorText.getCurrentTextColor())+1600000) % 16777216;
            if (color > 0)
                color = color * -1;
            binding.colorText.setText(String.valueOf(color));
            binding.colorText.setTextColor(color);

            binding.text.setText(binding.editText.getText().toString());
        });
        binding.saveButton.setOnClickListener(view -> {
            prefDataStore.set("name", binding.editText.getText().toString());
            binding.colorText.setText("成功");
        });
    }
}