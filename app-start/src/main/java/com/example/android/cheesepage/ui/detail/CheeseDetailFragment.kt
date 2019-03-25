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

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.cheesepage.R

class CheeseDetailFragment : DialogFragment() {

    companion object {
        private const val ARG_CHEESE_ID = "cheese_id"

        fun newInstance(cheeseId: Long) = CheeseDetailFragment().apply {
            arguments = Bundle().apply {
                putLong(ARG_CHEESE_ID, cheeseId)
            }
        }
    }

    private lateinit var viewModel: CheeseDetailViewModel

    private var tintNormal: ColorStateList? = null
    private var tintCurrent: ColorStateList? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tintNormal = ResourcesCompat.getColorStateList(resources, R.color.gray, context?.theme)
        tintCurrent = ResourcesCompat.getColorStateList(resources, R.color.accent, context?.theme)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.cheese_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(CheeseDetailViewModel::class.java)

        val cheeseId = arguments?.getLong(ARG_CHEESE_ID) ?: return
        viewModel.setCheeseId(cheeseId)

        val icon: ImageView = view.findViewById(R.id.icon)
        val name: TextView = view.findViewById(R.id.name)
        val description: TextView = view.findViewById(R.id.description)
        val ratingNegative: AppCompatImageButton = view.findViewById(R.id.rating_negative)
        val ratingPositive: ImageButton = view.findViewById(R.id.rating_positive)

        viewModel.cheese.observe(viewLifecycleOwner, Observer { cheese ->
            if (cheese == null) {
                return@Observer
            }
            Glide.with(icon).load(cheese.imageUrl).apply(RequestOptions.circleCropTransform()).into(icon)
            name.text = cheese.name
            description.text = cheese.description
            when (cheese.rating) {
                null -> {
                    ImageViewCompat.setImageTintList(ratingNegative, tintNormal)
                    ImageViewCompat.setImageTintList(ratingPositive, tintNormal)
                }
                true -> {
                    ImageViewCompat.setImageTintList(ratingNegative, tintNormal)
                    ImageViewCompat.setImageTintList(ratingPositive, tintCurrent)
                }
                false -> {
                    ImageViewCompat.setImageTintList(ratingNegative, tintCurrent)
                    ImageViewCompat.setImageTintList(ratingPositive, tintNormal)
                }
            }
        })

        ratingNegative.setOnClickListener {
            viewModel.submitRating(false)
            dismiss()
        }
        ratingPositive.setOnClickListener {
            viewModel.submitRating(true)
            dismiss()
        }
    }

}
