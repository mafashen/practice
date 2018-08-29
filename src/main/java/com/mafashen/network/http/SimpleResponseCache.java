package com.mafashen.network.http;

import java.io.IOException;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.MalformedURLException;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleResponseCache extends ResponseCache {
	private Map<URI , CacheResponse> cacheResponses = null;

	public SimpleResponseCache(){
		cacheResponses = new ConcurrentHashMap<>();
	}

	@Override
	public CacheResponse get(URI uri, String rqstMethod, Map<String, List<String>> rqstHeaders) throws IOException {
		if ("GET".equals(rqstMethod)){
			CacheResponse cacheResponse = cacheResponses.get(uri);
			if (cacheResponse != null){
				return cacheResponse;
			}
		}
		return null;
	}

	@Override
	public CacheRequest put(URI uri, URLConnection conn) throws IOException {
		CacheControl cacheControl = new CacheControl(conn.getHeaderField("cache-control"));
		if (cacheControl.isNoStore()){
			return null;
		}
		cacheResponses.put(uri, new SimpleCacheResponse(conn));
		CacheRequest cacheRequest = new SimpleCacheRequest(conn);
		return cacheRequest;
	}

	public static void main(String[] args) {
		ResponseCache.setDefault(new SimpleResponseCache());

		try {
			URL url = new URL("https://www.taobao.com");
			URLConnection conn = url.openConnection();
			System.out.println(conn.getHeaderField(0));

			conn = url.openConnection();
			System.out.println(conn.getHeaderField(0));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
