package net.matsudamper.git_push_hackathon.ui.login

import android.app.Application
import android.databinding.Bindable
import android.view.View
import net.matsudamper.git_push_hackathon.BR
import net.matsudamper.git_push_hackathon.R
import net.matsudamper.git_push_hackathon.ui.common.BaseObservableAndroidViewModel

class LoginViewModel(application: Application) : BaseObservableAndroidViewModel(application) {

    enum class State {
        NOT, NOW, COMPLETE, FAILED
    }

    var state: State = State.NOT
        set(value) {
            field = value
            notifyPropertyChanged(BR.stateString)
        }
    val stateString
        @Bindable get() = when (state) {
            State.NOT -> getApplication<Application>().getString(R.string.login_state_not)
            State.NOW -> getApplication<Application>().getString(R.string.login_state_now)
            State.COMPLETE -> getApplication<Application>().getString(R.string.login_state_complete)
            State.FAILED -> getApplication<Application>().getString(R.string.login_state_failed)
        }


    var clickListener: View.OnClickListener? = null
        @Bindable set(value) {
            field = value
        }
        @Bindable get() = field
}