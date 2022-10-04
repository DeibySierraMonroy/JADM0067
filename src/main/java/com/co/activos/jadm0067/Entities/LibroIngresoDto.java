package com.co.activos.jadm0067.Entities;

/**
 * Entidad RHU.LIBROINGRESO, solo se establecen los campos necesarios para la primera version de este modúlo
 *
 * @author Francisco Javier Rincon (nValue)
 * @version 1.0
 * @since JDK 1.8
 */
public class LibroIngresoDto {

    private Long libConsecutivo;
    private String tdcTdEpl;
    private Integer eplNd;
    private String empleadoNombre;
    private String tdcTdPpal;
    private Integer empNdPpal;
    private String principalNombre;
    private String tdcTdFil;
    private Integer empNdFil;
    private String usuariaNombre;
    private String cnoCodigo;
    private String cargoNombre;
    private String libEstado;
    private Integer requisicion;
    private Integer ctoNumero;

    public Long getLibConsecutivo() {
        return libConsecutivo;
    }

    public void setLibConsecutivo(Long libConsecutivo) {
        this.libConsecutivo = libConsecutivo;
    }

    public String getTdcTdEpl() {
        return tdcTdEpl;
    }

    public void setTdcTdEpl(String tdcTdEpl) {
        this.tdcTdEpl = tdcTdEpl;
    }

    public Integer getEplNd() {
        return eplNd;
    }

    public void setEplNd(Integer eplNd) {
        this.eplNd = eplNd;
    }

    public String getEmpleadoNombre() {
        return empleadoNombre;
    }

    public void setEmpleadoNombre(String empleadoNombre) {
        this.empleadoNombre = empleadoNombre;
    }

    public String getTdcTdPpal() {
        return tdcTdPpal;
    }

    public void setTdcTdPpal(String tdcTdPpal) {
        this.tdcTdPpal = tdcTdPpal;
    }

    public Integer getEmpNdPpal() {
        return empNdPpal;
    }

    public void setEmpNdPpal(Integer empNdPpal) {
        this.empNdPpal = empNdPpal;
    }

    public String getPrincipalNombre() {
        return principalNombre;
    }

    public void setPrincipalNombre(String principalNombre) {
        this.principalNombre = principalNombre;
    }

    public String getTdcTdFil() {
        return tdcTdFil;
    }

    public void setTdcTdFil(String tdcTdFil) {
        this.tdcTdFil = tdcTdFil;
    }

    public Integer getEmpNdFil() {
        return empNdFil;
    }

    public void setEmpNdFil(Integer empNdFil) {
        this.empNdFil = empNdFil;
    }

    public String getUsuariaNombre() {
        return usuariaNombre;
    }

    public void setUsuariaNombre(String usuariaNombre) {
        this.usuariaNombre = usuariaNombre;
    }

    public String getCnoCodigo() {
        return cnoCodigo;
    }

    public void setCnoCodigo(String cnoCodigo) {
        this.cnoCodigo = cnoCodigo;
    }

    public String getCargoNombre() {
        return cargoNombre;
    }

    public void setCargoNombre(String cargoNombre) {
        this.cargoNombre = cargoNombre;
    }

    public String getLibEstado() {
        return libEstado;
    }

    public void setLibEstado(String libEstado) {
        this.libEstado = libEstado;
    }

    public Integer getRequisicion() {
        return requisicion;
    }

    public void setRequisicion(Integer requisicion) {
        this.requisicion = requisicion;
    }

    public Integer getCtoNumero() {
        return ctoNumero;
    }

    public void setCtoNumero(Integer ctoNumero) {
        this.ctoNumero = ctoNumero;
    }
}
