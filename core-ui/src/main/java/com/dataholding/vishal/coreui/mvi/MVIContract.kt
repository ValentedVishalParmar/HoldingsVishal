package com.dataholding.vishal.coreui.mvi

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

// todo:: 19] create this mvi package and below code to manage the
// state == current state of UI or component like, loading, error, data
// effects == one time action UI performs like navigation, config changes
// event == user interaction with UI like button click, gesture are send to the VM.

interface MVIContractEvent<EVENT> {
    fun event(event: EVENT)
}

interface MVIContract<STATE,EFFECT,EVENT> : MVIContractEvent<EVENT>{
    val state: StateFlow<STATE>
    val effect: SharedFlow<EFFECT>
}