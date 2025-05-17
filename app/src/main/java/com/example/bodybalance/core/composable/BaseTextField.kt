package com.example.bodybalance.core.composable

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bodybalance.ui.theme.BodyBalanceTheme

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    label: String = "",
    isError: Boolean = false,
    supportingText: String = "",
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource? = null,
    focusRequester: FocusRequester? = null,
) {

    val textState = remember { mutableStateOf(TextFieldValue(value)) }

    LaunchedEffect(value) {
        textState.value = TextFieldValue(value, selection = TextRange(value.length))
    }

    TextField(
        modifier = modifier
            .focusRequester(focusRequester ?: FocusRequester.Default),
        value = textState.value,
        onValueChange = { newValue ->
            textState.value = newValue.copy(selection = newValue.selection)
            onValueChange(newValue.text)
        },
        interactionSource = interactionSource,
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
        trailingIcon = trailingIcon,
        supportingText = { if (isError) Text(text = supportingText) },
        label = { Text(text = label) },
    )
}

@Preview
@Composable
private fun PreviewCustomTextField() {
    BodyBalanceTheme {
        CustomTextField(
            onValueChange = {},
            supportingText = "Используйте только буквы и цифры",
            label = "Логин",
            isError = true,
        )
    }
}