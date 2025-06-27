# HoldingsVishal 📱

HoldingsVishal is a **modern Android app** built using the **MVI architecture**, **Clean Code principles**, and a **modular structure**. It leverages **Jetpack Compose** for UI, and is designed to be scalable, testable, and easy to maintain.

This project is ideal as a template for building production-grade apps using modern Android development practices.

---

## ✨ Features

- ✅ Jetpack Compose UI with Material 3
- ✅ **MVI (Model-View-Intent)** architecture using unidirectional data flow
- ✅ Clean architecture with separation of concerns (UI, domain, data layers)
- ✅ Modularization for better scalability and build optimization
- ✅ Reactive state management using Kotlin Flows and State management
- ✅ Dependency Injection ready (Hilt or manual, as per your structure)
- ✅ Easy to test and extend

---

## 🧠 Architecture Overview

This project follows **MVI + Clean Architecture** and is **multi-modular** for clear separation between UI, domain logic, and data sources.

### 🔁 MVI (Model - View - Intent)

- **Model**: Represents the business logic and state.
- **View**: A Jetpack Compose UI that reacts to state.
- **Intent**: User actions or events that trigger changes.

Each screen has:
- `State`: Immutable data class representing UI state
- `Intent`: Sealed class representing user actions
- `ViewModel`: Middle layer connecting intent to state updates

### 🧱 Clean Architecture Layers

app/
├── presentation/ # UI layer (Compose, State, Intent)
├── domain/ # Business logic (UseCases, Models)
├── data/ # Data layer (Repositories, Mappers, Sources)


---

## 🧩 Modularization

The app is divided into modules for better decoupling and maintainability:

| Module Name       | Responsibility                               |
|-------------------|----------------------------------------------|
| `app`             | Main module, dependency injector, navigation |
| `presentation`    | UI layer, MVI screen logic                   |
| `domain`          | Business logic, use-cases, and models        |
| `data`            | API layer, database, repository impl         |
| `core`            | Common utilities and shared resources        |

---

## 🛠️ Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **MVI Pattern**
- **Kotlin Flow**
- **AndroidX**
- **Multi-module Gradle Setup**
- **Clean Architecture**
- *(Optional DI Layer: Hilt / Manual injection)*

---

## 📋 Requirements

| Tool                | Version                |
|---------------------|------------------------|
| Android Studio      | Hedgehog (or above)    |
| Kotlin              | 1.9+                   |
| Gradle              | 8.0+                   |
| Android SDK         | 33 (API Level 33) or above |
| JDK                 | 17                     |

---



## Below i have shared the concept of Effect and its Usage as i understood and implemented in above project:

## What is Effect in MVI? Why its important to implement it?

**Effect** represents **one-time actions** that the UI should perform, such as:

* *   Navigation to another screen
* *   Showing a toast message
* *   Opening a dialog
* *   Playing a sound
* *   Triggering animations

Effects are **consumed once** and then forgotten, unlike State which persists.

## MVI vs MVVM Comparison

Let me show you the key differences:

### **MVVM Approach (Traditional)**

// MVVM ViewModel  
class DataHoldingViewModel : ViewModel() {  
    private val \_data = MutableLiveData<List<HoldingData>>()  
    val data: LiveData<List<HoldingData>> = \_data  
      
    private val \_isLoading = MutableLiveData<Boolean>()  
    val isLoading: LiveData<Boolean> = \_isLoading  
      
    private val \_error = MutableLiveData<String>()  
    val error: LiveData<String> = \_error  
      
    // Navigation is handled through callbacks or LiveData  
    private val \_navigateToDetails = MutableLiveData<HoldingData>()  
    val navigateToDetails: LiveData<HoldingData> = \_navigateToDetails  
      
    fun onItemClick(holdingData: HoldingData) {  
        \_navigateToDetails.value = holdingData  
        // Need to reset this to null after navigation  
        \_navigateToDetails.value = null  
    }  
}

### **MVI Approach (Your Current Implementation)**

// MVI Contract  
interface DataHoldingContract {  
    sealed class DataHoldingState {  
        object Loading : DataHoldingState()  
        data class Success(val data: List<HoldingData>) : DataHoldingState()  
        data class Error(val message: String) : DataHoldingState()  
    }  
      
    sealed class DataHoldingEffect {  
        data class NavigateToDetails(val data: HoldingData) : DataHoldingEffect()  
        data class ShowToast(val message: String) : DataHoldingEffect()  
        data class ShowDialog(val title: String, val message: String) : DataHoldingEffect()  
    }  
      
    sealed class DataHoldingEvent {  
        object LoadData : DataHoldingEvent()  
        data class ItemClicked(val data: HoldingData) : DataHoldingEvent()  
    }  
}

// MVI ViewModel  
class DataHoldingViewModel : ViewModel(), DataHoldingContract {  
    private val \_state = MutableStateFlow(DataHoldingState.Loading)  
    val state: StateFlow<DataHoldingState> = \_state.asStateFlow()  
      
    private val \_effect = MutableSharedFlow<DataHoldingEffect>()  
    val effect: SharedFlow<DataHoldingEffect> = \_effect.asSharedFlow()  
      
    fun event(event: DataHoldingEvent) {  
        when (event) {  
            is DataHoldingEvent.ItemClicked -> {  
                viewModelScope.launch {  
                    \_effect.emit(DataHoldingEffect.NavigateToDetails(event.data))  
                }  
            }  
        }  
    }  
}

## Key Differences Explained

### **1\. State vs Effect**

Aspect

State

Effect

**Purpose**

Current UI state (persistent)

One-time actions (consumed once)

**Persistence**

Stays until changed

Disappears after consumption

**Usage**

UI rendering

UI actions

### **2\. Navigation Handling**

