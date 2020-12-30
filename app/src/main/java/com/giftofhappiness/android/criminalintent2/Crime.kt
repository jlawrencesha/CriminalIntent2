package com.giftofhappiness.android.criminalintent2

import java.util.*

data class Crime (

    val id : UUID = UUID.randomUUID(),
    var title : String = "",
    var date : Date = Date(),
    var isSolved: Boolean = false
)


