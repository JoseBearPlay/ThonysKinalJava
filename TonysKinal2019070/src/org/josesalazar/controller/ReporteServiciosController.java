
package org.josesalazar.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javax.swing.JOptionPane;
import org.josesalazar.bean.Servicios;
import org.josesalazar.db.Conexion;
import org.josesalazar.report.GenerarReporte;
import org.josesalazar.system.Principal;


public class ReporteServiciosController implements Initializable{

    private enum operaciones {NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO; 
    private Principal escenarioPrincipal;
    private ObservableList<Servicios>listaServicios;
    @FXML private ComboBox cmbServicios;  
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbServicios.setItems(getServicios());
    }
    
    public ObservableList<Servicios> getServicios(){
        ArrayList<Servicios> lista = new ArrayList<Servicios>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarServicios}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Servicios(resultado.getInt("codigoServicio"),
                                        resultado.getDate("fechaServicio"),
                                        resultado.getString("tipoServicio"),
                                        resultado.getString("horaServicio"),
                                        resultado.getString("lugarServicio"),
                                        resultado.getString("telefonoContacto"),
                                        resultado.getInt("codigoEmpresa")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaServicios = FXCollections.observableArrayList(lista);
    } 
    
    public void generarReporte(){
       switch(tipoDeOperacion){
           case NINGUNO:
               imprimirReporte();
           break;    
   } 
  }
   
   public void imprimirReporte(){
         int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea generar el reporte de esta entidad?", "Crear reporte",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
         if(respuesta == JOptionPane.YES_OPTION)
             try{
                 JOptionPane.showMessageDialog(null, "Generar Reporte");
                 Map parametro = new HashMap();
                 int codServicio = Integer.valueOf(((Servicios)cmbServicios.getSelectionModel().getSelectedItem()).getCodigoServicio());
                 parametro.put("codServicio", codServicio);
                 GenerarReporte.mostrarReporte("ReporteServicios.jasper", "Reporte Servicios", parametro);
             }catch(Exception e){
                 e.printStackTrace();
             }
       }
     public void ventanaReportes(){
        escenarioPrincipal.ventanaReportes();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
     

}

