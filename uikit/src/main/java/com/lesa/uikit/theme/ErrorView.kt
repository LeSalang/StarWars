package com.lesa.uikit.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.lesa.uikit.R

@Composable
fun ErrorView(
    error: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.error_face),
            contentDescription = "error",
            tint = MaterialTheme.colorScheme.error
        )
        Text(
            text = "Error",
            color = MaterialTheme.colorScheme.error
        )
    }
}
