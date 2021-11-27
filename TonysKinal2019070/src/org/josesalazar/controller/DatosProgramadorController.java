package org.josesalazar.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.josesalazar.system.Principal;


public class DatosProgramadorController implements Initializable {
     private enum operaciones {Programador,Asistente,Ninguno}
     private Principal escenarioPrincipal;
     private operaciones tipoODeOperacion = operaciones.Ninguno;
     @FXML private Label lblNombre; 
     @FXML private Label lblApellidos;
     @FXML private Label lblCarrera;
     @FXML private Label lblColegio;
     @FXML private Label lblContacto;
     @FXML private Label lblDatosProgramador;
     @FXML private Button btnProgramador;
     @FXML private Button btnAsistente;
     @FXML private Button btnLimpiar;
     
     @FXML
     private void botones(ActionEvent event){
         if(event.getSource() == btnProgramador){
             lblDatosProgramador.setText("Informacion Programador");
             lblNombre.setText("José Julián");
             lblApellidos.setText("Salazar Melgar");
             lblCarrera.setText("Perito en Informatica");
             lblColegio.setText("Centro Educativo Tecnico Laboral Kinal");
         }else if(event.getSource()== btnAsistente){
             lblDatosProgramador.setText("Centro de asistencia");
             lblCarrera.setText("Asistencia del programa");
             lblColegio.setText("Dudas de la aplicacion");
             lblContacto.setText("www.Thony´s Kinal.org");
         }
     }
     
     @FXML
     private void limpiarBotones(ActionEvent event){
       if(event.getSource() == btnLimpiar){
         lblDatosProgramador.setText("");
         lblNombre.setText("");
         lblApellidos.setText("");
         lblCarrera.setText("");
         lblColegio.setText("");
         lblCarrera.setText("");
         lblContacto.setText("");
       }
     }
     
     
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal (){
        escenarioPrincipal.menuPrincipal();
    }
}
