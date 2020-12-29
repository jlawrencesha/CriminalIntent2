package com.giftofhappiness.android.criminalintent2

import java.util.*

data class Crime (

    val id : UUID = UUID.randomUUID(),
    val title : String = "",
    val date : Date = Date(),
    val isSolved: Boolean = false
)


