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
public class EmpresaPrincipal {
    private String EMP_ND_PRINCIPAL;
    private String EEMPRESA_PRINCIPAL;

    public EmpresaPrincipal() {
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
        final EmpresaPrincipal other = (EmpresaPrincipal) obj;
        if (!Objects.equals(this.EMP_ND_PRINCIPAL, other.EMP_ND_PRINCIPAL)) {
            return false;
        }
        if (!Objects.equals(this.EEMPRESA_PRINCIPAL, other.EEMPRESA_PRINCIPAL)) {
            return false;
        }
        return true;
    }
    
    
    
    

    public String getEMP_ND_PRINCIPAL() {
        return EMP_ND_PRINCIPAL;
    }

    public void setEMP_ND_PRINCIPAL(String EMP_ND_PRINCIPAL) {
        this.EMP_ND_PRINCIPAL = EMP_ND_PRINCIPAL;
    }

    public String getEEMPRESA_PRINCIPAL() {
        return EEMPRESA_PRINCIPAL;
    }

    public void setEEMPRESA_PRINCIPAL(String EEMPRESA_PRINCIPAL) {
        this.EEMPRESA_PRINCIPAL = EEMPRESA_PRINCIPAL;
    }
    
    
}
