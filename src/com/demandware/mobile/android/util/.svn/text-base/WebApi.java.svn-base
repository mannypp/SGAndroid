package com.demandware.mobile.android.util;

import java.io.IOException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.StringTokenizer;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;

import com.demandware.mobile.android.model.Category;
import com.demandware.mobile.android.model.Product;

/**
 * This class represents the open commerce apis.
 * 
 * @author mannyp
 */
public class WebApi {
	private static WebApi sInstance;
	
	protected String sBaseUri = "://demo.ocapi.demandware.net/s/Sites-SiteGenesis-Site/dw/shop/v12_2/";
	protected String sBaseDWUrl = "http" + sBaseUri;
	protected String sSecureBaseDWUrl = "https" + sBaseUri;
	protected String sClientId = "client_id=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	
	protected String sSelectParam = "select=";
	protected String sCategorySelectParam = sSelectParam + "(" + Category.SELECT_PARAMS + ")";
	protected String sProductSelectParam = sSelectParam + "(" + Product.SELECT_PARAMS + ")";
	protected String sProductSearchSelectParam = sSelectParam + "(" + Product.SEARCH_SELECT_PARAMS + ")";
	
	protected final String ETAG = "ETag";
	protected final String COOKIE = "Cookie";
	protected final String SET_COOKIE = "Set-Cookie";
	
	private String sETag = null;
	private String sSessionId = null;
	
	public static WebApi getInstance() {
		if (sInstance == null)
			sInstance = new WebApi();
		return sInstance;
	}
	
	WebApi() {
	}
	
	// http://demo.ocapi.demandware.net/s/Demos-SiteGenesis-Site/dw/shop/v1/categories/root?
	//     client_id=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa&levels=1&select=(id,name,image,c__showInMenu,categories:(id,name,image),c__showInMenu)
	public String getCategoriesUrl(String pCategoryId, String... pParams) {
		return encodeURLParams(sBaseDWUrl + "categories/" + pCategoryId + "?" + sClientId + getParamString(pParams) + "&" + sCategorySelectParam);
	}
	
	public String getProductUrl(String pProductId, String... pParams) {
		return encodeURLParams(sBaseDWUrl + "products/" + pProductId + "?" + sClientId + getParamString(pParams) + "&" + sProductSelectParam);
	}
	
	public String getProductSearchUrl(String pQuery, String... pParams) {
		return encodeURLParams(sBaseDWUrl + "product_search" + "?" + sClientId + "&q=" + pQuery + getParamString(pParams) + "&" + sProductSearchSelectParam);
	}
	
	public String getAddToCartUrl(String... pParams) {
		return encodeURLParams(sBaseDWUrl + "basket/this/!add" + "?" + sClientId + getParamString(pParams));
	}
	
	public String getCartContentsUrl(String... pParams) {
		return encodeURLParams(sBaseDWUrl + "basket/this" + "?" + sClientId + getParamString(pParams));
	}
	
	public String getCartUpdateUrl(String... pParams) {
		return encodeURLParams(sBaseDWUrl + "basket/this" + "?" + sClientId + getParamString(pParams));
	}
	
	public String getProfileUrl(String... pParams) {
		return encodeURLParams(sSecureBaseDWUrl + "account/this" + "?" + sClientId + getParamString(pParams));
	}
	
	public String getLoginUrl(String... pParams) {
		return encodeURLParams(sSecureBaseDWUrl + "account/this/!login" + "?" + sClientId + getParamString(pParams));
	}
	
	public String getLogoutUrl(String... pParams) {
		return encodeURLParams(sSecureBaseDWUrl + "account/this/!logout" + "?" + sClientId + getParamString(pParams));
	}

