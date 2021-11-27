
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
import org.josesalazar.bean.Productos;
import org.josesalazar.bean.Productos_has_Platos;
import org.josesalazar.db.Conexion;
import org.josesalazar.system.Principal;

public class Productos_has_PlatosController implements Initializable{

    private Principal escenarioPrincipal;
    private ObservableList<Productos>listaProductos;
    private ObservableList<Platos>listaPlatos;
    private ObservableList<Productos_has_Platos>listaProductos_has_Platos;
    @FXML private ComboBox cmbCodigoProductos;
    @FXML private ComboBox cmbCodigoPlatos;
    @FXML private TableView tblProductos_has_Platos;
    @FXML private TableColumn colCodigoProductos;
    @FXML private TableColumn colCodigoPlatos;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoProductos.setItems(getProductos());
        cmbCodigoPlatos.setItems(getPlatos());
    }
    
    public void cargarDatos(){
        tblProductos_has_Platos.setItems(getProductos_Platos());
        colCodigoProductos.setCellValueFactory(new PropertyValueFactory<Productos_has_Platos,Integer>("codigoProducto"));
        colCodigoPlatos.setCellValueFactory(new PropertyValueFactory<Productos_has_Platos,Integer>("codigoPlato"));
    }
    
    public ObservableList<Productos_has_Platos>getProductos_Platos(){
        ArrayList<Productos_has_Platos> lista = new ArrayList<Productos_has_Platos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos_has_Platos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Productos_has_Platos(resultado.getInt("codigoProducto"),
                                                  resultado.getInt("codigoPlato")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProductos_has_Platos = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Productos>getProductos(){
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Productos(resultado.getInt("codigoProducto"),
                                        resultado.getString("nombreProducto"),
                                        resultado.getInt("cantidad")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProductos = FXCollections.observableArrayList(lista);
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
    
    public Productos_has_Platos buscarProductos_has_Platos(int codigoProducto){
        Productos_has_Platos resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProductos_has_Platos(?,?))}");
            procedimiento.setInt(1, codigoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Productos_has_Platos(registro.getInt("codigoProcuto"),
                                                     registro.getInt("codigoPlato"));
                                                                        
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Productos buscarProductos(int codigoProducto){
        Productos resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProductos(?)}");
            procedimiento.setInt(1, codigoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Productos(registro.getInt("codigoProducto"),
                                          registro.getString("nombreProducto"),
                                          registro.getInt("cantidad"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
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
    
    public void seleccionarElemento(){
        cmbCodigoPlatos.getSelectionModel().select(buscarPlatos(((Productos_has_Platos)tblProductos_has_Platos.getSelectionModel().getSelectedItem()).getCodigoPlato()));
        cmbCodigoProductos.getSelectionModel().select(buscarProductos(((Productos_has_Platos)tblProductos_has_Platos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    
}
