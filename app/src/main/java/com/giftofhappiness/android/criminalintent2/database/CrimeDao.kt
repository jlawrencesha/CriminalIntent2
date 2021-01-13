package com.giftofhappiness.android.criminalintent2.database

import androidx.room.Dao
import androidx.room.Query
import com.giftofhappiness.android.criminalintent2.Crime
import java.util.*

@Dao
interface CrimeDao {


    @Query("SELECT * FROM crime")
    fun getCrimes(): List<Crime>


    @Query("SELECT * FROM crime WHERE id=(:id)")
    fun getCrime(id: UUID): Crime?


}