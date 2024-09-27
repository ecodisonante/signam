package com.ecodisonante.signam.ui.user

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ecodisonante.signam.MainActivity
import com.ecodisonante.signam.ui.components.CustomAlertInfo
import com.ecodisonante.signam.ui.components.CustomCard
import com.ecodisonante.signam.ui.components.CustomTextField
import com.ecodisonante.signam.ui.components.FatMainButton
import com.ecodisonante.signam.ui.components.MainButton
import com.ecodisonante.signam.ui.theme.lightBG
import com.ecodisonante.signam.viewmodel.UserViewModel

@Composable
fun RegisterScreen(viewModel: UserViewModel) {
    val context = LocalContext.current
    val user by viewModel.user
    val showDialog by viewModel.showDialog
    val dialogTitle by viewModel.dialogTitle
    val dialogMessage by viewModel.dialogMessage
    val successAction by viewModel.successAction

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = lightBG)
            .padding(top = 30.dp)
            .fillMaxSize()
    ) {
        CustomCard(customHeight = 500) {
            CustomTextField(
                value = user.name,
                label = "Nombre",
                onValueChange = { viewModel.updateUser(name = it) })

            Spacer(modifier = Modifier.size(15.dp))

            CustomTextField(
                value = user.email,
                label = "Correo",
                onValueChange = { viewModel.updateUser(email = it) })

            Spacer(modifier = Modifier.size(15.dp))

            CustomTextField(
                value = user.passwd,
                label = "Contraseña",
                onValueChange = { viewModel.updateUser(password = it) },
                isPassword = true
            )

            Row {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .width(250.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.size(15.dp))

                    FatMainButton(text = "Registrarse", onClick = { viewModel.registerUserWithEmailPassword() })

                    Spacer(modifier = Modifier.size(15.dp))

                    MainButton(
                        text = "Volver",
                        onClick = {
                            context.startActivity(Intent(context, MainActivity::class.java))
                        },
                    )
                }
            }
        }

        CustomAlertInfo(
            showDialog = showDialog,
            onDismiss = { viewModel.dismissDialog() },
            title = dialogTitle,
            message = dialogMessage,
            onConfirm = {
                if (successAction) {
                    context.startActivity(Intent(context, MainActivity::class.java))
                }
                viewModel.dismissDialog()
            },
        )
    }
}
