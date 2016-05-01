package com.task.rheinfabrik.traktapp.network;

/**
 * This exception is used when problems occur during a request to the trakt.tv server.
 */
public class TraktException extends Exception {

    /**
     * Constructs a TraktException.
     *
     * @param detailMessage This message shall contain more information on the problem that occured.
     */
    public TraktException(String detailMessage) {
        super(detailMessage);
    }
}
