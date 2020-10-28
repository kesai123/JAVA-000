import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class MyHttpClient {

    public static void main(String[] args) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8801");
        try {
            CloseableHttpResponse response = httpclient.execute(httpget);
            HttpEntity responseEntity = response.getEntity();
            System.out.println("response:" + EntityUtils.toString(responseEntity));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
