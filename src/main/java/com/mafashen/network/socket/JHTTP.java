package com.mafashen.network.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;

public class JHTTP {

	private File root ;
	private int port;

	JHTTP(String path , int port) throws IOException {
		File root = new File(path);
		if (!root.isDirectory()){
			throw new IOException(root + " is not a directory");
		}
		this.root = root;
		this.port = port;
	}

	private void start(){
		ExecutorService pool = Executors.newFixedThreadPool(50);
		try(ServerSocket server = new ServerSocket(port)){
			System.out.println("Accepting connections on port + " + server.getLocalPort());
			System.out.println("Document Root is " + root);

			while(true){
				try{
					Socket socket = server.accept();
					pool.submit(new RequestProcessor(socket , root));
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	static class RequestProcessor implements Runnable{
		private Socket conn ;
		private File root ;

		RequestProcessor(Socket conn , File root){
			this.root = root;
			this.conn = conn;
		}

		@Override
		public void run() {
			try (
					BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))
			) {
				String get = reader.readLine();
				System.out.println(get);

				String[] tokens = get.split("\\s+");
				String method = tokens[0];
				String version = "";

				if (Objects.equals(method , "GET")){
					String fileName = tokens[1];
					if (StringUtils.isNotBlank(fileName) && fileName.endsWith("/")){
						fileName += "youdao.txt";
					}
					String contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName);
					if (tokens.length > 2){
						version = tokens[2];
					}

					File file = new File(root, fileName);

					if (file.canRead() && file.getCanonicalPath().startsWith(root.getPath())){
						byte[] data = Files.readAllBytes(file.toPath());
						if (StringUtils.isNotBlank(version) && version.startsWith("HTTP/")){
							sendHeader(writer , "HTTP/1.0 200 OK" , contentType , data.length);
						}
						writer.write(new String(data));
						writer.flush();
					}else{
						String body = new StringBuilder("<HTML>\r\n")
								.append("<head><title>File Not Found</title></head>\r\n")
								.append("<body><h1>Error 404: File Not Found</h1></body>\r\n")
								.append("</HTML>\r\n")
								.toString();
						if (StringUtils.isNotBlank(version) && version.startsWith("HTTP/")){
							sendHeader(writer , "HTTP/1.0 404 File Not Found" , contentType , body.length());
						}
						writer.write(body);
						writer.flush();
					}
				}else{
					String body = new StringBuilder("<HTML>\r\n")
							.append("<head><title>Not Support</title></head>\r\n")
							.append("<body><h1>Error 501: Not Support</h1></body>\r\n")
							.append("</HTML>\r\n")
							.toString();
					if (StringUtils.isNotBlank(version) && version.startsWith("HTTP/")){
						sendHeader(writer , "HTTP/1.0 501 Not Support" , "text/html; charset=utf-8" , body.length());
					}
					writer.write(body);
					writer.flush();
				}
			}catch (IOException e){
				e.printStackTrace();
			}
		}

		private void sendHeader(BufferedWriter writer, String responseCode, String contentType, int length) throws IOException {
			writer.write(responseCode + "\r\n");
			writer.write("Date: " + new Date() + "\r\n");
			writer.write("Server: JHTTP 2.0 \r\n");
			writer.write("Content-length: " + length + "\r\n");
			writer.write("Content-type: " + contentType + "\r\n");
			writer.flush();
		}
	}

	public static void main(String[] args) {
		try {
			new JHTTP("/Users/mafashen/", 1314).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
