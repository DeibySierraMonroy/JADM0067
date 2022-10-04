package co.com.activos.jadm0065.util.primefaces.dialogs;

import org.primefaces.PrimeFaces;

import javax.faces.context.FacesContext;

/**
 * Programas encargados de crear y lanzar cuadros de diálogo de Primefaces desde un controlador
 *
 * @author Francisco Javier Rincon Alarcon
 * @version 1.0
 * @since JDK11
 */
public class DialogServices {

    private static final String FADE_EFFECT = "fade";

    /**
     * Crea un cuadro de diálogo con los valores de título y mensaje enviados como parámetro y lo lanza desde el
     * controlador
     *
     * @param title   Título del cuadro de diálogo
     * @param message Mensaje del cuadro de diálogo
     */
    public static void showDefaultDialog(String title, String message) {
        try {
            DialogModel dialog = new DialogModel(title, message, FADE_EFFECT, Boolean.TRUE, Boolean.FALSE);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dialog", dialog);
            PrimeFaces.current().executeScript("PF('genericDialog').show()");
            PrimeFaces.current().ajax().update("modalDialog");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
