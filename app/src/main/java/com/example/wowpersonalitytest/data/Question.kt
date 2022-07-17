package com.example.wowpersonalitytest.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import java.util.*

data class Question(
    @StringRes val questionResId: Int,
    @DrawableRes val imageResId: Int,
    val answer: Boolean
    ) {
}