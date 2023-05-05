package com.example.calculatethis

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
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


            )

        for (button in numberButtons) {
            button.setOnClickListener {
                numberAction(it)
            }

            binding.buttonAc.setOnClickListener {
                allClearAction(it)
            }
            val opButtons = listOf<Button>(
                binding.buttonDivide,
                binding.buttonMultiply,
                binding.buttonAdd,
                binding.buttonSubtract,
            )
            for (button in opButtons) {
                button.setOnClickListener {
                    operatorAction(it)
                }
            }

            binding.buttonBackspace.setOnClickListener {
                backspaceAction(it)
            }
            binding.buttonEquals.setOnClickListener {
                equalAction(it)
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
        if (view is Button && canAddOperation) {
            binding.workingsTV.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
    }

    // Function to handle all clear button click
    fun allClearAction(view: View) {
        binding.workingsTV.text = ""
        binding.resultTV.text = ""
    }

    // Function to handle backspace button click
    fun backspaceAction(view: View) {
        val length = binding.workingsTV.text.length
        if (length > 0) {
            binding.workingsTV.text = binding.workingsTV.text.subSequence(0, length - 1)
        }
    }

    // Function to handle equal button click
    fun equalAction(view: View) {
        // TODO: Implement logic to evaluate the expression in workingsTV and update resultTV

        binding.resultTV.text = calculateResults()

    }

    private fun calculateResults(): String {
        val digitsOperators = digitsOperators()
        if (digitsOperators.isEmpty()) {
            return ""
        }


        val timesDivision = timesDivisionCalculate(digitsOperators)
        if (timesDivision.isEmpty()) {
            return ""
        }

        val result = addSubtractCalculate(timesDivision)
        return result.toString()

    }

    private fun addSubtractCalculate(passedList: List<Any>): Float {
        var result = passedList[0] as Float

        for (i in 1 until passedList.size step 2) {
            val operator = passedList[i] as Char
            val nextDigit = passedList[i + 1] as Float

            when (operator) {
                '+' -> result += nextDigit
                '-' -> result -= nextDigit
            }
        }

        return result
    }


    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any> {
        var result = passedList.toMutableList()
        while (result.contains("x") || result.contains("/")) {
            val prevResult = result.toMutableList()
            result = calcTimeDiv(result, "x")
            if (prevResult == result) {
                result = calcTimeDiv(result, "/")
            }
        }
        return result
    }



    private fun calcTimeDiv(passedList: MutableList<Any>, operator: String): MutableList<Any> {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size
        for (i in passedList.indices) {
            if (passedList[i] is Char && i != passedList.lastIndex && i < restartIndex && passedList[i] == operator) {
                val prevDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float
                when (operator) {
                    "x" -> {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }

                    "/" -> {
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                }
            } else if (i >= restartIndex) {
                newList.add(passedList[i])
            }
        }
        return newList
    }


    private fun digitsOperators(): MutableList<Any> {

        val list = mutableListOf<Any>()
        var currentDigit = ""
        for (charecter in binding.workingsTV.text) {
            if (charecter.isDigit() || charecter.toString() == (".")) {
                currentDigit += charecter
            } else {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(charecter)
            }
        }

        if (currentDigit != "") {
            list.add(currentDigit.toFloat())
        }
        return list
    }

}




