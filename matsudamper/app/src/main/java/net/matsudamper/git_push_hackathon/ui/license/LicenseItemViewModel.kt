package net.matsudamper.git_push_hackathon.ui.license

import android.databinding.BaseObservable
import android.view.View

class LicenseItemViewModel(val name: String, val url: String) : BaseObservable() {

    var onClickListener: View.OnClickListener? = null
}