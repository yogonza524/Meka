/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.meka;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

/**
 * FXML Controller class
 *
 * @author gonza
 */
public class MLPController implements Initializable {

    @FXML private TitledPane t1;
    @FXML private Accordion ac;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initConfig();
        initButtons();
        initListeners();
        initComboBoxes();
    }    

    private void initConfig() {
        this.ac.setExpandedPane(t1);
    }

    private void initButtons() {
        
    }

    private void initListeners() {
        
    }

    private void initComboBoxes() {
        
    }
    
}
