import exceptions.GameNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
                () -> {
                    scoreboard.getGame(
                            Team.BRAZIL,
                            Team.ARGENTINA
                    );
                });
    }

    @Test
    void shouldFinishGameAndRemoveFromScoreboard() {}

    @Test
    void shouldUpdateScore() {}

    @Test
    void shouldGetAllGamesSortedByLastUpdated() {}
}