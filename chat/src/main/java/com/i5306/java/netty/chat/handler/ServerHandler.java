package com.i5306.java.netty.chat.handler;

import com.i5306.java.netty.chat.codec.PacketCodec;
import com.i5306.java.netty.chat.protocol.Packet;
import com.i5306.java.netty.chat.protocol.request.LoginRequestPacket;
import com.i5306.java.netty.chat.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof ByteBuf) {
            ByteBuf requestByteBuf = (ByteBuf) msg;

            // 解码
            Packet packet = PacketCodec.INSTANCE.decode(requestByteBuf);
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());

            // 判断是否是登录请求数据包
            if (packet instanceof LoginRequestPacket) {
                LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

                if (valid(loginRequestPacket)) {
                    loginResponsePacket.setSuccess(true);
                    System.out.println(loginRequestPacket);
                    System.out.println("登录成功:" + LocalDateTime.now());
                } else{
                    loginResponsePacket.setReason("账号密码校验失败");
                    loginResponsePacket.setSuccess(false);
                }
            }

            // 编码
            ByteBuf responseByteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
