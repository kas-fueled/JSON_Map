package com.fueled.volleyadditions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JacksonRequest<T> extends Request<T> {

	public JacksonRequest(int method, Class<T> clazz,Map<String, String> headers, String url, Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.clazz = clazz;
		this.headers = headers;
		this.listener =  listener;
	}

	private final Class<T> clazz;
	private final Map<String, String> headers;
	private final Listener<T> listener;	
	
	@Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }
 
    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
 
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {                   
            return Response.success( SingletonObjectMapper.INSTANCE.getDefaultMapper().readValue(response.data, clazz) , HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonMappingException e) {
        	return Response.error(new ParseError(e));
		} catch (IOException e) {
			return Response.error(new ParseError(e));
		}
    }
}
