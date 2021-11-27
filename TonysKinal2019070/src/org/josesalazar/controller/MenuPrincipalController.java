package org.josesalazar.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javax.swing.JOptionPane;
import org.josesalazar.system.Principal;


public class MenuPrincipalController implements Initializable {

    private Principal escenarioPrincipal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
   
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
   
    public void ventanaProgramador(){
        escenarioPrincipal.ventanaProgramador();
    }
    
    public void ventanaEmpresas(){
        escenarioPrincipal.ventanaEmpresas();
    }
    
    public void ventanaTipoEmpleado(){
        escenarioPrincipal.ventanaTipoEmpleado();
    }
    
    public void ventanTipoPlato(){
        escenarioPrincipal.ventanTipoPlato();
    }
    
    public void ventanaProductos(){
        escenarioPrincipal.ventanaProductos();
    } 
    
    public void ventanaServicios(){
        escenarioPrincipal.ventanaServicios();
    }
    
    public void ventanaServicios_has_Empleados(){
        escenarioPrincipal.ventanaServicios_has_Empleados();
    }
    
    public void ventanaServicios_has_Platos(){
        escenarioPrincipal.ventanaServicios_has_Platos();
    }
    
    public void ventanaProductos_has_Platos(){
        escenarioPrincipal.ventanaProductos_has_Platos();
    }
    
    public void ventanaReportes(){
        escenarioPrincipal.ventanaReportes();
    }
    
    public void ventanaManual(){
        escenarioPrincipal.ventanaManual();
    }
    
    public void opcionDelete(){
        escenarioPrincipal.opcionDelete();
    }
    
    public void Close (){
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea salir definitivamente de la aplicación?","Salir de la app",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == JOptionPane.YES_OPTION)
        try{
            System.exit(0);
        }catch(Exception e){
            e.printStackTrace();
        }else{
            
        }
        }
}
