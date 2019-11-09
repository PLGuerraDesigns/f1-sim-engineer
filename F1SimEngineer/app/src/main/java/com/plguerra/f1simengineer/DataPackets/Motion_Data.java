package com.plguerra.f1simengineer.DataPackets;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Motion_Data {
    float   worldPositionX;           // World space X position
    float   worldPositionY;           // World space Y position
    float   worldPositionZ;           // World space Z position
    float   worldVelocityX;           // Velocity in world space X
    float   worldVelocityY;           // Velocity in world space Y
    float   worldVelocityZ;           // Velocity in world space Z
    float   worldForwardDirX;         // World space forward X direction (normalised)
    float   worldForwardDirY;         // World space forward Y direction (normalised)
    float   worldForwardDirZ;         // World space forward Z direction (normalised)
    float   worldRightDirX;           // World space right X direction (normalised)
    float   worldRightDirY;           // World space right Y direction (normalised)
    float   worldRightDirZ;           // World space right Z direction (normalised)
    float   gForceLateral;            // Lateral G-Force component
    float   gForceLongitudinal;       // Longitudinal G-Force component
    float   gForceVertical;           // Vertical G-Force component
    float   yaw;                      // Yaw angle in radians
    float   pitch;                    // Pitch angle in radians
    float   roll;                     // Roll angle in radians
    public Motion_Data(byte[] content) {
        ByteBuffer bb = ByteBuffer.wrap(content);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        worldPositionX = bb.getFloat();
        worldPositionY = bb.getFloat();
        worldPositionZ = bb.getFloat();
        worldVelocityX = bb.getFloat();
        worldVelocityY = bb.getFloat();
        worldVelocityZ = bb.getFloat();
        worldForwardDirX = bb.getShort() / 32767.0f;
        worldForwardDirY = bb.getShort() / 32767.0f;
        worldForwardDirZ = bb.getShort() / 32767.0f;
        worldRightDirX = bb.getShort() / 32767.0f;
        worldRightDirY = bb.getShort() / 32767.0f;
        worldRightDirZ = bb.getShort() / 32767.0f;
        gForceLateral = bb.getFloat();
        gForceLongitudinal = bb.getFloat();
        gForceVertical = bb.getFloat();
        yaw = bb.getFloat();
        pitch = bb.getFloat();
        roll = bb.getFloat();
    }


    public String toString(){
        String ret = "World Position X: "+worldPositionX+"\n";
        ret += "World Position Y: "+worldPositionY+"\n";
        ret += "World Position Z: "+worldPositionZ+"\n";

        ret += "World Forward Dir X: "+worldForwardDirX+"\n";
        ret += "World Forward Dir Y: "+worldForwardDirY+"\n";
        ret += "World Forward Dir Z: "+worldForwardDirZ+"\n";
        ret += "World Right Dir X: "+worldRightDirX+"\n";
        ret += "World Right Dir Y: "+worldRightDirY+"\n";
        ret += "World Right Dir Z: "+worldRightDirZ+"\n";

        ret += "G Force Lateral: "+gForceLateral+"\n";
        ret += "G Force Longitudinal: "+gForceLongitudinal+"\n";
        ret += "G Force Vertical: "+gForceVertical+"\n";
        ret += "Yaw: "+yaw+"\n";
        ret += "Pitch: "+pitch+"\n";
        ret += "Roll: "+roll+"\n";

        return ret;
    }
}
