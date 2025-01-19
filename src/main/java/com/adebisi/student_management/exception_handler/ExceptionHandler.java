package com.adebisi.student_management.exception_handler;




import com.adebisi.student_management.dto.response.GeneralResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@Component
public class ExceptionHandler {



    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleDTOFieldException(MethodArgumentNotValidException e){
        e.printStackTrace();
        Map<String, String> errorObject = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(err-> errorObject.put(err.getField(), err.getDefaultMessage()));

        return new ResponseEntity<>(

                GeneralResponseDTO.builder().message("Invalid request body").success(false).data(null).errors(errorObject).build()

                ,HttpStatus.BAD_REQUEST );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        ex.printStackTrace();

        return  new ResponseEntity<>(

                GeneralResponseDTO.builder().message(Errors.INVALID_ENDPOINT.getErrorMessage()).success(false).data(null).errors(ex.getRequestURL()).build()

                ,HttpStatus.BAD_REQUEST );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMissingRequestParameter( MissingServletRequestParameterException ex) {
        ex.printStackTrace();
        String message = ex.getParameterName() + " parameter is missing";


        return new ResponseEntity<>(  GeneralResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .message(ex.getMessage())
                .success(false)
                .data(null)
                .errors(message)
                .build(), HttpStatus.BAD_REQUEST);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolation( ConstraintViolationException ex) {
        ex.printStackTrace();
        HashMap<String, Object> errorObject = new HashMap<>();

           ex.getConstraintViolations().forEach(
                        (e)-> errorObject.put(e.getPropertyPath().toString(),e.getMessage() )
                );


        return new ResponseEntity<>(  GeneralResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .message(ex.getMessage())
                .success(false)
                .data(null)
                .errors(errorObject)
                .build(), HttpStatus.BAD_REQUEST);
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ex.printStackTrace();
        String name = ex.getName();
        String type = Objects.requireNonNull(ex.getRequiredType()). getSimpleName();
        Object value = ex.getValue();
        String errorMessage = String.format("'%s' should be of type %s", name, type);


        return new ResponseEntity<>(  GeneralResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .message(ex.getMessage())
                .success(false)
                .data(null)
                .errors(errorMessage)
                .build(), HttpStatus.BAD_REQUEST);
    }






    @org.springframework.web.bind.annotation.ExceptionHandler(GeneralException.class)
    public ResponseEntity<Object> handleGeneralException(GeneralException e){
        e.printStackTrace();
        return new ResponseEntity<>(

                GeneralResponseDTO.builder().message(e.getMessage())
                        .status(e.getStatus())
                        .success(false).data(null).errors(e.getData()).build()

                ,e.getHttpStatus() );
    }



    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> AccessDeniedHandler(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(

                GeneralResponseDTO.builder().message(e.getMessage())
                        .status(HttpStatus.UNAUTHORIZED.toString())
                        .success(false).data(null).errors(null).build()

                ,HttpStatus.UNAUTHORIZED );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> UserNotFound(UsernameNotFoundException e){
        e.printStackTrace();
        return new ResponseEntity<>(

                GeneralResponseDTO.builder().message(e.getMessage())
                        .status(HttpStatus.NOT_FOUND.toString())
                        .success(false).data(null).errors(null).build()

                ,HttpStatus.NOT_FOUND );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> expiredTokenFound(ExpiredJwtException e){
        e.printStackTrace();
        return new ResponseEntity<>(

                GeneralResponseDTO.builder().message("Expired jwt token")
                        .status(HttpStatus.UNAUTHORIZED.toString())
                        .success(false).data(null).errors(null).build()

                ,HttpStatus.UNAUTHORIZED );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e){
        e.printStackTrace();
        return new ResponseEntity<>(

                GeneralResponseDTO.builder().message(e.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .success(false).data(null).errors(null).build()

                ,HttpStatus.INTERNAL_SERVER_ERROR );
    }




}
