package com.dataholding.vishal.presentation.dataholdingfeature.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dataholding.vishal.core.error.Failure
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
import kotlinx.coroutines.flow.asStateFlow
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
 * @property networkHandler The network handler for handling network connectivity and errors.
 * @constructor Injects the use case and network handler for dependency injection frameworks (e.g., Hilt).
 */
@HiltViewModel
class DataHoldingViewModel @Inject constructor(
    private val dataUseCase: HoldingDataUseCase,
) : ViewModel(), DataHoldingContract {

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
        get() = mutableUIState.asStateFlow()

    // Guard to prevent multiple simultaneous API calls
    private var isApiCallInProgress = false

    init {
        // Load data when ViewModel is created
        loadDataHoldings()
    }

    /**
     * Handles events from the UI and updates state or emits effects accordingly.
     *
     * @param event The [DataHoldingContract.DataHoldingEvent] to handle.
     */
    override fun event(event: DataHoldingContract.DataHoldingEvent) {
        when (event) {
            is DataHoldingContract.DataHoldingEvent.LoadDataHoldingList -> {
                // Only load if not already in progress
                if (!isApiCallInProgress) {
                    loadDataHoldings()
                }
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
            is DataHoldingContract.DataHoldingEvent.RetryDataLoad -> {
                // Always allow retry, even if currently loading (user explicitly requested it)
                loadDataHoldings()
            }
        }
    }

    /**
     * Loads holding data using the use case and updates the UI state based on the result.
     * Network connectivity errors and retry mechanisms are handled internally by the NetworkHandler.
     */
    private fun loadDataHoldings() {
        // Prevent multiple simultaneous API calls
        if (isApiCallInProgress) {
            return
        }
        
        viewModelScope.launch {
            isApiCallInProgress = true
            updateState(DataHoldingContract.DataHoldingState.Loading)
            
            try {
                dataUseCase.getInvokeHoldingDataApiCall().fold(
                    { failure ->
                        when (failure) {
                            is Failure.NetworkConnectivityError -> {
                                updateState(DataHoldingContract.DataHoldingState.NetworkError(failure.message))
                            }
                            is Failure.NetworkError -> {
                                updateState(DataHoldingContract.DataHoldingState.NetworkError("Network error occurred"))
                            }
                            else -> {
                                updateState(DataHoldingContract.DataHoldingState.Error(failure))
                            }
                        }
                    },
                    { data ->
                        updateState(DataHoldingContract.DataHoldingState.Success(data))
                    }
                )
            } finally {
                isApiCallInProgress = false
            }
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