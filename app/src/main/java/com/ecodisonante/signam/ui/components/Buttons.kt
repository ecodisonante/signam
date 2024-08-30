package com.ecodisonante.signam.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FatMainButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MainButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        padding = PaddingValues(all = 20.dp)
    )
}

@Composable
fun MainButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(vertical = 0.dp)
) {
    Button(
        onClick = onClick,
        modifier = modifier.width(250.dp),
        contentPadding = padding
    ) {
        Text(
            text = text,
            fontSize = 20.sp
        )
    }
}
