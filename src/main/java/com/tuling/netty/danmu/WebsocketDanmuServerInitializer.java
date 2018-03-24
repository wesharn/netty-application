package com.tuling.netty.danmu;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 服务端 ChannelInitializer
 *
 */
public class WebsocketDanmuServerInitializer extends
        ChannelInitializer<SocketChannel> {

	@Override
    public void initChannel(SocketChannel ch) throws Exception {
		 ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("http-decodec",new HttpRequestDecoder());//3
		pipeline.addLast("http-aggregator",new HttpObjectAggregator(65536));
		pipeline.addLast("http-encodec",new HttpResponseEncoder());//5
		pipeline.addLast("http-chunked",new ChunkedWriteHandler());
       /*
			pipeline.addLast(new HttpServerCodec());
			pipeline.addLast(new HttpObjectAggregator(64*1024));
			pipeline.addLast(new ChunkedWriteHandler());
		*/
		pipeline.addLast("http-request",new HttpRequestHandler("/ws"));//4
		pipeline.addLast("WebSocket-protocol",new WebSocketServerProtocolHandler("/ws"));//1
		pipeline.addLast("WebSocket-request",new TextWebSocketFrameHandler());//2

    }
}
