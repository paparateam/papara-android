package com.papara.sdk.utils;

/**
 * An Exception indicating that the Papara SDK has not been correctly initialized.
 */
public class PaparaSdkNotInitializedException extends PaparaException {

    static final long serialVersionUID = 1;

    /**
     * Constructs a PaparaSdkNotInitializedException with no additional information.
     */
    public PaparaSdkNotInitializedException() {
        super();
    }

    /**
     * Constructs a PaparaSdkNotInitializedException with a message.
     *
     * @param message A String to be returned from getMessage.
     */
    public PaparaSdkNotInitializedException(String message) {
        super(message);
    }

    /**
     * Constructs a PaparaSdkNotInitializedException with a message and inner error.
     *
     * @param message   A String to be returned from getMessage.
     * @param throwable A Throwable to be returned from getCause.
     */
    public PaparaSdkNotInitializedException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a PaparaSdkNotInitializedException with an inner error.
     *
     * @param throwable A Throwable to be returned from getCause.
     */
    public PaparaSdkNotInitializedException(Throwable throwable) {
        super(throwable);
    }
}
