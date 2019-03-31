package software.jevera.web;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import software.jevera.exceptions.UncorrectGrant;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handler(Exception e) {
        ModelAndView modelAndView = new ModelAndView("error");
        return modelAndView;
    }

    @ExceptionHandler(UncorrectGrant.class)
    public ResponseEntity<CustomErrorResponse> exceptionHandler() {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse();
        customErrorResponse.setMessage("Unauthorized");
        return new ResponseEntity<CustomErrorResponse>(customErrorResponse, UNAUTHORIZED);
    }

}
