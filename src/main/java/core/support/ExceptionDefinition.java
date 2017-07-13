package core.support;

public class ExceptionDefinition {

    private final int httpStatus;

    private final String code;

    private final String message;

    private final ExceptionType type;

    public ExceptionDefinition(int httpStatus, String code, String message, ExceptionType type) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.type = type;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ExceptionType getType() {
        return type;
    }
}
