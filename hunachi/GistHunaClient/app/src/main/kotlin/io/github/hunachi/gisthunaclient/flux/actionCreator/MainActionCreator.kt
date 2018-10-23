package io.github.hunachi.gisthunaclient.flux.actionCreator

import io.github.hunachi.gist.GistRepository
import io.github.hunachi.gisthunaclient.flux.FragmentState
import io.github.hunachi.gisthunaclient.flux.action.MainAction
import io.github.hunachi.shared.flux.Dispatcher

class MainActionCreator(
        private val dispatcher: Dispatcher,
        private val repository: GistRepository
) {

    fun clickedFav(){
        dispatcher.send(MainAction.ClickedFAB)
    }

    fun clickedBack(){
        dispatcher.send(MainAction.ClickedBack)
    }
}