/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import static Config.Config.i18n;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

/**
 *
 * @author Idomar Cerutti
 */
public class XPopOver {

    private PopOver.ArrowLocation posicao;

    private FXMLLoader loader;

    public XPopOver(String arquivoFXML, String titulo, Node node) {
        posicao = PopOver.ArrowLocation.TOP_LEFT;
        try {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(arquivoFXML));
            loader.setResources(i18n);
            if (node == null) {
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle(titulo);
                stage.setResizable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                
            } else {
                PopOver popOver = new PopOver();
                popOver.setContentNode(loader.load());
                popOver.setAutoFix(true);
                popOver.setAutoHide(true);
                popOver.setHideOnEscape(true);
                popOver.setHeaderAlwaysVisible(true);
                popOver.setArrowLocation(posicao);
                popOver.setTitle(titulo);
                popOver.show(node);
            }
        } catch (IOException ex) {
            Logger.getLogger(XPopOver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public FXMLLoader getLoader() {
        return loader;
    }

}
