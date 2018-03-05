package com.hq.yunyi2.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;


public class LoginHttpUtils {
    private static String PATH = "http://115.159.191.125:8080/YunYiServer/servlet/UserLoginAction";
    private static URL url;

    public LoginHttpUtils() {
        // TODO Auto-generated constructor stub
    }

    static {
        try {
            url = new URL(PATH);
        } catch (MalformedURLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static String sendPostMessage(Map<String, String> params,
                                         String encode) {
        StringBuffer buffer = new StringBuffer();

        try {
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    buffer.append(entry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue(), encode))
                            .append("&");
                }
            }
            // System.out.println(buffer.toString());
            // 删掉最后一个 &
            buffer.deleteCharAt(buffer.length() - 1);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url
                    .openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setDoInput(true);// 从服务器读取数据
            httpURLConnection.setDoOutput(true);// 向服务器写数据
            // 获得上传信息的字节大小以及长度
            byte[] mydata = buffer.toString().getBytes();
            // 表示设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Cotent-Type",
                    "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("Content-Length",
                    String.valueOf(mydata.length));
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(mydata);
            // 获得服务器响应的结果和状态码
            int responsecode = httpURLConnection.getResponseCode();
            if (responsecode == 200) {
                return changeInputStream(httpURLConnection.getInputStream(),
                        encode);
            }

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    // 将一个输入流转换成指定编码的字符串
    private static String changeInputStream(InputStream inputStream,
                                            String encode) {
        // TODO Auto-generated method stub
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = "";
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, len);
                }
                result = new String(outputStream.toByteArray(), encode);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }
}

