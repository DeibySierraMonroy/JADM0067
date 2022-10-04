package co.com.activos.jadm0065.util.strings;

import java.util.Arrays;

/**
 * Utilidades para el manejo de cadenas de texto
 *
 * @author Francisco Javier Rincon (nvalue)
 * @version 1.0
 * @since JDK 1.8
 */
public class CustomStringUtils {

    public static boolean anyItemIsNullOrEmpty(String... value) {
        return Arrays.stream(value).anyMatch(it -> it == null || it.isEmpty());
    }

}
