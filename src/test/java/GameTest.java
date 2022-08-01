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
                new int[]{0, 0},
                game.getScore()
                );
    }
}