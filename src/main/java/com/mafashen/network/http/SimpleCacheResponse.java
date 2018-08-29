package com.mafashen.network.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CacheResponse;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SimpleCacheResponse extends CacheResponse {

	private Map<String , List<String>> headers = null;
	private InputStream inputStream = null;

	public SimpleCacheResponse(URLConnection conn){
		if (conn != null){
			headers = Collections.unmodifiableMap(conn.getHeaderFields());
			try (InputStream is  = conn.getInputStream()) {
				this.inputStream = is;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Map<String, List<String>> getHeaders() throws IOException {
		return headers;
	}

	@Override
	public InputStream getBody() throws IOException {
		return new BufferedInputStream(inputStream);
	}
}
