package group.orders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("EXCEPTION OCCURRED: " + e.getMessage());
    }
}
