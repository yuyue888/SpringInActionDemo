package core.support;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SimpleException extends RuntimeException{
    private ResponseEntity<ExceptionDefinition> responseEntity;

    public SimpleException(String code, String message, HttpStatus status) {
        this(code, message, (HttpStatus)status, (Throwable)null);
    }

    public SimpleException(String code, String message, HttpStatus status, Throwable cause) {
        this(new ExceptionDefinition(status.value(),code, message ,ExceptionType.BUSINESS), status, cause);
    }

    public SimpleException(ExceptionDefinition errorMessage, HttpStatus status, Throwable cause) {
        this(new ResponseEntity<>(errorMessage, status), cause);
    }

    public SimpleException(ResponseEntity<ExceptionDefinition> responseEntity, Throwable cause) {
        super((responseEntity.getBody()).getMessage(), cause);
        this.responseEntity = responseEntity;
    }
    public SimpleException(ResponseEntity<ExceptionDefinition> entity){
        this(entity,null);
    }
    public ResponseEntity<ExceptionDefinition> getResponseEntity() {
        return this.responseEntity;
    }


}
