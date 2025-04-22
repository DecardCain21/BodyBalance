package com.example.bodybalance.core.domain.models

sealed class AccountTypeTherapy() {
    data object ExerciseBasic : AccountTypeTherapy()
    data object ExercisePro : AccountTypeTherapy()
    data object RehabilitationFirst : AccountTypeTherapy()
    data object RehabilitationSecond : AccountTypeTherapy()
}

data class Account(
    val name: String,
    val isActive: Boolean
)
