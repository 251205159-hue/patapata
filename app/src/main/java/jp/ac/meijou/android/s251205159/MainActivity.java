package jp.ac.meijou.android.s251205159;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import jp.ac.meijou.android.s251205159.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private PrefDataStore dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dataStore = PrefDataStore.getInstance(getApplicationContext());

        binding.item12.setText("よろしくお願いします");
        dataStore.getString("text").ifPresent(text -> {
            if ("a".equals(text)) {
                binding.textview1.setText("Aの画像です");
                binding.imageView3.setImageResource(R.drawable.baseline_drive_eta_24);
            } else if ("b".equals(text)) {
                binding.textview1.setText("Bの画像です");
                binding.imageView3.setImageResource(R.drawable.ic_launcher_background);
            } else {
                binding.textview1.setText("知らない画像です");
                binding.imageView3.setImageResource(R.drawable.ic_launcher_foreground);
            }
        });
        // 保存済みの名前があれば読み込んで表示
        dataStore.getString("name")
                .ifPresent(name -> binding.item12.setText(name));

        setOnClickListener();
    }

    private void setOnClickListener() {
        // Changeボタン: EditTextの内容をitem12に反映するだけ（保存はしない）
        binding.item2.setOnClickListener(view -> {
            var text = binding.name1.getText().toString();
            binding.item12.setText(text);
        });

        // Saveボタン: EditTextの内容をitem12に反映し、データストアに保存する
        binding.save1.setOnClickListener(view -> {
            var text = binding.name1.getText().toString();
            binding.item12.setText(text);
            dataStore.setString("name", text);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}