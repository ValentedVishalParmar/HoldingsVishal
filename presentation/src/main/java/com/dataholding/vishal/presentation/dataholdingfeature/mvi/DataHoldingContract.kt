package com.dataholding.vishal.presentation.dataholdingfeature.mvi

import com.dataholding.vishal.core.error.Failure
import com.dataholding.vishal.coreui.mvi.MVIContract
import com.dataholding.vishal.domain.model.HoldingData

// todo:: 24] create this file for state, effect, event for specific feature of this module per screen

interface DataHoldingContract :
    MVIContract<DataHoldingContract.DataHoldingState, DataHoldingContract.DataHoldingEffect, DataHoldingContract.DataHoldingEvent> {

    // todo:: 25] this class manage the event of my data holding screen list loading and list clicks done by user interaction
    sealed class DataHoldingEvent {
        data object LoadDataHoldingList : DataHoldingEvent()

        data class DataHoldingItemClicked(val dataHoldingData: HoldingData?) : DataHoldingEvent()
    }

    // todo:: 26] this is for manage the state of the data holding list screen for fetch data from remote

    sealed class DataHoldingState {
        data object Loading : DataHoldingState()

        data class Success(val dataHoldingList: List<HoldingData?>?) : DataHoldingState()

        data class Error(val error: Failure) : DataHoldingState()
    }

    // todo:: 27] define effects for manage navigation on click the item of above list

    sealed class DataHoldingEffect {
        data class NavigateToDataHoldingDetailsDetails(val dataHoldingData: HoldingData?) :
            DataHoldingEffect()
    }
}
