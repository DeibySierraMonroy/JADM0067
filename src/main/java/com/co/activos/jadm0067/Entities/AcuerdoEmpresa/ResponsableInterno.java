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
public class ResponsableInterno {
    
    private String  TCA_DESCRIPCION;
    private String  RIN_NOMBRE;
    private String  RIN_MAIL;
    private String  RIN_TELEFONO;

    public ResponsableInterno() {
    }
    
    

    public String getTCA_DESCRIPCION() {
        return TCA_DESCRIPCION;
    }

    public void setTCA_DESCRIPCION(String TCA_DESCRIPCION) {
        this.TCA_DESCRIPCION = TCA_DESCRIPCION;
    }

    public String getRIN_NOMBRE() {
        return RIN_NOMBRE;
    }

    public void setRIN_NOMBRE(String RIN_NOMBRE) {
        this.RIN_NOMBRE = RIN_NOMBRE;
    }

    public String getRIN_MAIL() {
        return RIN_MAIL;
    }

    public void setRIN_MAIL(String RIN_MAIL) {
        this.RIN_MAIL = RIN_MAIL;
    }

    public String getRIN_TELEFONO() {
        return RIN_TELEFONO;
    }

    public void setRIN_TELEFONO(String RIN_TELEFONO) {
        this.RIN_TELEFONO = RIN_TELEFONO;
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
        final ResponsableInterno other = (ResponsableInterno) obj;
        if (!Objects.equals(this.TCA_DESCRIPCION, other.TCA_DESCRIPCION)) {
            return false;
        }
        if (!Objects.equals(this.RIN_NOMBRE, other.RIN_NOMBRE)) {
            return false;
        }
        if (!Objects.equals(this.RIN_MAIL, other.RIN_MAIL)) {
            return false;
        }
        if (!Objects.equals(this.RIN_TELEFONO, other.RIN_TELEFONO)) {
            return false;
        }
        return true;
    }
    
    
    
    
}

