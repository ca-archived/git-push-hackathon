package io.github.hunachi.shared

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter

typealias CompleteEditListener = () -> Unit

// todo
@BindingAdapter("editTextComplete")
fun EditText.editComplete(operation: CompleteEditListener) {
    addTextChangedListener(object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if ((s?.length ?: 0) > 0) operation.invoke()
        }
    })
}

@BindingAdapter("isVisible")
fun ImageView.isVisible(boolean: Boolean) {
    if (!boolean) visibility = View.GONE
}