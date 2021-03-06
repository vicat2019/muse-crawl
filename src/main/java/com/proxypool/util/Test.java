package com.proxypool.util;

import com.google.common.collect.Maps;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * @program: muse-pay
 * @description: 测试类
 * @author: Vincent
 * @create: 2018-10-15 19:32
 **/
public class Test {

    private static final int capacity = 1000000;
    private static final int key = 999998;

    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), capacity);

    static {
        for (int i = 0; i < capacity; i++) {
            bloomFilter.put(i);
        }
    }

    public static void main(String[] args) {

        /*返回计算机最精确的时间，单位微妙*/
/*
        long start = System.nanoTime();

        if (bloomFilter.mightContain(key)) {
            System.out.println("成功过滤到" + key);
        }
        long end = System.nanoTime();
        System.out.println("布隆过滤器消耗时间:" + ((end - start)/1000000d));
        int sum = 0;
        for (int i = capacity + 20000; i < capacity + 30000; i++) {
            if (bloomFilter.mightContain(i)) {
                sum = sum + 1;
            }
        }
        System.out.println("错判率为:" + sum/1000000d);
*/


        /*String ip = "36.48.73.16";
        int port = 80;
        try {
            useProxy(ip, port, "https://www.baidu.com", "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        /*Instant instant = new Date().toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime nowLocalDateTime = instant.atZone(zoneId).toLocalDateTime();

        LocalDateTime time = LocalDateTime.of(2018, 10, 28, 9, 28, 42);

        Duration duration = java.time.Duration.between(time, nowLocalDateTime);
        long interval = duration.toDays();
        System.out.println(interval);*/


        // genRandomInfo(50000);


        System.getProperties().setProperty("webdriver.chrome.driver", "D:/chromedriver/chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://www.91160.com/dep/show/depid-3826.html");
        WebElement webElement = webDriver.findElement(By.xpath("/html"));
        System.out.println(webElement.getAttribute("outerHTML"));
        webDriver.close();

        /*PhantomJSDriver driver = PhantomJSDriverHelper.getDriver();
        try {
            PhantomJSDriverHelper.setCookies(driver, getCookieStore());
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.get("https://www.91160.com/dep/show/depid-3826.html");
        System.out.println(driver.getPageSource());*/

    }


    private static Map<String, String> getCookieStore() {
        Map<String, String> cookieStore = Maps.newHashMap();
        cookieStore.put("__guid", "MijNWz5c709f39737562.26040785");
        cookieStore.put("__jsluid", "bc46d490240b92ccbd29fea887c0dbfe");
        cookieStore.put("gr_user_id", "4288344b-21e1-47d9-9dfc-8c9bc13b1664");
        cookieStore.put("FISKCDDCC", "7fe30d8c46661d9d724b540783246e10");
        cookieStore.put("gr_session_id_88c697c1877e5045", "08f51ba5-0a09-492e-871d-de0568363e71");
        cookieStore.put("gr_session_id_88c697c1877e5045_08f51ba5-0a09-492e-871d-de0568363e71", "true");
        cookieStore.put("Hm_lpvt_c4e8e5b919a5c12647962ea08462e63b", "1550885276");
        cookieStore.put("Hm_lvt_c4e8e5b919a5c12647962ea08462e63b", "1550884666");
        cookieStore.put("ip_city", "sz");
        cookieStore.put("is_read_index_notice_", "1");

        return cookieStore;
    }

    private static void genRandomInfo(int size) {
        File file = new File("d:/logs/secKill.txt");

        try {
            FileOutputStream out = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            int userId = 1;
            while (size > 0) {
                writer.write(1000 + "," + userId + "\n");
                size--;
                userId++;
            }
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void useProxy(String ip, int port, String targetUrl, String encode) throws Exception {
        //创建httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建httpGet实例
        HttpGet httpGet = new HttpGet(targetUrl);
        //设置代理IP，设置连接超时时间 、 设置 请求读取数据的超时时间 、 设置从connect Manager获取Connection超时时间、
        HttpHost proxy = new HttpHost(ip, port);
        RequestConfig requestConfig = RequestConfig.custom()
                .setProxy(proxy)
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .setConnectionRequestTimeout(3000)
                .build();
        httpGet.setConfig(requestConfig);
        //设置请求头消息
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/62.0.3202.94 Safari/537.36");
        CloseableHttpResponse response = httpClient.execute(httpGet);

        if (response != null) {
            HttpEntity entity = response.getEntity();  //获取返回实体
            if (entity != null) {
                System.out.println("网页内容为:" + EntityUtils.toString(entity, encode));
            }
        }
        if (response != null) {
            response.close();
        }
        if (httpClient != null) {
            httpClient.close();
        }
    }


}
