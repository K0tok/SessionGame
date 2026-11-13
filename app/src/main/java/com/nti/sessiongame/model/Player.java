package com.nti.sessiongame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class Player {

    private final int id;
    private final String name;
    private final int colorArgb;
    private int position;
    private final Map<ExamDiscipline, Integer> examPoints;
    private final List<Card> hand;
    private boolean skipNextRoll;

    public Player(int id, String name, int colorArgb) {
        this.id = id;
        this.name = name;
        this.colorArgb = colorArgb;
        this.position = 0;
        this.examPoints = new EnumMap<>(ExamDiscipline.class);
        for (ExamDiscipline discipline : ExamDiscipline.values()) {
            examPoints.put(discipline, 0);
        }
        this.hand = new ArrayList<>();
        this.skipNextRoll = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getColorArgb() {
        return colorArgb;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getExamPoints(ExamDiscipline discipline) {
        return examPoints.get(discipline);
    }

    public Map<ExamDiscipline, Integer> getExamPoints() {
        return Collections.unmodifiableMap(examPoints);
    }

    public void addExamPoint(ExamDiscipline discipline) {
        int current = examPoints.get(discipline);
        if (current < ExamDiscipline.POINTS_TO_PASS) {
            examPoints.put(discipline, current + 1);
        }
    }

    public boolean hasPassedExam(ExamDiscipline discipline) {
        return examPoints.get(discipline) >= ExamDiscipline.POINTS_TO_PASS;
    }

    public boolean hasPassedAllExams() {
        for (ExamDiscipline discipline : ExamDiscipline.values()) {
            if (!hasPassedExam(discipline)) {
                return false;
            }
        }
        return true;
    }

    public List<Card> getHand() {
        return Collections.unmodifiableList(hand);
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public boolean removeCard(Card card) {
        return hand.remove(card);
    }

    public boolean removeCardOfType(CardType type) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getType() == type) {
                hand.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean hasCard(CardType type) {
        for (Card card : hand) {
            if (card.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public boolean isSkipNextRoll() {
        return skipNextRoll;
    }

    public void setSkipNextRoll(boolean skipNextRoll) {
        this.skipNextRoll = skipNextRoll;
    }
}
