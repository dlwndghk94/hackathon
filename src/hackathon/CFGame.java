package hackathon;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
    
    public void addOneBall(int col, int row, boolean needShowAnimation)
    {
        Ball ball = new Ball(col, row);
        ballContainer.getChildren().add(ball);
        ball.relocate(grid_width * col, grid_height * row);
        if(needShowAnimation)
        {
            //ani
        }
    }
    
    public void removeOneBall(int col, int row, boolean needShowAnimation)
    {
        for(int i = 0; i < this.ballContainer.getChildren().size(); i++)
        {
            Ball ball = (Ball)this.ballContainer.getChildren().get(i);
            if(ball.col == col && ball.row == row)
            {
                this.ballContainer.getChildren().remove(i);
                return;
            }
        }
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        Scene scene = new Scene(root, 800, 800);
        //add background
        this.root.getChildren().add(this.ballContainer);
        
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
        {

            @Override
            public void handle(MouseEvent event) {
                
                System.out.println(event.getX());
                System.out.println(event.getY());
            }
            
        });
        
        primaryStage.setTitle("Connect Four Game");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        addOneBall(0,0,false);
        addOneBall(1,0,false);
        addOneBall(0,1,false);
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
