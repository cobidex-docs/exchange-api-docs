package test;
 
import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
 
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;
 
public class Main {
    private static  final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Main.class.getName());
    private static final int DEFAULT_TIMEOUT = 10000;
    private static int timeout = DEFAULT_TIMEOUT;
    private static int maxConnTotal = 500;   //Do not exceed the maximum 1000
    private static int maxConnPerRoute = 200;//The actual single connection pool size，
 
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        /** api_key,secret_key */
        String api_key = "61908ad2a29e1c1f20c73b0dd72cdb30";
        String secret_key = "a665ac2932b63507b8f4ef1700997d7a";
        long time = new Date().getTime();
        /** Encapsulate parameters that need to be signed */
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("symbol", "btcusdt");
        params.put("type", "1");
        params.put("side", "BUY");
        params.put("volume", "1");
        params.put("price", "5");
        params.put("api_key", api_key);
        params.put("time", time+"");
        /** Concatenate signature string, md5 signature */
        StringBuilder result = new StringBuilder();
        Set<Entry<String, String>> entrys = params.entrySet();
        for (Entry<String, String> param : entrys) {
            /** Remove the signature field */
            if(param.getKey().equals("sign")){
                continue;
            }
 
            /** Null parameters do not participate in the signature */
            if(param.getValue()!=null) {
                result.append(param.getKey());
                result.append(param.getValue().toString());
            }
        }
        result.append(secret_key);
        String sign = getMD5(result.toString());
        params.put("sign", sign);
        ArrayList<BasicNameValuePair> basicNameValuePairs = new ArrayList<>();
        params.forEach((k,v) -> basicNameValuePairs.add(new BasicNameValuePair(k, v)));
        System.out.println(JSON.toJSONString(params));
        String resultJson = doPost("https://openapi.cobidex.com/open/api/create_order", basicNameValuePairs );
        System.out.println(resultJson);
    }
 
    /**
     * post request
     * @param url
     * @param data
     * @return
     */
    public static String doPost(String url, ArrayList<BasicNameValuePair> data) {
        HttpResponse response = null;
        try {
            //UrlEncodedFormEntity This class is used to encode input data into appropriate content
            //Two key-value pairs, are UrlEncodedFormEntityAfter the example is coded, it becomes the following content：param1=value1¶m2=value2
            //First set the parameters to the form of utf-8
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(data,
                    HTTP.UTF_8);
            String result = "";
            HttpPost post = new HttpPost(url);//post request
            RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout)
                    .setConnectTimeout(timeout).setSocketTimeout(timeout).build();
 
            post.setConfig(config);
            post.setEntity(entity);//Bring parameters
            CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(config)
                    .setMaxConnTotal(maxConnTotal)
                    .setMaxConnPerRoute(maxConnPerRoute).build();
            response = client.execute(post);//Response result
            if (response.getStatusLine().getStatusCode() == 200) {
                //The result is a STRING type
                result = EntityUtils.toString(response.getEntity());
                if (log.isDebugEnabled()) {
                    log.debug("doPost to {} response: {}", url, result);
                }
            } else {
                log.warn("doPost to {} response statusCode: {}, payload: {}", url, response.getStatusLine().getStatusCode(),
                        EntityUtils.toString(response.getEntity()));
            }
 
            return result;
        } catch (ConnectTimeoutException e) {
            log.error("doPost to {} raise ConnectTimeoutException, {}", url, e.getMessage());
        } catch (HttpHostConnectException e) {
            log.error("doPost to {} raise HttpHostConnectException, {}", url, e.getMessage());
        } catch (Exception e) {
            log.error("doPost to {} raise exception ,{}", url, e.getMessage());
        } finally {
            try {
                if (response != null) {
                    EntityUtils.consume(response.getEntity());
                }
            } catch (IOException e) {
                log.error("doPost to {} raise final exception ,{}", url, e.getMessage());
            }
        }
        return null;
    }
 
    /**
     * Submit data through get, method with parameters
     *
     * @param url Request address
     * @param params parameter
     * @return
     */
    public static String get(String url, Map<String, String> params) {
        String str = null;
        try {
            if (params != null && params.size() > 0) {
                int x = 1;
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if(x==1){
                        url = url + "?";
                    }else {
                        url = url + "&";
                    }
                    url+=entry.getKey()+"="+entry.getValue();
                    x++;
                }
            }
            HttpClient client = new HttpClient();
            System.out.println("url:::"+url);
            GetMethod method = new GetMethod(url);
//Set the style of the request header
            method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            int code = client.executeMethod(method);
            if (code >= 200 && code < 300) {
                InputStream in = method.getResponseBodyAsStream();
                str = IOUtils.toString(in);
            }
        } catch (Exception e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return str;
    }
 
 
    /**
     * Get the MD5 value of String
     *
     * @param info String
     * @return MD5 value of the string
     */
    public static String getMD5(String info) {
        try {
//Obtain MessageDigest Object, the parameter is MD5 String indicating that this is a MD5 Algorithm (other SHA1 Algorithm etc.)：
            MessageDigest md5 = MessageDigest.getInstance("MD5");
//update(byte[])Method, input original data
//Similar to the append() method of StringBuilder object, append mode is a process of cumulative change
            md5.update(info.getBytes("UTF-8"));
//digest()After being called, the MessageDigest object is reset, that is, the method cannot be called again continuously to calculate the MD5 value of the original data. You can manually call the reset() method to reset the input source.
//digest()The return value is a hash value with a length of 16 bits, which is taken over by byte[]
            byte[] md5Array = md5.digest();
//byte[] Usually we will convert it into a hexadecimal 32-bit string for use. This article will introduce three commonly used conversion methods
            return bytesToHex(md5Array);
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
 
    private static String bytesToHex(byte[] md5Array) {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < md5Array.length; i++) {
            int temp = 0xff & md5Array[i];
            String hexString = Integer.toHexString(temp);
            if (hexString.length() == 1) {//If it is hexadecimal 0f, only f is displayed by default, and 0 should be added at this time
                strBuilder.append("0").append(hexString);
            } else {
                strBuilder.append(hexString);
            }
        }
        return strBuilder.toString();
    }
 
}
