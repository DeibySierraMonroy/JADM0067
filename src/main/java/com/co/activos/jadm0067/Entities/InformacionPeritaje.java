
package com.co.activos.jadm0067.Entities;


/**
 * Entidad InformacionPeritaje, solo se establecen los campos necesarios para traer la informacion de acuerdo a la cedula seleccionada.
 *
 * @author desierra
 */
public class InformacionPeritaje {
    
    private String epl_nd;
    private String tdc_td;
    private String emp_nd;
    private String emp_nom;
    private String lib_consecutivo;
    private String nombre_empleado;
    private String cargo_empleado;
    private String tdc_td_epl;
    private String correo_empleado;
    private Long numero_contrato;
    

    public InformacionPeritaje() {
    }

    public Long getNumero_contrato() {
        return numero_contrato;
    }

    public void setNumero_contrato(Long numero_contrato) {
        this.numero_contrato = numero_contrato;
    }
    
    

    public String getCorreo_empleado() {
        return correo_empleado;
    }

    public void setCorreo_empleado(String correo_empleado) {
        this.correo_empleado = correo_empleado;
    }
    
    
    public String getTdc_td() {
        return tdc_td;
    }

    public void setTdc_td(String tdc_td) {
        this.tdc_td = tdc_td;
    }

    public String getEmp_nd() {
        return emp_nd;
    }

    public void setEmp_nd(String emp_nd) {
        this.emp_nd = emp_nd;
    }

    public String getEmp_nom() {
        return emp_nom;
    }

    public void setEmp_nom(String emp_nom) {
        this.emp_nom = emp_nom;
    }

    public String getLib_consecutivo() {
        return lib_consecutivo;
    }

    public void setLib_consecutivo(String lib_consecutivo) {
        this.lib_consecutivo = lib_consecutivo;
    }

    public String getNombre_empleado() {
        return nombre_empleado;
    }

    public void setNombre_empleado(String nombre_empleado) {
        this.nombre_empleado = nombre_empleado;
    }

    public String getCargo_empleado() {
        return cargo_empleado;
    }

    public void setCargo_empleado(String cargo_empleado) {
        this.cargo_empleado = cargo_empleado;
    }

    public String getEpl_nd() {
        return epl_nd;
    }

    public void setEpl_nd(String epl_nd) {
        this.epl_nd = epl_nd;
    }

    public String getTdc_td_epl() {
        return tdc_td_epl;
    }

    public void setTdc_td_epl(String tdc_td_epl) {
        this.tdc_td_epl = tdc_td_epl;
    }
    
    
    
    
    
    
}
