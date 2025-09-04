package jp.ac.meijou.android.s241205161;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.util.Optional;

import jp.ac.meijou.android.s241205161.databinding.ActivityMain5Binding;
import jp.ac.meijou.android.s241205161.databinding.ActivityMain6Binding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity6 extends AppCompatActivity {

    private final OkHttpClient okHttpClient = new OkHttpClient();
    private ActivityMain6Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMain6Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.getButton.setOnClickListener(view -> {
            var text = binding.getTextEditText.getText().toString();
            var url = Uri.parse("https://placehold.jp")
                    .buildUpon()
                    .appendPath(binding.getTextSizeEditText.getText().toString())
                    .appendPath(binding.getBackgroundColorEditText.getText().toString())
                    .appendPath(binding.getTextColorEditText.getText().toString())
                    .appendPath(binding.getWidthEditText.getText().toString()+"x"+binding.getHeightEditText.getText().toString()+".png")
                    .appendQueryParameter("text", text)
                    .build()
                    .toString();
            Log.v("MainActivity6", url);
            getImage(url);
        });
    }
    private void getImage(String url){
        var request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                var bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                runOnUiThread(() -> binding.contentImage.setImageBitmap(bitmap));
            }
        });
    }
}