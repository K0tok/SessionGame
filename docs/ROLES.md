# Распределение ролей

## Core / Logic

- `model/`, `controller/`, `content/`, `util/`
- unit-тесты правил
- сохранение партии
- схема JSON контента

## Никита — UI / Content

- экраны, layouts, стили, анимации
- наполнение `assets/` вопросами и событиями
- UX hot-seat («чей ход», «передай устройство»)

Не менять без договорённости: `model/`, `controller/` (кроме реализации `GameEventListener`).
