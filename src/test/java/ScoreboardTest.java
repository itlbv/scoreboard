import exceptions.GameNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreboardTest {

    @Test
    void shouldStartNewGame() {
        // given
        var scoreboard = new Scoreboard();

        // when
        scoreboard.startNewGame(
                Team.ARGENTINA,
                Team.AUSTRALIA
        );

        // then
        assertEquals(
                scoreboard.getGame(
                        Team.ARGENTINA,
                        Team.AUSTRALIA
                ),
                new Game(
                        Team.ARGENTINA,
                        Team.AUSTRALIA
                )
        );
    }

    @Test
    void shouldThrowWhenGameNotFound() {
        // given
        var scoreboard = new Scoreboard();

        // when
        scoreboard.startNewGame(
                Team.ARGENTINA,
                Team.AUSTRALIA
        );


        // then
        assertThrows(
                GameNotFoundException.class,
                () -> scoreboard.getGame(
                        Team.BRAZIL,
                        Team.ARGENTINA
                ));
    }

    @Test
    void shouldFinishGameAndRemoveFromScoreboard() {
        // given
        var scoreboard = new Scoreboard();
        scoreboard.startNewGame(
                Team.ARGENTINA,
                Team.AUSTRALIA
        );

        // when
        scoreboard.finishGame(
                Team.ARGENTINA,
                Team.AUSTRALIA
        );

        // then
        assertTrue(
                scoreboard.getGames()
                        .stream()
                        .noneMatch(g -> g.getHome() == Team.ARGENTINA)
        );
    }

    @Test
    void shouldNotFinishGameWhenGameNotFound() {
        // given
        var scoreboard = new Scoreboard();
        scoreboard.startNewGame(
                Team.ARGENTINA,
                Team.AUSTRALIA
        );

        // when, then
        assertThrows(
                GameNotFoundException.class,
                () -> scoreboard.finishGame(
                        Team.BRAZIL,
                        Team.AUSTRALIA
                )
        );
    }

    @Test
    void shouldUpdateScore() {
        // when
        var scoreboard = new Scoreboard();
        scoreboard.startNewGame(
                Team.ARGENTINA,
                Team.AUSTRALIA
        );

        // given
        scoreboard.updateScore(Team.ARGENTINA, 2, 3);

        // then
        assertEquals(
                2,
                scoreboard.getGame(
                        Team.ARGENTINA,
                        Team.AUSTRALIA
                ).getHomeScore()
        );

        assertEquals(
                3,
                scoreboard.getGame(
                        Team.ARGENTINA,
                        Team.AUSTRALIA
                ).getAwayScore()
        );
    }

    @Test
    void shouldNotUpdateScoreWhenGameNotFound() {
        // given
        var scoreboard = new Scoreboard();
        scoreboard.startNewGame(
                Team.ARGENTINA,
                Team.AUSTRALIA
        );

        // when, then
        assertThrows(
                GameNotFoundException.class,
                () -> scoreboard.updateScore(Team.BRAZIL, 1, 1)
        );
    }
}