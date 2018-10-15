package io.github.hunachi.shared

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.*
import java.util.concurrent.atomic.AtomicBoolean

// lazy of none safety but fast thread mode
fun <T> lazyFast(operation: () -> T) = lazy(LazyThreadSafetyMode.NONE) {
    operation()
}

fun ViewGroup.inflate(
        @LayoutRes layout: Int, attachToRoot: Boolean = false
): View {
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}

inline fun FragmentManager.inTransaction(
        func: FragmentTransaction.() -> FragmentTransaction
) {
    beginTransaction().func().commit()
}

inline fun AppCompatActivity.startActivity(next: AppCompatActivity) {
    startActivity(Intent(this, next.javaClass))
}

// ViewModelProvider
inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(
        provider: ViewModelProvider.Factory
) = ViewModelProviders.of(this, provider).get(VM::class.java)

inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
        provider: ViewModelProvider.Factory
) = ViewModelProviders.of(this, provider).get(VM::class.java)

inline fun <reified VM : ViewModel> Fragment.activityViewModelProvider(
        provider: ViewModelProvider.Factory
) = ViewModelProviders.of(requireActivity(), provider).get(VM::class.java)

inline fun <reified VM : ViewModel> Fragment.parentViewModelProvider(
        provider: ViewModelProvider.Factory
) = ViewModelProviders.of(parentFragment!!, provider).get(VM::class.java)

val <T> T.checkAllMatched: T
    get() = this

fun <T> LiveData<T>.nonNullObseve(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner, Observer { it?.let(observer) })
}

fun <T> singleLiveData(): MutableLiveData<T> {
    return MutableLiveData<T>().also { it.value = null }
}

fun <T> LiveData<T>.singleLiveData(owner: LifecycleOwner, observer: (T?) -> Unit) {
    val atomicBoolean = AtomicBoolean(true)
    this.observe(owner, Observer {
        if (atomicBoolean.getAndSet(false)) return@Observer
        observer(it)
    })
}