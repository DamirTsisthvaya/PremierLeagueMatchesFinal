#  Premier League App

Приложение для просмотра матчей Английской Премьер-Лиги с поддержкой кэширования, поиска и бесконечной прокрутки.

##  Возможности

-  Загрузка матчей из удалённого API
-  Кэширование матчей в локальной базе данных (Room)
-  Поиск матчей по командам (в реальном времени, без кнопки)
-  Плавная пагинация (LazyColumn)
-  Просмотр детальной информации о матче
-  Современный интерфейс с Jetpack Compose


##  Архитектура

Архитектура построена по принципу **MVVM + Clean Architecture**:



ui/
├── screens/                 # Экран списка матчей и детали
├── components/              # Общие элементы интерфейса
├── theme/                   # Цвета и стили

data/
├── remote/                  # Retrofit API (getMatches, getMatchById)
├── local/                   # Room: MatchDao, MatchDatabase
├── repository/              # MatchRepository (interface + реализация)

model/                       # Match.kt — модель матча (Entity + JSON)

di/                          # Модули Hilt:  AppModule

utils/                       # Утилиты и вспомогательные функции



## Технологии

| Технология      | Назначение                                 |
|-----------------|---------------------------------------------|
| **Kotlin**      | Основной язык проекта                       |
| **Jetpack Compose** | UI без XML, декларативно             |
| **Room**        | Локальное хранилище матчей                  |
| **Retrofit**    | Работа с REST API                           |
| **Hilt (Dagger)** | Dependency Injection (внедрение зависимостей) |
| **StateFlow**   | Хранение состояния и реактивность          |
| **MVVM**        | Разделение логики, UI и модели              |

---


## Источник данных

Матчи загружаются с [FixtureDownload.com](https://fixturedownload.com) (формат JSON):

```
https://fixturedownload.com/feed/json/epl-2023
```

## Автор

**Дамир Цицхвая**
Android-разработчик | Kotlin & Jetpack Compose Enthusiast
[GitHub](https://github.com/DamirTsisthvaya)

---


