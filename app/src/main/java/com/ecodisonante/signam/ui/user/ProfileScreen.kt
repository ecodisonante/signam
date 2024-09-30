package com.ecodisonante.signam.ui.user

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun ProfileScreen(viewModel: UserViewModel) {
    val context = LocalContext.current
    val user by viewModel.user
    val showDialog by viewModel.showDialog
    val dialogTitle by viewModel.dialogTitle
    val dialogMessage by viewModel.dialogMessage
    val successAction by viewModel.successAction
    val showDeleteDialog = remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = lightBG)
            .padding(top = 15.dp, bottom = 30.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CustomCard(customHeight = 800) {
            CustomTextField(value = user.name,
                label = "Nombre",
                onValueChange = { viewModel.updateUser(name = it) })

            Spacer(modifier = Modifier.size(15.dp))

            OutlinedTextField(
                shape = RoundedCornerShape(10.dp),
                value = user.email,
                label = { Text("Correo") },
                readOnly = true,
                onValueChange = { viewModel.updateUser(email = it) },
                singleLine = true,
            )

            Spacer(modifier = Modifier.size(15.dp))

            CustomTextField(value = user.city,
                label = "Ciudad",
                onValueChange = { viewModel.updateUser(city = it) })

            Spacer(modifier = Modifier.size(15.dp))

            CustomTextField(value = user.phone,
                label = "Teléfono",
                onValueChange = { viewModel.updateUser(phone = it) })

            Spacer(modifier = Modifier.size(15.dp))

            OutlinedTextField(
                shape = RoundedCornerShape(10.dp),
                value = user.about,
                label = { Text("Sobre Mi") },
                onValueChange = { viewModel.updateUser(about = it) },
                singleLine = false,
                minLines = 4
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

                    FatMainButton(text = "Actualizar", onClick = { viewModel.updateUserProfile() })

                    Spacer(modifier = Modifier.size(15.dp))

                    MainButton(
                        text = "Volver",
                        onClick = {
                            context.startActivity(Intent(context, MainActivity::class.java))
                        },
                    )

                    Spacer(modifier = Modifier.size(45.dp))

                    MainButton(text = "Darse de Baja", onClick = { showDeleteDialog.value = true })
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

        if (showDeleteDialog.value) {
            AlertDialog(
                title = { Text("Pero por queeee!!!") },
                text = { Text("No te vayas!! Todavía tenemos muchas cosas que algún día haremos con esta hermosa aplicación!!") },
                onDismissRequest = { showDeleteDialog.value = false },
                confirmButton = {
                    Button(
                        onClick = {
                            showDeleteDialog.value = false
                            viewModel.deleteFirebaseUser()
//                            context.startActivity(Intent(context, MainActivity::class.java))

                        },
                    ) { Text("Me voy") }
                },
                dismissButton = {
                    Button(onClick = {
                        Toast.makeText(
                            context, "Que buena broma nos gastaste!!", Toast.LENGTH_LONG
                        ).show()
                        showDeleteDialog.value = false
                    }) { Text("Me quedo") }
                },
            )
        }
    }
}
