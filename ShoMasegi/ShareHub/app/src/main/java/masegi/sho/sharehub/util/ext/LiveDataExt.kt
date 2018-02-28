package masegi.sho.sharehub.util.ext

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer

/**
 * Created by masegi on 2018/02/15.
 */

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T?) -> Unit) where T : Any =
        observe(owner, Observer<T> { v -> observer(v) })

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner, Observer {
        if (it != null) {
            observer(it)
        }
    })
}
