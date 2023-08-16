package com.colruyt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class DemandExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> CreateDemandRequestValidation(MethodArgumentNotValidException e){
        Map<String,String> checkMap=new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(i->checkMap.put(i.getField(),i.getDefaultMessage()));
        return checkMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RefCommNoException.class)
    public ResponseEntity<String> ValidateCreateException(RefCommNoException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TechnoloyMandatoryRoleSE.class)
    public ResponseEntity<String> ValidatePostException(TechnoloyMandatoryRoleSE e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UpdateException.class)
    public ResponseEntity<String> ValidateUpdateOfferedException(UpdateException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<String> DemandByIdException(IdNotFoundException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

}
