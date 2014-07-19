package hackathon;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author yunfanyang
 */
public class Ball extends Parent{
    public int col;
    public int row;
    
    public Ball(int col, int row, int type)
    {
        this.col = col;
        this.row = row;
        
        Circle ball = new Circle(0, 0, 50);
        if(type == 1)
            ball.setFill(Color.RED);
        this.getChildren().add(ball);
    }
}
