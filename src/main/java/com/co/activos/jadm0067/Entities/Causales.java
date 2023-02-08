/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.activos.jadm0067.Entities;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author desierra
 */
public class Causales implements Serializable{
    
    private Integer codigo_causal;
    private String descripcion_causal;
    private Integer ecl_secuencia;

    public Causales() {
       
    }
    

    public Integer getCodigo_causal() {
        if (codigo_causal == null) {
            return 0;
        }
        return codigo_causal;
    }

    public void setCodigo_causal(Integer codigo_causal) {
        this.codigo_causal = codigo_causal;
    }

    public String getDescripcion_causal() {
        return descripcion_causal;
    }

    public void setDescripcion_causal(String descripcion_causal) {
        this.descripcion_causal = descripcion_causal;
    }

    public Integer getEcl_secuencia() {
        return ecl_secuencia;
    }

    public void setEcl_secuencia(Integer ecl_secuencia) {
        this.ecl_secuencia = ecl_secuencia;
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
        final Causales other = (Causales) obj;
        if (!Objects.equals(this.descripcion_causal, other.descripcion_causal)) {
            return false;
        }
        if (!Objects.equals(this.codigo_causal, other.codigo_causal)) {
            return false;
        }
        if (!Objects.equals(this.ecl_secuencia, other.ecl_secuencia)) {
            return false;
        }
        return true;
    }
    
    
}
