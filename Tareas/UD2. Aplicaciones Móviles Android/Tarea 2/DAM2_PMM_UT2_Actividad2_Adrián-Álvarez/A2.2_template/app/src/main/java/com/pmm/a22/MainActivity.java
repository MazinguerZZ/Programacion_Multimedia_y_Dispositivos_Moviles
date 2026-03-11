package com.pmm.a22;

import static com.pmm.a22.R.*;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pmm.a22.calculator.Calculator;

public class MainActivity extends AppCompatActivity {

    private final Calculator _calculator = new Calculator();

    private String resultado = "";
    private boolean calculado = false;
    private boolean operadorPresionado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calculator);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void operandClick(View view) {
        if (calculado) clearClick(null);

        int operandButtonId = view.getId();
        System.out.println(operandButtonId);
        String operand = "";

        if (operandButtonId == R.id.Button0) operand = "0";
        else if (operandButtonId == R.id.Button1) operand = "1";
        else if (operandButtonId == R.id.Button2) operand = "2";
        else if (operandButtonId == R.id.Button3) operand = "3";
        else if (operandButtonId == R.id.Button4) operand = "4";
        else if (operandButtonId == R.id.Button5) operand = "5";
        else if (operandButtonId == R.id.Button6) operand = "6";
        else if (operandButtonId == R.id.Button7) operand = "7";
        else if (operandButtonId == R.id.Button8) operand = "8";
        else if (operandButtonId == R.id.Button9) operand = "9";

        _calculator.setOperand(operand);
        resultado += operand;

        TextView actuPantalla = findViewById(R.id.Resultado);
        actuPantalla.setText(resultado);

        operadorPresionado = false;
    }

    @SuppressLint("SetTextI18n")
    public void operatorClick(View view) {
        int operatorButtonId = view.getId();
        System.out.println(operatorButtonId);
        Calculator.Operators operator = null;

        if (!_calculator.isNewOperation() && !operadorPresionado) {
            try {
                double result = _calculator.calculate();
                resultado = String.valueOf(result);

                TextView tv = findViewById(R.id.Resultado);
                tv.setText(resultado);

                _calculator.clear();
                _calculator.setOperand(String.valueOf(result));

            } catch (Calculator.DivisionByZeroException e) {
                TextView tvError = findViewById(R.id.Error);
                tvError.setText("No se puede dividir entre 0");
                resultado = "";
                return;
            }
        }

        if (operatorButtonId == R.id.ButtonEquals) {
            if (!_calculator.isNewOperation()) {
                try {
                    double result = _calculator.calculate();
                    resultado = String.valueOf(result);

                    TextView tv = findViewById(R.id.Resultado);
                    tv.setText(resultado);
                    calculado = true;
                } catch (Calculator.DivisionByZeroException e) {
                    TextView tvError = findViewById(R.id.Error);
                    tvError.setText("No se puede dividir entre 0");
                    resultado = "";
                }
            }
            operadorPresionado = false;
            return;
        }

        if (operatorButtonId == R.id.ButtonSuma) operator = Calculator.Operators.ADD;
        else if (operatorButtonId == R.id.ButtonResta) operator = Calculator.Operators.SUBSTRACT;
        else if (operatorButtonId == R.id.ButtonX) operator = Calculator.Operators.MULTIPLY;
        else if (operatorButtonId == R.id.ButtonDivision) operator = Calculator.Operators.DIVIDE;

        _calculator.setOperator(operator);
        calculado = false;

        String operatorSymbol = "";
        if (operator == Calculator.Operators.ADD) operatorSymbol = "+";
        else if (operator == Calculator.Operators.SUBSTRACT) operatorSymbol = "-";
        else if (operator == Calculator.Operators.MULTIPLY) operatorSymbol = "×";
        else if (operator == Calculator.Operators.DIVIDE) operatorSymbol = "÷";

        if (operadorPresionado) {
            if (resultado.length() > 0 && "+-×÷".indexOf(resultado.charAt(resultado.length() - 1)) != -1) {
                resultado = resultado.substring(0, resultado.length() - 1) + operatorSymbol;
            }
        } else {
            resultado += operatorSymbol;
        }

        TextView pantalla = findViewById(R.id.Resultado);
        pantalla.setText(resultado);
        operadorPresionado = true;
    }

    public void clearClick(View view) {
        _calculator.clear();
        calculado = false;
        resultado = "";
        operadorPresionado = false;

        TextView tvSalida = findViewById(R.id.Resultado);
        tvSalida.setText("0");

        TextView tvLimpiar = findViewById(R.id.Error);
        tvLimpiar.setText("");
    }

    public void onSwitchChanged(View view) {
        android.widget.Switch tema = findViewById(R.id.switch1);
        View mainLayout = findViewById(R.id.main);

        if (tema.isChecked()) {
            mainLayout.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        } else {
            mainLayout.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }
}