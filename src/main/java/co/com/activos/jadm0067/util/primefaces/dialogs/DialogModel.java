package co.com.activos.jadm0067.util.primefaces.dialogs;

import java.io.Serializable;

/**
 * Modelo que almacena los valores genéricos de un Dialog de primefaces creado desde el controlador
 *
 * @author Francisco Javier Rincon Alarcon
 * @version 1.0
 * @since JDK11
 */
public class DialogModel implements Serializable {

    private String title;
    private String text;
    private String effect;
    private boolean modal;
    private boolean usaI18N;

    public DialogModel(String title, String text, String effect, boolean modal, boolean usaI18N) {
        this.text = text;
        this.title = title;
        this.modal = modal;
        this.effect = effect;
        this.usaI18N = usaI18N;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public boolean isModal() {
        return modal;
    }

    public void setModal(boolean modal) {
        this.modal = modal;
    }

    public boolean isUsaI18N() {
        return usaI18N;
    }

    public void setUsaI18N(boolean usaI18N) {
        this.usaI18N = usaI18N;
    }

}
