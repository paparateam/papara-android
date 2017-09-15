
package com.mobillium.paparasdk.sdk.sampleapp.webservice;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 1L;

    public ServiceException() {
        super();
    }

    public ServiceException(String detailMessage) {
        super(detailMessage);
    }


}
