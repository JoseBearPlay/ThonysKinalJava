package org.josesalazar.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.josesalazar.bean.Empresa;
import org.josesalazar.bean.Servicios;
import org.josesalazar.db.Conexion;
import org.josesalazar.report.GenerarReporte;
import org.josesalazar.system.Principal;


public class ServiciosController implements Initializable{
    
    private Principal escenarioPrincipal; 
    private enum operaciones {NUEVO,GUARDAR,ELIMINAR,EDITAR,ACTUALIZAR,CANCELAR,NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Servicios> listaServicio;
    private ObservableList<Empresa>listaEmpresa;
    private DatePicker fecha;
    @FXML private TextField txtCodigoServicio;
    @FXML private TextField txtTipoServicio;
    @FXML private TextField txtHoraServicio;
    @FXML private TextField txtLugarServicio;
    @FXML private TextField txtTelefonoContacto;
    @FXML private GridPane grpFechaServicio;
    @FXML private ComboBox cmbEmpresas;
    @FXML private TableView tblServicios;
    @FXML private TableColumn colCodigoServicio;
    @FXML private TableColumn colFechaServicio;
    @FXML private TableColumn colTipoServicio;
    @FXML private TableColumn colHoraServicio;
    @FXML private TableColumn colLugarServicio;
    @FXML private TableColumn colTelefonoContacto;
    @FXML private TableColumn colCodigoEmpresa;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        fecha = new DatePicker(Locale.ENGLISH);
        fecha.setDateFormat(new SimpleDateFormat("yyy-MM-dd"));
        fecha.getCalendarView().todayButtonTextProperty().set("Today");
        fecha.getCalendarView().setShowWeeks(false);
        grpFechaServicio.add(fecha, 1, 1);
        fecha.getStylesheets().add("/org/josesalazar/resource/Cascada.css");
        cmbEmpresas.setItems(getEmpresa());
    }

    public void cargarDatos(){
        tblServicios.setItems(getServicio());
        colCodigoServicio.setCellValueFactory(new PropertyValueFactory<Servicios,Integer>("codigoServicio"));
        colFechaServicio.setCellValueFactory(new PropertyValueFactory<Servicios, Date>("fechaServicio"));
        colTipoServicio.setCellValueFactory(new PropertyValueFactory<Servicios, String>("tipoServicio"));
        colHoraServicio.setCellValueFactory(new PropertyValueFactory<Servicios, String>("horaServicio"));
        colLugarServicio.setCellValueFactory(new PropertyValueFactory<Servicios, String>("lugarServicio"));
        colTelefonoContacto.setCellValueFactory(new PropertyValueFactory<Servicios, String>("telefonoContacto"));
        colCodigoEmpresa.setCellValueFactory(new PropertyValueFactory<Servicios, Integer>("codigoEmpresa"));
    }
    
    public ObservableList<Servicios> getServicio(){
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
        return listaServicio = FXCollections.observableArrayList(lista);
    }
    
        public ObservableList<Empresa> getEmpresa(){
        ArrayList<Empresa> lista = new ArrayList<Empresa>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmpresas}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empresa( resultado.getInt("codigoEmpresa"),
                                       resultado.getString("nombreEmpresa"),
                                       resultado.getString("direccion"),
                                       resultado.getString("telefono")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    
        return listaEmpresa = FXCollections.observableArrayList(lista);
    }
        
