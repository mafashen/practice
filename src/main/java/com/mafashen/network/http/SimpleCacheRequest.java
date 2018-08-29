package com.mafashen.network.http;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.CacheRequest;
import java.net.URLConnection;

public class SimpleCacheRequest extends CacheRequest {

	private OutputStream out = null;

	public SimpleCacheRequest(URLConnection conn){
		if (conn != null){
			try (OutputStream outputStream = conn.getOutputStream()) {
				this.out = outputStream;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public OutputStream getBody() throws IOException {
		return new BufferedOutputStream(out);
	}

	@Override
	public void abort() {
		if (out != null){
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
