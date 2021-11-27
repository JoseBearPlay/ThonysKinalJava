
package org.josesalazar.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.josesalazar.bean.Platos;
import org.josesalazar.bean.Servicios;
import org.josesalazar.bean.Servicios_has_Platos;
import org.josesalazar.db.Conexion;
import org.josesalazar.system.Principal;


public class Servicios_has_PlatosController implements Initializable{
    private Principal escenarioPrincipal;
    private ObservableList<Servicios> listaServicios;
    private ObservableList<Platos> listaPlatos;
    private ObservableList<Servicios_has_Platos>listaServicios_has_Platos;
    @FXML private ComboBox cmbCodigoServicio;
    @FXML private ComboBox cmbCodigoPlato;
    @FXML private TableView tblServicios_has_Platos;
    @FXML private TableColumn colCodigoServicio;
    @FXML private TableColumn colCodigoPlato;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoPlato.setItems(getPlatos());
        cmbCodigoServicio.setItems(getServicios());
    }
    
    public void cargarDatos(){
        tblServicios_has_Platos.setItems(getServicios_Platos());
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<Servicios_has_Platos, Integer>("codigoServicio"));
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<Servicios_has_Platos, Integer>("codigoPlato"));
    }
    
    public ObservableList<Servicios_has_Platos> getServicios_Platos(){
        ArrayList<Servicios_has_Platos> lista = new ArrayList<Servicios_has_Platos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarServicios_has_Platos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Servicios_has_Platos(resultado.getInt("codigoServicio"),
                                                   resultado.getInt("codigoPlato")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaServicios_has_Platos = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Platos> getPlatos(){
        ArrayList<Platos> lista = new ArrayList<Platos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPlatos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Platos(resultado.getInt("codigoPlato"),
                                     resultado.getInt("cantidad"),
                                     resultado.getString("nombrePlato"),
                                     resultado.getString("descripcionPlato"),
                                     resultado.getDouble("precioPlato"),
                                     resultado.getInt("codigoTipoPlato")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaPlatos = FXCollections.observableArrayList(lista);
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
   
    public Platos buscarPlatos(int codigoPlato){
        Platos resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarPlato(?)}");
            procedimiento.setInt(1, codigoPlato);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Platos(registro.getInt("codigoPlato"),
                                       registro.getInt("cantidad"),
                                       registro.getString("nombrePlato"),
                                       registro.getString("descripcionPlato"),
                                       registro.getDouble("precioPlato"),
                                       registro.getInt("codigoTipoPlato"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Servicios buscarServicios(int codigoServicio){
        Servicios resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarServicio(?)}");
            procedimiento.setInt(1, codigoServicio);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Servicios(registro.getInt("codigoServicio"),
                                       registro.getDate("fechaServicio"),
                                       registro.getString("tipoServicio"),
                                       registro.getString("horaServicio"),
                                       registro.getString("lugarServicio"),
                                       registro.getString("telefonoContacto"),
                                       registro.getInt("codigoEmpresa"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public void seleccionarElemento(){
        cmbCodigoServicio.getSelectionModel().select(buscarServicios(((Servicios_has_Platos)tblServicios_has_Platos.getSelectionModel().getSelectedItem()).getCodigoServicio()));
        cmbCodigoPlato.getSelectionModel().select(buscarPlatos(((Servicios_has_Platos)tblServicios_has_Platos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
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
