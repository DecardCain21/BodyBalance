package com.example.bodybalance.core.composable.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bodybalance.R
import com.example.bodybalance.ui.theme.BodyBalanceTheme

@Composable
public fun VideoItem(
    title: String,
    imageUrl: String = ""
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
        ) {

            Image(
                modifier = Modifier
                    .width(142.dp)
                    .height(80.dp),
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Изображение курса",
                contentScale = ContentScale.Crop,
            )
        }
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            text = if (title.length > 30) "${title.take(30)}..." else title,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary
        )

    }

}

@Preview(showBackground = true, backgroundColor = 0xFF141218)
@Composable
private fun VideoItemPreview() {
    BodyBalanceTheme {
        VideoItem(
            title = "Разминка перед упражнениями на отдельную группу мыщц",
            imageUrl = "Some Description"
        )
    }
}