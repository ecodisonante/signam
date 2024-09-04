package com.ecodisonante.signam.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun CustomAlertInfo(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    buttonText: String = "Ok",
    title: String,
    message: String
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = { Button(onClick = onConfirm) { Text(buttonText) } },
            title = { Text(title) },
            text = { Text(message) }
        )
    }
}


@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    customHeight: Int = 450,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .width(260.dp)
            .height(customHeight.dp)
            .padding(top = 50.dp)
            .background(Color.Transparent),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xDDFFFFFF),
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            content()
        }
    }
}
