package com.dataholding.vishal.presentation.dataholdingfeature.mvi

import com.dataholding.vishal.core.error.Failure
import com.dataholding.vishal.core.mvi.MVIContract
import com.dataholding.vishal.domain.model.HoldingData

// todo:: 24] create this file for state, effect, event for specific feature of this module per screen

/**
 * MVI contract for the Data Holding feature.
 *
 * Usage:
 * Defines the state, events, and effects for the Data Holding screen following the MVI pattern.
 */
interface DataHoldingContract :
    MVIContract<DataHoldingContract.DataHoldingState, DataHoldingContract.DataHoldingEffect, DataHoldingContract.DataHoldingEvent> {
    // todo:: 25] this class manage the event of my data holding screen list loading and list clicks done by user interaction

    /**
     * Events that can be triggered by user interactions or system actions.
     */
    sealed class DataHoldingEvent {
        /** Event to load the data holding list. */
        data object LoadDataHoldingList : DataHoldingEvent()

        /** Event when a data holding item is clicked. */
        data class DataHoldingItemClicked(val dataHoldingData: HoldingData?) : DataHoldingEvent()
        
        /** Event to retry loading data (e.g., after network error). */
        data object RetryDataLoad : DataHoldingEvent()
    }

    // todo:: 26] this is for manage the state of the data holding list screen for fetch data from remote

    /**
     * States representing the current UI state of the data holding screen.
     */
    sealed class DataHoldingState {
        /** State when data is being loaded. */
        data object Loading : DataHoldingState()

        /** State when data has been successfully loaded. */
        data class Success(val dataHoldingList: List<HoldingData?>?) : DataHoldingState()

        /** State when a general error occurs. */
        data class Error(val error: Failure) : DataHoldingState()
        
        /** State when a network connectivity error occurs. */
        data class NetworkError(val message: String) : DataHoldingState()
    }

    // todo:: 27] define effects for manage navigation on click the item of above list


    /**
     * Effects representing one-time actions that should be performed by the UI.
     */
    sealed class DataHoldingEffect {
        /** Effect to navigate to data holding details screen. */
        data class NavigateToDataHoldingDetailsDetails(val dataHoldingData: HoldingData?) :
            DataHoldingEffect()
    }
}
