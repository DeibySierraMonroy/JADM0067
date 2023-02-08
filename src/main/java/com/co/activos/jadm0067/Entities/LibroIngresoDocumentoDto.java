package com.co.activos.jadm0067.Entities;

/**
 * Entidad RHU.LIBROINGRESO_DOCUMENTO, solo se establecen los campos necesarios para la primera version de este modúlo
 *
 * @author Francisco Javier Rincon (nValue)
 * @version 1.0
 * @since JDK 1.8
 */
public class LibroIngresoDocumentoDto {

    private static final String SIN_VALIDAR = "SIN_VALIDAR";
    private static final String ACEPTADO = "ACEPTADO";
    private static final String RECHAZADO = "RECHAZADO";
    private static final String ZERO = "0";
    private static final String UNO = "1";
    private static final String DOS = "2";

    private Long id;
    private Long idPadre;
    private DataErpAzDto dataErpAz;
    private TipoDocumentoDetalleDto tipoDocumentoDetalle;
    private String nombreDocumento;
    private String ldoEstado;
    private String ldoTemporalEstado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Long idPadre) {
        this.idPadre = idPadre;
    }

    public DataErpAzDto getDataErpAz() {
        return dataErpAz;
    }

    public void setDataErpAz(DataErpAzDto dataErpAz) {
        this.dataErpAz = dataErpAz;
    }

    public TipoDocumentoDetalleDto getTipoDocumentoDetalle() {
        return tipoDocumentoDetalle;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public void setTipoDocumentoDetalle(TipoDocumentoDetalleDto tipoDocumentoDetalle) {
        this.tipoDocumentoDetalle = tipoDocumentoDetalle;
    }

    public String getLdoEstado() {
        return ldoEstado;
    }

    public void setLdoEstado(String ldoEstado) {
        this.ldoEstado = ldoEstado;
    }

    public String getLdoTemporalEstado() {
        return ldoTemporalEstado;
    }

    public void setLdoTemporalEstado(String ldoTemporalEstado) {
        this.ldoTemporalEstado = ldoTemporalEstado;
    }

    public String getLdoEstadoNumerico(String estado) {
        if (estado.equals(SIN_VALIDAR)) {
            return ZERO;
        }
        if (estado.equals(ACEPTADO)) {
            return UNO;
        }
        if (estado.equals(RECHAZADO)) {
            return DOS;
        }
        return null;
    }

    public String getLdoEstadoAlfanumerico(String estado) {
        if (estado.equals(ZERO)) {
            return SIN_VALIDAR;
        }
        if (estado.equals(UNO)) {
            return ACEPTADO;
        }
        if (estado.equals(DOS)) {
            return RECHAZADO;
        }
        return null;
    }

}
