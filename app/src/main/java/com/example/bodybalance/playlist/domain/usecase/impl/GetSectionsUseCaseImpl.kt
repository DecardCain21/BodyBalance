package com.example.bodybalance.playlist.domain.usecase.impl

import com.example.bodybalance.playlist.domain.GetSectionsUseCase
import javax.inject.Inject

class GetSectionsUseCaseImpl @Inject constructor() : GetSectionsUseCase {

    override operator fun invoke(): List<String> {
        return listOf("Шея", "Плечо", "Колено", "Голеностоп", "Планки", "Отжимания у стены")
    }
}