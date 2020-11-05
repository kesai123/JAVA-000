package io.github.kimmking.gateway.outbound.myclient;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class MyHttpOutboundHandler {

    private CloseableHttpClient httpClient;
    private String outboundUrl;

    public MyHttpOutboundHandler(String outboundUrl) {
        this.outboundUrl = outboundUrl;
        httpClient = HttpClients.createDefault();
    }

    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        final String url = this.outboundUrl + fullRequest.uri();
        get(url, fullRequest, ctx);
    }

    public void get(String url, final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
//      System.out.println("url: "+url);
//      System.out.println("outboundUrl: "+outboundUrl);
        HttpGet httpGet = new HttpGet(url);

        //将源消息头添加到新消息中
        for (Map.Entry<String, String> e : fullRequest.headers().entries()) {
            httpGet.addHeader(e.getKey(),e.getValue());
        }
        httpGet.setHeader("Connection", "keep-alive");

        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
//          System.out.println(EntityUtils.toString(httpEntity));
            handleResponse(fullRequest, ctx, httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final HttpResponse endpointResponse) {
        FullHttpResponse response = null;
        try {
            byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
//            response.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));
            response.headers().setInt("Content-Length", body.length);
        } catch (Exception e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }

    }
}