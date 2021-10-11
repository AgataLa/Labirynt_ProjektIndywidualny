package GUIComponents;

public enum DIFFICULTY {
    EASY("EASY", 16, 800/16, 3),
    MEDIUM("MEDIUM", 25, 800/25, 2),
    HARD("HARD", 32, 800/32, 1.75);

    private String text;
    private int mazeSize;
    private int cellSize;
    private double speed;

    DIFFICULTY(String text, int mazeSize, int cellSize, double speed) {
        this.text = text;
        this.mazeSize = mazeSize;
        this.cellSize = cellSize;
        this.speed = speed;
    }

    public int getMazeSize() {
        return mazeSize;
    }

    public int getCellSize() {
        return cellSize;
    }

    public double getSpeed() { return speed; }

    public String getText() {
        return text;
    }
}
