package com.plguerra.f1simengineer.DataPackets;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PacketHeader {
    public static int HEADER_SIZE = 23;
    public int     packetFormat;         // 2019
    public short   gameMajorVersion;     // Game major version - "X.00"
    public short   gameMinorVersion;     // Game minor version - "1.XX"
    public short   packetVersion;        // Version of this packet type, all start from 1
    public short   packetId;             // Identifier for the packet type, see below
    public BigInteger sessionUID;        // Unique identifier for the session
    public float   sessionTime;          // Session timestamp
    public Long    frameIdentifier;      // Identifier for the frame the data was retrieved on
    public short   playerCarIndex;       // Index of player's car in the array


    public PacketHeader(byte[] content) {
        ByteBuffer bb = ByteBuffer.wrap(content);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        packetFormat = bb.getShort();
        gameMajorVersion = bb.get();
        gameMinorVersion = bb.get();
        packetVersion = bb.get();
        packetId = bb.get();
        sessionUID = BigInteger.valueOf(bb.getLong());
        sessionTime = bb.getFloat();
        frameIdentifier = Long.valueOf(bb.getInt());
        playerCarIndex = bb.get();
    }

}
