package net.matsudamper.git_push_hackathon.ui.events

import android.app.Application
import android.databinding.Bindable
import net.matsudamper.git_push_hackathon.ui.common.BaseObservableAndroidViewModel

class EventsViewModel(application: Application) : BaseObservableAndroidViewModel(application) {

    @Bindable
    var adapter: EventsAdapter = EventsAdapter(getApplication<Application>().applicationContext)


}