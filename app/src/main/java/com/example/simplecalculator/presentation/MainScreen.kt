package com.example.simplecalculator.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplecalculator.R
import com.example.simplecalculator.model.OneCalculatorItem

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    darkTheme: Boolean,
    onThemeUpdated: () -> Unit,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val tertiaryColor = MaterialTheme.colorScheme.tertiary

    val uiState by viewModel.uiState.collectAsState()

    viewModel.setupCalculationList(
        primaryColor = primaryColor,
        secondaryColor = secondaryColor,
        tertiaryColor = tertiaryColor
    )

    ScreenContent(
        modifier = modifier,
        darkTheme = darkTheme,
        onThemeUpdated = onThemeUpdated,
        uiState = uiState,
        onCalculationItemClicked = viewModel::onCalculationItemClicked
    )
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier,
    darkTheme: Boolean,
    onThemeUpdated: () -> Unit,
    uiState: MainScreenUiState,
    onCalculationItemClicked: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenHeader(
            darkTheme = darkTheme,
            onThemeUpdated = onThemeUpdated
        )

        ScreenBody(
            uiState = uiState,
            onCalculationItemClicked = onCalculationItemClicked,
            calculationList = uiState.calculationList
        )
    }

}


@Composable
fun ScreenHeader(
    modifier: Modifier = Modifier,
    darkTheme: Boolean,
    onThemeUpdated: () -> Unit
) {

    ThemeSwitcher(
        modifier = modifier
            .fillMaxSize(),
        darkTheme = darkTheme,
        onThemeUpdated = onThemeUpdated
    )
}

@Composable
fun ScreenBody(
    modifier: Modifier = Modifier,
    uiState: MainScreenUiState,
    calculationList: List<OneCalculatorItem>,
    onCalculationItemClicked: (Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = uiState.equalsPressed,
            enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1000)),
        ) {
            Text(
                text = uiState.expression,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (uiState.equalsPressed) uiState.result else uiState.expression,
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))
        CalculationBox(
            calculationList = calculationList,
            onCalculationItemClicked = onCalculationItemClicked
        )
    }
}

@Composable
fun CalculationBox(
    modifier: Modifier = Modifier,
    calculationList: List<OneCalculatorItem>,
    onCalculationItemClicked: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(calculationList, key = { _, item -> item.text }) { index, item ->
            OneCalculationItem(
                oneCalculationItemClicked = { onCalculationItemClicked(index) },
                oneCalculationItem = item,
            )
        }

    }
}

@Composable
fun OneCalculationItem(
    modifier: Modifier = Modifier,
    oneCalculationItemClicked: () -> Unit,
    oneCalculationItem: OneCalculatorItem,
) {
    Box(
        modifier = modifier
            .size(72.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(oneCalculationItem.backgroundColor)
            .clickable {
                oneCalculationItemClicked()
            },
        contentAlignment = Alignment.Center
    ) {
        if (oneCalculationItem.icon != null) {
            Icon(
                painter = painterResource(id = oneCalculationItem.icon),
                contentDescription = "icon",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text(
                text = oneCalculationItem.text,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}