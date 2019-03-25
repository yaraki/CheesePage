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

package com.example.android.cheesepage.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.android.cheesepage.observedValue
import com.example.android.cheesepage.ui.createDummyRepository
import com.example.android.cheesepage.vo.Cheese
import com.example.android.cheesepage.vo.CheeseDetail
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class CheeseDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun showsCheese() {
        val (_, db, repository) = createDummyRepository()
        db.cheese().insert(listOf(Cheese(1L, "1", "https://example.com/1.jpg", "description")))
        val viewModel = CheeseDetailViewModel(ApplicationProvider.getApplicationContext(), repository)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            viewModel.setCheeseId(1L)
        }
        assertThat(viewModel.cheese.observedValue()).isEqualTo(
            CheeseDetail(
                1L,
                "1",
                "https://example.com/1.jpg",
                "description",
                null
            )
        )
        viewModel.submitRating(true)
        assertThat(viewModel.cheese.observedValue()).isEqualTo(
            CheeseDetail(
                1L,
                "1",
                "https://example.com/1.jpg",
                "description",
                true
            )
        )
    }

}
