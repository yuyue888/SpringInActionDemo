package core.support;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ssc on 2017/6/18 0018.
 */
public class ExceptionResponse {
    private final String code;

    private final String message;

    private final ExceptionType type;

    private final String hostId;

    private final String requestId;

    private final String serverTime;

    public ExceptionResponse(String code, String message, String hostId, String requestId, ExceptionType type) {
        super();
        this.code = code;
        this.message = message;
        this.type = type;
        this.hostId = hostId;
        this.requestId = requestId;
        this.serverTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(new Date());
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

    public String getHostId() {
        return hostId;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getServerTime() {
        return serverTime;
    }
}
