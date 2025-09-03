package jp.ac.meijou.android.s241205161;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import jp.ac.meijou.android.s241205161.databinding.ActivityCalculatorBinding;
import jp.ac.meijou.android.s241205161.databinding.ActivityMainBinding;

public class Calculator extends AppCompatActivity {

    private ActivityCalculatorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCalculatorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        float result = 0;

        binding.equalButton.setOnClickListener(view -> {
            String result_text = Float.toString(result);
            binding.resultText.setText(result_text);
        });


    }
}