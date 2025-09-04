package jp.ac.meijou.android.s241205161;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Optional;

import jp.ac.meijou.android.s241205161.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;

    private final ActivityResultLauncher<Intent> getActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                switch (result.getResultCode()){
                    case RESULT_OK:
                        Optional.ofNullable(result.getData())
                                .map(data -> data.getStringExtra("ret"))
                                .map(text -> "Result: " + text)
                                .ifPresent(text -> binding.resultText.setText(text));
                        break;
                    case RESULT_CANCELED:
                        binding.resultText.setText("Result: Cancelled");
                        break;
                    default:
                        binding.resultText.setText("Result: Unknown(" + result.getResultCode() + ")");
                        break;
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.buttonA.setOnClickListener(view -> {
            Intent intent = null;
            switch (Integer.parseInt(binding.editTextText2.getText().toString())){
                case 1:
                    intent = new Intent(this, MainActivity.class);
                    break;
                case 2:
                    intent = new Intent(this, MainActivity2.class);
                    break;
                case 3:
                    intent = new Intent(this, Calculator.class);
                    break;
                case 4:
                    intent = new Intent(this, MainActivity4.class);
                    break;
                case 5:
                    intent = new Intent(this, MainActivity5.class);
                    break;
                case 6:
                    intent = new Intent(this, MainActivity6.class);
                    break;
                case 7:
                    intent = new Intent(this, MainActivity7.class);
                    break;
                default:
                    binding.editTextText2.setText("1~7の数字を入力してください");
                    return;
            }
            startActivity(intent);
        });
        binding.buttonB.setOnClickListener(view -> {
            var intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://note.com/ayasiki_gerahu"));
            startActivity(intent);
        });
        binding.sendButton.setOnClickListener(view -> {
            var text = binding.editTextText.getText().toString();
            var intent = new Intent(this, MainActivity4.class);
            intent.putExtra("cipher", text);
            startActivity(intent);
        });
        binding.bootButton.setOnClickListener(view -> {
            var intent = new Intent(this, MainActivity4.class);
            getActivityResult.launch(intent);
        });
    }
}