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

package com.example.android.cheesepage.ui

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.android.cheesepage.api.CheeseApi
import com.example.android.cheesepage.db.CheeseDatabase
import com.example.android.cheesepage.repository.CheeseRepository
import java.util.concurrent.Executor

data class DummyRepository(
    val api: CheeseApi,
    val db: CheeseDatabase,
    val repository: CheeseRepository
)

fun createDummyRepository(): DummyRepository {
    val api = CheeseApi().apply { dummySleepInterval = 0L }
    val db = Room
        .inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CheeseDatabase::class.java
        )
        .allowMainThreadQueries()
        .build()
    val repository = CheeseRepository(api, db, Executor { command -> command?.run() })
    return DummyRepository(api, db, repository)
}
