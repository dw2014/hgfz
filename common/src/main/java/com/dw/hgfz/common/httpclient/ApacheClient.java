package com.dw.hgfz.common.httpclient;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;

/**
 * Created by dw on 9/7/2015.
 */
public class ApacheClient {

    private static final MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
    private static final HttpClient client = new HttpClient(connectionManager);

    private ApacheClient() {

    }

    public static String executeMethod(HttpMethodBase method) throws Exception {
        try {
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
            int responseCode = client.executeMethod(method);
            assert responseCode == 200;
            return IOUtils.toString(method.getResponseBodyAsStream(), "UTF-8");
        } finally {
            method.releaseConnection();
        }
    }

    public static String executeGet(String uri) throws Exception {
        GetMethod method = new GetMethod(uri);
        return executeMethod(method);
    }
}
