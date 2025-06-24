package com.dataholding.vishal.presentation.dataholdingfeature.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dataholding.vishal.core.functional.fold
import com.dataholding.vishal.core.extension.stateInWhileActive
import com.dataholding.vishal.domain.usecase.HoldingDataUseCase
import com.dataholding.vishal.presentation.dataholdingfeature.mvi.DataHoldingContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Data Holding feature, implementing the MVI contract.
 *
 * Usage:
 * Used in the presentation layer to manage UI state, handle events, and emit effects for the Data Holding screen.
 *
 * @property dataUseCase The use case for retrieving and processing holding data.
 * @constructor Injects the use case for dependency injection frameworks (e.g., Hilt).
 */
@HiltViewModel
class DataHoldingViewModel @Inject constructor(private val dataUseCase: HoldingDataUseCase) :
    ViewModel(), DataHoldingContract {

    /**
     * Mutable state for the UI, representing the current screen state.
     */
    private val mutableUIState: MutableStateFlow<DataHoldingContract.DataHoldingState> =
        MutableStateFlow(DataHoldingContract.DataHoldingState.Loading)

    /**
     * Mutable shared flow for emitting one-time effects (e.g., navigation).
     */
    private val mutableSharedFlow: MutableSharedFlow<DataHoldingContract.DataHoldingEffect> =
        MutableSharedFlow()

    /**
     * Shared flow of effects for the UI to observe.
     */
    override val effect: SharedFlow<DataHoldingContract.DataHoldingEffect>
        get() = mutableSharedFlow.asSharedFlow()

    /**
     * State flow of the current UI state for the UI to observe.
     */
    override val state: StateFlow<DataHoldingContract.DataHoldingState>
        get() = mutableUIState.stateInWhileActive(
            viewModelScope,
            DataHoldingContract.DataHoldingState.Loading
        ) {
            event(DataHoldingContract.DataHoldingEvent.LoadDataHoldingList)
        }

    /**
     * Handles events from the UI and updates state or emits effects accordingly.
     *
     * @param event The [DataHoldingContract.DataHoldingEvent] to handle.
     */
    override fun event(event: DataHoldingContract.DataHoldingEvent) {
        when (event) {
            is DataHoldingContract.DataHoldingEvent.LoadDataHoldingList -> {
                loadDataHoldings()
            }
            is DataHoldingContract.DataHoldingEvent.DataHoldingItemClicked -> {
                viewModelScope.launch {
                    mutableSharedFlow.emit(
                        DataHoldingContract.DataHoldingEffect.NavigateToDataHoldingDetailsDetails(
                            dataHoldingData = event.dataHoldingData
                        )
                    )
                }
            }
        }
    }

    /**
     * Loads holding data using the use case and updates the UI state based on the result.
     */
    private fun loadDataHoldings() {
        viewModelScope.launch {
            dataUseCase.getInvokeHoldingDataApiCall().fold({
                updateState(DataHoldingContract.DataHoldingState.Error(it))
            }, {
                updateState(DataHoldingContract.DataHoldingState.Success(it))
            })
        }
    }

    /**
     * Updates the UI state.
     *
     * @param state The new [DataHoldingContract.DataHoldingState] to set.
     */
    private fun updateState(state: DataHoldingContract.DataHoldingState) {
        mutableUIState.update { state }
    }
}