package io.github.hunachi.shared.flux

import androidx.lifecycle.ViewModel

abstract class Store: ViewModel(){

    abstract fun onCreate()
}