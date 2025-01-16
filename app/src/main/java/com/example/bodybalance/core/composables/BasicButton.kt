package com.example.bodybalance.core.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bodybalance.ui.theme.BodyBalanceTheme

@Composable
fun BasicButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        shape = RoundedCornerShape(14.dp),
        contentPadding = PaddingValues(top = 18.dp, bottom = 18.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = textColor
        )
    }
}

@Preview
@Composable
private fun BasicButtonPreview() {
    BodyBalanceTheme(dynamicColor = false) {
        BasicButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Кнопка",
            onClick = { }
        )
    }
}