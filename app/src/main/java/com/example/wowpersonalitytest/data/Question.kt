package com.example.wowpersonalitytest.data

import androidx.annotation.StringRes
import java.util.*

data class Question(
    @StringRes val questionResId: Int,
    val answer: Boolean
    ) {
}