package fpt.capstone.vuondau.config;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<String> handleUnAuthorizedException(AccessDeniedException e) {
        return ResponseEntity.badRequest().body("403: Access Denied");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleCommonException(Exception e) {
        return ResponseEntity.badRequest().body("Exception occurred, please contact to dev!");
    }
}