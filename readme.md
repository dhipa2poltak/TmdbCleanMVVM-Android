# TmdbCleanMVVM-Android

**Technology Stack:**
- Kotlin Programming Language
- Clean Architecture
- MVVM Architecture Pattern
- ViewModel
- Coroutine
- Navigation Graph
- Hilt Dependency Injection
- ViewBinding, DataBinding
- Retrofit REST + OkHttp
- GSON Serialization
- Picasso Image Loader
- Unit Test: JUnit, Mockito, MockWebServer, Robolectric
- Code Coverage: JaCoCo
- All layouts are implemented using ConstraintLayout
- Gradle build flavors
- Proguard
- Embedded Youtube Android Player

**Caution:**
**All the api calls are switching to IO dispatchers in DataSource class.**

To run Code Coverage (JaCoCo):
1. Open Terminal then move to "root_project" directory.
2. type "./gradlew codeCoverModules" (enter), wait until finish executing.
3. type "./gradlew allDebugCoverage" (enter), wait until finish executing.

The report file will be located in "root_project/build/reports/jacoco/allDebugCoverage/html/index.html", open it using browser.

**Caution:**
**Later I will add more unit tests to increase the code coverage value.**
