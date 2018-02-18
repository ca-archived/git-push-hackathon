package io.moatwel.github.presentation.view.binding

import android.databinding.BindingAdapter
import android.widget.ImageView
import io.moatwel.github.presentation.view.GlideApp

object EventDataBinder {

  @JvmStatic
  @BindingAdapter("imageUrl")
  fun loadUserImage(view: ImageView, avatarUrl: String) {
    GlideApp.with(view.context)
      .load(avatarUrl)
      .circleCrop()
      .into(view)
  }
}