	public String getParamString(String... pParams) {
		if (pParams == null || pParams.length == 0)
			return "";
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < pParams.length; i++) {
			buf.append("&");
			buf.append(pParams[i]);
		}
		return buf.toString();
	}
	
	public String encodeURLParams(String url) {
		int index = url.indexOf('?');
		if (index == -1)
			return url;
		StringBuffer buf = new StringBuffer(url.substring(0, index));
		buf.append("?");
		StringTokenizer st = new StringTokenizer(url.substring(index + 1), "&");
		while (st.hasMoreTokens()) {
			String tok = st.nextToken();
			index = tok.indexOf('=');
			if (index > -1)
				buf.append(tok.substring(0, index));
			else
				buf.append(tok);
			buf.append("=");
			if (index > -1)
				buf.append(URLEncoder.encode(tok.substring(index + 1)));
			if (st.hasMoreTokens())
				buf.append("&");
		}
		return buf.toString();
	}
	
	public HttpClient createHttpClient() {
		SSLSocketFactory sf = null;
		
		try {
	        KeyStore trusted = KeyStore.getInstance(KeyStore.getDefaultType());
	        trusted.load(null, null);
	        
	        sf = new CertificateBypassSSLSocketFactory(trusted);
	        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	    }
	    catch (Exception e) {
	    	sf = null;
	    }
	    
        HttpParams params = new BasicHttpParams();
    	HttpConnectionParams.setConnectionTimeout(params, 10000);
    	HttpConnectionParams.setSoTimeout(params, 15000);
    	HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
    	HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
    	
    	SchemeRegistry schemeRegistry = new SchemeRegistry();
    	schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    	schemeRegistry.register(new Scheme("https", sf, 443)); //SSLSocketFactory.getSocketFactory()

    	ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
    	
    	HttpClient client = new DefaultHttpClient(cm, params);
		return client;
	}
	
	public String executeHttpGet(String url) throws ClientProtocolException, URISyntaxException, IOException {
    	Log.d("WebAPI", url);
    	
    	HttpGet get = new HttpGet(url);

    	if (sSessionId != null)
    		get.setHeader(COOKIE, sSessionId);
    	if (sETag != null)
    		get.setHeader(ETAG, sETag);
    		
    	HttpClient client = createHttpClient();
    	HttpContext context = new BasicHttpContext();

    	HttpResponse response = client.execute(get, context);
    	String responseString = readResponse(response);
    	
        return responseString;
    }

    public String executeHttpPost(String url, JSONObject json) throws ClientProtocolException, IOException {
    	String body = null;
    	if (json != null)
    		body = json.toString();
    	Log.d("WebAPI", url);
    	Log.d("WebAPI-JSON", body == null ? "null" : body);

    	HttpPost post = new HttpPost(url);
    	return executeHttpPostPatch(post, url, body);
    }    
    
    public String executeHttpPatch(String url, JSONObject json) throws ClientProtocolException, IOException {
    	String body = null;
    	if (json != null)
    		body = json.toString();
    	Log.d("WebAPI", url);
    	Log.d("WebAPI-JSON", body == null ? "null" : body);

    	HttpPatch patch = new HttpPatch(url);
    	return executeHttpPostPatch(patch, url, body);
    }
    
    private String executeHttpPostPatch(HttpEntityEnclosingRequestBase requestBase, String url, String jsonString)
    		throws ClientProtocolException, IOException
    {
    	if (sETag != null
    			&& (url.startsWith(getCartUpdateUrl()) || url.startsWith(getProfileUrl())))
    		requestBase.setHeader("If-Match", sETag);
    	
    	if (sSessionId != null)
    		requestBase.setHeader(COOKIE, sSessionId);
    	if (sETag != null)
    		requestBase.setHeader(ETAG, sETag);
    	
    	if (jsonString != null) {
	    	StringEntity entity = new StringEntity(jsonString);
	    	entity.setContentType("application/json");
	    	requestBase.setEntity(entity);
    	}
    	
    	HttpClient client = createHttpClient();
    	HttpResponse response = client.execute(requestBase);
    	return readResponse(response);    	
    }
    
    private String getHeader(String pName, HttpResponse pResponse) {
    	Header[] headers = pResponse.getHeaders(pName);
    	if (headers == null || headers.length == 0)
    		headers = pResponse.getHeaders(pName.toLowerCase());
    	if (headers == null || headers.length == 0)
    		headers = pResponse.getHeaders(pName.toUpperCase());
    	
    	if (headers != null && headers.length > 0)
    		return headers[0].getValue();
    	return null;
    }
    
    private String readResponse(HttpResponse pResponse) throws IOException {
    	if (pResponse == null)
    		return null;
    	
    	Header[] headers = pResponse.getAllHeaders();
    	Log.d("HEADERS", "HEADERS");
    	for (Header header : headers)
    		Log.d(header.getName(), header.getValue());

    	readAndStoreSessionCookies(pResponse);
    	
    	String etag = getHeader(ETAG, pResponse);
    	if (etag != null)
    		sETag = etag;
    	
    	HttpEntity entity = pResponse.getEntity();
    	if (entity == null || entity.getContentLength() == -1)
    		return null;
    	
    	String response = EntityUtils.toString(entity);
		Log.d("Response String", response);

    	return response;
    }
    
    private void readAndStoreSessionCookies(HttpResponse pResponse) {
		Header[] headers = pResponse.getHeaders(SET_COOKIE);
		if (headers == null || headers.length == 0)
			headers = pResponse.getHeaders(SET_COOKIE.toLowerCase());
		if (headers == null || headers.length == 0)
			headers = pResponse.getHeaders(SET_COOKIE.toUpperCase());
		if (headers == null || headers.length == 0)
			return;
    	
		StringBuffer buf = new StringBuffer();
    	for (Header header : headers) {
    		StringTokenizer st = new StringTokenizer(header.getValue(), ";");
    		while (st.hasMoreTokens()) {
    			String tok = st.nextToken();
    			if (tok.startsWith("sid") || tok.startsWith("dwsid")) {
        			int index = tok.indexOf('=');
        			if (index == -1)
        				continue;
        			String key = tok.substring(0, index);
        			String value = tok.substring(index + 1);
        			if (value.startsWith("\""))
        				value = value.substring(1);
        			if (value.endsWith("\""))
        				value = value.substring(0, value.length() - 1);
        			if (buf.length() != 0)
        				buf.append("; ");
        			buf.append(key).append('=').append(value);
    			}
    			else if (tok.startsWith("dwanonymous_")
    					|| tok.startsWith("dwcustomer_")
    					|| tok.startsWith("dwsecuretoken_"))
    			{
        			if (buf.length() != 0)
        				buf.append("; ");
        			buf.append(tok);    				
    			}
    		}
    	}
    	sSessionId = buf.toString();
    }

    public class CertificateBypassSSLSocketFactory extends SSLSocketFactory {
        SSLContext sslContext = SSLContext.getInstance("TLS");

        public CertificateBypassSSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException,
                        KeyStoreException, UnrecoverableKeyException
        {
            super(truststore);

            TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
                public X509Certificate[] getAcceptedIssuers() {
                        return null;
                }
            };

            sslContext.init(null, new TrustManager[] { tm }, null);
        }

        @Override
        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
        	return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        @Override
        public Socket createSocket() throws IOException {
        	return sslContext.getSocketFactory().createSocket();
        }
    }    
}
