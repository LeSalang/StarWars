package com.lesa.uikit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ErrorView(
    error: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.error_face),
            contentDescription = "error",
            tint = MaterialTheme.colorScheme.error
        )
        Text(
            text = "Network error".uppercase(),
            color = MaterialTheme.colorScheme.error
        )
    }
}
