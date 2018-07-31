package messenger.client;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import messenger.server.ChatServerHandler;

public class ChatClientInitializer extends ChannelInitializer<SocketChannel> {
	private final SslContext sslCtx;
	
	public ChatClientInitializer(SslContext sslCtx) {
		this.sslCtx = sslCtx;
	}
	
	@Override
	protected void initChannel(SocketChannel arg0) throws Exception {
		ChannelPipeline pipeline = arg0.pipeline();
		
		//pipeline.addLast(sslCtx.newHandler(arg0.alloc(), ChatClient.HOST, ChatClient.Port));
		pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast(new StringDecoder());
		pipeline.addLast(new StringEncoder());
		
		pipeline.addLast(new ChatServerHandler());
	}
	
}
