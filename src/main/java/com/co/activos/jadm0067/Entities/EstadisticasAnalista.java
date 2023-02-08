/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.activos.jadm0067.Entities;

import java.util.Objects;

/**
 * Clase  utilizada para guardar las estadisticas del analista.
 * @author desierra
 */

public class EstadisticasAnalista {
    
    private Integer candidatosAprobados;
    private Integer candidatosRechazados;
    private Integer totalCandidatos;

    public EstadisticasAnalista() {
    }

    public Integer getCandidatosAprobados() {
        return candidatosAprobados;
    }

    public void setCandidatosAprobados(Integer candidatosAprobados) {
        this.candidatosAprobados = candidatosAprobados;
    }

    public Integer getCandidatosRechazados() {
        return candidatosRechazados;
    }

    public void setCandidatosRechazados(Integer candidatosRechazados) {
        this.candidatosRechazados = candidatosRechazados;
    }

    public Integer getTotalCandidatos() {
        return totalCandidatos;
    }

    public void setTotalCandidatos(Integer totalCandidatos) {
        this.totalCandidatos = totalCandidatos;
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
        final EstadisticasAnalista other = (EstadisticasAnalista) obj;
        if (!Objects.equals(this.candidatosAprobados, other.candidatosAprobados)) {
            return false;
        }
        if (!Objects.equals(this.candidatosRechazados, other.candidatosRechazados)) {
            return false;
        }
        if (!Objects.equals(this.totalCandidatos, other.totalCandidatos)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
