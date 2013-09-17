package com.demandware.mobile.android.util;

import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

//@NotThreadSafe
public class HttpPatch extends HttpEntityEnclosingRequestBase {

    public final static String METHOD_NAME = "PATCH";

    public HttpPatch() {
        super();
    }

    public HttpPatch(final URI uri) {
        super();
        setURI(uri);
    }

    /**
     * @throws IllegalArgumentException if the uri is invalid.
     */
    public HttpPatch(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }

}
