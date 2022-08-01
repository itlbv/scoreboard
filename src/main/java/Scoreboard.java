import exceptions.GameNotFound;

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
                .orElseThrow(GameNotFound::new);

        if (game.getAway() != away) {
            throw new GameNotFound();
        }

        return game;
    }
}
