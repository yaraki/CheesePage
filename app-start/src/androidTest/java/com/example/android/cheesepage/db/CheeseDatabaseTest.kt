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

import androidx.paging.toLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.cheesepage.observedValue
import com.example.android.cheesepage.vo.Cheese
import com.example.android.cheesepage.vo.CheeseDetail
import com.example.android.cheesepage.vo.CheeseRating
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class CheeseDatabaseTest {

    @Test
    fun basic() {
        val db = Room
            .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), CheeseDatabase::class.java)
            .build()
        db.cheese().insert(
            listOf(
                Cheese(1L, "1", "https://example.com/1.jpg", "description")
            )
        )
        assertThat(db.cheese().find(1L).observedValue()).isEqualTo(
            CheeseDetail(
                1L,
                "1",
                "https://example.com/1.jpg",
                "description",
                null
            )
        )
        db.cheese().insert(CheeseRating(1L, true))
        assertThat(db.cheese().find(1L).observedValue()).isEqualTo(
            CheeseDetail(
                1L,
                "1",
                "https://example.com/1.jpg",
                "description",
                true
            )
        )
        assertThat(db.cheese().all().toLiveData(10).observedValue()).hasSize(1)
        db.cheese().deleteAll()
        assertThat(db.cheese().all().toLiveData(10).observedValue()).hasSize(0)
    }

}
