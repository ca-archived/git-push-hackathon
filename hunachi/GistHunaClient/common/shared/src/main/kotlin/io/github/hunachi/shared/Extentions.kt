package io.github.hunachi.shared

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.*
import java.util.concurrent.atomic.AtomicBoolean
import android.net.ConnectivityManager
import android.widget.Toast
import io.github.hunachi.shared.network.NetWorkError


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

fun AppCompatActivity.startActivity(next: AppCompatActivity) {
    startActivity(Intent(this, next.javaClass))
}

val <T> T.checkAllMatched: T
    get() = this

fun <T> LiveData<T>.nonNullObserve(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner, Observer { it?.let(observer) })
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T?) -> Unit) {
    this.observe(owner, Observer(observer))
}

fun Context.netWorkCheck(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = cm.activeNetworkInfo
    return info?.isConnected ?: false
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toastNetworkError(error: NetWorkError) {
    toast(when {
        !netWorkCheck() -> "ネット環境の確認をお願いにゃ！"
        error == NetWorkError.FIN -> "読み込めるものがもうないにゃ！"
        else -> "えらーにゃーん"
    })
}