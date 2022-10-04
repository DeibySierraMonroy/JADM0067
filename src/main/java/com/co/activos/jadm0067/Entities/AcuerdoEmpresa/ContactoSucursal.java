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
public class ContactoSucursal {
    private String SUE_CODIGO;
    private String CSU_NOMBRE;
    private String TIC_CODIGO;
    private String CSU_DESCRIPCION;

    public ContactoSucursal() {
    }
    
    

    public String getSUE_CODIGO() {
        return SUE_CODIGO;
    }

    public void setSUE_CODIGO(String SUE_CODIGO) {
        this.SUE_CODIGO = SUE_CODIGO;
    }

    public String getCSU_NOMBRE() {
        return CSU_NOMBRE;
    }

    public void setCSU_NOMBRE(String CSU_NOMBRE) {
        this.CSU_NOMBRE = CSU_NOMBRE;
    }

    public String getTIC_CODIGO() {
        return TIC_CODIGO;
    }

    public void setTIC_CODIGO(String TIC_CODIGO) {
        this.TIC_CODIGO = TIC_CODIGO;
    }

    public String getCSU_DESCRIPCION() {
        return CSU_DESCRIPCION;
    }

    public void setCSU_DESCRIPCION(String CSU_DESCRIPCION) {
        this.CSU_DESCRIPCION = CSU_DESCRIPCION;
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
        final ContactoSucursal other = (ContactoSucursal) obj;
        if (!Objects.equals(this.SUE_CODIGO, other.SUE_CODIGO)) {
            return false;
        }
        if (!Objects.equals(this.CSU_NOMBRE, other.CSU_NOMBRE)) {
            return false;
        }
        if (!Objects.equals(this.TIC_CODIGO, other.TIC_CODIGO)) {
            return false;
        }
        if (!Objects.equals(this.CSU_DESCRIPCION, other.CSU_DESCRIPCION)) {
            return false;
        }
        return true;
    }
    
    
    
}
