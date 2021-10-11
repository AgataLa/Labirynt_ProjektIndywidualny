package gameComponents;

import GUIComponents.DIFFICULTY;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import viewManager.GameViewManager;

import java.util.ArrayList;
import java.util.List;

public class Dog {
    private List<Image> goDown;
    private List<Image> goRight;
    private List<Image> goUp;
    private List<Image> goLeft;
    private ImageView currentImage;
    private int imageWidth;
    private int imageHeight;
    private SimpleBooleanProperty upPressed;
    private SimpleBooleanProperty rightPressed;
    private SimpleBooleanProperty downPressed;
    private SimpleBooleanProperty leftPressed;
    private double speed;
    private int frame = 0;
    private Cell currentCell;
    private Cell[][] grid;
    private AnchorPane gamePane;
    private double sideWallWidth;
    private double size;

    public Dog(AnchorPane gamePane, DIFFICULTY difficulty, Cell startCell, Cell[][] grid) {
        this.gamePane = gamePane;
        speed = difficulty.getSpeed();
        this.currentCell = startCell;
        size = (double)currentCell.getSize()/(double)100;
        this.grid = grid;
        upPressed = new SimpleBooleanProperty(false);
        rightPressed = new SimpleBooleanProperty(false);
        downPressed = new SimpleBooleanProperty(false);
        leftPressed = new SimpleBooleanProperty(false);
        goDown = new ArrayList<>();
        goRight = new ArrayList<>();
        goUp = new ArrayList<>();
        goLeft = new ArrayList<>();
        loadImages();
        currentImage = new ImageView(goDown.get(0));
        currentImage.setFocusTraversable(true);
        int sizeCell = difficulty.getCellSize();
        currentImage.setLayoutY(GameViewManager.MARGINY + startCell.getRow()*sizeCell + (sizeCell-imageHeight)/2);
        currentImage.setLayoutX(GameViewManager.MARGINX + startCell.getCol()*sizeCell + (sizeCell-imageWidth)/2);
        addListeners();
        int index = gamePane.getChildren().indexOf(grid[currentCell.getRow()][grid[0].length - 1].getRightWall());
        gamePane.getChildren().add(index, currentImage);
        this.sideWallWidth = grid[grid.length-1][grid[0].length-1].getRightWall().getImage().getWidth();
    }

    private void loadImages() {
        Image image;
        try {
            for (int i = 1; i < 5; i++) {
                image = new Image("character/down" + i + ".png");
                goDown.add(new Image("character/down" + i + ".png", image.getWidth()*size, image.getHeight()*size, false, false));
                if(i == 1) {
                    imageWidth = (int)(image.getWidth()*size);
                    imageHeight = (int)(image.getHeight()*size);
                }
                image = new Image("character/right" + i + ".png");
                goRight.add(new Image("character/right" + i + ".png",image.getWidth()*size, image.getHeight()*size, false, false));

                image = new Image("character/up" + i + ".png");
                goUp.add(new Image("character/up" + i + ".png",image.getWidth()*size, image.getHeight()*size, false, false));

                image = new Image("character/left" + i + ".png");
                goLeft.add(new Image("character/left" + i + ".png",image.getWidth()*size, image.getHeight()*size, false, false));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addListeners() {
        currentImage.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case UP: upPressed.set(true); break;
                    case RIGHT: rightPressed.set(true); break;
                    case DOWN: downPressed.set(true); break;
                    case LEFT: leftPressed.set(true); break;
                }
            }
        });

