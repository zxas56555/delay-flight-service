package com.sys.manager.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 工具类
 *
 * @author qmy
 */
public class EmpowerUtils {

    public static void main(String[] args) {
        System.out.println(getKey());
    }

    //    public static void main(String[] args) {
//        Map<String, String> codeMap = new HashMap<>();
//        String processorId = "BFEBFBFFOO0986EA";
//        codeMap.put("ProcessorId",processorId);
//        System.out.println("ProcessorId：" + processorId);
//        String serialNumber = "WD-WXT1A39KYF4S";
//        codeMap.put("SerialNumber",serialNumber);
//        System.out.println("SerialNumber：" + serialNumber);
//        String codeMapStr = JSON.toJSONString(codeMap);
//        String serials = MD5Util.getMD5CusSalt(codeMapStr, salt);
//        String result = getSplitString(serials, "-", 4);
//        System.out.println(result);
//    }


    private static String salt = "888888";

    public static String getKeyByRequestIp() {
        String requestIp = getRequestIp();
        Map<String, String> codeMap = new HashMap<>();
        codeMap.put("requestIp", requestIp);
        System.out.println("requestIp：" + requestIp);
        String codeMapStr = JSON.toJSONString(codeMap);
        String serials = MD5Util.getMD5CusSalt(codeMapStr, salt);
        String result = getSplitString(serials, "-", 4);
        return result;
    }

    /**
     * 获取请求ip
     * @return
     * @throws IOException
     */
    public static String getRequestIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getKey() {
        Map<String, String> codeMap = new HashMap<>();
        String result = "";
        String os = System.getProperty("os.name");
        if (os != null && os.toLowerCase().startsWith("win")) {
            String processorId = getCPUSerialNumber();
            codeMap.put("ProcessorId",processorId);
            System.out.println("ProcessorId：" + processorId);
            String serialNumber = getHardDiskSerialNumber();
            codeMap.put("SerialNumber",serialNumber);
            System.out.println("SerialNumber：" + serialNumber);
            String codeMapStr = JSON.toJSONString(codeMap);
            String serials = MD5Util.getMD5CusSalt(codeMapStr, salt);
            result = getSplitString(serials, "-", 4);
        } else if (os != null && os.toLowerCase().startsWith("linux")) {//Linux操作系统
            String boisVersion = getBoisVersion();
            codeMap.put("boisVersion",boisVersion);
            System.out.println("boisVersion：" + boisVersion);
            String uuid = getUUID();
            codeMap.put("uuid",uuid);
            System.out.println("uuid：" + uuid);
            String codeMapStr = JSON.toJSONString(codeMap);
            String serials = MD5Util.getMD5CusSalt(codeMapStr, salt);
            result = getSplitString(serials, "-", 4);
        } else {
            System.out.println("系统类型未识别");
        }
//        System.out.println(result);
        return result;
    }


    /**
     * 获取CPU序列号
     * @return
     * @throws IOException
     */
    public static String getCPUSerialNumber() {
        String next;
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            String serial = sc.next();
            next = sc.next();
        } catch (IOException e) {
            throw new RuntimeException("获取CPU序列号失败");
        }
        return next;
    }

    /**
     * 获取 硬盘序列号(Windows)
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static String getHardDiskSerialNumber() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"wmic", "path", "win32_physicalmedia", "get", "serialnumber"});
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            String serial = sc.next();
            return sc.next();
        } catch (IOException e) {
            throw new RuntimeException("获取硬盘序列号失败");
        }
    }

    /**
     * bois版本号(linux)
     *
     * @return
     */
    public static String getBoisVersion() {
        String result = "";
        Process p;
        try {
            // 管道
            p = Runtime.getRuntime().exec("sudo dmidecode -s bios-version");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result += line;
                break;
            }
            br.close();
        } catch (IOException e) {
            System.out.println("获取主板信息错误");
        }
        return result;
    }

    /**
     * 获取系统序列号(linux)
     *
     * @return
     */
    public static String getUUID() {
        String result = "";
        try {
            Process process = Runtime.getRuntime().exec("sudo dmidecode -s system-uuid");
            InputStream in;
            BufferedReader br;
            in = process.getInputStream();
            br = new BufferedReader(new InputStreamReader(in));
            while (in.read() != -1) {
                result = br.readLine();
            }
            br.close();
            in.close();
            process.destroy();
            System.out.println("获取序列号："+result);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String getSplitString(String str, String split, int length) {
        int len = str.length();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if (i % length == 0 && i > 0) {
                temp.append(split);
            }
            temp.append(str.charAt(i));
        }
        return temp.toString();
    }

}
