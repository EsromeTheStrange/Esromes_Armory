package net.esromethestrange.esromes_armory.screen;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.math.BlockPos;

public class WorkbenchData {
    private BlockPos blockPos;

    public WorkbenchData(BlockPos blockPos){
        this.blockPos = blockPos;
    }

    public static final PacketCodec<PacketByteBuf, WorkbenchData> PACKET_CODEC = PacketCodec.of(
        (value, buf) -> {
            buf.writeBlockPos(value.blockPos);
        },
        buf -> {
            BlockPos buf_blockPos = buf.readBlockPos();

            return new WorkbenchData(buf_blockPos);
        }
    );

    public BlockPos blockPos(){
        return blockPos;
    }
}
