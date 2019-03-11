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

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.cheesepage.vo.Cheese
import com.example.android.cheesepage.vo.CheeseDetail
import com.example.android.cheesepage.vo.CheeseRating

@Database(
    version = 1,
    entities = [
        Cheese::class,
        CheeseRating::class
    ],
    views = [
        CheeseDetail::class
    ]
)
abstract class CheeseDatabase : RoomDatabase() {

    abstract fun cheese(): CheeseDao

}
