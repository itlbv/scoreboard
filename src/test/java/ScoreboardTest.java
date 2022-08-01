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
                () -> {
                    scoreboard.getGame(
                            Team.BRAZIL,
                            Team.ARGENTINA
                    );
                });
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
        scoreboard.updateScore(Team.ARGENTINA);
        scoreboard.updateScore(Team.ARGENTINA);
        scoreboard.updateScore(Team.AUSTRALIA);

        // then
        assertEquals(
                2,
                scoreboard.getGame(
                        Team.ARGENTINA,
                        Team.AUSTRALIA
                ).getHomeScore()
        );

        assertEquals(
                1,
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
                () -> scoreboard.updateScore(Team.BRAZIL)
        );
    }

    @Test
    void shouldGetAllGamesSortedByTotalScore() {
        // given
        var scoreboard = new Scoreboard();

        var argentinaAustralia = new Game(Team.ARGENTINA, Team.AUSTRALIA);
        argentinaAustralia.scoreHome();
        argentinaAustralia.scoreHome();
        argentinaAustralia.scoreHome();
        argentinaAustralia.scoreHome();
        argentinaAustralia.scoreHome();

        var mexicoCanada = new  Game(Team.MEXICO, Team.CANADA);
        mexicoCanada.scoreHome();
        mexicoCanada.scoreAway();

        var spainBrazil = new  Game(Team.SPAIN, Team.BRAZIL);
        spainBrazil.scoreAway();
        spainBrazil.scoreAway();

        var uruguayItaly = new  Game(Team.URUGUAY, Team.ITALY);
        uruguayItaly.scoreHome();
        uruguayItaly.scoreAway();
        uruguayItaly.scoreAway();

        var expectedGames = new ArrayList<Game>();
        expectedGames.add(argentinaAustralia);
        expectedGames.add(spainBrazil);
        expectedGames.add(uruguayItaly);
        expectedGames.add(mexicoCanada);

        // when
        scoreboard.startNewGame(Team.MEXICO, Team.CANADA);
        scoreboard.startNewGame(Team.ARGENTINA, Team.AUSTRALIA);
        scoreboard.startNewGame(Team.URUGUAY, Team.ITALY);
        scoreboard.startNewGame(Team.SPAIN, Team.BRAZIL);

        scoreboard.updateScore(Team.ARGENTINA);
        scoreboard.updateScore(Team.ARGENTINA);
        scoreboard.updateScore(Team.MEXICO);
        scoreboard.updateScore(Team.ITALY);
        scoreboard.updateScore(Team.ITALY);
        scoreboard.updateScore(Team.ARGENTINA);
        scoreboard.updateScore(Team.BRAZIL);
        scoreboard.updateScore(Team.ARGENTINA);
        scoreboard.updateScore(Team.ARGENTINA);
        scoreboard.updateScore(Team.BRAZIL);
        scoreboard.updateScore(Team.URUGUAY);
        scoreboard.updateScore(Team.BRAZIL);
        scoreboard.updateScore(Team.SPAIN);
        scoreboard.updateScore(Team.SPAIN);
        scoreboard.updateScore(Team.SPAIN);
        scoreboard.updateScore(Team.CANADA);
        scoreboard.updateScore(Team.ARGENTINA);

        // then
        assertEquals(expectedGames, scoreboard.getGames());
    }
}