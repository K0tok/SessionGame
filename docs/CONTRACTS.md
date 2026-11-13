# Контракты UI ↔ логика

Пакет приложения: `com.nti.sessiongame`

## Зоны

| Зона | Пакет / путь | Кто |
|------|--------------|-----|
| Модель | `model/` | Core |
| Контроллер | `controller/` | Core |
| Контент (парсер) | `content/` | Core |
| Утилиты логики | `util/` | Core |
| Экраны | `ui/`, `MainActivity` | Никита |
| Layouts / resources | `app/src/main/res/` | Никита |
| JSON-контент | `app/src/main/assets/` | схема — Core, наполнение — Никита |

**Правило:** UI не содержит правил игры. Решения принимает только `GameController`.

## Навигация экранов

```
MainActivity
  ├─→ LobbyActivity ─→ GameActivity ─→ (назад в MainActivity)
  └─→ RulesActivity
```

## Планируемые классы модели (этап 1)

- `Player`
- `Board`
- `Cell` / `CellType`
- `ExamDiscipline`
- `GameState` / `GamePhase`
- `Dice`
- карты: `StolenNotes`, `ExtraTicket`, `Automat`

## Контроллер

- `GameController` — единственная точка изменения состояния партии
- `GameEventListener` — UI (`GameActivity`) подписывается на события

### События listener

| Метод | Когда |
|-------|--------|
| `onTurnChanged` | сменился активный игрок |
| `onMoved` | фишка переместилась |
| `onCellEntered` | нужно показать UI клетки |
| `onGameWon` | победа |

Новые события — в Core; реализацию в Activity — Никита.

## Intent extras

Из `LobbyActivity` в `GameActivity` передаём состав игроков, например:

- `EXTRA_PLAYER_NAMES` — `String[]`
- `EXTRA_PLAYER_COLORS` — `int[]` (ARGB)

Точные ключи зафиксируем при реализации лобби.
