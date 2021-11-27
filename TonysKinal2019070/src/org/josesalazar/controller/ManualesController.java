
package org.josesalazar.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javax.swing.JOptionPane;
import org.josesalazar.report.GenerarReporte;
import org.josesalazar.system.Principal;



public class ManualesController implements Initializable{

    private enum operaciones {NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    
    @FXML private Button btnManualUsuario;
    @FXML private Button btnManualProcedimiento;
    @FXML private Button btnRegresar;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }
    
    
    public void generarManual(){
        switch(tipoDeOperacion){
            case NINGUNO:
                imprimirManual();
            break;
        }
    }
    
    public void imprimirManual(){
        String url = "C:\\Users\\LAPTOP\\Desktop\\Manual de App Thony´s Kinal No1 Finalizado.docx";
       /* ProcessBuilder p = new ProcessBuilder;*/
    }
    

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
}
