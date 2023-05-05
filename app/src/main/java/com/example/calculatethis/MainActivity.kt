package com.example.calculatethis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.calculatethis.databinding.ActivityMainBinding


private lateinit var binding: ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private var canAddOperation = false
    private var canAddDecimal = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bind all number buttons to numberAction() function
        val numberButtons = listOf<Button>(
            binding.button0,
            binding.button1,
            binding.button2,
            binding.button3,
            binding.button4,
            binding.button5,
            binding.button6,
            binding.button7,
            binding.button8,
            binding.button9,
            binding.buttonDecimal,
            binding.buttonAc,
            binding.buttonDivide,
            binding.buttonMultiply,
            binding.buttonEquals,
            binding.buttonAdd,
            binding.buttonSubtract,
        )

        for (button in numberButtons) {
            button.setOnClickListener {
                numberAction(it)
            }
        }


    }


    // Function to handle number button clicks
    fun numberAction(view: View) {
        if (view is Button) {
            if (view.text == ".") {
                if (canAddOperation) {
                    canAddOperation = false
                }
            }

            binding.workingsTV.append(view.text)
            canAddOperation = true
        }
    }

    // Function to handle operator button clicks
    fun operatorAction(view: View) {
        // TODO: Implement logic to add operator to workingsTV
        if (view is Button && canAddOperation) {
            binding.workingsTV.append(view.text)
            canAddOperation = false
            canAddOperation = true
        }
    }

    // Function to handle all clear button click
    fun allClearAction() {
        binding.workingsTV.text = "";
    }

    // Function to handle backspace button click
    fun backspaceAction() {
        val length = binding.workingsTV.text.length
        if (length > 0) {
            binding.workingsTV.text = binding.workingsTV.text.subSequence(0, length - 1)
        }
    }

    // Function to handle equal button click
    fun equalAction() {
        // TODO: Implement logic to evaluate the expression in workingsTV and update resultTV
    }



}


