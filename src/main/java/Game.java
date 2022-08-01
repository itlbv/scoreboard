import java.util.Arrays;
import java.util.Objects;

class Game {
    private final Team home;
    private final Team away;

    private final int[] score = {0, 0};

    public Game(Team home, Team away) {
        this.home = home;
        this.away = away;
    }

    public void scoreHome() {
        score[0]++;
    }

    public void scoreAway() {
        score[1]++;
    }

    public Team getHome() {
        return this.home;
    }

    public Team getAway() {
        return this.away;
    }

    public int getHomeScore() {
        return score[0];
    }

    public int getAwayScore() {
        return score[1];
    }

    public int getTotalScore() {
        return score[0] + score[1];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return home == game.home && away == game.away;
    }

    @Override
    public int hashCode() {
        return Objects.hash(home, away);
    }

    @Override
    public String toString() {
        return "Game{" +
                "home=" + home +
                ", away=" + away +
                ", score=" + Arrays.toString(score) +
                '}';
    }
}
