package dagimon.spring5course.recipes.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;


@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    //global nfe handling
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class) //for type mismatches within framework prevalidation fail
    public String handleNumberFormatException(Exception ex, Model model) {
        log.error("Handling binding exception");
        log.error("Exception is: " + ex.getMessage());

        model.addAttribute("exception", ex);

        return "400error";
    }
}
