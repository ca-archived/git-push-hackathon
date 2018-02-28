package net.matsudamper.git_push_hackathon.ui.license

import android.app.Application
import android.databinding.Bindable
import net.matsudamper.git_push_hackathon.ui.common.BaseObservableAndroidViewModel

class LicenseViewModel(application: Application) : BaseObservableAndroidViewModel(application) {

    @Bindable
    var adapter: LicenseAdapter = LicenseAdapter(getApplication<Application>().applicationContext)
}