        currentImage.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case UP: upPressed.set(false); break;
                    case RIGHT: rightPressed.set(false); break;
                    case DOWN: downPressed.set(false); break;
                    case LEFT: leftPressed.set(false); break;
                }
            }
        });
    }

    public boolean isUpPressed() {
        return upPressed.get();
    }

    public boolean isRightPressed() {
        return rightPressed.get();
    }

    public boolean isDownPressed() {
        return downPressed.get();
    }

    public boolean isLeftPressed() {
        return leftPressed.get();
    }

    public void moveUp() {
        frame++;
        currentImage.setImage(goUp.get((frame % 24) /6));
        currentImage.setLayoutY(currentImage.getLayoutY() - calculateUpMove(speed));
        calculateCurrentCell();
    }

    public void moveRight() {
        frame++;
        currentImage.setImage(goRight.get((frame % 24) /6));
        currentImage.setLayoutX(currentImage.getLayoutX() + calculateRightMove(speed));
        calculateCurrentCell();
    }

    public void moveDown() {
        frame++;
        currentImage.setImage(goDown.get((frame % 24) /6));
        currentImage.setLayoutY(currentImage.getLayoutY() + calculateDownMove(speed));
        calculateCurrentCell();
    }

    public void moveLeft() {
        frame++;
        currentImage.setImage(goLeft.get((frame % 24) /6));
        currentImage.setLayoutX(currentImage.getLayoutX() - calculateLeftMove(speed));
        calculateCurrentCell();
    }

    private double calculateUpMove(double speed) {
        double dogY = currentImage.getLayoutY();
        double dogX = currentImage.getLayoutX();
        double dogHeight = goUp.get(0).getHeight();
        double dogWidth = goUp.get(0).getWidth();
        double cellY = currentCell.getY();
        double cellX = currentCell.getX();
        double cellSize = currentCell.getSize();

        if((dogY + dogHeight/2 - speed) > cellY) {
            return speed;
        } else {
            if(currentCell.isTopWall()) {
                if(currentCell.isTopWallEnd()) {
                    GameViewManager.setEndReached();
                }
                return dogY + dogHeight/2 - cellY;
            } else {
                if(dogX >= (cellX + sideWallWidth/2) && (dogX + dogWidth) <= (cellX + cellSize - sideWallWidth/2)) {
                    return speed;
                } else {
                    return dogY + dogHeight/2 - cellY;
                }
            }
        }
    }

    private double calculateDownMove(double speed) {
        double dogY = currentImage.getLayoutY();
        double dogX = currentImage.getLayoutX();
        double dogHeight = goDown.get(0).getHeight();
        double dogWidth = goDown.get(0).getWidth();
        double cellY = currentCell.getY();
        double cellX = currentCell.getX();
        double cellSize = currentCell.getSize();

        if((dogY + dogHeight + speed) < (cellY + cellSize - dogHeight/10)) {
            return speed;
        } else {
            if(currentCell.isBottomWall()) {
                if(currentCell.isBottomWallEnd()) {
                    GameViewManager.setEndReached();
                }
                return cellY + cellSize - dogHeight/10 - (dogY + dogHeight);
            } else {
                if(dogX >= (cellX + sideWallWidth/2) && (dogX + dogWidth) <= (cellX + cellSize - sideWallWidth/2)) {
                    return speed;
                } else {
                    return cellY + cellSize - dogHeight/10 - (dogY + dogHeight);
                }
            }
        }
    }

    private double calculateRightMove(double speed) {
        double dogY = currentImage.getLayoutY();
        double dogX = currentImage.getLayoutX();
        double dogUpHeight = goUp.get(0).getHeight();
        double dogDownHeight = goDown.get(0).getHeight();
        double dogWidth = goRight.get(0).getWidth();
        double cellY = currentCell.getY();
        double cellX = currentCell.getX();
        double cellSize = currentCell.getSize();

        if((dogX + dogWidth + speed) < (cellX + cellSize - sideWallWidth/2)) {
            return speed;
        } else {
            if(currentCell.isRightWall()) {
                if(currentCell.isRightWallEnd()) {
                    GameViewManager.setEndReached();
                }
                return cellX + cellSize - sideWallWidth/2 - (dogX + dogWidth);
            } else {
                if((dogY + dogUpHeight/2 >= cellY) && (dogY + dogDownHeight) <= (cellY + cellSize - dogDownHeight/10)) {
                    return speed;
                } else {
                    return cellX + cellSize - sideWallWidth/2 - (dogX + dogWidth);
                }
            }
        }
    }

    private double calculateLeftMove(double speed) {
        double dogY = currentImage.getLayoutY();
        double dogX = currentImage.getLayoutX();
        double dogUpHeight = goUp.get(0).getHeight();
        double dogDownHeight = goDown.get(0).getHeight();
        double cellY = currentCell.getY();
        double cellX = currentCell.getX();
        double cellSize = currentCell.getSize();

        if((dogX - speed) > (cellX + sideWallWidth/2)) {
            return speed;
        } else {
            if(currentCell.isLeftWall()) {
                if(currentCell.isLeftWallEnd()) {
                    GameViewManager.setEndReached();
                }
                return dogX - cellX - sideWallWidth/2;
            } else {
                if((dogY + dogUpHeight/2 >= cellY) && (dogY + dogDownHeight) <= (cellY + cellSize - dogDownHeight/10)) {
                    return speed;
                } else {
                    return dogX - cellX - sideWallWidth/2;
                }
            }
        }
    }

    private void calculateCurrentCell() {
        double dogCenterX = currentImage.getLayoutX() + currentImage.getImage().getWidth()/2;
        double dogCenterY = currentImage.getLayoutY() + currentImage.getImage().getHeight()*3/4;

        if(currentCell.getX() < dogCenterX) {
            if(currentCell.getX() + currentCell.getSize() > dogCenterX) {
                if(currentCell.getY() < dogCenterY) {
                    if(!(currentCell.getY() + currentCell.getSize() > dogCenterY)) {
                        currentCell = grid[currentCell.getRow() + 1][currentCell.getCol()];
                        moveImage();
                    }
                } else {
                    currentCell = grid[currentCell.getRow()-1][currentCell.getCol()];
                    moveImage();
                }
            } else {
                currentCell = grid[currentCell.getRow()][currentCell.getCol()+1];
                moveImage();
            }
        } else {
            currentCell = grid[currentCell.getRow()][currentCell.getCol()-1];
            moveImage();
        }
    }

    private void moveImage() {
        int index = gamePane.getChildren().indexOf(grid[currentCell.getRow()][grid[0].length - 1].getRightWall());
        gamePane.getChildren().remove(currentImage);
        gamePane.getChildren().add(index, currentImage);
    }
}
