package com.example.bodybalance.core.composable.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bodybalance.R
import com.example.bodybalance.core.util.nonScaledSp
import com.example.bodybalance.ui.theme.Black
import com.example.bodybalance.ui.theme.BodyBalanceTheme
import com.example.bodybalance.ui.theme.Grey
import org.burnoutcrew.reorderable.ReorderableLazyListState
import org.burnoutcrew.reorderable.detectReorder
import org.burnoutcrew.reorderable.detectReorderAfterLongPress

@Composable
public fun VideoItem(
    title: String,
    modifier: Modifier = Modifier,
    imageUrl: String = "",
    category: String = "",
    showIconDrag: Boolean = true,
    showSelectItem: Boolean = false,
    reorderState: ReorderableLazyListState? = null
) {
    val imageModel = imageUrl.takeIf { it.isNotBlank() }
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
                    modifier = Modifier
                        .width(30.dp)
                        .wrapContentHeight()
                        .then(reorderState?.let { Modifier.detectReorder(it) }
                            ?: Modifier),
                    painter = painterResource(R.drawable.ic_drag),
                    contentDescription = "Icon drag"
                )
            }
            Image(
                modifier = Modifier
                    .width(142.dp)
                    .clip(RoundedCornerShape(12.dp)),
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageModel)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = "Image course",
                contentScale = ContentScale.Crop
            )
            VideoTextContent(title = title, category = category)
        }
    }
}

@Composable
private fun RowScope.VideoTextContent(
    title: String,
    category: String = "",
) {
    Column(modifier = Modifier.weight(1f)) {
        TitleText(
            title = title,
            maxLines = if (category.isNotEmpty()) 1 else 2,
            paddingTop = if(category.isNotEmpty()) 18 else 0
        )

        if (category.isNotEmpty()) {
            CategoryText(category = category)
        }
    }
}

@Composable
private fun TitleText(
    title: String,
    maxLines: Int,
    paddingTop:Int,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, bottom = 4.dp, top = paddingTop.dp),
        text = title,
        fontWeight = FontWeight(500),
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines,
        fontSize = 16.nonScaledSp,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun CategoryText(
    category: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.padding(start = 16.dp, end = 16.dp,bottom = 18.dp),
        text = category,
        fontWeight = FontWeight(400),
        overflow = TextOverflow.Ellipsis,
        fontSize = 12.nonScaledSp,
        color = MaterialTheme.colorScheme.primary
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF141218)
@Composable
private fun VideoItemPreview() {
    BodyBalanceTheme {
        VideoItem(
            title = "Разминка перед упражнениями на отдельную группу мыщц",
            imageUrl = "Some Description",
            showIconDrag = true,
        )
    }
}