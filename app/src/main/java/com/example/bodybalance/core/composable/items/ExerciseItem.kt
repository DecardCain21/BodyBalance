package com.example.bodybalance.core.composable.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bodybalance.R
import com.example.bodybalance.ui.theme.BodyBalanceTheme

@Composable
public fun ExerciseItem(
    title: String,
    imageUrl: String = ""
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.outlineVariant)
                .height(80.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Image(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Изображение курса",
                contentScale = ContentScale.Crop,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                text = title,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight(500),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )

        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF141218)
@Composable
private fun ExerciseItemPreview() {
    BodyBalanceTheme {
        ExerciseItem(
            title = "Голеностоп",
            imageUrl = "Some Description"
        )
    }
}