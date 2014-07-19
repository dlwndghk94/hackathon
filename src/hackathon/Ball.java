package hackathon;

import javafx.scene.Parent;
import javafx.scene.shape.Circle;

/**
 *
 * @author yunfanyang
 */
public class Ball extends Parent{
    public int col;
    public int row;
    
    public Ball(int col, int row)
    {
        this.col = col;
        this.row = row;
        Circle ball = new Circle(0, 0, 50);
        this.getChildren().add(ball);
    }
}
