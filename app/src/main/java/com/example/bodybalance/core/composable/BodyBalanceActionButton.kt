package com.example.bodybalance.core.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
public fun BodyBalanceActionButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    text: String
) {
    TextButton(onClick = { onClick() }) {
        Row(
            modifier = Modifier
                .background(
                    Color.White,
                    shape = RoundedCornerShape(100.dp)
                )
                .padding(top = 10.dp, bottom = 10.dp, start = 16.dp, end = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = Color.Black
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = text,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
//Icons.Default.Download