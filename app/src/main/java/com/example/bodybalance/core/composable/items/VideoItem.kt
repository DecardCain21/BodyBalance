package com.example.bodybalance.core.composable.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bodybalance.R
import com.example.bodybalance.ui.theme.Black
import com.example.bodybalance.ui.theme.BodyBalanceTheme
import com.example.bodybalance.ui.theme.Grey

@Composable
public fun VideoItem(
    title: String,
    modifier: Modifier = Modifier,
    imageUrl: String = "",
    showIconDrag: Boolean = true,
    showSelectItem: Boolean = false
) {

    val colorBackground = if (showSelectItem) Grey else Black

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorBackground)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (showIconDrag) {
                Image(
                    modifier = Modifier.fillMaxHeight(),
                    painter = painterResource(R.drawable.ic_drag),
                    contentDescription = "Icon drag"
                )
            }
            Image(
                modifier = Modifier
                    .width(142.dp)
                    .clip(RoundedCornerShape(12.dp)),
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Image course",
                contentScale = ContentScale.Crop,
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = title,
                fontWeight = FontWeight(500),
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
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