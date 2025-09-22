# Мой Домик (My Domik)

Это минимальный Android-проект (Kotlin + Jetpack Compose) — прототип приложения "Мой Домик".
В проект включены:
- Модуль `app/` с исходниками.
- `codemagic.yaml` для сборки на Codemagic (конфиг пытается установить Gradle в CI, поэтому gradle-wrapper.jar не обязателен).
- stub-версии `gradlew` и `gradlew.bat`. Чтобы иметь рабочий wrapper локально выполните `gradle wrapper`.

**Важно:** gradle-wrapper.jar не включён. Если вам нужен полноценный gradle wrapper в репозитории — выполните `./gradlew wrapper` локально и закоммитьте `gradle/wrapper/gradle-wrapper.jar`.
