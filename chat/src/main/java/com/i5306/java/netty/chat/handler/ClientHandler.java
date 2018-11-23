package com.i5306.java.netty.chat.handler;


import com.i5306.java.netty.chat.codec.PacketCodec;
import com.i5306.java.netty.chat.protocol.Packet;
import com.i5306.java.netty.chat.protocol.request.LoginRequestPacket;
import com.i5306.java.netty.chat.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) msg;
            Packet packet = PacketCodec.INSTANCE.decode(byteBuf);

            if (packet instanceof LoginResponsePacket) {
                LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

                if (loginResponsePacket.isSuccess()) {
                    System.out.println(LocalDateTime.now() + ":客户端登录成功");
                } else {
                    System.out.println(LocalDateTime.now() + ":客户端登录失败,原因：" + loginResponsePacket.getReason());
                }
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        // 创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUserName("ming");
        loginRequestPacket.setPassword("ge");

        // 编码
        ByteBuf encodeData = PacketCodec.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        // 写数据
        ctx.channel().writeAndFlush(encodeData);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
