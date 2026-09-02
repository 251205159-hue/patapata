package jp.ac.meijou.android.s251205159;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Optional;
import java.util.function.BiFunction;

import jp.ac.meijou.android.s251205159.databinding.ActivityMain3Binding;

public class MainActivity3 extends AppCompatActivity {
    private ActivityMain3Binding binding;

    private int display;
    private int operand1;
    private int operand2;
    private Operator operator;

    /**
     * アクティビティが最初に作成されるときに呼び出されるメソッド。
     * 数字ボタン・演算子ボタン・AC・＝ ボタンそれぞれにクリックリスナーを設定します。
     *
     * @param savedInstanceState 以前に保存された状態データ（存在する場合のみ非null）
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.button0.setOnClickListener(view -> pushOperand(0));
        binding.button1.setOnClickListener(view -> pushOperand(1));
        binding.button2.setOnClickListener(view -> pushOperand(2));
        binding.button3.setOnClickListener(view -> pushOperand(3));
        binding.button4.setOnClickListener(view -> pushOperand(4));
        binding.button5.setOnClickListener(view -> pushOperand(5));
        binding.button6.setOnClickListener(view -> pushOperand(6));
        binding.button7.setOnClickListener(view -> pushOperand(7));
        binding.button8.setOnClickListener(view -> pushOperand(8));
        binding.button9.setOnClickListener(view -> pushOperand(9));

        // AC（All Clear）ボタン：入力値・演算子をすべてリセットしてディスプレイを0に戻す
        binding.buttonAC.setOnClickListener(view -> clear());

        // 演算子ボタン：選択した演算子を保持する（＝ボタン押下時に使用）
        binding.buttonTasu.setOnClickListener(view -> operator = Operator.PLUS);
        binding.buttonMai.setOnClickListener(view -> operator = Operator.MINUS);
        binding.buttonKake.setOnClickListener(view -> operator = Operator.MULTIPLY);
        binding.buttonDi.setOnClickListener(view -> operator = Operator.DIVIDE);


        binding.buttonIco.setOnClickListener(view -> calc());


        Optional.ofNullable(getIntent().getStringExtra("text"))
                .ifPresent(text -> binding.resultA.setText(text));


        binding.buttonOK.setOnClickListener(view -> {
            var intent = new Intent();
            intent.putExtra("ret", "OK");
            setResult(RESULT_OK, intent);
            finish();
        });

        // 【演習3】Cancelボタン：RESULT_CANCELED を1画面目に返し、Activity を破棄して戻る
        binding.buttonCancel.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void pushOperand(int num) {
        if (operator == null) {
            operand1 = operand1 * 10 + num;
            display = operand1;
        } else {
            operand2 = operand2 * 10 + num;
            display = operand2;
        }
        binding.resultA.setText(String.valueOf(display));
    }

    private void clear() {
        operand1 = 0;
        operand2 = 0;
        display = 0;
        operator = null;
        binding.resultA.setText(String.valueOf(display));
    }

    private void calc() {
        if (operator == null) {
            return;
        }
        display = operator.calc.apply(operand1, operand2);
        binding.resultA.setText(String.valueOf(display));
    }

    private enum Operator {
        /**
         * 加算（operand1 + operand2）
         */
        PLUS(Integer::sum),
        /**
         * 減算（operand1 − operand2）
         */
        MINUS((a, b) -> a - b),
        /**
         * 乗算（operand1 × operand2）
         */
        MULTIPLY((a, b) -> a * b),
        /**
         * 除算（operand1 ÷ operand2）※ゼロ除算は未ガード
         */
        DIVIDE((a, b) -> a / b);

        /**
         * この演算子に対応する計算処理。
         */
        public final BiFunction<Integer, Integer, Integer> calc;

        Operator(BiFunction<Integer, Integer, Integer> calc) {
            this.calc = calc;
        }
    }
}