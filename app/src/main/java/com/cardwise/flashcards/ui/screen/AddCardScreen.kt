package com.cardwise.flashcards.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cardwise.flashcards.ui.viewmodel.CardViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(
    viewModel: CardViewModel = hiltViewModel(),
    onCardAdded: () -> Unit
) {
    var foreignWord by remember { mutableStateOf("") }
    var translation by remember { mutableStateOf("") }
    var languageId by remember { mutableStateOf(1) } // Default language ID is 1

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Card") },
                actions = {
                    IconButton(onClick = onCardAdded) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                value = foreignWord,
                onValueChange = { foreignWord = it },
                label = { Text("Word in Foreign Language") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = translation,
                onValueChange = { translation = it },
                label = { Text("Translation") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            // You can add language selection, for example, using a DropdownMenu
            Button(
                onClick = {
                    if (foreignWord.isNotBlank() && translation.isNotBlank()) {
                        viewModel.addCard(foreignWord, translation, languageId)
                        onCardAdded()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = foreignWord.isNotBlank() && translation.isNotBlank()
            ) {
                Text("Add Card")
            }
        }
    }
}
