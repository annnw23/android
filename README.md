# 🚀 Celestial Viewport

> A space-exploration educational Android app that gamifies astronomy learning through mission-structured lessons, interactive quizzes, an XP reward system, and a live leaderboard.

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-7F52FF?style=flat&logo=kotlin&logoColor=white)
![Compose](https://img.shields.io/badge/Jetpack_Compose-BOM_2024.08-4285F4?style=flat&logo=jetpackcompose&logoColor=white)
![Min SDK](https://img.shields.io/badge/Min_SDK-26_(Android_8.0)-brightgreen?style=flat)
![Version](https://img.shields.io/badge/Version-4.2.0-orange?style=flat)
![Branch](https://img.shields.io/badge/Branch-end-blue?style=flat)

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Screenshots & Screen Catalogue](#screenshots--screen-catalogue)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Database Design](#database-design)
- [Pre-Coding Setup (Before You Start)](#pre-coding-setup-before-you-start)
- [Quick Start](#quick-start)
- [Development Roadmap](#development-roadmap)
- [Known Limitations (etap1)](#known-limitations-etap1)

---

## Overview

Celestial Viewport is a native Android application built entirely in **Kotlin** with **Jetpack Compose**. It turns astronomy education into a gamified mission-based experience — users read lessons, complete interactive quizzes, earn XP rewards, and compete on a live leaderboard. The app uses a **MVVM + Repository** architecture with **Hilt DI** and **Room SQLite** for type-safe data persistence.

| Property | Value |
|---|---|
| Package ID | `com.celestial.viewport` |
| Version | `4.2.0` (versionCode 1) |
| Min SDK | Android 8.0 (API 26) |
| Target SDK | Android 14 (API 34) |
| Language | Kotlin 2.0.0 |
| UI Framework | Jetpack Compose |
| Architecture | MVVM + Repository + Hilt DI |
| Database | Room 2.6.1 (SQLite) |

---

## Features

- **Onboarding** — Animated nebula glow screen with mission launch CTA
- **Dashboard** — Personalised greeting, XP stats, featured lesson hero card, and full lesson feed
- **Explore** — Real-time search filtering across lessons and categories
- **Missions** — Progress tracking list showing completion status per lesson
- **Lesson Detail** — Full lesson view with image, description, and quiz entry
- **Interactive Quiz** — Answer feedback (green/red), question progress indicator, score calculation
- **Quiz Results** — Pass/fail display, XP earned, retry or return to dashboard
- **Ranks / Leaderboard** — Top-3 podium (gold/silver/bronze) + full ranked list with "YOU" badge

---

## Screenshots & Screen Catalogue

| Screen | Route | ViewModel | Key Behaviour |
|---|---|---|---|
| Onboarding | `onboarding` | — | `InfiniteTransition` glow animation, navigates to Dashboard (pops self) |
| Dashboard | `dashboard` | `DashboardViewModel` | Hero lesson card + lazy lesson list |
| Explore | `explore` | `ExploreViewModel` | `combine()` Flow for real-time search |
| Missions | `missions` | `DashboardViewModel` | Completion icons per lesson |
| Lesson Detail | `lesson/{lessonId}` | `LessonDetailViewModel` | `SavedStateHandle` lessonId, shows quiz button if quiz exists |
| Quiz | `quiz/{quizId}` | `QuizViewModel` | `QuizUiState` drives all state; saves result + XP on finish |
| Quiz Result | `quiz_result/{quizId}/{score}/{total}` | — | Nav args only; Retry or Done actions |
| Ranks | `ranks` | `RanksViewModel` | Podium top-3 + full leaderboard; current user highlighted |

---

## Tech Stack

### Core Platform

| Technology | Version |
|---|---|
| Kotlin | 2.0.0 |
| Android Gradle Plugin | 8.5.2 |
| JVM Target | 17 |
| Min SDK | 26 |
| Target / Compile SDK | 34 |

### UI

| Library | Version |
|---|---|
| Jetpack Compose BOM | 2024.08.00 |
| Material 3 | BOM-managed |
| Navigation Compose | 2.7.7 |
| Activity Compose | 1.9.1 |
| Material Icons Extended | BOM-managed |

### Architecture & DI

| Library | Version |
|---|---|
| Hilt Android | 2.51.1 |
| Hilt Navigation Compose | 1.2.0 |
| Lifecycle ViewModel Compose | 2.8.4 |
| Lifecycle Runtime KTX | 2.8.4 |

### Data

| Library | Version |
|---|---|
| Room Runtime | 2.6.1 |
| Room KTX | 2.6.1 |
| Room Compiler (KSP) | 2.6.1 |
| Kotlinx Coroutines Android | 1.8.1 |
| Gson | 2.10.1 |

### Media & Other

| Library | Version |
|---|---|
| Coil Compose | 2.6.0 |
| KSP | 2.0.0-1.0.21 |

---

## Architecture

The app strictly follows **MVVM + Repository** pattern:

```
┌─────────────────────────────────────────┐
│              UI Layer                   │
│   Screen Composables (observe StateFlow)│
└────────────────┬────────────────────────┘
                 │ collectAsState()
┌────────────────▼────────────────────────┐
│           ViewModel Layer               │
│  DashboardVM / QuizVM / RanksVM / ...   │
│  Exposes StateFlow · calls Repository   │
└────────────────┬────────────────────────┘
                 │ suspend funs + Flow
┌────────────────▼────────────────────────┐
│          Repository Layer               │
│        CelestialRepository              │
│   Single source of truth for all DAOs  │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│            Data Layer                   │
│  Room DAOs → SQLite (celestial_viewport)│
└─────────────────────────────────────────┘
             ▲
             │ @Provides via Hilt
┌───────────┴─────────────────────────────┐
│          DI Layer (Hilt)                │
│  DatabaseModule · SingletonComponent    │
└─────────────────────────────────────────┘
```

### Navigation

All routes are defined as sealed subclasses of `Screen`, preventing string typos at call sites. Arguments are typed (`NavType.IntType`) and extracted via `SavedStateHandle` in ViewModels.

```kotlin
sealed class Screen(val route: String) {
    object Onboarding   : Screen("onboarding")
    object Dashboard    : Screen("dashboard")
    object Explore      : Screen("explore")
    object Missions     : Screen("missions")
    object Ranks        : Screen("ranks")
    object LessonDetail : Screen("lesson/{lessonId}") {
        fun createRoute(id: Int) = "lesson/$id"
    }
    object Quiz         : Screen("quiz/{quizId}") {
        fun createRoute(id: Int) = "quiz/$id"
    }
    object QuizResult   : Screen("quiz_result/{quizId}/{score}/{total}") {
        fun createRoute(quizId: Int, score: Int, total: Int) = "quiz_result/$quizId/$score/$total"
    }
}
```

### State Management

All UI state flows as `StateFlow<T>` using `SharingStarted.WhileSubscribed(5000)` — keeps flows alive for 5 seconds after the last subscriber disconnects, preventing unnecessary re-queries on configuration changes. Each ViewModel exposes immutable state only; business logic resides exclusively in the Repository layer.

**Example: QuizViewModel**

```kotlin
@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: CelestialRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val quizId: Int = savedStateHandle.get<Int>("quizId") ?: -1
    
    private val _uiState = MutableStateFlow<QuizUiState>(QuizUiState.Loading)
    val uiState = _uiState.asStateFlow()
    
    init {
        loadQuiz()
    }
    
    private fun loadQuiz() {
        viewModelScope.launch {
            repository.getQuizWithQuestions(quizId)
                .collect { quizData ->
                    _uiState.value = QuizUiState.Loaded(quizData)
                }
        }
    }
    
    fun submitAnswer(questionId: Int, selectedOption: Int) {
        // Update state, calculate score, persist result
    }
}
```

---

## Project Structure

```
CelestialViewport/
├── app/
│   ├── build.gradle.kts                   ← app-level deps & plugins
│   └── src/main/
│       ├── AndroidManifest.xml            ← INTERNET permission, app class, activity
│       └── java/com/celestial/viewport/
│           ├── CelestialApp.kt            ← @HiltAndroidApp
│           ├── MainActivity.kt            ← @AndroidEntryPoint · AppNavHost
│           ├── data/
│           │   ├── entity/Entities.kt     ← Room @Entity: User, Lesson, Quiz, QuizQuestion, QuizResult
│           │   ├── dao/Daos.kt            ← @Dao interfaces (Flow-returning queries)
│           │   ├── database/
│           │   │   └── CelestialDatabase.kt  ← Singleton DB + SeedCallback
│           │   └── repository/
│           │       └── CelestialRepository.kt ← @Singleton, aggregates all DAOs
│           ├── di/
│           │   └── DatabaseModule.kt      ← @Module @InstallIn(SingletonComponent)
│           ├── ui/
│           │   ├── Navigation.kt          ← sealed Screen hierarchy
│           │   ├── components/
│           │   │   └── SharedComponents.kt  ← TopBar, BottomNav, GlassCard, CosmicButton, StatChip
│           │   ├── screens/
│           │   │   ├── OnboardingScreen.kt
│           │   │   ├── DashboardScreen.kt
│           │   │   ├── ExploreScreen.kt
│           │   │   ├── MissionsScreen.kt
│           │   │   ├── LessonDetailScreen.kt
│           │   │   ├── QuizScreen.kt
│           │   │   ├── QuizResultScreen.kt
│           │   │   └── RanksScreen.kt
│           │   └── theme/
│           │       ├── Color.kt           ← Cosmic palette (Primary cyan, Secondary purple, Tertiary gold)
│           │       ├── Theme.kt           ← CelestialTheme (darkColorScheme)
│           │       └── Type.kt            ← Space Grotesk typography
│           └── viewmodel/
│               └── ViewModels.kt          ← DashboardVM, LessonDetailVM, QuizVM, RanksVM, ExploreVM
├── gradle/
│   └── libs.versions.toml                 ← Central version catalogue
└── build.gradle.kts                       ← Root build (plugin declarations)
```

---

## Database Design

Room database: **`celestial_viewport.db`** — 5 tables with FK constraints and CASCADE delete.

```
user ──────────────────────────────────────────┐
  id (PK)                                      │
  username                                     │
  xpTotal                                      │
  rank                                         │
                                               │ FK
lessons ──────────────────┐                    │
  id (PK)                 │                    │
  title                   │                    │
  description             │ FK                 │
  imageUrl                │                    │
  category                │                    │
  missionNumber           ▼                    │
                       quizzes                 │
                         id (PK)               │
                         lessonId (FK)         │
                         title            quiz_results
                         passingScore       id (PK)
                         points    ◄────── userId (FK) ──► user
                              │            quizId (FK) ──► quizzes
                              │ FK          score
                              ▼
                       quiz_questions
                         id (PK)
                         quizId (FK)
                         questionText
                         options (JSON → List<String> via Gson TypeConverter)
                         correctOption
```

### Seed Data (auto-inserted on first launch)

- **7 users** — 1 local player + 6 leaderboard rivals with varying XP and ranks
- **5 lessons** — Saturn, Mars, Black Holes, Stars Life Cycle, Dark Matter (Wikipedia images)
- **5 quizzes** — one per lesson with passing thresholds (60–80%) and XP rewards (500–1000)
- **10 quiz questions** — 5 for Saturn, 5 for Mars (remaining lessons seeded in etap3)

---

## Pre-Coding Setup (Before You Start)

Complete these steps **before writing any application code**.

### 1. Install Android Studio

Download **Android Studio Hedgehog (2023.1.1)** or later from [developer.android.com/studio](https://developer.android.com/studio). During setup install the Android SDK, AVD tools, and default SDK packages.

### 2. Install SDK Components

Open **SDK Manager** and install:

- Android SDK Platform **34** (compile target)
- Android SDK Platform **26** (min SDK — for emulator testing)
- Android SDK Build-Tools **34.0.0**
- Android Emulator + HAXM (or equivalent accelerator for your OS)

### 3. Configure JDK 17

The project targets JVM 17. In Android Studio go to **File → Project Structure → SDK Location** and confirm JDK 17 is selected (bundled with Hedgehog+). Both `build.gradle.kts` files must contain:

```kotlin
compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlinOptions { jvmTarget = "17" }
```

### 4. Install Git

Install Git 2.40+ from [git-scm.com](https://git-scm.com), configure your identity, then clone and checkout the branch:

```bash
git clone https://github.com/annnw23/android.git
cd android
git checkout end
```

### 5. Sync Gradle

Open the `CelestialViewport/` subfolder as the project root in Android Studio, then:

```
File → Sync Project with Gradle Files
```

Wait for all dependencies to download. Confirm zero errors in the Build output panel.

> **Note:** All dependency versions are managed centrally in `gradle/libs.versions.toml`. Never hard-code version strings in `build.gradle.kts`.

### 6. Verify KSP

Room and Hilt both require KSP (not KAPT). Confirm the plugin is applied in `app/build.gradle.kts`:

```kotlin
alias(libs.plugins.ksp)
```

Never mix KSP and KAPT in the same module.

### 7. Verify Hilt Application Class

`CelestialApp` must be annotated `@HiltAndroidApp` and registered in `AndroidManifest.xml` under `android:name=".CelestialApp"`. Without this, Hilt throws a runtime exception on launch.

### 8. Verify INTERNET Permission

Lesson images load from Wikipedia CDN via Coil. Confirm this is in `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### 9. Create an AVD

In **Device Manager** create a virtual device:

- **Device:** Pixel 6 (or similar)
- **System Image:** API 34
- **RAM:** 2 GB minimum for smooth Compose rendering

### 10. Review the Design System

Before writing any UI, read `Color.kt` and `Type.kt` to internalise the design tokens:

| Token | Hex | Usage |
|---|---|---|
| `Primary` | `#81ECFF` | Cyan — interactive elements, XP values, highlights |
| `Secondary` | `#CB7BFF` | Purple — category labels, secondary actions |
| `Tertiary` | `#FFB151` | Gold — leaderboard podium, rank badges |
| `Background` | `#0C0E12` | Near-black — all screen backgrounds |
| `GlassPanel` | `#6622262B` | Semi-transparent dark (glass-morphism cards) |
| `OnSurface` | `#F8F9FE` | Primary text |
| `OnSurfaceVariant` | `#A9ABB0` | Muted / secondary text |

---

## Quick Start

```bash
# 1. Clone and checkout
git clone https://github.com/annnw23/android.git
cd android && git checkout end

# 2. Open CelestialViewport/ as project root in Android Studio

# 3. Sync Gradle (File → Sync Project with Gradle Files)

# 4. Create AVD (Pixel 6, API 34) in Device Manager

# 5. Run
# Press Shift+F10 — the app installs, launches, and seeds the DB automatically

# 6. Tap "START MISSION" on the Onboarding screen
```

> **Resetting data:** The `SeedCallback` runs only on the first database creation (`onCreate`). To reset all data, uninstall and reinstall the app.

---

## Development Roadmap

### ✅ etap1 — Foundation (complete)

- [x] Project scaffold with version catalogue and all plugins
- [x] Design system (Color, Type, Theme)
- [x] Full Room data layer (5 entities, 5 DAOs, repository, DI module)
- [x] Database seeding on first launch
- [x] Sealed `Screen` navigation with typed arguments
- [x] Shared component library (TopBar, BottomNav, GlassCard, CosmicButton, StatChip)
- [x] All 8 screens implemented and connected
- [x] Quiz engine with answer feedback and XP persistence

### 🔄 etap2 — Persistence & State (planned)

- [ ] `CompletedLesson` junction table to replace hard-coded completion status
- [ ] `DataStore Preferences` for storing current user ID (replace hard-coded `id=1`)
- [ ] Username entry flow on first launch
- [ ] Best-score display on Lesson Detail screen
- [ ] Quiz retry logic that clears `QuizUiState` properly

### 📋 etap3 — Enrichment (planned)

- [ ] Expand to 20+ lessons across 5 categories
- [ ] Category filter chips on Explore screen
- [ ] Progress ring chart on Dashboard
- [ ] Seed quiz questions for all 5 existing lessons
- [ ] Room database migration strategy (version 2)

### 🎨 etap4 — Polish & Release (planned)

- [ ] Enter/exit screen transitions (`AnimatedContent`)
- [ ] Full accessibility audit (content descriptions, contrast, touch targets)
- [ ] ProGuard / R8 minification in release build
- [ ] Unit tests for `QuizViewModel` (score calculation, state transitions)
- [ ] Room DAO integration tests
- [ ] Play Store listing assets and signed release APK

---

## Known Limitations (etap1)

| Area | Current Behaviour | Planned Fix |
|---|---|---|
| Mission completion | First 2 lessons always show as DONE (hard-coded) | etap2: `CompletedLesson` table |
| User identity | Local user is always `id=1` with seeded username | etap2: DataStore + name entry flow |
| Quiz seeding | Only Saturn and Mars have questions; others are empty | etap3: full question seeding |
| Challenge a Friend | Button is UI-only, no backend | Future phase |
| Rank update cadence | Footer says "24 hours" but updates are instant (local DB) | Future phase: backend sync |
| Image caching | Default Coil in-memory/disk cache; no offline-first strategy | etap3: explicit `DiskCache` config |
| DB migrations | `exportSchema = false`; no migration scripts defined | etap3: add before v2 |

---

## License

This project is for educational purposes. See `LICENSE` for details.
