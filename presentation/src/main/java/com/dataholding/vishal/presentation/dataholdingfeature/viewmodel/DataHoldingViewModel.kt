package com.dataholding.vishal.presentation.dataholdingfeature.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dataholding.vishal.core.functional.fold
import com.dataholding.vishal.coreui.functional.stateInWhileActive
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

//todo:: 28] create feature specific view model as below
/*
* now add @HiltViewmodel and inject usecase in constructor and extend viewModel,
*  implement specific contract and override its method for event
* */
@HiltViewModel
class DataHoldingViewModel @Inject constructor(private val dataUseCase: HoldingDataUseCase): ViewModel(), DataHoldingContract {

    //1]
    private val mutableUIState: MutableStateFlow<DataHoldingContract.DataHoldingState> =
        MutableStateFlow(DataHoldingContract.DataHoldingState.Loading)
//2]
    private val mutableSharedFlow: MutableSharedFlow<DataHoldingContract.DataHoldingEffect> = MutableSharedFlow()

    //3]
    override val effect: SharedFlow<DataHoldingContract.DataHoldingEffect>
        get() = mutableSharedFlow.asSharedFlow()

    //4]
    override val state: StateFlow<DataHoldingContract.DataHoldingState>
        get() = mutableUIState.stateInWhileActive(viewModelScope, DataHoldingContract.DataHoldingState.Loading) {
            event(DataHoldingContract.DataHoldingEvent.LoadDataHoldingList)
        }

    override fun event(event: DataHoldingContract.DataHoldingEvent) {
        //7]
       when(event) {
           is DataHoldingContract.DataHoldingEvent.LoadDataHoldingList -> {
               loadDataHoldings()
           }

           is DataHoldingContract.DataHoldingEvent.DataHoldingItemClicked -> {
               viewModelScope.launch {
                   mutableSharedFlow.emit(DataHoldingContract.DataHoldingEffect.NavigateToDataHoldingDetailsDetails(dataHoldingData = event.dataHoldingData ))
               }
           }
       }
    }

    //6]
    private fun loadDataHoldings() {
        viewModelScope.launch {
            dataUseCase.getInvokeHoldingDataApiCall().fold({
                updateState(DataHoldingContract.DataHoldingState.Error(it))
            },{
                updateState(DataHoldingContract.DataHoldingState.Success(it))
            })
        }
    }

    // 5]
    private fun updateState(state: DataHoldingContract.DataHoldingState) {
        mutableUIState.update { state }
    }
}