package com.example.tugas2kalkulator

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tugas2kalkulator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDarkModeToggle()
        setupNumericButtons()
        setupOperatorButtons()
        setupClearButton()
        setupBackspaceButton()
        setupEqualsButton()
    }

    private fun setupDarkModeToggle() {
        binding.SwitchAtas.setOnCheckedChangeListener { _, isChecked ->
            val mode =
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }


    private fun setupNumericButtons() {
        val numericButtons = listOf(
            binding.numberZero to "0",
            binding.numberOne to "1",
            binding.numberTwo to "2",
            binding.numberThree to "3",
            binding.numberFour to "4",
            binding.numberFive to "5",
            binding.numberSix to "6",
            binding.numberSeven to "7",
            binding.numberEight to "8",
            binding.numberNine to "9",
            binding.buttonComa to "."
        )
        numericButtons.forEach { (button, value) ->
            button.setOnClickListener { appendOnExpressions(value, true) }
        }
    }


    private fun setupOperatorButtons() {
        val operatorButtons = listOf(
            binding.buttonPlus to " + ", binding.buttonMinus to " - ",
            binding.buttonTimes to " * ", binding.buttonDistribution to " / ",
            binding.buttonOpen to "(", binding.buttonClose to ")"
        )
        operatorButtons.forEach { (button, value) ->
            button.setOnClickListener { appendOnExpressions(value, false) }
        }
    }

    private fun setupClearButton() {
        binding.buttonClear.setOnClickListener {
            binding.tvExpression.text = ""
            binding.tvResult.text = ""
        }
    }

    private fun setupBackspaceButton() {
        binding.backspace.setOnClickListener {
            val string = binding.tvExpression.text.toString()
            if (string.isNotEmpty()) {
                binding.tvExpression.text = string.dropLast(1)
            }
            binding.tvResult.text = ""
        }
    }

    private fun setupEqualsButton() {
        binding.buttonTogheterWith.setOnClickListener {
            try {
                val expression = ExpressionBuilder(binding.tvExpression.text.toString()).build()
                val result = expression.evaluate()
                val longResult = result.toLong()
                binding.tvResult.text = if (result == longResult.toDouble()) {
                    longResult.toString()
                } else {
                    result.toString()
                }
            } catch (e: Exception) {
                Log.d("Exception", "Message: ${e.message}")
            }
        }
    }

    private fun appendOnExpressions(string: String, canClear: Boolean) {
        if (canClear && binding.tvResult.text.isNotEmpty()) {
            binding.tvExpression.text = ""
        }

        if (canClear) {
            binding.tvResult.text = ""
            binding.tvExpression.append(string)
        } else {
            binding.tvExpression.append(binding.tvResult.text)
            binding.tvExpression.append(string)
            binding.tvResult.text = ""
        }
    }
}