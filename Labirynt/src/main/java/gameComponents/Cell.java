package gameComponents;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import viewManager.GameViewManager;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Cell {
    private int row;
    private int col;
    private double x;
    private double y;
    private int size;
    private boolean[] walls;
    private boolean[] endWall;
    private boolean visited;
    private List<Cell> neighbours;
    private Random rand;
    private ImageView topWall;
    private ImageView rightWall;
    private ImageView bottomWall;
    private ImageView leftWall;
    private final int movex = GameViewManager.MARGINX;
    private final int movey = GameViewManager.MARGINY;
    private final String START_DOOR = "maze/startDoor.png";
    private final String START_LEFT_DOOR = "maze/startLeftDoor.png";
    private final String START_RIGHT_DOOR = "maze/startRightDoor.png";
    private final String END_LEFT_DOOR = "maze/endLeftDoor.png";
    private final String END_RIGHT_DOOR = "maze/endRightDoor.png";
    private final String END_DOOR = "maze/endDoor.png";

    public Cell(int row, int col, int size) {
        this.row = row;
        this.col = col;
        this.size = size;
        x = col * size;
        y = row * size;
        visited = false;
        neighbours = new LinkedList<>();
        rand = new Random();

        walls = new boolean[4];
        endWall = new boolean[4];
        for(int i = 0; i < 4; i++) {
            walls[i] = true;
            endWall[i] = false;
        }
    }

    public Cell getNeighbour(Cell[][] grid) {
        neighbours.clear();

        int up = row - 1;
        int right = col + 1;
        int down = row + 1;
        int left = col - 1;
        int maxRow = grid.length;
        int maxCol = grid[0].length;

        if(up >= 0 && !grid[up][col].isVisited()) {
            neighbours.add(grid[up][col]);
        }
        if(right < maxCol && !grid[row][right].isVisited()) {
            neighbours.add(grid[row][right]);
        }
        if(down < maxRow && !grid[down][col].isVisited()) {
            neighbours.add(grid[down][col]);
        }
        if(left >= 0 && !grid[row][left].isVisited()) {
            neighbours.add(grid[row][left]);
        }

        if(neighbours.size() > 0) {
            int index = rand.nextInt(neighbours.size());
            return neighbours.get(index);
        }
        return null;
    }

    public void displayTopWall(AnchorPane pane, Image frontWall, Image sideWall) {
        if(walls[0]) {
            try {
                topWall = new ImageView(frontWall);
                topWall.setX(movex + x - sideWall.getWidth() / 2);
                topWall.setY(movey + y - frontWall.getHeight());
                pane.getChildren().add(topWall);
            } catch (Exception e) {
            }
        }
    }

    public void displayBottomWall(AnchorPane pane, Image frontWall, Image sideWall) {
        if(walls[2]) {
            try {
                bottomWall = new ImageView(frontWall);
                bottomWall.setX(movex + x - sideWall.getWidth()/2);
                bottomWall.setY(movey + y + size - frontWall.getHeight());
                pane.getChildren().add(bottomWall);
            } catch (Exception e) {
            }
        }
    }

    public void displayRightWall(AnchorPane pane, Image sideWall, Image frontWall) {
        if(walls[1]) {
            try {
            rightWall = new ImageView(sideWall);
            rightWall.setX(movex + x + size - sideWall.getWidth()/2);
            rightWall.setY(movey + y - frontWall.getHeight());
            pane.getChildren().add(rightWall);
            } catch (Exception e) {
            }
        }
    }

    public void displayLeftWall(AnchorPane pane, Image sideWall, Image frontWall) {
        if(walls[3]) {
            try {
                leftWall = new ImageView(sideWall);
                leftWall.setX(movex + x - sideWall.getWidth() / 2);
                leftWall.setY(movey + y - frontWall.getHeight());
                pane.getChildren().add(leftWall);
            } catch (Exception e) {
            }
        }
    }

    public void addStartDoor(AnchorPane pane, int maxRow, Image sideWall, Cell[][] grid) {
        Image startDoorImage;
        ImageView startDoor = new ImageView();
        int index = 0;

        try {
            if (row == 0) {
                startDoorImage = new Image(START_DOOR, size - sideWall.getWidth(), size * 5 / 7, false, false);
                startDoor = new ImageView(startDoorImage);
                startDoor.setX(movex + x + sideWall.getWidth() / 2);
                startDoor.setY(movey + y - size * 4 / 7);
                index = pane.getChildren().indexOf(topWall) + 1;
            } else if (row == maxRow) {
                startDoorImage = new Image(START_DOOR, size - sideWall.getWidth(), size * 5 / 7, false, false);
                startDoor = new ImageView(startDoorImage);
                startDoor.setX(movex + x + sideWall.getWidth() / 2);
                startDoor.setY(movey + y + size - size * 4 / 7);
                index = pane.getChildren().indexOf(bottomWall) + 1;
            } else if (col == 0) {
                startDoorImage = new Image(START_LEFT_DOOR, 100 * sideWall.getWidth() / 38, size, false, false);
                startDoor = new ImageView(startDoorImage);
                startDoor.setX(movex + x - startDoorImage.getWidth() * 3 / 10 - sideWall.getWidth() / 2);
                startDoor.setY(movey + y);
                index = pane.getChildren().indexOf(grid[row + 1][col].leftWall) + 1;
            } else {
                startDoorImage = new Image(START_RIGHT_DOOR, 100 * sideWall.getWidth() / 38, size, false, false);
                startDoor = new ImageView(startDoorImage);
                startDoor.setX(movex + x + size - startDoorImage.getWidth() * 3 / 10 - sideWall.getWidth() / 2);
                startDoor.setY(movey + y);
                index = pane.getChildren().indexOf(grid[row + 1][col].rightWall) + 1;
            }
        } catch (Exception e) {
        }

        pane.getChildren().add(index, startDoor);
    }

    public void addEndDoor(AnchorPane pane, int maxRow, Image sideWall, Cell[][] grid) {
        Image endDoorImage;
        ImageView endDoor = new ImageView();
        int index = 0;

        try {
            if (row == 0) {
                endWall[0] = true;
                endDoorImage = new Image(END_DOOR, size - sideWall.getWidth(), size * 4 / 7, false, false);
                endDoor = new ImageView(endDoorImage);
                endDoor.setX(movex + x + sideWall.getWidth() / 2);
                endDoor.setY(movey + y - size * 35 / 70);
                index = pane.getChildren().indexOf(topWall) + 1;
            } else if (row == maxRow) {
                endWall[2] = true;
                endDoorImage = new Image(END_DOOR, size - sideWall.getWidth(), size * 4 / 7, false, false);
                endDoor = new ImageView(endDoorImage);
                endDoor.setX(movex + x + sideWall.getWidth() / 2);
                endDoor.setY(movey + y + size - size * 35 / 70);
                index = pane.getChildren().indexOf(bottomWall) + 1;
            } else if (col == 0) {
                endWall[3] = true;
                endDoorImage = new Image(END_LEFT_DOOR, 98 * sideWall.getWidth() / 63, size, false, false);
                endDoor = new ImageView(endDoorImage);
                endDoor.setX(movex + x - endDoorImage.getWidth() * 35 / 98 - sideWall.getWidth() / 2);
                endDoor.setY(movey + y);
                index = pane.getChildren().indexOf(grid[row + 1][col].leftWall) + 1;
            } else {
                endWall[1] = true;
                endDoorImage = new Image(END_RIGHT_DOOR, 98 * sideWall.getWidth() / 63, size, false, false);
                endDoor = new ImageView(endDoorImage);
                endDoor.setX(movex + x + size - sideWall.getWidth() / 2);
                endDoor.setY(movey + y);
                index = pane.getChildren().indexOf(grid[row + 1][col].rightWall) + 1;
            }
        } catch (Exception e) {
        }
        pane.getChildren().add(index, endDoor);
    }

    public double getX() {
        return GameViewManager.MARGINX + x;
    }

    public double getY() {
        return GameViewManager.MARGINY + y;
    }

    public int getSize() {
        return size;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited() {
        visited = true;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void removeWall(int index) {
        walls[index] = false;
    }

    public boolean isTopWall() {
        return walls[0];
    }

    public boolean isRightWall() {
        return walls[1];
    }

    public boolean isBottomWall() {
        return walls[2];
    }

    public boolean isLeftWall() {
        return walls[3];
    }

    public boolean isTopWallEnd() { return endWall[0]; }

    public boolean isRightWallEnd() {
        return endWall[1];
    }

    public boolean isBottomWallEnd() {
        return endWall[2];
    }

    public boolean isLeftWallEnd() {
        return endWall[3];
    }

    public ImageView getRightWall(){
        return rightWall;
    }
}
