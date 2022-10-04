package com.co.activos.jadm0067.Entities;



import com.co.activos.jadm0067.Enum.DatabaseResultStatus;
import java.util.List;
import java.util.Map;

/**
 * Utilidad genérica para mapear la respuesta que retorna la BD desde los procedimientos de almacenado, puede ser
 * utilizada para mapear tanto listas como un solo objeto
 *
 * @param <T> Parámetro que le indica a la clase que puede contener tipos genéricos
 * @author Francisco Javier Rincon Alarcon
 * @version 1.0
 * @since JDK 1.8
 */
public class DatabaseResultDto<T> {

    private DatabaseResultStatus status;
    private String message;
    private T singleResult;
    private List<T> listResult;
    private Long resultLong;
    private Map<String, ?> listValues;

    public DatabaseResultDto(DatabaseResultStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public DatabaseResultDto(DatabaseResultStatus status, String message, T singleResult) {
        this.status = status;
        this.message = message;
        this.singleResult = singleResult;
    }

    public DatabaseResultDto(DatabaseResultStatus status, String message, Long resultLong) {
        this.status = status;
        this.message = message;
        this.resultLong = resultLong;
    }
    

    public DatabaseResultDto(DatabaseResultStatus status, String message, List<T> listResult) {
        this.status = status;
        this.message = message;
        this.listResult = listResult;
    }

    public DatabaseResultDto(DatabaseResultStatus status, Map<String, ?> listValues) {
        this.status = status;
        this.listValues = listValues;
    }

    public DatabaseResultDto(DatabaseResultStatus status, String message, Map<String, ?> listValues) {
        this.status = status;
        this.message = message;
        this.listValues = listValues;
    }

    public DatabaseResultStatus getStatus() {
        return status;
    }

    public void setStatus(DatabaseResultStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getSingleResult() {
        return singleResult;
    }

    public void setSingleResult(T singleResult) {
        this.singleResult = singleResult;
    }

    public List<T> getListResult() {
        return listResult;
    }

    public void setListResult(List<T> listResult) {
        this.listResult = listResult;
    }

    public Map<String, ?> getListValues() {
        return listValues;
    }

    public void setListValues(Map<String, ?> listValues) {
        this.listValues = listValues;
    }

    public Long getResultLong() {
        return resultLong;
    }

    public void setResultLong(Long resultLong) {
        this.resultLong = resultLong;
    }

    
}
