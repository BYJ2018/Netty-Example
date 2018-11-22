package com.i5306.java.netty.chat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.time.LocalDateTime;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) msg;
            System.out.println(String.format("%s:服务端获取数据 -> %s", LocalDateTime.now(), byteBuf.toString(CharsetUtil.UTF_8)));

            System.out.println(String.format("%s:服务端写回数据", LocalDateTime.now()));
            ByteBuf out = getByteBuf(ctx);
            ctx.channel().writeAndFlush(out);
        }
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "我是服务端!!!".getBytes(CharsetUtil.UTF_8);

        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes);
        return buffer;
    }
}
