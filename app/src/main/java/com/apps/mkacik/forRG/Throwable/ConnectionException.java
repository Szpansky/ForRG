package com.apps.mkacik.forRG.Throwable;

public class ConnectionException extends Throwable {
    public static final String DOWNLOAD_DATA_ERROR = "********************************************************** Can't download Data, caused in: ";

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException() {
        super();
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionException(Throwable cause) {
        super(cause);
    }
}
