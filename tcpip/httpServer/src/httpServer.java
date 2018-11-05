//httpServer.java 
import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class httpServer {
	//实现了tomcat的功能
	public static void main(String args[]) {
		int port;
		ServerSocket server_socket;	

		
		try {
			// 监听服务器端口，等待连接请求
			server_socket = new ServerSocket(8080);
			System.out.println("httpServer running on port "
					+ server_socket.getLocalPort());
			// 显示启动信息
			while (true) {
				//客户端连上服务器,服务器上产生socket
				Socket socket = server_socket.accept();
				
				// 创建分线程
				try {
					httpRequestHandler request = new httpRequestHandler(socket);
					Thread thread = new Thread(request);
					// 启动线程
					thread.start();
				} catch (Exception e) {
					//System.out.println(e);
				}
			}
		} catch (IOException e) {
			//System.out.println(e);
		}
	}
}

class httpRequestHandler implements Runnable {
	final static String CRLF = "\r\n";
	Socket socket;
	InputStream input;
	OutputStream output;
	BufferedReader br;

	// 构造方法
	public httpRequestHandler(Socket socket) throws Exception {
		this.socket = socket;
		this.input = socket.getInputStream();
		this.output = socket.getOutputStream();
		this.br = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
	}

	// 实现Runnable 接口的run()方法
	public void run() {
		try {
			System.out.println("新用户,启了新线程,id="+Thread.currentThread().getId()
					+"客户端ip"+ socket.getInetAddress() + ",客户端源端口号" + socket.getPort());
			while (true) {
				// 读取并显示Web 浏览器提交的请求信息
				String headerLine = br.readLine();
				//System.out.println("The client request is " + headerLine);
				if (headerLine.equals(CRLF) || headerLine.equals(""))
					break;
				StringTokenizer s = new StringTokenizer(headerLine);
				String temp = s.nextToken();
				if (temp.equals("GET")) {
					String fileName = s.nextToken();
					fileName = "." + fileName;
					// 打开所请求的文件
					FileInputStream fis = null;
					boolean fileExists = true;
					try {
						//System.out.println("fileName="+fileName);
						fileName=fileName.substring(2);
						fileName="E:/1702/"+fileName;
						fis = new FileInputStream(fileName);
					} catch (FileNotFoundException e) {
						fileExists = false;
					}
					// 完成回应消息
					String serverLine = "Server: a simple java httpServer";
					String statusLine = null;
					String contentTypeLine = null;
					String entityBody = null;
					String contentLengthLine = "error";
					if (fileExists) {
						statusLine = "HTTP/1.0 200 OK" + CRLF;
						contentTypeLine = "Content-type: " + contentType(fileName)
								+ CRLF;
						contentLengthLine = "Content-Length: "
								+ (new Integer(fis.available())).toString() + CRLF;
					} else {
						statusLine = "HTTP/1.0 404 Not Found" + CRLF;
						contentTypeLine = "text/html";
						entityBody = "<HTML>"
								+ "<HEAD><TITLE>404 Not Found</TITLE></HEAD>"
								+ "<BODY>1601作品 web服务器 404 Not Found"
								+ "<br>usage:http://yourHostName:port/"
								+ "fileName.html</BODY></HTML>";
					}
					// 发送到服务器信息
					output.write(statusLine.getBytes());
					output.write(serverLine.getBytes());
					output.write(contentTypeLine.getBytes());
					output.write(contentLengthLine.getBytes());
					output.write(CRLF.getBytes());
					// 发送信息内容
					if (fileExists) {
						sendBytes(fis, output);
						fis.close();
					} else {
						output.write(entityBody.getBytes());
					}
				}
			}
			// 关闭套接字和流
			try {
				output.close();
				br.close();
				socket.close();
			} catch (Exception e) {
			}
		
		} catch (Exception e) {
			//System.out.println(e);
		}
	}

	private void processRequest() throws Exception {}

	private static void sendBytes(FileInputStream fis, OutputStream os)
			throws Exception {
		// 创建一个 1K buffer
		byte[] buffer = new byte[1024];
		int bytes = 0;
		// 将文件输出到套接字输出流中
		while ((bytes = fis.read(buffer)) != -1) {
			os.write(buffer, 0, bytes);
		}
	}

	private static String contentType(String fileName) {
		if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {
			return "text/html";
		}

		return "fileName";
	}
}