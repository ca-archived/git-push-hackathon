package net.matsudamper.git_push_hackathon.binding

import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.support.v4.widget.SwipeRefreshLayout


@BindingMethods(
        BindingMethod(type = SwipeRefreshLayout::class, attribute = "android:onRefresh", method = "setOnRefreshListener")
)
class SwipeRefreshLayoutBindingAdapter()