/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.meka;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author gonza
 */
public class SplashMekaController implements Initializable {

    @FXML private AnchorPane anchor_pane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new SplashScreen().start();
    }    
    
    class SplashScreen extends Thread{

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
//                        Parent root;
//                        try {
                            Util.showMainWindowMaximized("Meka 1.0", "/fxml/Principal.fxml", PrincipalController.class, false, true);
//                        } catch (IOException ex) {
//                            Logger.getLogger(SplashController.class.getName()).log(Level.SEVERE, null, ex);
//                            Dialog.error("Excepción grave", "Sucedió un error", ex.getMessage());
//                            System.exit(0);
//                        }
                
                            anchor_pane.getScene().getWindow().hide();
                    }
                });
                
            } catch (InterruptedException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                Util.error("Excepción grave", "Sucedió un error", ex.getMessage());
                System.exit(0);
            }
        }
    }
}
