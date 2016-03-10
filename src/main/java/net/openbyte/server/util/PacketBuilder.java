package net.openbyte.server.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;

/**
 * Allows for seamless building of packets.
 */
public class PacketBuilder {
    private int opcode;
    private ByteBuf byteBuf = Unpooled.buffer();

    public PacketBuilder(){}

    public PacketBuilder varInt(int theInteger) {
        ByteBufDecoders.writeVarInt(byteBuf, theInteger);
        return this;
    }

    public PacketBuilder bool(boolean flag) {
        byteBuf.writeBoolean(flag);
        return this;
    }

    public PacketBuilder string(String string) throws IOException {
        ByteBufDecoders.writeUTF8(byteBuf, string);
        return this;
    }

    public ByteBuf build() {
        return byteBuf;
    }
}
