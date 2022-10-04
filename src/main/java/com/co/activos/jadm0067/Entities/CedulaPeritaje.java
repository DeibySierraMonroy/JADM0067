/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.activos.jadm0067.Entities;

/**
 * Entidad CedulaPeritaje, solo se establecen los campos necesarios para traer la informacion de acuerdo a la cedula seleccionada.
 *
 * @author desierra
 */
public class CedulaPeritaje {
    
    private String tdc_td_epl;
    private String epl_nd;
    private Long lib_consecutivo;

    public CedulaPeritaje() {
    }
    
    

    public String getTdc_td_epl() {
        return tdc_td_epl;
    }

    public void setTdc_td_epl(String tdc_td_epl) {
        this.tdc_td_epl = tdc_td_epl;
    }

    public String getEpl_nd() {
        return epl_nd;
    }

    public void setEpl_nd(String epl_nd) {
        this.epl_nd = epl_nd;
    }

    public Long getLib_consecutivo() {
        return lib_consecutivo;
    }

    public void setLib_consecutivo(Long lib_consecutivo) {
        this.lib_consecutivo = lib_consecutivo;
    }

  
    
    
    
}
