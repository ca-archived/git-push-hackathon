package net.matsudamper.git_push_hackathon.binding

import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.databinding.adapters.ToolbarBindingAdapter
import android.support.v7.widget.Toolbar


@BindingMethods(
        BindingMethod(type = Toolbar::class, attribute = "android:onMenuItemClick", method = "setOnMenuItemClickListener"),
        BindingMethod(type = Toolbar::class, attribute = "android:onNavigationClick", method = "setNavigationOnClickListener")
)
class ToolbarBindingAdapterV7() : ToolbarBindingAdapter()