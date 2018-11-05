package com.ajiatech;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadFile {
	private static String path = "c:/1";
	private static String fileName = "1702.txt";
	
	public static void main(String[] args){
		uploadFile();
	}

	/* 上传文件至Server的方法 */
	public static void uploadFile() {
		// String uploadUrl = "http://172.16.5.183/s1/do_uploadImg.jsp";
		// String uploadUrl = "http://172.16.5.183:4959/WebSite1/Default.aspx";
		String uploadUrl = "http://127.0.0.1/fileUpload2.html";

		try {
			URL url = new URL(uploadUrl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=******");

			DataOutputStream dos = new DataOutputStream(
					httpURLConnection.getOutputStream());
			dos.writeBytes("--******\r\n");
			dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
					+ fileName + "\"\r\n");
			dos.writeBytes("\r\n");

			FileInputStream fis = new FileInputStream(path + "/" + fileName);
			byte[] buffer = new byte[8192]; // 8k
			int count = 0;
			while ((count = fis.read(buffer)) != -1) {
				dos.write(buffer, 0, count);

			}
			fis.close();

			dos.writeBytes("\r\n");
			dos.writeBytes("--******--\r\n");
			dos.flush();

			InputStream is = httpURLConnection.getInputStream();

			DataInputStream dis = new DataInputStream(is);
			// 得到返回的所有数据

			InputStreamReader isr = new InputStreamReader(dis);
			BufferedReader br = new BufferedReader(isr);

			String lineContent = "";
			StringBuffer stringBuffer = new StringBuffer();
			while ((lineContent = br.readLine()) != null) {
				// System.out.println(lineContent);
				stringBuffer.append(lineContent);
			}

			dos.close();
			is.close();

		} catch (Exception e) {
			e.printStackTrace();
			// setTitle(e.getMessage());
		}

	}

}
