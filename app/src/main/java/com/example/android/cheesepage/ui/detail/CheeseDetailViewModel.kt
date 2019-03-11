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

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.android.cheesepage.repository.CheeseRepository
import com.example.android.cheesepage.vo.CheeseDetail

class CheeseDetailViewModel @JvmOverloads constructor(
    application: Application,
    private val repository: CheeseRepository = CheeseRepository.getInstance(application)
) : AndroidViewModel(application) {

    private val _cheeseId = MutableLiveData<Long>()

    val cheese: LiveData<CheeseDetail?> = Transformations.switchMap(_cheeseId) { id -> repository.findCheese(id) }

    fun setCheeseId(cheeseId: Long) {
        _cheeseId.value = cheeseId
    }

    fun submitRating(rating: Boolean) {
        val cheeseId = _cheeseId.value ?: return
        repository.rateCheese(cheeseId, rating)
    }

}
