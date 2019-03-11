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

package com.example.android.cheesepage.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Room
import com.example.android.cheesepage.api.CheeseApi
import com.example.android.cheesepage.db.CheeseDatabase
import com.example.android.cheesepage.vo.CheeseDetail
import com.example.android.cheesepage.vo.CheeseRating
import com.example.android.cheesepage.vo.CheeseSummary
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class CheeseRepository(
    private val api: CheeseApi,
    private val db: CheeseDatabase,
    private val executor: Executor
) {

    companion object {
        const val PAGE_SIZE = 30

        private var instance: CheeseRepository? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: CheeseRepository(
                CheeseApi(),
                Room.databaseBuilder(context, CheeseDatabase::class.java, "cheese").build(),
                Executors.newFixedThreadPool(4)
            ).also { instance = it }
        }
    }

    fun listCheeses(): DataSource.Factory<Int, CheeseSummary> {
        return db.cheese().all()
    }

    fun findCheese(cheeseId: Long): LiveData<CheeseDetail?> {
        return db.cheese().find(cheeseId)
    }

    fun rateCheese(cheeseId: Long, rating: Boolean) {
        executor.execute {
            db.cheese().insert(CheeseRating(cheeseId, rating))
        }
    }

    fun initialize() {
        executor.execute {
            db.cheese().deleteAll()
            db.cheese().insert(api.fetchPage(startIndex = 0, size = PAGE_SIZE))
        }
    }

    fun sync(startIndex: Int) {
        executor.execute {
            db.cheese().insert(api.fetchPage(startIndex = startIndex, size = PAGE_SIZE))
        }
    }

}
