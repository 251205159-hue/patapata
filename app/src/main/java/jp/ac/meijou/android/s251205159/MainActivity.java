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

    /**
     * @param savedInstanceState 以前に保存された状態データ
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.item_2), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    TextView text = findViewById(R.id.item_12);
        text.setText("よろしくお願いします");
        binding.item12.setText("こんにちは");
        binding.change1.setOnClickListener(view -> {
            binding.save1.setText("はーい！");
        });
        setOnClickListener();
        }
        private void setOnClickListener() {
            binding.change1.setOnClickListener(view -> {
                var text = binding.name1.getText();
                binding.item12.setText(text);
            });
        }
        private void setTextChangedListener() {
            binding.name1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

             @Override
                public void afterTextChanged(Editable editable) {
                binding.name1.setText(editable.toString());
                }
            });
            dataStore = PrefDataStore.getInstance(getApplicationContext());

            // "name" というキーで保存されている文字列を読み込み、存在すればTextViewにセットする
            dataStore.getString("name")
                    .ifPresent(name -> binding.save1.setText(name));

            // Changeボタンが押されたら、EditTextの内容をTextViewに反映する（保存はしない）
            binding.save1.setOnClickListener(view -> {
                var text = binding.name1.getText().toString();
                binding.item12.setText(text);
            });

            binding.save1.setOnClickListener(view -> {
                var text = binding.change1.getText().toString();
                dataStore.setString("name", text);
            });
        }

    @Override
    protected void onStart() {
        super.onStart();
//        dataStore.getString("name")
//                .ifPresent(name -> binding.lesson5Text.setText(name));
    }
}


