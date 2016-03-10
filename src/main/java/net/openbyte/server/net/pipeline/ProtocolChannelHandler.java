package net.openbyte.server.net.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import net.openbyte.server.ServerConnection;
import net.openbyte.server.util.ByteBufDecoders;
import net.openbyte.server.util.PacketBuilder;

import java.util.ArrayList;

/**
 * A netty channel handler that follows all of the protocol guidelines of CodeUpdate.
 */
public class ProtocolChannelHandler extends ChannelHandlerAdapter {
    public static final ArrayList<ServerConnection> SERVER_CONNECTIONS = new ArrayList<ServerConnection>();

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        ByteBuf read = (ByteBuf) msg;
        int packetId = ByteBufDecoders.readVarInt(read);

        if (packetId == 0x00) {
            //validation packet
            String name = ByteBufDecoders.readUTF8(read);
            String email = ByteBufDecoders.readUTF8(read);
            int clientId = ByteBufDecoders.readVarInt(read);

            //generate server connection object representation and authenticate client
            ServerConnection connection = new ServerConnection(context.channel(), name, email, clientId);
            /*  [OLD]
                ByteBuf packet = Unpooled.buffer();
                ByteBufDecoders.writeVarInt(packet, 0x00);
                packet.writeBoolean(true);
                ByteBufDecoders.writeUTF8(packet, connection.getAuthenticationId());
            */
            ByteBuf packet = new PacketBuilder()
                    .varInt(0x00)
                    .bool(true)
                    .string(connection.getAuthenticationId())
                    .build();
            SERVER_CONNECTIONS.add(connection);

            // send authenticate packet
            context.writeAndFlush(packet);
            return;
        }

        // get client data
        String authenticationId = ByteBufDecoders.readUTF8(read);
        // create object representation of the connection
        ServerConnection client = null;
        for (ServerConnection clientone : SERVER_CONNECTIONS) {
            if (authenticationId.equals(clientone.getAuthenticationId())) {
                client = clientone;
            }
        }
        // check if authentication id to client was unsuccessful to prevent NPEs
        if (client == null) {
            return;
        }

        if (packetId == 0x01) {
            // add file packet
            String filePath = ByteBufDecoders.readUTF8(read);
            String content = ByteBufDecoders.readUTF8(read);

            // generate packet
            /** [OLD]
             *  ByteBuf packet = Unpooled.buffer();
             *  ByteBufDecoders.writeVarInt(packet, 0x01);
             *  ByteBufDecoders.writeUTF8(read, filePath);
             * ByteBufDecoders.writeUTF8(read, content);
             */
            ByteBuf packet = new PacketBuilder()
                    .varInt(0x01)
                    .string(filePath)
                    .string(content)
                    .build();

            // send packet
            for (ServerConnection connection : SERVER_CONNECTIONS) {
                if (!connection.getAuthenticationId().equals(authenticationId)) {
                    connection.getClientChannel().writeAndFlush(packet);
                }
            }
            return;
        }

        if (packetId == 0x02) {
            // update file packet
            String filePath = ByteBufDecoders.readUTF8(read);
            String content = ByteBufDecoders.readUTF8(read);

            // generate packet
            ByteBuf packet = new PacketBuilder()
                    .varInt(0x02)
                    .string(filePath)
                    .string(content)
                    .build();

            // send packet
            for (ServerConnection connection : SERVER_CONNECTIONS) {
                if(!connection.getAuthenticationId().equals(authenticationId)) {
                    connection.getClientChannel().writeAndFlush(packet);
                }
            }
            return;
        }

        if (packetId == 0x03) {
            // get clients packet
            // generate packet
            StringBuilder members = new StringBuilder();
            for (ServerConnection connection : SERVER_CONNECTIONS) {
                members.append(connection.getClientName() + " ");
            }

            ByteBuf packet = new PacketBuilder()
                    .varInt(0x03)
                    .varInt(SERVER_CONNECTIONS.size())
                    .string(members.toString())
                    .build();

            // send packet
            context.writeAndFlush(packet);
            return;
        }

        if (packetId == 0x04) {
            // get email packet
            String clientName = ByteBufDecoders.readUTF8(read);
            ServerConnection clientNameRepresentation = null;
            for (ServerConnection connection : SERVER_CONNECTIONS) {
                if (connection.getClientName().equals(clientName)) {
                    clientNameRepresentation = connection;
                }
            }
            if (clientNameRepresentation == null) {
                return;
            }

            // generate packet
            ByteBuf packet = new PacketBuilder()
                    .varInt(0x04)
                    .string(clientNameRepresentation.getEmailAddress())
                    .build();

            // send packet
            context.writeAndFlush(packet);
            return;
        }
    }
}
