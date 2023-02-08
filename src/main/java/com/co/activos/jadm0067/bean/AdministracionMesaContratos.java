
package com.co.activos.jadm0067.bean;
import com.co.activos.jadm0067.Entities.DatabaseResultDto;
import com.co.activos.jadm0067.Entities.InformacionSeguimiento;
import static com.co.activos.jadm0067.Enum.DatabaseResultStatus.SUCCESS;
import com.co.activos.jadm0067.Repositories.AdministracionRepo;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author desierra
 */
@Named
@ViewScoped
public class AdministracionMesaContratos implements Serializable{
    
    
    @Inject
    private AdministracionRepo administracionRepo;
    
    private List<InformacionSeguimiento> InformacionSeguimientoList;
    
    private InformacionSeguimiento informacionSeguimiento;
    

    @PostConstruct
    public void init(){
    
        listarCedulasPendientesEnEstadoEMC();
        
    }
    
    public void listarCedulasPendientesEnEstadoEMC(){
        try {
             DatabaseResultDto<InformacionSeguimiento> cedulasPendientes = administracionRepo.listarCedulasPendientesEMC();
             if (cedulasPendientes.getStatus().equals(SUCCESS)) {
                InformacionSeguimientoList = cedulasPendientes.getListResult();
            }           
        } catch (Exception e) {
            System.out.println("Error al traer la informacion " + e.getStackTrace());
        }
    
    
    }
    
    public String validarEltiempoTrancurrido(String tiempo){  
        String background = "#059669";
        Integer tiempoTotal =0;
        Integer validarHoras =0;
        tiempoTotal=Integer.parseInt(tiempo.substring(5, 7));
        validarHoras=Integer.parseInt(tiempo.substring(1,3));
        if (validarHoras >= 1) {
         background = "#DC2626";  
        }
        if(tiempoTotal >3 && tiempoTotal<5 && validarHoras < 1){
         background = "#f57c00";  
        }
         if (tiempoTotal >= 5) {
         background = "#DC2626";  
        }
      
        return background;       
    }
    

    public List<InformacionSeguimiento> getInformacionSeguimientoList() {
        return InformacionSeguimientoList;
    }

    public void setInformacionSeguimientoList(List<InformacionSeguimiento> InformacionSeguimientoList) {
        this.InformacionSeguimientoList = InformacionSeguimientoList;
    }

    public InformacionSeguimiento getInformacionSeguimiento() {
        return informacionSeguimiento;
    }

    public void setInformacionSeguimiento(InformacionSeguimiento informacionSeguimiento) {
        this.informacionSeguimiento = informacionSeguimiento;
    }

 
  
 


  
    
    
}
