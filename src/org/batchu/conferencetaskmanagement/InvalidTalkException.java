package org.batchu.conferencetaskmanagement;

/**
 * Created by pbatchu on 10/24/2016.
 */
public class InvalidTalkException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidTalkException(String msg) {
        super(msg);
    }
}
