package com.co.activos.jadm0067.Utilities;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

/**
 * Utilidades para obtener o almacenar valores de una sesion
 *
 * @author Francisco Javier Rincon (nValue)
 * @version 1.0
 * @since JDK 1.8
 */
public class SessionUtils {

    public static void setParameterValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }

    public static Object getParameterValue(String key) {
        try {
            Map<String, Object> sessionParameters = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            return sessionParameters.get(key);
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getRemoteAddr() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getRemoteAddr();
    }
    
     public static String getUrlContext() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getScheme()+"://"+ request.getServerName()+":" +request.getLocalPort();
     
    }


}
