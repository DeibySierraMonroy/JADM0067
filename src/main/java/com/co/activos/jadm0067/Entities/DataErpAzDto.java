package com.co.activos.jadm0067.Entities;

/**
 * Entidad ADM.DATA_ERP_AZ, solo se establecen los campos necesarios para la primera version de este modúlo
 *
 * @author Francisco Javier Rincon (nValue)
 * @version 1.0
 * @since JDK 1.8
 */
public class DataErpAzDto {

    private Long id;
    private String deaEstado;
    private String deaPeritaje;
    private Long txpCodigo;
    private Long tipFlujo;
    private String txpDescripcion;
    private String nombreTabla;
    private Long txpCodigoRef;
    private Long azdCodigo;
    private String azdCodigoCli;
    private String azdNombreRuta;
    private String azdTipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeaEstado() {
        return deaEstado;
    }

    public void setDeaEstado(String deaEstado) {
        this.deaEstado = deaEstado;
    }

    public String getDeaPeritaje() {
        return deaPeritaje;
    }

    public void setDeaPeritaje(String deaPeritaje) {
        this.deaPeritaje = deaPeritaje;
    }

    public Long getTxpCodigo() {
        return txpCodigo;
    }

    public void setTxpCodigo(Long txpCodigo) {
        this.txpCodigo = txpCodigo;
    }

    public Long getTipFlujo() {
        return tipFlujo;
    }

    public void setTipFlujo(Long tipFlujo) {
        this.tipFlujo = tipFlujo;
    }

    public String getTxpDescripcion() {
        return txpDescripcion;
    }

    public void setTxpDescripcion(String txpDescripcion) {
        this.txpDescripcion = txpDescripcion;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public Long getTxpCodigoRef() {
        return txpCodigoRef;
    }

    public void setTxpCodigoRef(Long txpCodigoRef) {
        this.txpCodigoRef = txpCodigoRef;
    }

    public Long getAzdCodigo() {
        return azdCodigo;
    }

    public void setAzdCodigo(Long azdCodigo) {
        this.azdCodigo = azdCodigo;
    }

    public String getAzdCodigoCli() {
        return azdCodigoCli;
    }

    public void setAzdCodigoCli(String azdCodigoCli) {
        this.azdCodigoCli = azdCodigoCli;
    }

    public String getAzdNombreRuta() {
        return azdNombreRuta;
    }

    public void setAzdNombreRuta(String azdNombreRuta) {
        this.azdNombreRuta = azdNombreRuta;
    }

    public String getAzdTipo() {
        return azdTipo;
    }

    public void setAzdTipo(String azdTipo) {
        this.azdTipo = azdTipo;
    }
}
