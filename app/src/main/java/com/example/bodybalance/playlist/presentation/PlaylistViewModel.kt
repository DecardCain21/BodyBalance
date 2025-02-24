package com.example.bodybalance.playlist.presentation

import androidx.lifecycle.ViewModel
import com.example.bodybalance.playlist.domain.usecase.GetSectionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val getSectionsUseCase: GetSectionsUseCase
) : ViewModel() {

    fun setContent() : List<String>{
        val temp = getSectionsUseCase.invoke()
        return temp
    }

}