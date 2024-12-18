package com.example.simplecalculator.presentation

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.simplecalculator.R
import com.example.simplecalculator.model.OneCalculatorItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiState = _uiState.asStateFlow()

    fun updateIsDarkTheme(isDarkTheme: Boolean) {
        _uiState.update {
            it.copy(
                darkTheme = isDarkTheme
            )
        }
    }

    fun setupCalculationList(
        primaryColor: Color,
        secondaryColor: Color,
        tertiaryColor: Color,

        ) {
        _uiState.update {
            it.copy(
                calculationList = listOf(
                    OneCalculatorItem(
                        text = "C",
                        backgroundColor = secondaryColor,
                    ), OneCalculatorItem(
                        text = "%", backgroundColor = secondaryColor
                    ), OneCalculatorItem(
                        icon = R.drawable.delete, backgroundColor = secondaryColor
                    ), OneCalculatorItem(
                        text = "/", backgroundColor = primaryColor
                    ), OneCalculatorItem(
                        text = "7", backgroundColor = tertiaryColor
                    ), OneCalculatorItem(
                        text = "8", backgroundColor = tertiaryColor
                    ), OneCalculatorItem(
                        text = "9", backgroundColor = tertiaryColor
                    ), OneCalculatorItem(
                        text = "*", backgroundColor = primaryColor
                    ), OneCalculatorItem(
                        text = "4", backgroundColor = tertiaryColor
                    ), OneCalculatorItem(
                        text = "5", backgroundColor = tertiaryColor
                    ), OneCalculatorItem(
                        text = "6", backgroundColor = tertiaryColor
                    ), OneCalculatorItem(
                        text = "-", backgroundColor = primaryColor
                    ), OneCalculatorItem(
                        text = "1", backgroundColor = tertiaryColor
                    ), OneCalculatorItem(
                        text = "2", backgroundColor = tertiaryColor
                    ), OneCalculatorItem(
                        text = "3", backgroundColor = tertiaryColor
                    ), OneCalculatorItem(
                        text = "+", backgroundColor = primaryColor
                    ), OneCalculatorItem(
                        text = "00", backgroundColor = tertiaryColor
                    ), OneCalculatorItem(
                        text = "0", backgroundColor = tertiaryColor
                    ), OneCalculatorItem(
                        text = ".", backgroundColor = tertiaryColor
                    ), OneCalculatorItem(
                        text = "=", backgroundColor = primaryColor
                    )
                )
            )
        }
    }

    fun onCalculationItemClicked(calculationItemIndex: Int) {
        when (calculationItemIndex) {
            0 -> {
                _uiState.update {
                    it.copy(
                        expression = "", result = ""
                    )
                }
            }

            1 -> {
                _uiState.update {
                    it.copy(
                        expression = it.expression + "%",
                    )
                }
            }

            2 -> {
                if (_uiState.value.expression.isNotEmpty()) {
                    _uiState.update {
                        it.copy(
                            expression = it.expression.removeSuffix(
                                it.expression.last().toString()
                            ),
                        )
                    }
                }

            }

            3 -> {
                _uiState.update {
                    it.copy(
                        expression = it.expression + "/",
                    )
                }
            }

            4 -> {
                _uiState.update {
                    it.copy(
                        expression = it.expression + "7",
                    )
                }
            }

            5 -> {
                _uiState.update {
                    it.copy(
                        expression = it.expression + "8",
                    )
                }
            }

            6 -> {
                _uiState.update {
                    it.copy(
                        expression = it.expression + "9",
                    )
                }
            }

            7 -> {
                _uiState.update {
                    it.copy(
                        expression = it.expression + "*",
                    )
                }
            }

            8 -> {
                _uiState.update {
                    it.copy(
                        expression = it.expression + "4",
                    )
                }
            }

            9 -> {
                _uiState.update {
                    it.copy(
                        expression = it.expression + "5",
                    )
                }
            }

            10 -> {
                _uiState.update {
                    it.copy(
                        expression = it.expression + "6",
                    )
                }
            }

            11 -> {
                _uiState.update {
                    it.copy(
                        expression = it.expression + "-",
                    )
                }
            }

            12 -> {
                _uiState.update {
                    it.copy(
                        expression = it.expression + "1",
                    )
                }
            }

            13 -> {
                _uiState.update {
                    it.copy(
                        expression = it.expression + "2",
                    )
                }
            }

            14 -> {
                _uiState.update {
                    it.copy(
                        expression = it.expression + "3",
                    )
                }
            }

            15 -> {
                _uiState.update {
                    it.copy(
                        expression = it.expression + "+",
                    )


                }
            }

            16 -> {
                _uiState.update {
                    it.copy(
                        expression = it.expression + "00",
                    )
                }
            }

            17 -> {
                _uiState.update {
                    it.copy(
                        expression = it.expression + "0",
                    )
                }
            }

            18 -> {
                _uiState.update {
                    it.copy(
                        expression = it.expression + ".",
                    )
                }
            }

            19 -> {
                evaluateExpression()
            }
        }
    }

    private fun onEqualsClicked() {
        val expression = _uiState.value.expression
        val result = evaluatePostfixExpression(expression)
        _uiState.update {
            it.copy(
                result = ""
            )
        }
    }

    private fun evaluatePostfixExpression(expression: String): String {
        var result = "" // 8 2 / 7 + 4 2
        var stack = "" // - *
        expression.forEach { char ->
            if (char.isDigit() || char == '.') {
                result += char
            } else {
                when (char) {
                    '*' -> {
                        Log.d("5555555", "evaluateExpression: * ")
                        if (stack.isNotEmpty()) {
                            stack.forEach { stackChar ->
                                when (stackChar) {
                                    '/' -> {
                                        result += stackChar
                                        stack = stack.replace("/", "*")
                                    }

                                    '+', '-' -> {
                                        stack += '*'
                                    }

                                    else -> {
                                        result += '*'
                                    }
                                }
                            }
                        } else {
                            stack += '*'
                        }

                    }

                    '/' -> {
                        Log.d("5555555", "evaluateExpression: / ")
                        if (stack.isNotEmpty()) {
                            stack.forEach { stackChar ->
                                when (stackChar) {
                                    '*' -> {
                                        result += stackChar
                                        stack = stack.replace("*", "/")
                                    }

                                    '+', '-' -> {
                                        stack += '/'
                                    }

                                    else -> {
                                        result += '/'

                                    }
                                }
                            }
                        } else {
                            stack += '/'
                        }

                    }

                    '+' -> {
                        Log.d("5555555", "evaluateExpression: + ")
                        if (stack.isNotEmpty()) {
                            stack.forEach { stackChar ->
                                if (stackChar == '*' || stackChar == '/' || stackChar == '-') {
                                    result += stackChar
                                    stack = stack.replace(stackChar.toString(), "+")
                                } else {
                                    result += '+'
                                }
                            }
                        } else {
                            stack += '+'
                        }

                    }

                    '-' -> {
                        Log.d("5555555", "evaluateExpression: - ")
                        if (stack.isNotEmpty()) {
                            stack.forEach { stackChar ->
                                if (stackChar == '*' || stackChar == '/' || stackChar == '+') {
                                    result += stackChar
                                    stack = stack.replace(stackChar.toString(), "-")
                                }
                            }
                        } else {
                            stack += '-'
                        }

                    }
                }
            }
        }
        if (stack.isNotEmpty()) {
            val reversedStack = stack.reversed()
            reversedStack.forEach { char ->
                result += char
            }
        }
        Log.d("5555555", "evaluateExpression: $result")
        Log.d("5555555", "evaluateExpression: $stack")
        return result
    }

    private fun evaluateExpression() {
        val postfixExpression = evaluatePostfixExpression(_uiState.value.expression)
        var stack = ""// 11 4 2
        var result = ""
        postfixExpression.forEach { char ->
            if (char.isDigit() || char == '.') {
                stack += char
            } else {
                when (char) {
                    '*' -> {
                        val num1 = stack[stack.length - 2].toString().toInt()
                        val num2 = stack[stack.length - 1].toString().toInt()
                        val res = num1 * num2
                        stack = stack.removeRange(stack.length - 2, stack.length)
                        Log.d("5555555", "evaluateExpression: stack $stack")
                        stack += res
                        Log.d("5555555", "evaluateExpression: $num1, $num2, $res")
                    }

                    '/' -> {
                        val num1 = stack[stack.length - 2].toString().toInt() // 8
                        val num2 = stack[stack.length - 1].toString().toInt() // 2
                        val res = num1 / num2 // 4
                        stack = stack.removeRange(stack.length - 2, stack.length)
                        Log.d("5555555", "evaluateExpression: stack $stack")
                        stack += res //4
                        Log.d("5555555", "evaluateExpression: $num1, $num2, $res")
                    }

                    '+' -> {
                        val num1 = stack[stack.length - 2].toString().toInt() //4
                        val num2 = stack[stack.length - 1].toString().toInt() // 7
                        val res = num1 + num2
                        stack = stack.removeRange(stack.length - 2, stack.length)
                        Log.d("5555555", "evaluateExpression: stack $stack")
                        stack += res
                        Log.d("5555555", "evaluateExpression: $num1, $num2, $res")
                    }

                    '-' -> {
                        val num1 = stack[stack.length - 2].toString().toInt()
                        val num2 = stack[stack.length - 1].toString().toInt()
                        val res = num1 - num2
                        stack = stack.removeRange(stack.length - 2, stack.length)
                        Log.d("5555555", "evaluateExpression: stack $stack")
                        stack += res
                        Log.d("5555555", "evaluateExpression: $num1, $num2, $res")
                    }
                }
            }
        }
        Log.d("5555555", "evaluateExpression: eltany $result")
        Log.d("5555555", "evaluateExpression:  eltany $stack")
    }
}