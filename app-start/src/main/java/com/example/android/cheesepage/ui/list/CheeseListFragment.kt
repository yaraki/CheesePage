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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.cheesepage.R
import com.example.android.cheesepage.ui.detail.CheeseDetailFragment

class CheeseListFragment : Fragment() {

    companion object {
        private const val FRAGMENT_CHEESE_DETAIL = "cheese_detail"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.cheese_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val cheeseAdapter = CheeseAdapter { cheese ->
            CheeseDetailFragment.newInstance(cheese.id).show(fragmentManager, FRAGMENT_CHEESE_DETAIL)
        }
        val viewModel = ViewModelProviders.of(this).get(CheeseListViewModel::class.java)

        // This is the LiveData of PagedList. It can be observed just like a normal LiveData of List.
        viewModel.cheeses.observe(viewLifecycleOwner, Observer { cheeses ->
            cheeseAdapter.submitList(cheeses)
        })

        (view.findViewById(R.id.cheese_list) as RecyclerView).run {
            layoutManager = LinearLayoutManager(view.context)
            setHasFixedSize(true)
            adapter = cheeseAdapter
        }
    }

}
