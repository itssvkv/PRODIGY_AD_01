package com.example.simplecalculator.presentation

import com.example.simplecalculator.model.OneCalculatorItem

data class MainScreenUiState(
    val darkTheme: Boolean = false,
    val expression: String = "",
    val result: String = "",
    val expressionError: Boolean = false,
    val resultError: Boolean = false,
    val expressionErrorText: String = "",
    val resultErrorText: String = "",
    val equalsPressed: Boolean = false,
    val calculationList: List<OneCalculatorItem> = emptyList()
)
