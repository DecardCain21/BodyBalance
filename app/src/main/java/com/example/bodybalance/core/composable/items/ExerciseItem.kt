package com.example.bodybalance.core.composable.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bodybalance.R
import com.example.bodybalance.core.util.nonScaledSp
import com.example.bodybalance.ui.theme.BodyBalanceTheme

@Composable
public fun ExerciseItem(
    title: String,
    modifier: Modifier = Modifier,
    imageUrl: String = ""
) {
    val imageModel = imageUrl.takeIf { it.isNotBlank() }

    Surface(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.outlineVariant)
                .height(80.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier
                    .size(100.dp),
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageModel)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .crossfade(false)
                        .build()
                ),
                contentDescription = "Изображение курса",
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                text = title,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight(500),
                fontSize = 16.nonScaledSp,
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