package hackathon;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * A class used to display modal dialogs to the user. A Javafx alternative to
 * JDialog.
 *
 * @author Yunfan Yang
 * @version 1.0
 *
 * <pre>
 * Project: Matrix Manipulator
 * Platform: JDK 1.8u5
 * IDE: Netbeans 8.0
 * Course: CS& 143
 * Hours: 2
 * </pre>
 *
 */
public class AlertDlg {

    /**
     * Shows a AlertDlg box with the given parameter
     *
     * @param owner - Dialog Owner
     * @param title - Title of dialog
     * @param message - Body of dialog
     * @return
     */
    public static int showDlg(Window owner, String title, String message) {
        Stage dialogStage = new Stage();
        Button confirmBtn = new Button("OK");
        Text text = new Text(message);
        text.setWrappingWidth(400);
        text.setTextAlignment(TextAlignment.CENTER);
        dialogStage.initOwner(owner);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setTitle(title);
        //dialogStage.getIcons().add(new Image("/resources/icon.png"));
        dialogStage.setScene(new Scene(VBoxBuilder.create().
                children(text, confirmBtn).
                alignment(Pos.CENTER).padding(new Insets(15)).build()));
        dialogStage.setResizable(false);
        confirmBtn.setOnAction((ActionEvent event) -> {
            dialogStage.close();
        });
        dialogStage.showAndWait();
        return 0;
    }
}
