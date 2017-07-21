package core.exceptions;

/**
 * Created by ssc on 2017/7/20 0020.
 */
public class GoodsNotExistException extends BusinessException {
    private String msg;

    public GoodsNotExistException() {
    }

    public GoodsNotExistException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public GoodsNotExistException(String msg,Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }

    public GoodsNotExistException(String msg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(msg, cause, enableSuppression, writableStackTrace);
        this.msg = msg;
    }
}
