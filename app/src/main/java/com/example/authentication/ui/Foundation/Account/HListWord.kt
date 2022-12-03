package com.example.authentication.ui.Foundation.Account

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun HListWord() {
    LazyRow(contentPadding = PaddingValues(16.dp)){
        items(8){
            itemWord(it.toString())
        }
    }
}