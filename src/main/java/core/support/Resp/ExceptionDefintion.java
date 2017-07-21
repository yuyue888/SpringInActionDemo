package core.support.Resp;

/**
 * 异常定义
 * Created by ssc on 2017/7/21 0021.
 */
public class ExceptionDefintion {
    private int status;

    private String message;

    private String detail;

    private ExceptionType type;

    public ExceptionDefintion() {}

    public ExceptionDefintion(int status, String message, String detail, ExceptionType type) {
        this.status = status;
        this.message = message;
        this.detail = detail;
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public ExceptionType getType() {
        return type;
    }

    public void setType(ExceptionType type) {
        this.type = type;
    }
}
