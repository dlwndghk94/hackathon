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
        
        this.getStylesheets().add(this.getClass().getResource("/ball.css").toExternalForm());
        
        Circle ball = new Circle(0, 0, 45);
        ball.getStyleClass().add("ball");
        ball.getStyleClass().add("ball" + type);
        if(type == 1)
            ball.setFill(Color.RED);
        this.getChildren().add(ball);
    }
}
