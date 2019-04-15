package com.reven.uitl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;

/**
 * <code>{@link XssHttpServletRequestWrapper}</code>
 * 
 * @author
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    HttpServletRequest orgRequest = null;
    // 判断是否是上传，上传的请求则忽略
    boolean isUpData = false;

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        orgRequest = request;
        String contentType = request.getContentType();
        if (null != contentType) {
            isUpData = contentType.startsWith("multipart");
        }
    }

    /**
     * 覆盖getParameter方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取<br/>
     * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name) {
        Boolean flag = ("content".equals(name) || name.endsWith("WithHtml"));
        if (flag) {
            return super.getParameter(name);
        }
        name = JsoupUtil.clean(name);
        String value = super.getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            value = JsoupUtil.clean(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        Boolean flag = ("content".equals(name) || name.endsWith("WithHtml"));
        if (flag) {
            return super.getParameterValues(name);
        }
        String[] arr = super.getParameterValues(name);
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = JsoupUtil.clean(arr[i]);
            }
        }
        return arr;
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。<br/>
     * 如果需要获得原始的值，则通过super.getHeaders(name)来获取<br/>
     * getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {
        name = JsoupUtil.clean(name);
        String value = super.getHeader(name);
        if (StringUtils.isNotBlank(value)) {
            value = JsoupUtil.clean(value);
        }
        return value;
    }

    @Override
    public Map getParameterMap() {
        @SuppressWarnings("unchecked")
        HashMap<String, String[]> paramMap = (HashMap) super.getParameterMap();
        paramMap = (HashMap) paramMap.clone();

        for (Iterator iterator = paramMap.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String[] values = (String[]) entry.getValue();
            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof String) {
                    values[i] = xssEncode(values[i], 0);
                }
            }
            entry.setValue(values);
        }
        return paramMap;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (isUpData) {
            return super.getInputStream();
        } else {
            // 处理原request的流中的数据
            byte[] bytes = inputHandlers(super.getInputStream()).getBytes();
            final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            return new ServletInputStream() {
                @Override
                public int read() throws IOException {
                    return bais.read();
                }

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                }
            };
        }

    }

    public String inputHandlers(ServletInputStream servletInputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(servletInputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (servletInputStream != null) {
                try {
                    servletInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String finl = JsoupUtil.cleanJson(sb.toString());
        return finl;
    }

    /**
     * 将容易引起xss漏洞的半角字符直接替换成全角字符 有用？
     * 
     * @param s
     * @return
     */
    private static String xssEncode(String s, int type) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (type == 0) {
                switch (c) {
                case '\'':
                    // 全角单引号
                    sb.append('‘');
                    break;
                case '\"':
                    // 全角双引号
                    sb.append('“');
                    break;
                case '>':
                    // 全角大于号
                    sb.append('＞');
                    break;
                case '<':
                    // 全角小于号
                    sb.append('＜');
                    break;
                case '&':
                    // 全角&符号
                    sb.append('＆');
                    break;
                case '\\':
                    // 全角斜线
                    sb.append('＼');
                    break;
                case '#':
                    // 全角井号
                    sb.append('＃');
                    break;
                // < 字符的 URL 编码形式表示的 ASCII 字符（十六进制格式） 是: %3c
                case '%':
                    processUrlEncoder(sb, s, i);
                    break;
                default:
                    sb.append(c);
                    break;
                }
            } else {
                switch (c) {
                case '>':
                    // 全角大于号
                    sb.append('＞');
                    break;
                case '<':
                    // 全角小于号
                    sb.append('＜');
                    break;
                case '&':
                    // 全角&符号
                    sb.append('＆');
                    break;
                case '\\':
                    // 全角斜线
                    sb.append('＼');
                    break;
                case '#':
                    // 全角井号
                    sb.append('＃');
                    break;
                // < 字符的 URL 编码形式表示的 ASCII 字符（十六进制格式） 是: %3c
                case '%':
                    processUrlEncoder(sb, s, i);
                    break;
                default:
                    sb.append(c);
                    break;
                }
            }

        }
        return sb.toString();
    }

    public static void processUrlEncoder(StringBuilder sb, String s, int index) {
        if (s.length() >= index + 2) {
            // %3c, %3C
            if (s.charAt(index + 1) == '3' && (s.charAt(index + 2) == 'c' || s.charAt(index + 2) == 'C')) {
                sb.append('＜');
                return;
            }
            // %3c (0x3c=60)
            if (s.charAt(index + 1) == '6' && s.charAt(index + 2) == '0') {
                sb.append('＜');
                return;
            }
            // %3e, %3E
            if (s.charAt(index + 1) == '3' && (s.charAt(index + 2) == 'e' || s.charAt(index + 2) == 'E')) {
                sb.append('＞');
                return;
            }
            // %3e (0x3e=62)
            if (s.charAt(index + 1) == '6' && s.charAt(index + 2) == '2') {
                sb.append('＞');
                return;
            }
        }
        sb.append(s.charAt(index));
    }

    /**
     * 获取最原始的request
     * 
     * @return
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取最原始的request的静态方法
     * 
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssHttpServletRequestWrapper) {
            return ((XssHttpServletRequestWrapper) req).getOrgRequest();
        }

        return req;
    }

}