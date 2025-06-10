package com.example.bodybalance.core.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.bodybalance.ui.theme.OnSurfaceOpacity12
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bodybalance.core.util.nonScaledSp
import com.example.bodybalance.ui.theme.BodyBalanceTheme

@Composable
public fun BasicButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    enabledTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    disabledTextColor: Color = MaterialTheme.colorScheme.primary,
    isEnabled: Boolean = true
) {

    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            disabledContainerColor = OnSurfaceOpacity12,
            disabledContentColor = OnSurfaceOpacity12,
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(top = 18.dp, bottom = 18.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.nonScaledSp,
            color =
            if (isEnabled) {
                enabledTextColor
            } else {
                disabledTextColor
            }

        )
    }
}

@Preview
@Composable
private fun BasicButtonPreview() {
    BodyBalanceTheme {
        BasicButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Кнопка",
            onClick = { }
        )
    }
}