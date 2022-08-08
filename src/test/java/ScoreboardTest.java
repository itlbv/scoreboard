import exceptions.GameNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
    void shouldNotStartNewGameIfOneOfTeamsAlreadyPlaying() {
        // given
        var scoreboard = new Scoreboard();
        scoreboard.startNewGame(
                Team.ARGENTINA,
                Team.AUSTRALIA
        );
        scoreboard.startNewGame(
                Team.MEXICO,
                Team.CANADA
        );

        // when, then
        assertThrows(
                IllegalArgumentException.class,
                () -> scoreboard.startNewGame(
                        Team.AUSTRALIA,
                        Team.ITALY
                )
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> scoreboard.startNewGame(
                        Team.URUGUAY,
                        Team.MEXICO
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
        // given
        var scoreboard = new Scoreboard();
        scoreboard.startNewGame(
                Team.ARGENTINA,
                Team.AUSTRALIA
        );

        // when
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

    @Test
    void shouldReturnGamesOrderedByLastStartedWhenEqualTotalScore() {
        // given
        var mexicoCanada = new Game(Team.MEXICO, Team.CANADA);
        mexicoCanada.updateScore(new int[]{2, 2});

        var germanyFrance = new Game(Team.GERMANY, Team.FRANCE);
        germanyFrance.updateScore(new int[]{4, 0});

        var argentinaAustralia = new Game(Team.ARGENTINA, Team.AUSTRALIA);
        argentinaAustralia.updateScore(new int[]{1, 3});

        var expectedGamesInOrder = List.of(
                mexicoCanada,
                germanyFrance,
                argentinaAustralia
        );

        // when
        var scoreboard = new Scoreboard();
        scoreboard.startNewGame(Team.ARGENTINA, Team.AUSTRALIA);
        scoreboard.startNewGame(Team.GERMANY, Team.FRANCE);
        scoreboard.startNewGame(Team.MEXICO, Team.CANADA);

        scoreboard.updateScore(Team.GERMANY, 4, 0);
        scoreboard.updateScore(Team.MEXICO, 2, 2);
        scoreboard.updateScore(Team.ARGENTINA, 1, 3);

        // then
        assertEquals(
                expectedGamesInOrder,
                scoreboard.getGames()
        );
    }

    @Test
    void shouldReturnGamesOrderedByTotalScore() {
        // given
        var mexicoCanada = new Game(Team.MEXICO, Team.CANADA);
        mexicoCanada.updateScore(new int[]{1, 3});

        var argentinaAustralia = new Game(Team.ARGENTINA, Team.AUSTRALIA);
        argentinaAustralia.updateScore(new int[]{1, 2});

        var uruguayItaly = new Game(Team.URUGUAY, Team.ITALY);
        uruguayItaly.updateScore(new int[]{1, 1});

        var expectedGamesInOrder = List.of(
                mexicoCanada,
                argentinaAustralia,
                uruguayItaly
        );

        // when
        var scoreboard = new Scoreboard();
        scoreboard.startNewGame(Team.ARGENTINA, Team.AUSTRALIA);
        scoreboard.startNewGame(Team.URUGUAY, Team.ITALY);
        scoreboard.startNewGame(Team.MEXICO, Team.CANADA);

        scoreboard.updateScore(Team.ARGENTINA, 1, 2);
        scoreboard.updateScore(Team.URUGUAY, 1, 1);
        scoreboard.updateScore(Team.MEXICO, 1, 3);

        // then
        assertEquals(
                expectedGamesInOrder,
                scoreboard.getGames()
        );
    }

    @Test
    void shouldGetAllGamesSortedByTotalScoreAndStartTime() {
        // given
        var mexicoCanada = new  Game(Team.MEXICO, Team.CANADA);
        mexicoCanada.updateScore(new int[]{0, 5});

        var spainBrazil = new  Game(Team.SPAIN, Team.BRAZIL);
        spainBrazil.updateScore(new int[]{10, 2});

        var germanyFrance = new  Game(Team.GERMANY, Team.FRANCE);
        germanyFrance.updateScore(new int[]{2, 2});

        var uruguayItaly = new  Game(Team.URUGUAY, Team.ITALY);
        uruguayItaly.updateScore(new int[]{6, 6});

        var argentinaAustralia = new  Game(Team.ARGENTINA, Team.AUSTRALIA);
        argentinaAustralia.updateScore(new int[]{3, 1});

        var expectedGamesInOrder = new ArrayList<Game>();
        expectedGamesInOrder.add(uruguayItaly);
        expectedGamesInOrder.add(spainBrazil);
        expectedGamesInOrder.add(mexicoCanada);
        expectedGamesInOrder.add(argentinaAustralia);
        expectedGamesInOrder.add(germanyFrance);

        // when
        var scoreboard = new Scoreboard();
        scoreboard.startNewGame(Team.MEXICO, Team.CANADA);
        scoreboard.updateScore(Team.MEXICO, 0, 5);

        scoreboard.startNewGame(Team.SPAIN, Team.BRAZIL);
        scoreboard.updateScore(Team.SPAIN, 10, 2);

        scoreboard.startNewGame(Team.GERMANY, Team.FRANCE);
        scoreboard.updateScore(Team.GERMANY, 2, 2);

        scoreboard.startNewGame(Team.URUGUAY, Team.ITALY);
        scoreboard.updateScore(Team.URUGUAY, 6, 6);

        scoreboard.startNewGame(Team.ARGENTINA, Team.AUSTRALIA);
        scoreboard.updateScore(Team.ARGENTINA, 3, 1);

        // then
        assertEquals(
                expectedGamesInOrder,
                scoreboard.getGames()
        );
    }
}