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

package com.example.android.cheesepage.ui.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.android.cheesepage.repository.CheeseRepository
import com.example.android.cheesepage.vo.CheeseSummary
import java.util.concurrent.atomic.AtomicBoolean

class CheeseListViewModel @JvmOverloads constructor(
    application: Application,
    repository: CheeseRepository = CheeseRepository.getInstance(application)
) : AndroidViewModel(application) {

    val cheeses = repository.listCheeses().toLiveData(
        pageSize = CheeseRepository.PAGE_SIZE,
        boundaryCallback = object : PagedList.BoundaryCallback<CheeseSummary>() {
            private val loading = AtomicBoolean(false)
            override fun onItemAtEndLoaded(itemAtEnd: CheeseSummary) {
                if (loading.compareAndSet(false, true)) {
                    repository.sync((itemAtEnd.id + 1).toInt())
                    loading.set(false)
                }
            }
        })

    init {
        repository.initialize()
    }

}
