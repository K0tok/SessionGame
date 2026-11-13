# Сессия — Android hot-seat игра

Формат: 2–4 игрока на одном устройстве.  
Цель: закрыть три экзамена (по 3 балла) и пройти финальную защиту.

Стек: Android Studio, Java, minSdk 24.

## Открыть проект

1. Android Studio → Open → папка `SessionGame`
2. Дождаться Gradle Sync
3. Run на эмуляторе / устройстве

## Структура пакетов

```
com.nti.sessiongame
├── MainActivity          — меню
├── model/                — игровая модель
├── controller/           — логика партии
├── content/              — загрузка JSON
├── util/                 — утилиты
└── ui/                   — Lobby / Rules / Game (Никита)
```

Документы: `docs/CONTRACTS.md`, `docs/ROLES.md`, `docs/PLAN.md`.

## Этап 0

- [x] Каркас проекта
- [x] Пакеты model / controller / ui / content / util
- [x] Навигация: Меню → Лобби → Игра, Меню → Правила
- [x] Контракт `GameEventListener`
