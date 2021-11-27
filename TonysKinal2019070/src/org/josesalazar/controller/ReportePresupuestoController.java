
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
import org.josesalazar.bean.Empresa;
import org.josesalazar.db.Conexion;
import org.josesalazar.report.GenerarReporte;
import org.josesalazar.system.Principal;


public class ReportePresupuestoController implements Initializable{
    
    private enum operaciones {NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Empresa> listaEmpresa;
    @FXML private ComboBox cmbEmpresas; 
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    cmbEmpresas.setItems(getEmpresa());
    }
    
    public ObservableList<Empresa> getEmpresa(){
        ArrayList<Empresa> lista = new ArrayList<Empresa>();
        try{
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpresas}");
         ResultSet resultado = procedimiento.executeQuery();
         while(resultado.next()){
             lista.add(new Empresa(resultado.getInt("codigoEmpresa"),
                                    resultado.getString("nombreEmpresa"),
                                    resultado.getString("direccion"),
                                    resultado.getString("telefono")));
         }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaEmpresa = FXCollections.observableArrayList(lista);
    }
    
       public void generarReporte(){
         switch(tipoDeOperacion){
             case NINGUNO:
                imprimirReporte();
             break;
                }
        }
   
    
    public void imprimirReporte(){
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea generar el reporte de esta entidad?","Crear reporte",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(respuesta == JOptionPane.YES_OPTION)
        try{
            JOptionPane.showMessageDialog(null, "Generando el Reporte");
            Map parametro = new HashMap();
            int codEmpresa = Integer.valueOf(((Empresa)cmbEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
            parametro.put("codEmpresa", codEmpresa);
            GenerarReporte.mostrarReporte("ReportePresupuestoFinal.jasper", "Reporte Presupuesto", parametro);
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
