package com.cardwise.flashcards.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cardwise.flashcards.ui.viewmodel.CardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningScreen(
    onExitlearning: () -> Unit,
    viewModel: LearningViewModel
) {
    // Существующий TopAppBar и другие UI компоненты

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        if (error != null) {
            Text(
                text = error!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        } else if (unlearnedCards.isEmpty()) {
            Text("You have no unlearned cards!", style = MaterialTheme.typography.titleMedium)
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Spacer для верхнего отступа
                Spacer(modifier = Modifier.weight(1f))

                val card = unlearnedCards.getOrNull(currentCardIndex)
                card?.let {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable { isFlipped = !isFlipped },
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(32.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (isFlipped) {
                                Text(
                                    text = it.translation,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            } else {
                                Text(
                                    text = it.foreignWord,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                        }
                    }
                }

                // Spacer для нижнего отступа
                Spacer(modifier = Modifier.weight(1f))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 16.dp)
                ) {
                    Button(
                        onClick = {
                            viewModel.markCardAslearned(card!!, true)
                            if (currentCardIndex < unlearnedCards.size - 1) {
                                currentCardIndex++
                            } else {
                                onExitlearning()
                            }
                            isFlipped = false
                        },
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text("Learned")
                    }
                    Button(
                        onClick = {
                            if (currentCardIndex < unlearnedCards.size - 1) {
                                currentCardIndex++
                            } else {
                                onExitlearning()
                            }
                            isFlipped = false
                        },
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text("Next")
                    }
                }
            }
        }
    }
}