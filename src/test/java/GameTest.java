import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void shouldCreateGameWithZeroScore() {
        // given
        var game = new Game(
                Team.ARGENTINA,
                Team.AUSTRALIA
        );

        // when, then
        assertEquals(
                0,
                game.getHomeScore()
                );

        assertEquals(
                0,
                game.getAwayScore()
        );
    }
}