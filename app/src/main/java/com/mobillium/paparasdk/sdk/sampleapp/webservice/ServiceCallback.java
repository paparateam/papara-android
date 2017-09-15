
package com.mobillium.paparasdk.sdk.sampleapp.webservice;


public interface ServiceCallback<T> {
    void success(T result);

    void error(ServiceException e);
}
