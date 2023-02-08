/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.activos.jadm0067.Entities;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author desierra
 */
public class InformacionSeguimiento {

    private Date fecha_estado_emc;
    private String inc_usuario_nue;
    private String epl_nd;
    private String empresa;
    private String tdc_td_epl;
    private String lib_consecutivo;
    private String tiempo_transcurrido;
 
    public InformacionSeguimiento() {

    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InformacionSeguimiento other = (InformacionSeguimiento) obj;
        if (!Objects.equals(this.inc_usuario_nue, other.inc_usuario_nue)) {
            return false;
        }
        if (!Objects.equals(this.epl_nd, other.epl_nd)) {
            return false;
        }
        if (!Objects.equals(this.empresa, other.empresa)) {
            return false;
        }
        if (!Objects.equals(this.tdc_td_epl, other.tdc_td_epl)) {
            return false;
        }
        if (!Objects.equals(this.lib_consecutivo, other.lib_consecutivo)) {
            return false;
        }
        if (!Objects.equals(this.fecha_estado_emc, other.fecha_estado_emc)) {
            return false;
        }
        return true;
    }

    public Date getFecha_estado_emc() {
        return fecha_estado_emc;
    }

    public void setFecha_estado_emc(Date fecha_estado_emc) {
        this.fecha_estado_emc = fecha_estado_emc;
    }

    public String getInc_usuario_nue() {
        return inc_usuario_nue;
    }

    public void setInc_usuario_nue(String inc_usuario_nue) {
        this.inc_usuario_nue = inc_usuario_nue;
    }

    public String getEpl_nd() {
        return epl_nd;
    }

    public void setEpl_nd(String epl_nd) {
        this.epl_nd = epl_nd;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTdc_td_epl() {
        return tdc_td_epl;
    }

    public void setTdc_td_epl(String tdc_td_epl) {
        this.tdc_td_epl = tdc_td_epl;
    }

    public String getLib_consecutivo() {
        return lib_consecutivo;
    }

    public void setLib_consecutivo(String lib_consecutivo) {
        this.lib_consecutivo = lib_consecutivo;
    }

    public String getTiempo_transcurrido() {
        return tiempo_transcurrido;
    }

    public void setTiempo_transcurrido(String tiempo_transcurrido) {
        this.tiempo_transcurrido = tiempo_transcurrido;
    }

  
    
}
