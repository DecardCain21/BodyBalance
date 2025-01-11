package com.example.bodybalance.core.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bodybalance.ui.theme.BodyBalanceTheme
import com.example.bodybalance.ui.theme.Dark
import com.example.bodybalance.ui.theme.Grey
import com.example.bodybalance.ui.theme.LightGrey

@Composable
fun SingInTextField(
    modifier: Modifier = Modifier,
    text: String,
    placeHolderText: String,
    singleLine: Boolean = false,
    placeholderTextColor: Color = Grey,
    textColor: Color = Dark,
    disabledBorderColor: Color = MaterialTheme.colorScheme.onPrimary,
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.onPrimary,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    isPasswordField: Boolean = false,
    onValueChange: (String) -> Unit
) {

    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeHolderText,
                color = placeholderTextColor
            )
        },
        singleLine = singleLine,
        textStyle = TextStyle(textColor, fontSize = 16.sp),
        shape = RoundedCornerShape(16.dp),
        visualTransformation = if (isPasswordField && !isPasswordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        trailingIcon = {
            if (isPasswordField) {
                PasswordVisibilityToggle(
                    isPasswordVisible = isPasswordVisible,
                    onToggleVisibility = { isPasswordVisible = !isPasswordVisible }
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            disabledBorderColor = disabledBorderColor,
            unfocusedBorderColor = unfocusedBorderColor,
            focusedBorderColor = focusedBorderColor
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
    )
}

@Composable
fun PasswordVisibilityToggle(
    isPasswordVisible: Boolean,
    onToggleVisibility: () -> Unit
) {
    val icon = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
    val contentDescription = if (isPasswordVisible) "Скрыть пароль" else "Показать пароль"

    IconButton(onClick = onToggleVisibility) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewJustNotesTitleTextField() {
    BodyBalanceTheme(dynamicColor = false) {
        SingInTextField(
            text = "solaiman57544@gmail.com",
            placeHolderText = "Title",
            onValueChange = {}
        )
    }
}