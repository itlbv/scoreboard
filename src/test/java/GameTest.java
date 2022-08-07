import org.junit.jupiter.api.Test;

import java.util.Arrays;

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

    @Test
    void shouldUpdateScore() {
        // given
        var game = new Game(
                Team.ARGENTINA,
                Team.AUSTRALIA
        );

        // when
        game.updateScore(new int[]{2, 3});

        // then
        assertEquals(2, game.getHomeScore());
        assertEquals(3, game.getAwayScore());
    }

    @Test
    void shouldNotUpdateScoreWhenLessThanZero() {
        // given
        var game = new Game(
                Team.ARGENTINA,
                Team.AUSTRALIA
        );

        // when, then
        assertThrows(
                IllegalArgumentException.class,
                () -> game.updateScore(new int[]{-1, 1})
        );
    }
}
