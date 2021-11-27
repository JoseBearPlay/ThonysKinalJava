
package org.josesalazar.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javax.swing.JOptionPane;
import org.josesalazar.bean.Empresa;
import org.josesalazar.report.GenerarReporte;
import org.josesalazar.system.Principal;


public class ReportesController implements Initializable{

    private enum operaciones {NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }
    
    public void generarReporteEmpresas(){
        switch(tipoDeOperacion){
            case NINGUNO:
                imprimirReporteEmpresas();
                break;
        }
    }
    
    public void imprimirReporteEmpresas(){
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea generar el reporte general de Empresas?","Crear reporte",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == JOptionPane.YES_OPTION)
        try{
            Map parametros = new HashMap();
            parametros.put("codigoEmpresa", null);
            GenerarReporte.mostrarReporte("ReporteEmpresas.jasper", "Reporte de Empresas", parametros);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaReportePresupuesto(){
        escenarioPrincipal.ventanaReportePresupuesto();
    }
    
     public void ventanaReporteServicios(){
         escenarioPrincipal.ventanaReporteServicios();
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
