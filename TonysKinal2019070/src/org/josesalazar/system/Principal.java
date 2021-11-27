package org.josesalazar.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.josesalazar.controller.DatosProgramadorController;
import org.josesalazar.controller.EmpleadosController;
import org.josesalazar.controller.EmpresasController;
import org.josesalazar.controller.ManualesController;
import org.josesalazar.controller.MenuPrincipalController;
import org.josesalazar.controller.PlatosController;
import org.josesalazar.controller.PresupuestoController;
import org.josesalazar.controller.ProductosController;
import org.josesalazar.controller.Productos_has_PlatosController;
import org.josesalazar.controller.ReportePresupuestoController;
import org.josesalazar.controller.ReporteServiciosController;
import org.josesalazar.controller.ReportesController;
import org.josesalazar.controller.ServiciosController;
import org.josesalazar.controller.Servicios_has_EmpleadosController;
import org.josesalazar.controller.Servicios_has_PlatosController;
import org.josesalazar.controller.TipoEmpleadoController;
import org.josesalazar.controller.TipoPlatoController;
import org.josesalazar.report.GenerarReporte;


public class Principal extends Application {
    
    private enum operaciones {NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private final String PAQUETE_VISTA = "/org/josesalazar/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
      this.escenarioPrincipal = escenarioPrincipal;
      escenarioPrincipal.setTitle("Thony´s Kinal App");
      escenarioPrincipal.getIcons().add(new Image("/org/josesalazar/image/Logotipo.png"));
      menuPrincipal();
      escenarioPrincipal.show();
    } 

    public void menuPrincipal(){
        try{
            MenuPrincipalController menuPrincipal = (MenuPrincipalController)cambiarEscena(("MenuPrincipalView.fxml"),400,400);
            menuPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void ventanaProgramador(){
        try{
            DatosProgramadorController datosPersonales = (DatosProgramadorController)cambiarEscena(("DatosProgramadorView.fxml"),470,470);
            datosPersonales.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
        public void ventanaEmpresas(){
        try{
            EmpresasController datosEmpresa = (EmpresasController)cambiarEscena(("EmpresasView.fxml"),666,474);
            datosEmpresa.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
        
        public void ventanaPresupuesto(){
            try{
                PresupuestoController datosPresupuesto = (PresupuestoController)cambiarEscena(("PresupuestoView.fxml"),600,400);
                datosPresupuesto.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        public void ventanaTipoEmpleado(){
        try{
            TipoEmpleadoController tipoEmpleado = (TipoEmpleadoController)cambiarEscena(("TipoEmpleadoView.fxml"),632,424);
            tipoEmpleado.setEscenarioPrincipal(this);
        }catch(Exception e){
           e.printStackTrace(); 
        }
    }
        
        public void ventanTipoPlato(){
        try{
            TipoPlatoController tipoPlato = (TipoPlatoController)cambiarEscena(("TipoPlatoView.fxml"),640,440);
            tipoPlato.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
 
        
        public void ventanaProductos(){
            try{
                ProductosController productos = (ProductosController)cambiarEscena(("ProductosView.fxml"),640,440);
                productos.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        public void ventanaEmpleados(){
            try{
                EmpleadosController empleados = (EmpleadosController)cambiarEscena(("EmpleadosView.fxml"),907,624);
                empleados.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        public void ventanaServicios(){
            try{
                ServiciosController servicios = (ServiciosController)cambiarEscena(("ServiciosView.fxml"),600,436);
                servicios.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        public void ventanaPlatos(){
            try{
                PlatosController platos = (PlatosController)cambiarEscena(("PlatosView.fxml"),682,430);
                platos.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        public void ventanaServicios_has_Empleados(){
            try{
                Servicios_has_EmpleadosController servicios_has_empleados = (Servicios_has_EmpleadosController)cambiarEscena(("Servicios_has_EmpleadosView.fxml"),750,429);
                servicios_has_empleados.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
            }
                
        }
        
        public void ventanaServicios_has_Platos(){
            try{
                Servicios_has_PlatosController servicios_has_platos = (Servicios_has_PlatosController)cambiarEscena(("Servicios_has_PlatosView.fxml"),600,400);
                servicios_has_platos.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        public void ventanaProductos_has_Platos(){
            try{
                Productos_has_PlatosController productos_has_platos = (Productos_has_PlatosController)cambiarEscena(("Productos_has_PlatosView.fxml"),600,418);
                productos_has_platos.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        public void ventanaReportes(){
            try{
                ReportesController reportes = (ReportesController)cambiarEscena(("ReportesView.fxml"),452,259);
                reportes.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        public void ventanaReportePresupuesto(){
            try{
                ReportePresupuestoController reporteP = (ReportePresupuestoController)cambiarEscena(("ReportePresupuestoView.fxml"),336,200);
                reporteP.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        public void ventanaReporteServicios(){
            try{
                ReporteServiciosController reporteS = (ReporteServiciosController)cambiarEscena(("ReporteServiciosView.fxml"),391,266);
                reporteS.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        public void ventanaManual(){
            try{
                ManualesController manual = (ManualesController) cambiarEscena(("ManualesView.fxml"),394,252);
                manual.setEscenarioPrincipal(this);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        public void opcionDelete(){
        try{
            JOptionPane.showMessageDialog(null, "Aun no existe nada para borrar");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
               
        
    public static void main(String[] args) {
        launch(args);
    }
    
    public Initializable cambiarEscena(String fxml,int ancho,int alto) throws Exception{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene ((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }
}
