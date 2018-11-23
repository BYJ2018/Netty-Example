package com.i5306.java.netty.chat.protocol.response;

import com.i5306.java.netty.chat.protocol.Command;
import com.i5306.java.netty.chat.protocol.Packet;

public class LoginResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
