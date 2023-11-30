package br.com.viniciusapps.gym_app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.viniciusapps.gym_app.ui.activity.RegisterActivity
import br.com.viniciusapps.gym_app.ui.theme.OrangeStrong


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthComponent(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (username: String, password: String) -> Unit,
    navController: NavController? = null,
    tela: Class<*>? = null
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val username = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }
        val passwordVisible = remember { mutableStateOf(false) }

        Text(
            text = text,
            modifier = modifier,
            style = TextStyle(
                fontSize = 40.sp,
                color = OrangeStrong,
                fontFamily = FontFamily.SansSerif
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = username.value,
            label = { Text(text = "Email") },
            singleLine = true,
            onValueChange = { username.value = it })

        Spacer(modifier = Modifier.height(20.dp))

        TextField(value = password.value,
            label = { Text(text = "Senha") },
            singleLine = true,
            onValueChange = { password.value = it },
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible.value)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description = if (passwordVisible.value) "Esconder Senha" else "Mostrar Senha"
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(imageVector = image, description)
                }
            })
        Spacer(modifier = Modifier.height(20.dp))
        IsRegisterScreen(tela, navController!!, password.value.text)
        Spacer(modifier = Modifier.height(20.dp))
        ElevatedButton(onClick = {
            onClick(username.value.text, password.value.text)
        }, colors = ButtonDefaults.buttonColors(
            OrangeStrong
        )) {
            Text(text = text)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IsRegisterScreen(tela: Class<*>? = null, navController: NavController, password: String) {
    if (tela != RegisterActivity::class.java) {
        Spacer(modifier = Modifier.height(20.dp))
        ClickableText(
            text = AnnotatedString("Já tem uma conta?"),
            onClick = {
                navController.navigate("register")
            }
        )
        return
    }
    val secondPassword = remember { mutableStateOf(TextFieldValue()) }
    val secondPasswordVisible = remember { mutableStateOf(false) }

    Spacer(modifier = Modifier.height(20.dp))
    TextField(value = secondPassword.value,
        label = { Text(text = "Confirmar Senha") },
        singleLine = true,
        onValueChange = { secondPassword.value = it },
        visualTransformation = if (secondPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = !secondPassword.value.equals(password),
        trailingIcon = {
            val image = if (secondPasswordVisible.value)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff
            val description = if (secondPasswordVisible.value) "Esconder Senha" else "Mostrar Senha"
            IconButton(onClick = { secondPasswordVisible.value = !secondPasswordVisible.value }) {
                Icon(imageVector = image, description)
            }
        }
    )


}

