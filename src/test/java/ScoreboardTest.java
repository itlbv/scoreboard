import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void shouldFinishGameAndRemoveFromScoreboard() {}

    @Test
    void shouldUpdateScore() {}

    @Test
    void shouldGetAllGamesSortedByLastUpdated() {}
}