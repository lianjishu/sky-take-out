package io.github.lianjishu;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class HttpClientTest {

    /**
     * 测试通过httpClient发送GET请求
     */
    @Test
    public void testGET() throws IOException {
        CloseableHttpResponse response;

        //创建httpClient对象
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            //创建请求对象
            HttpGet httpGet = new HttpGet("http://localhost:8080/admin/shop/status");
            //发送请求
            response = httpClient.execute(httpGet);
            response.getEntity();
            //处理响应结果
            System.out.println("响应状态码：" + response.getStatusLine().getStatusCode());
        }
    }


    /**
     * 测试通过httpClient发送POST请求
     */
    @Test
    public void testPOST() {
        CloseableHttpResponse response;
        //创建httpClient对象
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            //创建请求对象
            HttpPost httpPost = new HttpPost("http://localhost:8080/admin/employee/login");
            //设置请求对象
            //httpPost.setEntity(new StringEntity("{\"username\":\"admin\",\"password\":\"123456\"}"));
            JSONObject reqParams = new JSONObject();
            reqParams.put("username", "admin");
            reqParams.put("password", "123456");
            String reqParamsStr = reqParams.toString();
            httpPost.setEntity(new StringEntity(reqParamsStr, "UTF-8"));
            httpPost.setHeader("Content-type", "application/json");
            //发送请求
            response = httpClient.execute(httpPost);
            //处理响应结果
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println("响应状态码：" + statusCode);
            System.out.println("响应数据：" + responseBody);
        } catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

}
