package com.example.bodybalance.core.composable

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bodybalance.ui.theme.BodyBalanceTheme

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    isError: Boolean = false,
    supportingText: String = "",
    isSupportTextVisible: Boolean = false,
    onValueChange: (String) -> Unit
) {

    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        modifier = modifier,
        value = text,
        onValueChange = { newValue ->
            text = newValue
            onValueChange(newValue)
        },
        singleLine = true,
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        trailingIcon = {
            LabelIcon(
                isError = isError,
                isEmpty = text.isEmpty()
            )
        },
        supportingText = {
            ShowSupportingText(
                isVisible = isSupportTextVisible,
                supportingText = supportingText
            )
        },
        label = { Text(text = label) },
    )
}

@Composable
private fun ShowSupportingText(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    supportingText: String
) {

    if (isVisible) Text(modifier = modifier, text = supportingText)
}

@Composable
private fun LabelIcon(
    modifier: Modifier = Modifier,
    isError: Boolean,
    isEmpty: Boolean
) {

    val icon = when {
        isEmpty -> null
        isError -> Icons.Default.Error
        else -> Icons.Default.HighlightOff
    }

    if (icon != null) {
        IconButton(modifier = modifier, onClick = {}) {
            Icon(
                imageVector = icon,
                contentDescription = "Label Icon"
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCustomTextField() {
    BodyBalanceTheme {
        CustomTextField(
            onValueChange = {},
            isSupportTextVisible = true,
            supportingText = "Используйте только буквы и цифры",
            label = "Логин",
            isError = true,
        )
    }
}