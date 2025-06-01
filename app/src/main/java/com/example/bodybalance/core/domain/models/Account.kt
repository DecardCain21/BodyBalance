package com.example.bodybalance.core.domain.models

/*public sealed class AccountTypeTherapy() {
    public data object ExerciseBasic : AccountTypeTherapy()
    public data object ExercisePro : AccountTypeTherapy()
    public data object RehabilitationFirst : AccountTypeTherapy()
    public data object RehabilitationSecond : AccountTypeTherapy()
}*/

public data class Account(
    val id: Int,
    val name: String,
    val isActive: Boolean
) {
    public companion object {
        public fun empty(id: Int = 0): Account =
            Account(id = id, name = "", isActive = true)
    }
}