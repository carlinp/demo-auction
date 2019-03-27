package software.jevera.web;

import lombok.Data;

@Data
public class CustomErrorResponse {
    private int status;
    private String error;
}
