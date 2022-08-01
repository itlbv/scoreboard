class Game {
    private Team home;
    private Team away;

    private int[] score = {0, 0};

    public Game(Team home, Team away) {
        this.home = home;
        this.away = away;
    }

    public int getHomeScore() {
        return score[0];
    }

    public int getAwayScore() {
        return score[1];
    }
}
