import exceptions.GameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    private final List<Game> games = new ArrayList<>();

    public void startNewGame(Team home, Team away) {
        games.add(new Game(home, away));
    }

    public Game getGame(Team home, Team away) {
        var game = games.stream()
                .filter(g -> g.getHome() == home)
                .findAny()
                .orElseThrow(() -> new GameNotFoundException(
                        String.format("Game not found for team %s", home)
                ));

        if (game.getAway() != away) {
            throw new GameNotFoundException(
                    String.format("Found game %s, which does not match with provided away team %s", game, away)
            );
        }

        return game;
    }

    public void finishGame(Team home, Team away) {
        var success =
                getGames().removeIf(game ->
                        game.getHome() == home
                                && game.getAway() == away
                );
        if (!success) {
            throw new GameNotFoundException(
                    String.format("Game not found for teams %s and %s", home, away)
            );
        }
    }

    public List<Game> getGames() {
        return games;
    }

    public int[] updateScore(Team team) {
        var game = getGames().stream()
                .filter(g ->
                        g.getHome() == team
                                || g.getAway() == team)
                .findAny()
                .orElseThrow(() -> new GameNotFoundException(
                        String.format("No games of %s team found", team)
                ));

        if (team == game.getHome()) {
            game.scoreHome();
        } else {
            game.scoreAway();
        }

        sortGamesByTotalScoreAndLastUpdated(game);

        return new int[] {game.getHomeScore(), game.getAwayScore()};
    }

    private void sortGamesByTotalScoreAndLastUpdated(Game lastUpdatedGame) {
        var i = -1;
        while (true) {
            i++;
            var currentGame = games.get(i);

            if (currentGame == lastUpdatedGame) {
                break;
            }

            if (currentGame.getTotalScore() <= lastUpdatedGame.getTotalScore()) {
                // swap games
                games.remove(lastUpdatedGame);
                games.add(i, lastUpdatedGame);
                i = -1;
            }
        }
    }
}
