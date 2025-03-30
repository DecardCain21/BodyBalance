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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.example.bodybalance.R
import com.example.bodybalance.core.composable.BasicButton
import com.example.bodybalance.core.composable.CustomTextField
import com.example.bodybalance.introduction.presentation.IntroductionScreenState.Input
import com.example.bodybalance.introduction.presentation.IntroductionScreenState.IntroductionPlayerState
import com.example.bodybalance.player.composable.exoPlayer
import com.example.bodybalance.ui.theme.BodyBalanceTheme

const val INTRODUCTION = "Introduction"

@SuppressLint("OpaqueUnitKey")
@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun IntroductionScreen(
    modifier: Modifier = Modifier,
    navToPlaylist: () -> Unit = {},
    viewModel: IntroductionViewModel = hiltViewModel()
) {

    val isPreview = LocalInspectionMode.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentState = uiState

    LaunchedEffect(Unit) {
        viewModel.getVideo(INTRODUCTION)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isPreview) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3 / 4f)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text("ExoPlayer Placeholder", color = Color.White)
            }
        } else {
            when (currentState.videoState) {
                is IntroductionPlayerState.Content -> {
                    val input: String =
                        when (currentState.inputValue) {
                            Input.Empty -> ""
                            is Input.Text -> currentState.inputValue.value
                        }
                    IntroductionScreenContent(
                        videoUrl = currentState.videoState.videoUrl,
                        navToPlaylist = navToPlaylist,
                        input = input,
                        inputCodeWord = {
                            viewModel.handleEvent(IntroductionScreenUiEvent.InputLogin(it))
                        },
                        isEnabledButton = currentState.buttonIsEnabled,
                        supportText = currentState.supportText

                    )
                }

                is IntroductionPlayerState.Empty -> Unit
                is IntroductionPlayerState.Loading -> IntroductionScreenLoading()
            }
        }
    }
}

@Composable
fun IntroductionScreenContent(
    modifier: Modifier = Modifier,
    videoUrl: String,
    navToPlaylist: () -> Unit,
    input: String,
    inputCodeWord: (String) -> Unit,
    isEnabledButton: Boolean,
    supportText: String
) {
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
        exoPlayer(
            context = LocalContext.current,
            videoUrl = videoUrl,
            listener = listener
        )
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
            value = input,
            onValueChange = { inputCodeWord(it) },
            isError = !isEnabledButton,
            supportingText = supportText,
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
            onClick = { navToPlaylist() },
            isEnabled = isEnabledButton
        )
    }
}

@Composable
private fun IntroductionScreenLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Preview(showBackground = true)
@Composable
fun IntroductionPreview() {
    BodyBalanceTheme {

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /*rememberExoPlayer(
            context = LocalContext.current,
            modifier = Modifier.fillMaxSize(),
            videoUrl = "videoUrl",
            listener = null
        )*/
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text("ExoPlayer Placeholder", color = Color.White)
            }
            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 16.dp),
                text = stringResource(R.string.introduction),
                textAlign = TextAlign.Start,
                fontSize = 22.sp,
                color = colorResource(R.color.white)
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Start)
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
                    .align(Alignment.Start)
                    .padding(top = 24.dp),
                label = "Кодовое слово",
                onValueChange = {},
            )
            Spacer(modifier = Modifier.fillMaxWidth(1f))
            BasicButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Продолжить",
                buttonColor = Color.Transparent,
                enabledTextColor = MaterialTheme.colorScheme.primary,
                onClick = { }
            )
        }


    }
}