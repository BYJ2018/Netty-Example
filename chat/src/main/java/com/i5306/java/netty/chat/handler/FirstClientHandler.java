package com.i5306.java.netty.chat.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.time.LocalDateTime;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(String.format("客户端写出数据:%s", LocalDateTime.now()));

        // 1、获取数据
        ByteBuf byteBuf = getByteBuf(ctx);
        System.out.println("最大容量：" + byteBuf.maxCapacity());
        System.out.println("容量：" + byteBuf.capacity());
        System.out.println("可读字节：" + byteBuf.readableBytes());
        System.out.println("可写字节：" + byteBuf.writableBytes());

        // 2、写回数据
        ctx.channel().writeAndFlush(byteBuf);

    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        ByteBuf buffer = ctx.alloc().buffer();

        byte[] bytes = "我是客户端!".getBytes(CharsetUtil.UTF_8);

        buffer.writeBytes(bytes);

        return buffer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof  ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) msg;
            System.out.println(String.format("%s:客户端获取数据 -> %s", LocalDateTime.now(), byteBuf.toString(CharsetUtil.UTF_8)));
        }
    }
}
