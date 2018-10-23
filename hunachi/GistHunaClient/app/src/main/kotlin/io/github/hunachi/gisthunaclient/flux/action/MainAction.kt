package io.github.hunachi.gisthunaclient.flux.action

import io.github.hunachi.gisthunaclient.flux.FragmentState

sealed class MainAction {

    class ChangeFragmentState(val fragmentState: FragmentState): MainAction()

    object ClickedFAB: MainAction()
}