package com.co.activos.jadm0067.Entities;

/**
 * Entidad ADM.TIPO_DOCUMENTO_DETALLE, solo se establecen los campos necesarios para la primera version de este modúlo
 *
 * @author Francisco Javier Rincon (nValue)
 * @version 1.0
 * @since JDK 1.8
 */
public class TipoDocumentoDetalleDto {

    private Long id;
    private Integer tpdCodigo;
    private String tddOrigen;
    private Long tddIdOrigen;
    private String tddNombreDocumento;
    private String tddEstado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTpdCodigo() {
        return tpdCodigo;
    }

    public void setTpdCodigo(Integer tpdCodigo) {
        this.tpdCodigo = tpdCodigo;
    }

    public String getTddOrigen() {
        return tddOrigen;
    }

    public void setTddOrigen(String tddOrigen) {
        this.tddOrigen = tddOrigen;
    }

    public Long getTddIdOrigen() {
        return tddIdOrigen;
    }

    public void setTddIdOrigen(Long tddIdOrigen) {
        this.tddIdOrigen = tddIdOrigen;
    }

    public String getTddNombreDocumento() {
        return tddNombreDocumento;
    }

    public void setTddNombreDocumento(String tddNombreDocumento) {
        this.tddNombreDocumento = tddNombreDocumento;
    }

    public String getTddEstado() {
        return tddEstado;
    }

    public void setTddEstado(String tddEstado) {
        this.tddEstado = tddEstado;
    }
}
