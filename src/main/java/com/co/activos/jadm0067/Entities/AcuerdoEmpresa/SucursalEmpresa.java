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
public class SucursalEmpresa {
   private String SUE_CODIGO;
   private String SUE_NOMBRE;
   private String SUE_DIRECCION;

    public SucursalEmpresa() {
    }

    public String getSUE_NOMBRE() {
        return SUE_NOMBRE;
    }

    public void setSUE_NOMBRE(String SUE_NOMBRE) {
        this.SUE_NOMBRE = SUE_NOMBRE;
    }

    public String getSUE_DIRECCION() {
        return SUE_DIRECCION;
    }

    public void setSUE_DIRECCION(String SUE_DIRECCION) {
        this.SUE_DIRECCION = SUE_DIRECCION;
    }

    public String getSUE_CODIGO() {
        return SUE_CODIGO;
    }

    public void setSUE_CODIGO(String SUE_CODIGO) {
        this.SUE_CODIGO = SUE_CODIGO;
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
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
        final SucursalEmpresa other = (SucursalEmpresa) obj;
        if (!Objects.equals(this.SUE_NOMBRE, other.SUE_NOMBRE)) {
            return false;
        }
        if (!Objects.equals(this.SUE_DIRECCION, other.SUE_DIRECCION)) {
            return false;
        }
        return true;
    }
   
    
   
   
}

