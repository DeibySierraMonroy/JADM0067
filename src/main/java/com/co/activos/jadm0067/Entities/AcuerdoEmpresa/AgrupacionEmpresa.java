/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.activos.jadm0067.Entities.AcuerdoEmpresa;

import java.util.Objects;

/**
 *
 * @author desierra
 */
public class AgrupacionEmpresa {
    
  private Integer AGA_CODIGO;
  private String PAC_CODIGO;
  private String AGA_DESCRIPCION;
  private Integer AOR_CODIGO;

    public AgrupacionEmpresa() {
    }

    public Integer getAGA_CODIGO() {
        return AGA_CODIGO;
    }

    public void setAGA_CODIGO(Integer AGA_CODIGO) {
        this.AGA_CODIGO = AGA_CODIGO;
    }

    public String getPAC_CODIGO() {
        return PAC_CODIGO;
    }

    public void setPAC_CODIGO(String PAC_CODIGO) {
        this.PAC_CODIGO = PAC_CODIGO;
    }

    public String getAGA_DESCRIPCION() {
        return AGA_DESCRIPCION;
    }

    public void setAGA_DESCRIPCION(String AGA_DESCRIPCION) {
        this.AGA_DESCRIPCION = AGA_DESCRIPCION;
    }

    public Integer getAOR_CODIGO() {
        return AOR_CODIGO;
    }

    public void setAOR_CODIGO(Integer AOR_CODIGO) {
        this.AOR_CODIGO = AOR_CODIGO;
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
        final AgrupacionEmpresa other = (AgrupacionEmpresa) obj;
        if (!Objects.equals(this.PAC_CODIGO, other.PAC_CODIGO)) {
            return false;
        }
        if (!Objects.equals(this.AGA_DESCRIPCION, other.AGA_DESCRIPCION)) {
            return false;
        }
        if (!Objects.equals(this.AGA_CODIGO, other.AGA_CODIGO)) {
            return false;
        }
        return true;
    }
  
    
  
}
