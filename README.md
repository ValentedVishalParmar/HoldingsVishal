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
- `Reducer`: Pure functions to derive new state
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
