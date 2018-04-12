package com.fengchi.TimeTravel.Utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * 上传文件到服务器（图片）的工具类
 * @author baron
 *
 */
public class UploadUtil {
	    private static final String TAG = "uploadFile";
	    private static final int TIME_OUT = 20 * 1000; // 超时时间
	    private static final String CHARSET = "utf-8"; // 设置编码
	    private static final String CHARSET1 = "ISO8859-1"; // 设置编码
	    public static final String REST_NO_NET="NO_NET";//没有网络
	    public static final String REST_FILE_NULL="NO_FILE";//文件为空
	    public static final String REST_REPONSE_ERROR="RESPO_ERROR";//服务器返回错误
	    /**
	     * 上传文件到服务器
	     * @param file 需要上传的文件
	     * @param RequestURL 请求的rul
	     * @return 返回响应的内容
	     */
	    public static String uploadFile(File file, String RequestURL) {
	    	  String result = null;
	    	if(HttpUtils.isNetworkAvailable()){ //判断网络是否正常    
	    		 int res=0;
	 	        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
	 	        String PREFIX = "--", LINE_END = "\r\n";
	 	        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
	 	        try {
	 	            URL url = new URL(RequestURL);
	 	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	 	            conn.setReadTimeout(TIME_OUT);
	 	            conn.setConnectTimeout(TIME_OUT);
	 	            conn.setDoInput(true); // 允许输入流
	 	            conn.setDoOutput(true); // 允许输出流
	 	            conn.setUseCaches(false); // 不允许使用缓存
	 	            conn.setRequestMethod("POST"); // 请求方式
	 	            conn.setRequestProperty("Charset", CHARSET); // 设置编码
	 	            conn.setRequestProperty("connection", "keep-alive");
	 	            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="+ BOUNDARY);
	 	            if (file != null) {
	 	                /**
	 	                 * 当文件不为空时执行上传
	 	                 */
	 	                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
	 	                StringBuffer sb = new StringBuffer();
	 	                sb.append(PREFIX);
	 	                sb.append(BOUNDARY);
	 	                sb.append(LINE_END);
	 	                /**
	 	                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
	 	                 * filename是文件的名字，包含后缀名
	 	                 */

	 	                sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
	 	                        + file.getName() + "\"" + LINE_END);
	 	                sb.append("Content-Type: application/octet-stream; charset="
	 	                        + CHARSET + LINE_END);
	 	                sb.append(LINE_END);
	 	                dos.write(sb.toString().getBytes());
	 	                InputStream is = new FileInputStream(file);
	 	                byte[] bytes = new byte[1024];
	 	                int len = 0;
	 	                while ((len = is.read(bytes)) != -1) {
	 	                    dos.write(bytes, 0, len);
	 	                }
	 	                is.close();
	 	                dos.write(LINE_END.getBytes());
	 	                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
	 	                        .getBytes();
	 	                dos.write(end_data);
	 	                dos.flush();
	 	                /**
	 	                 * 获取响应码 200=成功 当响应成功，获取响应的流
	 	                 */
	 	                 res = conn.getResponseCode();
						LogUtil.warn(TAG, "request url:" + RequestURL);
						LogUtil.warn(TAG, "response code:" + res);
	 	                if (res == 200) {
	 	                	LogUtil.warn(TAG, "request success");
	 	                    InputStream input = conn.getInputStream();
	 	                    StringBuffer sb1 = new StringBuffer();
	 	                    int ss;
	 	                    while ((ss = input.read()) != -1) {
	 	                        sb1.append((char) ss);
	 	                    }
	 	                    result = sb1.toString();
	 	                   LogUtil.warn(TAG, "result : " + result);
	 	                } else {
	 	                	result=REST_REPONSE_ERROR;
	 	                	LogUtil.warn(TAG, "request error");
	 	                }
	 	            }else{
	 	            	result=REST_FILE_NULL;
	 	            }
	 	            
	 	        } catch (MalformedURLException e) {
	 	        	result=null;
	 	            e.printStackTrace();
	 	        } catch (IOException e) {
					result=REST_FILE_NULL;   //文件读取异常
	 	            e.printStackTrace();
	 	        }
	    	}else{
	    		result=REST_NO_NET;
	    	}
	        return result;
	    }
	/**
	 * 上传文件到服务器
	 * @param RequestURL 请求的rul
	 * @return 返回响应的内容
	 */
	public static String uploadFile(String path, String RequestURL) {
		File file=new File(path);
		String result = null;
		if(HttpUtils.isNetworkAvailable()){ //判断网络是否正常
			int res=0;
			String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
			String PREFIX = "--", LINE_END = "\r\n";
			String CONTENT_TYPE = "multipart/form-data"; // 内容类型
			try {
				URL url = new URL(RequestURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(TIME_OUT);
				conn.setConnectTimeout(TIME_OUT);
				conn.setDoInput(true); // 允许输入流
				conn.setDoOutput(true); // 允许输出流
				conn.setUseCaches(false); // 不允许使用缓存
				conn.setRequestMethod("POST"); // 请求方式
				conn.setRequestProperty("Charset", CHARSET); // 设置编码
				conn.setRequestProperty("connection", "keep-alive");
				conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="+ BOUNDARY);
				if (file != null) {
					/**
					 * 当文件不为空时执行上传
					 */
					DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
					StringBuffer sb = new StringBuffer();
					sb.append(PREFIX);
					sb.append(BOUNDARY);
					sb.append(LINE_END);
					/**
					 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
					 * filename是文件的名字，包含后缀名
					 */

					sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
							+ file.getName() + "\"" + LINE_END);
					sb.append("Content-Type: application/octet-stream; charset="
							+ CHARSET + LINE_END);
					sb.append(LINE_END);
					dos.write(sb.toString().getBytes());
					InputStream is = new FileInputStream(file);
					byte[] bytes = new byte[1024];
					int len = 0;
					while ((len = is.read(bytes)) != -1) {
						dos.write(bytes, 0, len);
					}
					is.close();
					dos.write(LINE_END.getBytes());
					byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
							.getBytes();
					dos.write(end_data);
					dos.flush();
					/**
					 * 获取响应码 200=成功 当响应成功，获取响应的流
					 */
					res = conn.getResponseCode();
					LogUtil.warn(TAG, "request url:" + RequestURL);
					LogUtil.warn(TAG, "response code:" + res);
					if (res == 200) {
						LogUtil.warn(TAG, "request success");
						InputStream input = conn.getInputStream();
						StringBuffer sb1 = new StringBuffer();
						int ss;
						while ((ss = input.read()) != -1) {
							sb1.append((char) ss);
						}


						result = sb1.toString();
						LogUtil.warn(TAG, "result : " + result);
					} else {

						result=REST_REPONSE_ERROR;
						LogUtil.warn(TAG, "request error");
					}
				}else{
					result=REST_FILE_NULL;
				}

			} catch (MalformedURLException e) {
				result=null;
				e.printStackTrace();
			} catch (IOException e) {
				result=REST_FILE_NULL;   //文件读取异常
				e.printStackTrace();
			}

		}else{
			result=REST_NO_NET;
		}

		return result;
	}
	/**
	 * 上传文件到服务器(不加上判断)
	 * @param RequestURL 请求的rul
	 * @return 返回响应的内容
	 */
	public static String uploadFile_NoKey(String path, String RequestURL) {
		File file=new File(path);
		String result = null;
		if(HttpUtils.isNetworkAvailable()){ //判断网络是否正常
			int res=0;
			String fileMd5 =FileMd5.getFileMd5(file);
			LogUtil.error("fileMd5",fileMd5);
			String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
			String PREFIX = "--", LINE_END = "\r\n";
			String CONTENT_TYPE = "multipart/form-data"; // 内容类型
			try {
				URL url = new URL(RequestURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(TIME_OUT);
				conn.setConnectTimeout(TIME_OUT);
				conn.setDoInput(true); // 允许输入流
				conn.setDoOutput(true); // 允许输出流
				conn.setUseCaches(false); // 不允许使用缓存
				conn.setRequestMethod("POST"); // 请求方式
				conn.setRequestProperty("Charset", CHARSET); // 设置编码
				conn.setRequestProperty("connection", "keep-alive");
				conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="+ BOUNDARY);
				if (file != null) {
					/**
					 * 当文件不为空时执行上传
					 */
					DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
					StringBuffer sb = new StringBuffer();
					sb.append(PREFIX);
					sb.append(LINE_END);
					sb.append(LINE_END);
					sb.append(PREFIX);



					dos.write(sb.toString().getBytes());
					InputStream is = new FileInputStream(file);
					byte[] bytes = new byte[1024];
					int len = 0;
					while ((len = is.read(bytes)) != -1) {
						dos.write(bytes, 0, len);
					}
					is.close();
					dos.write(PREFIX.getBytes());
					dos.write(LINE_END.getBytes());
					dos.write(LINE_END.getBytes());
					dos.write(PREFIX.getBytes());
					//dos.write(fileMd5.getBytes());
					dos.flush();
					/**
					 * 获取响应码 200=成功 当响应成功，获取响应的流
					 */
					res = conn.getResponseCode();
					LogUtil.warn(TAG, "request url:" + RequestURL);
					LogUtil.warn(TAG, "response code:" + res);
					if (res == 200) {
						LogUtil.warn(TAG, "request success");
						InputStream input = conn.getInputStream();
						InputStreamReader reader=new InputStreamReader(input,"UTF-8");
						StringBuffer sb1 = new StringBuffer();

						int ss;
						while ((ss = reader.read()) != -1) {
							sb1.append((char) ss);
						}

						result = sb1.toString();
						LogUtil.warn(TAG, "result : " + result);
					} else {

						result=REST_REPONSE_ERROR;
						LogUtil.warn(TAG, "request error");
					}
				}else{
					result=REST_FILE_NULL;
				}

			} catch (MalformedURLException e) {
				result=null;
				e.printStackTrace();
			} catch (IOException e) {
				result=REST_FILE_NULL;   //文件读取异常
				e.printStackTrace();
			}
		}else{
			result=REST_NO_NET;
		}

		return result;
	}
}
