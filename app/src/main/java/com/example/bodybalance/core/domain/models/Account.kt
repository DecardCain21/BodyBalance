package com.example.bodybalance.core.domain.models

sealed class AccountTypeTherapy() {
    data object ExerciseBasic : AccountTypeTherapy()
    data object ExercisePro : AccountTypeTherapy()
    data object RehabilitationFirst : AccountTypeTherapy()
    data object RehabilitationSecond : AccountTypeTherapy()
}

data class Account(
    val id: Int,
    val name: String,
    val isActive: Boolean
) {
    companion object {
        fun empty() = Account(id = 0, name = "", isActive = true)
    }
}