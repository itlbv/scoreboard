import exceptions.GameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Scoreboard {
    private final List<Game> games = new ArrayList<>();

    public void startNewGame(Team home, Team away) {
        if (getGameByTeam(home).isPresent()
                || getGameByTeam(away).isPresent()) {
            throw new IllegalArgumentException(
                    String.format("There is an already active game with one or both of supplied teams. The teams are: %s, %s", home, away)
            );
        }

        games.add(new Game(home, away));
    }

    public List<Game> getGames() {
        return List.copyOf(games);
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

    private Optional<Game> getGameByTeam(Team team) {
        return games.stream()
                .filter(g ->
                        g.getHome() == team
                                || g.getAway() == team)
                .findAny();
    }

    public void finishGame(Team home, Team away) {
        var success =
                games.removeIf(game ->
                        game.getHome() == home
                                && game.getAway() == away
                );
        if (!success) {
            throw new GameNotFoundException(
                    String.format("Game not found for teams %s and %s", home, away)
            );
        }
    }

    public void updateScore(Team team, int homeScore, int awayScore) {
        var game = getGameByTeam(team)
                .orElseThrow(() -> new GameNotFoundException(
                        String.format("No games of %s team found", team)
                ));

        game.updateScore(new int[]{homeScore, awayScore});

        sortGamesByTotalScoreAndLastUpdated(game);
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
