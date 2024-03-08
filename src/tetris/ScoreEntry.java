package tetris;

public class ScoreEntry {
    private int points;
    private String name;

    public ScoreEntry(int points, String name) {
	this.points = points;
	this.name = name;
    }

    public int getPoints() {
	return points;
    }

    public String getName() {
	return name;
    }
}