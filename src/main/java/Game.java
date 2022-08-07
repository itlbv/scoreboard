import java.util.Arrays;
import java.util.Objects;

class Game {
    private final Team home;
    private final Team away;

    private int[] score = {0, 0};

    Game(Team home, Team away) {
        this.home = home;
        this.away = away;
    }

    public void updateScore(int[] newScore) throws IllegalArgumentException {
        if (newScore[0] < 0
                || newScore[1] < 0) {
            throw new IllegalArgumentException(
                    String.format("Score can't be less than 0! Provided score is: %s", Arrays.toString(newScore))
            );
        }
        this.score = newScore;
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
