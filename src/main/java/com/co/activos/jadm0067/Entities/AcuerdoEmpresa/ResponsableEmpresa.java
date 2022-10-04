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
public class ResponsableEmpresa {
    
    private Integer TRS_CODIGO;
    private String  TRS_TIPO;
    private Integer RCL_CODIGO;
    private String  RCL_NOMBRE;
    private String  RCL_CARGO;
    private String  RCL_OBSERVACION;

    public ResponsableEmpresa() {
    }
    
    

    public Integer getTRS_CODIGO() {
        return TRS_CODIGO;
    }

    public void setTRS_CODIGO(Integer TRS_CODIGO) {
        this.TRS_CODIGO = TRS_CODIGO;
    }

    public String getTRS_TIPO() {
        return TRS_TIPO;
    }

    public void setTRS_TIPO(String TRS_TIPO) {
        this.TRS_TIPO = TRS_TIPO;
    }

    public Integer getRCL_CODIGO() {
        return RCL_CODIGO;
    }

    public void setRCL_CODIGO(Integer RCL_CODIGO) {
        this.RCL_CODIGO = RCL_CODIGO;
    }

    public String getRCL_NOMBRE() {
        return RCL_NOMBRE;
    }

    public void setRCL_NOMBRE(String RCL_NOMBRE) {
        this.RCL_NOMBRE = RCL_NOMBRE;
    }

    public String getRCL_CARGO() {
        return RCL_CARGO;
    }

    public void setRCL_CARGO(String RCL_CARGO) {
        this.RCL_CARGO = RCL_CARGO;
    }

    public String getRCL_OBSERVACION() {
        return RCL_OBSERVACION;
    }

    public void setRCL_OBSERVACION(String RCL_OBSERVACION) {
        this.RCL_OBSERVACION = RCL_OBSERVACION;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final ResponsableEmpresa other = (ResponsableEmpresa) obj;
        if (!Objects.equals(this.TRS_TIPO, other.TRS_TIPO)) {
            return false;
        }
        if (!Objects.equals(this.RCL_NOMBRE, other.RCL_NOMBRE)) {
            return false;
        }
        if (!Objects.equals(this.RCL_CARGO, other.RCL_CARGO)) {
            return false;
        }
        if (!Objects.equals(this.RCL_OBSERVACION, other.RCL_OBSERVACION)) {
            return false;
        }
        if (!Objects.equals(this.TRS_CODIGO, other.TRS_CODIGO)) {
            return false;
        }
        if (!Objects.equals(this.RCL_CODIGO, other.RCL_CODIGO)) {
            return false;
        }
        return true;
    }
 
    
    
    
}
