package com.sys.manager.utils;

import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;

public class UserAgentUtil {

    /**
     * 获取浏览器信息,对文件名进行重新编码
     * @param filename
     * @param request
     * @return
     * @throws IOException
     */
    public static String filenameEncoding(String filename, HttpServletRequest request) throws IOException {
        //获取浏览器
        String agent = request.getHeader("User-Agent");
        if (agent.contains("Firefox")) {
            BASE64Encoder base64Encoder = new BASE64Encoder();
            filename = "=?utf-8?B?"
                    + base64Encoder.encode(filename.getBytes("utf-8"))
                    + "?=";
        } else if(agent.contains("MSIE")) {
            filename = URLEncoder.encode(filename, "utf-8");
        } else if(agent.contains ("Safari")) {
            filename = new String (filename.getBytes ("utf-8"),"ISO8859-1");
        } else {
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }
}
