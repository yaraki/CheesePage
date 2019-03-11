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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.cheesepage.R
import com.example.android.cheesepage.vo.CheeseSummary

class CheeseAdapter(
    private val onItemClicked: (CheeseSummary) -> Unit
) : PagedListAdapter<CheeseSummary, CheeseViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheeseViewHolder {
        val holder = CheeseViewHolder(parent)
        holder.itemView.setOnClickListener {
            val cheese = getItem(holder.adapterPosition)
            if (cheese != null) {
                onItemClicked(cheese)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: CheeseViewHolder, position: Int) {
        val cheese = getItem(position)
        if (cheese == null) {
            holder.icon.setImageDrawable(null)
            holder.name.text = "……"
            holder.rating.visibility = View.GONE
        } else {
            Glide.with(holder.icon).load(cheese.imageUrl).apply(RequestOptions.circleCropTransform()).into(holder.icon)
            holder.name.text = cheese.name
            if (cheese.rating == null) {
                holder.rating.visibility = View.GONE
            } else {
                holder.rating.visibility = View.VISIBLE
                if (cheese.rating) {
                    holder.rating.setImageResource(R.drawable.ic_thumb_up)
                } else {
                    holder.rating.setImageResource(R.drawable.ic_thumb_down)
                }
            }
        }
    }

}

class CheeseViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.cheese_list_item, parent, false)
) {
    val icon: ImageView = itemView.findViewById(R.id.icon)
    val name: TextView = itemView.findViewById(R.id.name)
    val rating: ImageView = itemView.findViewById(R.id.rating)
}

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CheeseSummary>() {

    override fun areItemsTheSame(oldItem: CheeseSummary, newItem: CheeseSummary): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CheeseSummary, newItem: CheeseSummary): Boolean {
        return oldItem == newItem
    }

}
