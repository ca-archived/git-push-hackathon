package net.matsudamper.git_push_hackathon.binding

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso


@BindingAdapter("bind:imageUrl")
fun ImageView.loadImage(url: String?) {
    val url = url ?: return

    this.setImageResource(0)
    Picasso.with(this.context)
            .load(url)
            .into(this)
}

@BindingAdapter("bind:imageUrl")
fun ImageView.loadImage(resId: Int?) {
    val resId = resId ?: return

    this.setImageResource(0)
    Picasso.with(this.context)
            .load(resId)
            .into(this)
}