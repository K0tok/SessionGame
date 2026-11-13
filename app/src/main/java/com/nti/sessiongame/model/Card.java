package com.nti.sessiongame.model;

public abstract class Card {

    private final CardType type;

    protected Card(CardType type) {
        this.type = type;
    }

    public CardType getType() {
        return type;
    }

    public boolean isSabotage() {
        return type == CardType.STOLEN_NOTES || type == CardType.EXTRA_TICKET;
    }

    public boolean isDefense() {
        return type == CardType.AUTOMAT;
    }

    public static Card of(CardType type) {
        switch (type) {
            case STOLEN_NOTES:
                return new StolenNotesCard();
            case EXTRA_TICKET:
                return new ExtraTicketCard();
            case AUTOMAT:
                return new AutomatCard();
            default:
                throw new IllegalArgumentException("Unknown card type: " + type);
        }
    }
}