**MVVM Problem:**

// MVVM - Need to reset navigation state  
\_navigateToDetails.value = holdingData  
// Must remember to reset to null after navigation  
\_navigateToDetails.value = null

**MVI Solution:**

// MVI - Effect is consumed once automatically  
\_effect.emit(DataHoldingEffect.NavigateToDetails(holdingData))  
// No need to reset - effect disappears after consumption

### **3\. Multiple Actions**

**MVVM Problem:**

// MVVM - Multiple LiveData for different actions  
private val \_navigateToDetails = MutableLiveData<HoldingData>()  
private val \_showToast = MutableLiveData<String>()  
private val \_showDialog = MutableLiveData<Pair<String, String>>()  
// Need to manage multiple LiveData and their resets

**MVI Solution:**

// MVI - Single effect channel for all actions  
sealed class DataHoldingEffect {  
    data class NavigateToDetails(val data: HoldingData) : DataHoldingEffect()  
    data class ShowToast(val message: String) : DataHoldingEffect()  
    data class ShowDialog(val title: String, val message: String) : DataHoldingEffect()  
}  
// All effects handled in one place

## Real-World Example from our Code

Let me show you how effects work in our current implementation:

Read DataHoldingViewModel.kt

Perfect! Here's how effects work in your code:

## our Current Effect Implementation:

// In our ViewModel (lines 75-82)  
is DataHoldingContract.DataHoldingEvent.DataHoldingItemClicked -> {  
    viewModelScope.launch {  
        mutableSharedFlow.emit(  
            DataHoldingContract.DataHoldingEffect.NavigateToDataHoldingDetailsDetails(  
                dataHoldingData = event.dataHoldingData  
            )  
        )  
    }  
}

## How to Use Effects in Compose UI

Here's how we would consume effects in your Compose UI:

@Composable  
fun DataHoldingScreen(  
    viewModel: DataHoldingViewModel = hiltViewModel()  
) {  
    val state by viewModel.state.collectAsState()  
    val context = LocalContext.current  
      
    // Collect effects  
    LaunchedEffect(Unit) {  
        viewModel.effect.collect { effect ->  
            when (effect) {  
                is DataHoldingContract.DataHoldingEffect.NavigateToDataHoldingDetailsDetails -> {  
                    // Navigate to details screen  
                    // This effect is consumed once and forgotten  
                    navController.navigate("details/${effect.dataHoldingData.symbol}")  
                }  
            }  
        }  
    }  
      
    // UI based on state  
    when (state) {  
        is DataHoldingContract.DataHoldingState.Loading -> {  
            LoadingScreen()  
        }  
        is DataHoldingContract.DataHoldingState.Success -> {  
            DataList(  
                data = state.data,  
                onItemClick = { data ->  
                    viewModel.event(DataHoldingContract.DataHoldingEvent.DataHoldingItemClicked(data))  
                }  
            )  
        }  
        is DataHoldingContract.DataHoldingState.Error -> {  
            ErrorScreen(  
                onRetry = {  
                    viewModel.event(DataHoldingContract.DataHoldingEvent.RetryDataLoad)  
                }  
            )  
        }  
    }  
}

## Benefits of Effects Over MVVM

### **1\. No State Reset Issues**

// MVVM - Problematic  
\_navigateToDetails.value = data  
// Must remember to reset  
\_navigateToDetails.value = null

// MVI - Automatic  
\_effect.emit(NavigateToDetails(data))  
// Effect disappears after consumption

### **2\. Multiple Actions in Sequence**

// MVI - Can emit multiple effects  
viewModelScope.launch {  
    \_effect.emit(ShowToast("Data saved"))  
    \_effect.emit(NavigateToDetails(data))  
    \_effect.emit(PlaySound("success"))  
}

## Summary: Effects vs MVVM

Aspect

MVVM

MVI Effects

**Navigation**

LiveData with manual reset

SharedFlow, auto-consumed

**Multiple Actions**

Multiple LiveData

Single effect channel

**State Management**

Mixed with actions

Separated concerns

**Testing**

Harder to test actions

Easy to test effects

**Memory Leaks**

Risk of not resetting

No risk, auto-consumed

**Complexity**

Higher for multiple actions

Lower, unified approach

* * *

## Key Takeaway

**Effects in MVI solve the "action state management" problem that MVVM has.** Instead of managing multiple LiveData for different actions and remembering to reset them, MVI uses a single effect channel that automatically handles one-time actions.

**Think of it this way:**

* *   **State** = "What the screen looks like right now"
* *   **Effect** = "What action should happen once"  
*       
*       
*     This separation makes your code more predictable, testable, and maintainable!



## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/ValentedVishalParmar/HoldingsVishal.git
cd HoldingsVishal
2. Open in Android Studio
Launch Android Studio.

Choose Open an Existing Project and select the cloned folder.

3. Sync and Build
Let Gradle sync and build complete.

Make sure all required SDK packages are installed.

4. Run the App
Use an emulator or a connected Android device.

Click Run or press Shift + F10.

💡 Example: MVI Implementation in HomeScreen
State: HomeState holds loading status and data.

Intent: HomeIntent defines actions like LoadHoldings.

Reducer: Applies changes to the state based on the result.

ViewModel: Receives intent, triggers use-case, updates state.

UI (Composable): Observes state and updates the UI reactively.
---

🤝 Contributing
Want to improve or contribute? Please fork the repo and make a pull request:

bash
Copy
Edit
1. Fork it
2. Create your feature branch: git checkout -b feature-name
3. Commit your changes: git commit -am 'Add feature'
4. Push to the branch: git push origin feature-name
5. Open a Pull Request
📄 License
This project is licensed under the MIT License. See LICENSE for more information.
