package co.edu.unbosque.software_electroadonai.model;

import java.util.List;

public class RecaptchaDto {

    private boolean success;
    private List<String> errors;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
