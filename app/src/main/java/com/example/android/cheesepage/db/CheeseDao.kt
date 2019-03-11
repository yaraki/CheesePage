/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.cheesepage.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.cheesepage.vo.Cheese
import com.example.android.cheesepage.vo.CheeseDetail
import com.example.android.cheesepage.vo.CheeseRating
import com.example.android.cheesepage.vo.CheeseSummary

@Dao
interface CheeseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cheeses: List<Cheese>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cheeseRating: CheeseRating)

    @Query("SELECT id, name, imageUrl, rating FROM CheeseDetail")
    fun all(): DataSource.Factory<Int, CheeseSummary>

    @Query("SELECT * FROM CheeseDetail WHERE id = :id")
    fun find(id: Long): LiveData<CheeseDetail?>

    @Query("DELETE FROM Cheese")
    fun deleteAll()

}
