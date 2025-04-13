package com.example.bodybalance.core.composable.items

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.ui.theme.BodyBalanceTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavItem(
    modifier: Modifier = Modifier,
    videoList: List<Video>,
    onItemSelected: (Video) -> Unit
) {

    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedItem by rememberSaveable { mutableStateOf("1") }

    // Константы
    val collapsedHeight = 55.dp
    val expandedHeight = 200.dp
    val shapeRadius = 40.dp

    // Анимации
    val cardHeight by animateDpAsState(
        targetValue = if (expanded) expandedHeight else collapsedHeight,
        label = "cardHeight"
    )

    val cardColor = MaterialTheme.colorScheme.primary
    val selectedColor = MaterialTheme.colorScheme.onTertiary

    Card(
        modifier = modifier
            .width(collapsedHeight)
            .height(cardHeight)
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { expanded = !expanded }
            ),
        shape = RoundedCornerShape(shapeRadius),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(visible = expanded) {
                LazyColumn(
                    modifier = Modifier
                        .height(145.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    itemsIndexed(videoList) { index, video ->
                        Text(
                            text = (index + 1).toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedItem = (index + 1).toString()
                                    expanded = false
                                    onItemSelected(video)
                                }
                                .padding(6.dp),
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(collapsedHeight)
                    .clip(RoundedCornerShape(collapsedHeight))
                    .background(if (expanded) selectedColor else cardColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = selectedItem,
                    textAlign = TextAlign.Center,
                    color = if (expanded) Color.Black else Color.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun NavItemPreview() {
    BodyBalanceTheme {
        NavItem(videoList = emptyList(), onItemSelected = {})
    }
}