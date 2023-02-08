package com.co.activos.jadm0067.Entities;

import java.io.Serializable;

public class UsuarioSesion
        implements Serializable {

    private String ussID;
    private String ussIdSession;
    private String ussIdSessionAs;
    private String ussIP;
    private String usuUsuario;
    private String tdcTdEpl;
    private Long eplNd;
    private String tdcTd;
    private String empNd;
    private String tdcTdFil;
    private String empNdFil;
    private String ussFechaIngreso;
    private String emsId;
    private String esaId;
    private String dsaId;
    private String idUsuario;
    private String empNombreFil;
    private String empNombrePal;
    private String usuCorreo;

    public UsuarioSesion() {
    }

    public UsuarioSesion(String ussID, String ussIdSession, String ussIdSessionAs, String ussIP, String usuUsuario, String tdcTdEpl, Long eplNd, String tdcTd, String empNd, String tdcTdFil, String empNdFil, String ussFechaIngreso, String emsId, String esaId, String dsaId, String idUsuario, String usuCorreo) {
        this.ussID = ussID;
        this.ussIdSession = ussIdSession;
        this.ussIdSessionAs = ussIdSessionAs;
        this.ussIP = ussIP;
        this.usuUsuario = usuUsuario;
        this.tdcTdEpl = tdcTdEpl;
        this.eplNd = eplNd;
        this.tdcTd = tdcTd;
        this.empNd = empNd;
        this.tdcTdFil = tdcTdFil;
        this.empNdFil = empNdFil;
        this.ussFechaIngreso = ussFechaIngreso;
        this.emsId = emsId;
        this.esaId = esaId;
        this.dsaId = dsaId;
        this.idUsuario = idUsuario;
        this.usuCorreo = usuCorreo;
    }

    public String getUssID() {
        return this.ussID;
    }

    public void setUssID(String ussID) {
        this.ussID = ussID;
    }

    public String getUssIdSession() {
        return this.ussIdSession;
    }

    public void setUssIdSession(String ussIdSession) {
        this.ussIdSession = ussIdSession;
    }

    public String getUssIdSessionAs() {
        return this.ussIdSessionAs;
    }

    public void setUssIdSessionAs(String ussIdSessionAs) {
        this.ussIdSessionAs = ussIdSessionAs;
    }

    public String getUssIP() {
        return this.ussIP;
    }

    public void setUssIP(String ussIP) {
        this.ussIP = ussIP;
    }

    public String getUsuUsuario() {
        return this.usuUsuario;
    }

    public void setUsuUsuario(String usuUsuario) {
        this.usuUsuario = usuUsuario;
    }

    public String getTdcTdEpl() {
        return this.tdcTdEpl;
    }

    public void setTdcTdEpl(String tdcTdEpl) {
        this.tdcTdEpl = tdcTdEpl;
    }

    public Long getEplNd() {
        return this.eplNd;
    }

    public void setEplNd(Long eplNd) {
        this.eplNd = eplNd;
    }

    public String getTdcTd() {
        return this.tdcTd;
    }

    public void setTdcTd(String tdcTd) {
        this.tdcTd = tdcTd;
    }

    public String getEmpNd() {
        return this.empNd;
    }

    public void setEmpNd(String empNd) {
        this.empNd = empNd;
    }

    public String getTdcTdFil() {
        return this.tdcTdFil;
    }

    public void setTdcTdFil(String tdcTdFil) {
        this.tdcTdFil = tdcTdFil;
    }

    public String getEmpNdFil() {
        return this.empNdFil;
    }

    public void setEmpNdFil(String empNdFil) {
        this.empNdFil = empNdFil;
    }

    public String getUssFechaIngreso() {
        return this.ussFechaIngreso;
    }

    public void setUssFechaIngreso(String ussFechaIngreso) {
        this.ussFechaIngreso = ussFechaIngreso;
    }

    public String getEmsId() {
        return this.emsId;
    }

    public void setEmsId(String emsId) {
        this.emsId = emsId;
    }

    public String getEsaId() {
        return this.esaId;
    }

    public void setEsaId(String esaId) {
        this.esaId = esaId;
    }

    public String getDsaId() {
        return this.dsaId;
    }

    public void setDsaId(String dsaId) {
        this.dsaId = dsaId;
    }

    public String getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmpNombreFil() {
        return this.empNombreFil;
    }

    public void setEmpNombreFil(String empNombreFil) {
        this.empNombreFil = empNombreFil;
    }

    public String getUsuCorreo() {
        return this.usuCorreo;
    }

    public void setUsuCorreo(String usuCorreo) {
        this.usuCorreo = usuCorreo;
    }

    public String getEmpNombrePal() {
        return this.empNombrePal;
    }

    public void setEmpNombrePal(String empNombrePal) {
        this.empNombrePal = empNombrePal;
    }
}
