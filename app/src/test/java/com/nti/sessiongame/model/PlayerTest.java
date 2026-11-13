package com.nti.sessiongame.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    @Test
    public void examPointsCapAtThreeAndUnlockFinal() {
        Player player = new Player(0, "Аня", 0xFF1B4F72);

        for (int i = 0; i < 5; i++) {
            player.addExamPoint(ExamDiscipline.IT);
        }
        assertEquals(ExamDiscipline.POINTS_TO_PASS, player.getExamPoints(ExamDiscipline.IT));
        assertFalse(player.hasPassedAllExams());

        for (int i = 0; i < 3; i++) {
            player.addExamPoint(ExamDiscipline.ENGINEERING);
            player.addExamPoint(ExamDiscipline.NTI_LIFE);
        }
        assertTrue(player.hasPassedAllExams());
    }

    @Test
    public void handStoresAndRemovesCards() {
        Player player = new Player(1, "Никита", 0xFFF4D03F);
        player.addCard(new StolenNotesCard());
        player.addCard(new AutomatCard());

        assertTrue(player.hasCard(CardType.AUTOMAT));
        assertTrue(player.removeCardOfType(CardType.STOLEN_NOTES));
        assertFalse(player.hasCard(CardType.STOLEN_NOTES));
        assertEquals(1, player.getHand().size());
    }
}
