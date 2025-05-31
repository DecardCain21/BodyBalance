package com.example.bodybalance.introduction.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.example.bodybalance.R
import com.example.bodybalance.core.composable.BasicButton
import com.example.bodybalance.core.composable.CustomTextField
import com.example.bodybalance.core.composable.exoPlayer
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.introduction.presentation.state.IntroductionScreenState
import com.example.bodybalance.introduction.presentation.state.IntroductionScreenState.Input
import com.example.bodybalance.introduction.presentation.state.IntroductionScreenState.IntroductionPlayerState
import com.example.bodybalance.ui.theme.BodyBalanceTheme
import kotlinx.coroutines.delay

@SuppressLint("OpaqueUnitKey")
@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun IntroductionScreen(
    uiState: IntroductionScreenState,
    inputCodeWord: (String) -> Unit,
    navToPlaylist: () -> Unit,
    eventContinue: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (uiState.videoState) {
            is IntroductionPlayerState.Content -> {
                val input: String =
                    when (uiState.inputValue) {
                        Input.Empty -> ""
                        is Input.Text -> uiState.inputValue.value
                    }
                IntroductionScreenContent(
                    video = uiState.videoState.video,
                    navToPlaylist = navToPlaylist,
                    eventContinue = eventContinue,
                    inputValue = input,
                    inputCodeWord = { inputCodeWord(it) },
                    isEnabledButton = uiState.buttonIsEnabled,
                    supportText = uiState.supportText

                )
            }
        }
    }
}

@Composable
fun IntroductionScreenContent(
    video: Video,
    inputValue: String,
    isEnabledButton: Boolean,
    supportText: String,
    modifier: Modifier = Modifier,
    navToPlaylist: () -> Unit,
    eventContinue: () -> Unit,
    inputCodeWord: (String) -> Unit,
) {

    var shouldRequestFocus by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(shouldRequestFocus) {
        if (shouldRequestFocus) {
            delay(500) // Необходимо
            focusRequester.requestFocus()
            shouldRequestFocus = false
        }
    }

    val isPreview = LocalInspectionMode.current
    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is FocusInteraction.Focus -> isFocused = true
                is FocusInteraction.Unfocus -> isFocused = false
            }
        }
    }

    val listener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                Player.STATE_ENDED -> println("Video ended")
                Player.STATE_READY -> println("Video is ready")
                Player.STATE_BUFFERING -> println("Buffering...")
                Player.STATE_IDLE -> println("Idle")
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isPreview) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text("ExoPlayer Placeholder", color = Color.White)
            }
        } else {
            ExoPlayer(
                video = video,
                listener = listener,
                showButton = true,
                shouldRequestFocus = { shouldRequestFocus = true }
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            text = stringResource(R.string.introduction),
            textAlign = TextAlign.Start,
            fontSize = 22.sp,
            color = colorResource(R.color.white)
        )
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            text = "Это видео поможет вам быстро разобраться, как всё работает\n" +
                    "\n" +
                    "После просмотра введите кодовое слово из видео, чтобы продолжить",
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            color = colorResource(R.color.white)
        )
        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp),
            label = stringResource(R.string.code_word),
            value = inputValue,
            onValueChange = { inputCodeWord(it) },
            isError = !isEnabledButton,
            supportingText = supportText,
            focusRequester = focusRequester,
            trailingIcon = {
                if (!isEnabledButton) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = stringResource(R.string.error),
                    )
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        BasicButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = stringResource(R.string.continue_button),
            buttonColor = MaterialTheme.colorScheme.primary,
            enabledTextColor = MaterialTheme.colorScheme.onPrimary,
            disabledTextColor = MaterialTheme.colorScheme.primary,
            onClick = {
                eventContinue()
                navToPlaylist()
            },
            isEnabled = isEnabledButton
        )
    }
}

@Composable
private fun ExoPlayer(
    video: Video,
    listener: Player.Listener,
    showButton: Boolean,
    shouldRequestFocus: () -> Unit,
    modifier: Modifier = Modifier,
) {
    exoPlayer(
        modifier = modifier,
        context = LocalContext.current,
        video = video,
        listener = listener,
        showButton = showButton,
        shouldRequestFocus = shouldRequestFocus
    )
}

@Preview(backgroundColor = 0xFF141218, showBackground = true)
@Composable
fun IntroductionPreview() {
    BodyBalanceTheme {
        IntroductionScreenContent(
            video = Video.emptyVideo(1),
            navToPlaylist = { },
            inputValue = "",
            inputCodeWord = { },
            isEnabledButton = true,
            eventContinue = {},
            supportText = "Неверное кодовое слово"
        )
    }
}