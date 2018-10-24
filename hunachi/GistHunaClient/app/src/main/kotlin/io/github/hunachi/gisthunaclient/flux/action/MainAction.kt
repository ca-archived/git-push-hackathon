package io.github.hunachi.gisthunaclient.flux.action

sealed class MainAction {

    object ClickedFAB: MainAction()

    object ClickedBack: MainAction()
}