        public Servicios buscarServicio (int codigoServicio){
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
    
        public Empresa buscarEmpresa (int codigoEmpresa){
        Empresa resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarEmpresa(?)}");
            procedimiento.setInt(1, codigoEmpresa);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Empresa(registro.getInt("codigoEmpresa"),
                                        registro.getString("nombreEmpresa"),
                                        registro.getString("direccion"),
                                        registro.getString("telefono"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
        
    public void seleccionarElemento(){
        txtCodigoServicio.setText((String.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getCodigoServicio())));
        txtTipoServicio.setText(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getTipoServicio());
        txtHoraServicio.setText(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getHoraServicio());
        txtLugarServicio.setText(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getLugarServicio());
        txtTelefonoContacto.setText(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getTelefonoContacto());
        fecha.selectedDateProperty().set(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getFechaServicio());
        cmbEmpresas.getSelectionModel().select(buscarEmpresa(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getCodigoEmpresa()));
    }
    
    
   public void nuevo(){
       switch(tipoDeOperacion){
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
       Servicios registro = new Servicios();
       registro.setFechaServicio(fecha.getSelectedDate());
       registro.setTipoServicio(txtTipoServicio.getText());
       registro.setHoraServicio(txtHoraServicio.getText());
       registro.setLugarServicio(txtLugarServicio.getText());
       registro.setTelefonoContacto(txtTelefonoContacto.getText());
       registro.setCodigoEmpresa(((Empresa)cmbEmpresas.getSelectionModel().getSelectedItem()).getCodigoEmpresa());
       try{
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarServicio(?,?,?,?,?,?)}");
           procedimiento.setDate(1, new java.sql.Date(registro.getFechaServicio().getTime()));
           procedimiento.setString(2, registro.getTipoServicio());
           procedimiento.setString(3, registro.getHoraServicio());
           procedimiento.setString(4, registro.getLugarServicio());
           procedimiento.setString(5, registro.getTelefonoContacto());
           procedimiento.setInt(6, registro.getCodigoEmpresa());
           procedimiento.execute();
           listaServicio.add(registro);
       }catch(Exception e){
           e.printStackTrace();
       }
   }
   
   public void Eliminar(){
        switch (tipoDeOperacion){
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
                if(tblServicios.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar el registro?", "Eliminar Servicio",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION)
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_Eliminarervicio(?)}");
                            procedimiento.setInt(1, ((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getCodigoServicio());
                            procedimiento.executeQuery();
                            listaServicio.remove(tblServicios.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                }else{
                    JOptionPane.showMessageDialog(null, "Debes seleccionar un elemento");
                }
        }
   }
   
   public void editar(){
       switch(tipoDeOperacion){
           case NINGUNO:
               if(tblServicios.getSelectionModel().getSelectedItem() != null){
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
           PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarServicio(?,?,?,?,?,?)}");
           Servicios  registro = ((Servicios)tblServicios.getSelectionModel().getSelectedItem());
           registro.setFechaServicio(fecha.getSelectedDate());
           registro.setTipoServicio(txtTipoServicio.getText());
           registro.setHoraServicio(txtHoraServicio.getText());
           registro.setLugarServicio(txtLugarServicio.getText());
           registro.setTelefonoContacto(txtTelefonoContacto.getText());
           procedimiento.setInt(1, registro.getCodigoServicio());
           procedimiento.setDate(2, new java.sql.Date(registro.getFechaServicio().getTime()));
           procedimiento.setString(3, registro.getTipoServicio());
           procedimiento.setString(4, registro.getHoraServicio());
           procedimiento.setString(5, registro.getLugarServicio());
           procedimiento.setString(6, registro.getTelefonoContacto());
           procedimiento.execute();
      }catch(Exception e){
          e.printStackTrace();
      }
   }
   
   public void reporte(){
       switch(tipoDeOperacion){
           case ACTUALIZAR:
               desactivarControles();
               limpiarControles();
               btnEditar.setText("Editar");
               btnEditar.setDisable(false);
               btnReporte.setText("Reporte");
               btnReporte.setDisable(false);
               btnNuevo.setDisable(false);
               btnEliminar.setDisable(false);
               tipoDeOperacion = operaciones.NINGUNO;
               break;
       }
   }
   
   public void generarReporte(){
       switch(tipoDeOperacion){
           case NINGUNO:
               imprimirReporte();
           break;    
   } 
  }
   
   public void imprimirReporte(){
       if(tblServicios.getSelectionModel().getSelectedItem() != null){
         int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea eliminar el registro?", "Eliminar Servicio",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
         if(respuesta == JOptionPane.YES_OPTION)
             try{
                 JOptionPane.showMessageDialog(null, "Generar Reporte");
                 Map parametro = new HashMap();
                 int codServicio = Integer.valueOf(((Servicios)tblServicios.getSelectionModel().getSelectedItem()).getCodigoServicio());
                 parametro.put("codServicio", codServicio);
                 GenerarReporte.mostrarReporte("ReporteServicios.jasper", "Reporte Servicios", parametro);
             }catch(Exception e){
                 e.printStackTrace();
             }
   }else{
      JOptionPane.showMessageDialog(null, "Debes seleccionar un elemento");
       }
           
   }
   
    public void desactivarControles(){
        txtCodigoServicio.setEditable(false);
        txtTipoServicio.setEditable(false);
        txtHoraServicio.setEditable(false);
        txtLugarServicio.setEditable(false);
        txtTelefonoContacto.setEditable(false);
        grpFechaServicio.setDisable(false);
        cmbEmpresas.setEditable(false);
        cmbEmpresas.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoServicio.setEditable(false);
        txtTipoServicio.setEditable(true);
        txtHoraServicio.setEditable(true);
        txtLugarServicio.setEditable(true);
        txtTelefonoContacto.setEditable(true);
        grpFechaServicio.setDisable(false);
        cmbEmpresas.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoServicio.setText("");
        txtTipoServicio.setText("");
        txtHoraServicio.setText("");
        txtLugarServicio.setText("");
        txtTelefonoContacto.setText("");
        cmbEmpresas.getSelectionModel().clearSelection();
    }
    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaEmpresas(){
        escenarioPrincipal.ventanaEmpresas();
    }
    
}
