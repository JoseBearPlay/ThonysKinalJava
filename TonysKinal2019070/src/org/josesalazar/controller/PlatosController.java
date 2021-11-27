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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.josesalazar.bean.Platos;
import org.josesalazar.bean.TipoPlato;
import org.josesalazar.db.Conexion;
import org.josesalazar.system.Principal;

public class PlatosController implements Initializable{
    private enum operaciones {NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private Principal escenarioPrincipal;
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Platos> listaPlato;
    private ObservableList<TipoPlato> listaTipoPlato;
    @FXML private TextField txtCodigoPlato;
    @FXML private TextField txtNombrePlato;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtDescripcionPlato;
    @FXML private TextField txtPrecioPlato;
    @FXML private ComboBox cmbTipoPlato;
    @FXML private TableView tblPlatos;
    @FXML private TableColumn colCodigoPlato;
    @FXML private TableColumn colNombrePlato;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colDescripcionPlato;
    @FXML private TableColumn colPrecioPlato;
    @FXML private TableColumn colCodigoTipoPlato;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbTipoPlato.setItems(getTipoPlatos());
    }
    
    public void cargarDatos(){
        tblPlatos.setItems(getPlatos());
        colCodigoPlato.setCellValueFactory(new PropertyValueFactory<Platos, Integer>("codigoPlato"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<Platos, Integer>("cantidad"));
        colNombrePlato.setCellValueFactory(new PropertyValueFactory<Platos, String>("nombrePlato"));
        colDescripcionPlato.setCellValueFactory(new PropertyValueFactory<Platos, String>("descripcionPlato"));
        colPrecioPlato.setCellValueFactory(new PropertyValueFactory<Platos, Double>("precioPlato"));
        colCodigoTipoPlato.setCellValueFactory(new PropertyValueFactory<Platos, Integer>("codigoTipoPlato"));
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
        return listaPlato = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<TipoPlato> getTipoPlatos(){
        ArrayList<TipoPlato> lista = new ArrayList<TipoPlato>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoPlatos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new TipoPlato(resultado.getInt("codigoTipoPlato"),
                                        resultado.getString("descripcionTipo")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaTipoPlato = FXCollections.observableArrayList(lista);
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
    
    public TipoPlato buscarTipoPlatos(int codigoTipoPlato){
        TipoPlato resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarTipoPlato(?)}");
            procedimiento.setInt(1, codigoTipoPlato);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new TipoPlato(registro.getInt("codigoTipoPlato"),
                                          registro.getString("descripcionTipo"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
}
    
    public void seleccionarElemento(){
        txtCodigoPlato.setText((String.valueOf(((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato())));
        txtCantidad.setText((String.valueOf(((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getCantidad())));
        txtNombrePlato.setText(((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getNombrePlato());
        txtDescripcionPlato.setText(((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getDescripcionPlato());
        txtPrecioPlato.setText((String.valueOf(((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getPrecioPlato())));
        cmbTipoPlato.getSelectionModel().select(buscarTipoPlatos(((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoTipoPlato()));
    }
    
     public void nuevo(){
        switch (tipoDeOperacion){
            case NINGUNO:
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setDisable(false);
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperacion = operaciones.GUARDAR;
                break;
                
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(false);
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;    
        }
    }
     
     public void guardar(){
         Platos registro = new Platos();
         registro.setCantidad((Integer.parseInt(txtCantidad.getText())));
         registro.setNombrePlato(txtNombrePlato.getText());
         registro.setDescripcionPlato(txtDescripcionPlato.getText());
         registro.setPrecioPlato(Double.parseDouble(txtPrecioPlato.getText()));
         registro.setCodigoTipoPlato(((TipoPlato)cmbTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlato());
         try{
             PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarPlato(?,?,?,?,?)");
             procedimiento.setInt(1, registro.getCantidad());
             procedimiento.setString(2, registro.getNombrePlato());
             procedimiento.setString(3, registro.getDescripcionPlato());
             procedimiento.setDouble(4, registro.getPrecioPlato());
             procedimiento.setInt(5, registro.getCodigoTipoPlato());
             procedimiento.execute();
             listaPlato.add(registro);
         }catch(Exception e){
             e.printStackTrace();
         }
         
     }
     
     public void Eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnNuevo.setDisable(false);
                btnEliminar.setText("Eliminar");
                btnEliminar.setDisable(false);
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            default:
                if(tblPlatos.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar el registro?", "Eliminar Platos", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION)
                       try{
                           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ElimiarPlato(?)");
                           procedimiento.setInt(1, ((Platos)tblPlatos.getSelectionModel().getSelectedItem()).getCodigoPlato());
                           procedimiento.executeQuery();
                           listaPlato.remove(tblPlatos.getSelectionModel().getSelectedIndex());
                           limpiarControles();
                       }catch(Exception e){
                            e.printStackTrace();
                       }
                       }else{
                            JOptionPane.showMessageDialog(null, "Debes seleccionar un elemento");
                     }
             }
     }
     
     public void Editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblPlatos.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debes seleccionar un elemento");
                }
            break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
            break;  
        }
    }
     
     public void actualizar(){
         try{
         PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarPlato(?,?,?,?,?,?)}");
         Platos registro = ((Platos)tblPlatos.getSelectionModel().getSelectedItem());
         registro.setCantidad((Integer.parseInt(txtCantidad.getText())));
         registro.setNombrePlato(txtNombrePlato.getText());
         registro.setDescripcionPlato(txtDescripcionPlato.getText());
         registro.setPrecioPlato((Double.parseDouble(txtPrecioPlato.getText())));
         registro.setCodigoTipoPlato(((TipoPlato)cmbTipoPlato.getSelectionModel().getSelectedItem()).getCodigoTipoPlato());
         procedimiento.setInt(1, registro.getCodigoPlato());
         procedimiento.setInt(2, registro.getCantidad());
         procedimiento.setString(3, registro.getNombrePlato());
         procedimiento.setString(4, registro.getDescripcionPlato());
         procedimiento.setDouble(5, registro.getPrecioPlato());
         procedimiento.setInt(6, registro.getCodigoTipoPlato());
         procedimiento.execute();
     }catch(Exception e){
         e.printStackTrace();
     }
   }
     
     public void Reporte(){
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnEditar.setDisable(false);
                btnReporte.setText("Reporte");
                btnReporte.setDisable(false);
                btnEliminar.setDisable(false);
                btnNuevo.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
             
    public void desactivarControles(){
        txtCodigoPlato.setEditable(false);
        txtNombrePlato.setEditable(false);
        txtCantidad.setEditable(false);
        txtDescripcionPlato.setEditable(false);
        txtPrecioPlato.setEditable(false);
        cmbTipoPlato.setEditable(false);
        cmbTipoPlato.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoPlato.setEditable(false);
        txtNombrePlato.setEditable(true);
        txtCantidad.setEditable(true);
        txtDescripcionPlato.setEditable(true);
        txtPrecioPlato.setEditable(true);
        cmbTipoPlato.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoPlato.setText("");
        txtNombrePlato.setText("");
        txtCantidad.setText("");
        txtDescripcionPlato.setText("");
        txtPrecioPlato.setText("");
        cmbTipoPlato.getSelectionModel().clearSelection();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void ventanaTipoPlato(){
        escenarioPrincipal.ventanTipoPlato();
    } 
    
    
}
