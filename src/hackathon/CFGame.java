package hackathon;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author yunfanyang
 */
public class CFGame extends Application {

    Pane root = new Pane();
    Pane ballContainer = new Pane();

    final int grid_height = 100;
    final int grid_width = 100;

    private final int xLength = 7;
    private final int yLength = 6;
    
    private boolean diskDropped = true;

    private final int player1 = 1;
    private final int player2 = 2;
    private int myTurn;
    private int[][] myBoard;
    private Point lastPos;
    private ArrayList<Point> aroundLastPos = new ArrayList<Point>();

    Label lbl_player_status = new Label();
    
    public void addOneBall(int col, int row, boolean needShowAnimation, int type) {
        Ball ball = new Ball(col, row, type);
        ballContainer.getChildren().add(ball);
        ball.relocate(grid_width * col, grid_height * row);
        if (needShowAnimation) {
            //ani
        }
    }

    public void removeOneBall(int col, int row, boolean needShowAnimation) {
        for (int i = 0; i < this.ballContainer.getChildren().size(); i++) {
            Ball ball = (Ball) this.ballContainer.getChildren().get(i);
            if (ball.col == col && ball.row == row) {
                this.ballContainer.getChildren().remove(i);
                return;
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {

        myBoard = new int[xLength][xLength];
        myTurn = player1;
        lastPos = null;

        Scene scene = new Scene(root, 950, 600);
        //add background
        this.root.getChildren().add(this.ballContainer);

        //add restart button
        Button btn_restart = new Button();
        btn_restart.setText("New Game");
        btn_restart.setFont(new Font(20));
        btn_restart.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                startANewTurn();
            }
        });
        
        Label info = new Label();
        info.setWrapText(true);
        info.setPrefWidth(200);
        info.setText("Author: \nGunjan Baid,\n Aaron Fung,\n Joong Hwa(J) Lee,\n Yunfan Yang\n\n"
                + "Instruction: Take turns dropping disks into the slots until a player gets four of his disks in a row horizontally, vertically, or diagonally."
                + "");
        info.setFont(new Font(20));
        
        //add player status label
        lbl_player_status.setText("Current: Player 1");
        lbl_player_status.setFont(new Font(25));
        
        root.getChildren().add(btn_restart);
        btn_restart.relocate(grid_width * xLength, 500);
        
        root.getChildren().add(lbl_player_status);
        lbl_player_status.relocate(grid_width * xLength, 50);
        
        root.getChildren().add(info);
        info.relocate(grid_width * xLength, 120);
        ballContainer.setPrefSize(700, 600);
        ballContainer.getStylesheets().add(this.getClass().getResource("/ballcontainer.css").toExternalForm());
        
        ballContainer.getStyleClass().add("ballcontainer");
        
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                System.out.println(event.getX());
                System.out.println(event.getY());
                //System.out.println("Drop at column:" + );
                int columnToDrop = ((int)event.getX() / grid_width);
                ballContainer.getChildren().clear();
                //if (isFull()){
                //    lbl_player_status.setText("Tie game.");
                //}
                //else 
                if(isLegal(columnToDrop)){
                    drop(columnToDrop);
                    if(checkWin())
                    {
                        lbl_player_status.setText("Player " + myBoard[lastPos.x][lastPos.y] + " wins!");
                        AlertDlg.showDlg(scene.getWindow(), "Congrats!" ,"Player " + myBoard[lastPos.x][lastPos.y] + " wins!" );
                    }else if (isFull()){
                    lbl_player_status.setText("Tie game.");
                    AlertDlg.showDlg(scene.getWindow(), "The board is full" ,"Tie game.");
                    } else
                        switchTurns();
                }
                
                printBoard();
            }

        });

        primaryStage.setTitle("Connect Four Game");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void drop(int x) {
        if (!gameOver()) {
            if (!isLegal(x) ) {
                System.out.print("this line is full ");
                return;
            }
            for (int i = 0; i < yLength; i++) {
                if (myBoard[x][i] == 0) {
                    myBoard[x][i] = myTurn;
                    lastPos = new Point(x, i);
                    addAround(lastPos);
                    break;
                }
            }
        }
    }

    public void addAround(Point last) {
        int xPos = last.x;
        int yPos = last.y;
        aroundLastPos.add(new Point(xPos + 1, yPos));
        aroundLastPos.add(new Point(xPos - 1, yPos));
        aroundLastPos.add(new Point(xPos + 1, yPos + 1));
        aroundLastPos.add(new Point(xPos - 1, yPos + 1));
        aroundLastPos.add(new Point(xPos + 1, yPos - 1));
        aroundLastPos.add(new Point(xPos - 1, yPos - 1));
        aroundLastPos.add(new Point(xPos, yPos + 1));
        aroundLastPos.add(new Point(xPos, yPos - 1));
        for (int i = 0; i < aroundLastPos.size(); i++) {
            Point temp = aroundLastPos.get(i);
            if (temp.x < 0 || temp.x >= xLength || temp.y < 0 || temp.y >= yLength) {
                aroundLastPos.remove(temp);
                i--;
            }
        }

    }

    public boolean checkWin() {
        if (lastPos == null) {
            return false;
        } else {
            for (Point temp : aroundLastPos) {
                if (myBoard[temp.x][temp.y] == myBoard[lastPos.x][lastPos.y]) {
                    int xChange = temp.x - lastPos.x;
                    int yChange = temp.y - lastPos.y;
                    try {
                        if (myBoard[temp.x + xChange][temp.y + yChange] == myBoard[lastPos.x][lastPos.y]
                                && myBoard[temp.x + xChange + xChange][temp.y + yChange + yChange]
                                == myBoard[lastPos.x][lastPos.y]) {
                            
                                //this.lbl_player_status.setText("Player " + myBoard[lastPos.x][lastPos.y] + " wins!");
                            return true;
                        }

                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                }
            }
        }
        aroundLastPos.clear();
        return false;

    }

    public boolean gameOver() {
        if (isFull()) {
            return true;
        }
        Boolean hasSomeoneWon = checkWin();
        
        return hasSomeoneWon;
    }

    public void switchTurns() {
        if (myTurn == player1) {
            myTurn = player2;
            lbl_player_status.setText("Current: Player 2");
        } else {
            myTurn = player1;
            lbl_player_status.setText("Current: Player 1");
        }
    }

    public boolean isLegal(int xPos) {
        if (gameOver()) {
            return false;
        }
        else if (myBoard[xPos][yLength - 1] != 0) {
            return false;
        }
        return true;

    }

    private boolean isFull() {
        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {
                if (myBoard[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void startANewTurn(){
        myBoard = new int[xLength][xLength];
        myTurn = player1;
        lastPos = null;
        this.ballContainer.getChildren().clear();
        printBoard();
        this.lbl_player_status.setText("");
    }
    
    public void printBoard() {
        for (int j = yLength - 1; j >= 0; j--) {
            for (int i = 0; i < xLength; i++) {
                if (i == xLength - 1) {
                    System.out.println(myBoard[i][j]);

                } else {
                    System.out.print(myBoard[i][j] + " ");
                }
            }
        }
        
        for(int i = 0; i < myBoard.length; i++){
            for(int j = 0; j < myBoard[0].length; j++)
            {
                if(myBoard[i][j] != 0)
                    this.addOneBall(i, yLength - j -1, true, myBoard[i][j]);
            }
        }
    }

}
