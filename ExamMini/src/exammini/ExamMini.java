/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exammini;

import com.aptech.navigator.Navigator;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Administrator
 */
public class ExamMini extends Application {
     @Override
    public void start(Stage primaryStage) throws IOException {
       Navigator.getInstance().setStage(primaryStage);
       Navigator.getInstance().goToIndex();
        primaryStage.show();
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
