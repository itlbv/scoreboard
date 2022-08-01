import exceptions.GameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    private List<Game> games = new ArrayList<>();

    public void startNewGame(Team home, Team away) {
        games.add(new Game(home, away));
    }

    public Game getGame(Team home, Team away) {
        var game = games.stream()
                .filter(g -> g.getHome() == home)
                .findFirst()
                .orElseThrow(GameNotFoundException::new);

        if (game.getAway() != away) {
            throw new GameNotFoundException();
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
            throw new GameNotFoundException();
        }
    }

    public List<Game> getGames() {
        return games;
    }

    public void updateScore(Team team) {
        var game = getGames().stream()
                .filter(g ->
                        g.getHome() == team
                                || g.getAway() == team)
                .findAny()
                .orElseThrow(GameNotFoundException::new);

        if (team == game.getHome()) {
            game.scoreHome();
        } else {
            game.scoreAway();
        }
    }
}
