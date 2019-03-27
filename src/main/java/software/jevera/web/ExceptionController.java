package software.jevera.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import software.jevera.exceptions.BusinessException;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleCustomException(Exception ex) {
        ModelAndView model = new ModelAndView("error");
        return model;

    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<CustomErrorResponse> customHandle(Exception ex) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }

}
