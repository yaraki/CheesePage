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

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.android.cheesepage.observedValue
import com.example.android.cheesepage.ui.createDummyRepository
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class CheeseRepositoryTest {

    @Test
    fun overall() {
        val (_, _, repository) = createDummyRepository()
        assertThat(repository.listCheeses().observedValue()).isEmpty()
        repository.initialize()
        val initialCheeses = repository.listCheeses().observedValue()
        assertThat(initialCheeses).isNotEmpty()
        repository.sync(initialCheeses.size + 1)
        assertThat(repository.listCheeses().observedValue().size).isGreaterThan(initialCheeses.size)
        assertThat(repository.findCheese(1L).observedValue()!!.rating).isNull()
        repository.rateCheese(1L, true)
        assertThat(repository.findCheese(1L).observedValue()!!.rating).isTrue()
        repository.rateCheese(1L, false)
        assertThat(repository.findCheese(1L).observedValue()!!.rating).isFalse()
        repository.initialize()
        assertThat(repository.listCheeses().observedValue().size).isEqualTo(initialCheeses.size)
    }

}
