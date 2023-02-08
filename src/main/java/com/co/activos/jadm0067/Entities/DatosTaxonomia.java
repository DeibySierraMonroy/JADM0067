/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.activos.jadm0067.Entities;

import java.util.Objects;

/**
 * Contiene los atributos de DatosTaxonomia al consultar el ADM.QB_DATOS_TAXONOMIA.PL_OBTENER_LIBRO_PROCESO
 * @author desierra
 */
public class DatosTaxonomia {
    
    private String DeaCodigo;
    private String AzCodigoCli;

    public DatosTaxonomia() {
    }

    public String getDeaCodigo() {
        return DeaCodigo;
    }

    public void setDeaCodigo(String DeaCodigo) {
        this.DeaCodigo = DeaCodigo;
    }

    public String getAzCodigoCli() {
        return AzCodigoCli;
    }

    public void setAzCodigoCli(String AzCodigoCli) {
        this.AzCodigoCli = AzCodigoCli;
    }

    

    
    
    
}
