package com.example.wowpersonalitytest.data

import androidx.annotation.StringRes
import java.util.*

data class Question(
    @StringRes val questionResId: Int,
    @StringRes val trueAnswer: Int,
    val anotherAnswersList: LinkedList<Int>
    ) {
}