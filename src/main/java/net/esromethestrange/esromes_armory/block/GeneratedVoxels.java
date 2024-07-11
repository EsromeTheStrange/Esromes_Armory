package net.esromethestrange.esromes_armory.block;

import net.minecraft.block.Block;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

import java.util.stream.Stream;

public class GeneratedVoxels {
    public static VoxelShape ANVIL_SHAPE = Stream.of(
            Stream.of(
                    Block.createCuboidShape(4, 0, 6, 14, 2, 10),
                    Block.createCuboidShape(12, 0, 10, 14, 2, 12),
                    Block.createCuboidShape(4, 0, 4, 6, 2, 6),
                    Block.createCuboidShape(12, 0, 4, 14, 2, 6),
                    Block.createCuboidShape(4, 0, 10, 6, 2, 12),
                    Block.createCuboidShape(3, 0, 4, 4, 1, 12),
                    Block.createCuboidShape(14, 0, 4, 15, 1, 12),
                    Block.createCuboidShape(7, 0, 10, 8, 1, 11),
                    Block.createCuboidShape(10, 0, 10, 11, 1, 11),
                    Block.createCuboidShape(10, 0, 5, 11, 1, 6),
                    Block.createCuboidShape(7, 0, 5, 8, 1, 6),
                    Block.createCuboidShape(6, 0, 10, 7, 2, 11),
                    Block.createCuboidShape(11, 0, 10, 12, 2, 11),
                    Block.createCuboidShape(11, 0, 5, 12, 2, 6),
                    Block.createCuboidShape(6, 0, 5, 7, 2, 6)
            ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get(),
            Block.createCuboidShape(5, 6, 4, 13, 8, 12),
            Block.createCuboidShape(14, 7, 5, 15, 8, 11),
            Block.createCuboidShape(13, 6, 5, 14, 7, 11),
            Block.createCuboidShape(13, 7, 4, 14, 8, 12),
            Block.createCuboidShape(1, 7, 6, 3, 8, 10),
            Block.createCuboidShape(0, 7, 7, 1, 8, 9),
            Block.createCuboidShape(14, 6, 6, 15, 7, 10),
            Block.createCuboidShape(15, 7, 6, 16, 8, 10),
            Block.createCuboidShape(13, 5, 6, 14, 6, 10),
            Block.createCuboidShape(12, 4, 7, 13, 5, 9),
            Block.createCuboidShape(11, 3, 7, 12, 4, 9),
            Block.createCuboidShape(5, 3, 7, 7, 4, 9),
            Block.createCuboidShape(4, 4, 7, 5, 5, 9),
            Block.createCuboidShape(1, 6, 7, 3, 7, 9),
            Block.createCuboidShape(3, 6, 5, 5, 8, 11),
            Block.createCuboidShape(3, 5, 6, 5, 6, 10),
            Block.createCuboidShape(7, 2, 6, 11, 4, 10),
            Block.createCuboidShape(5, 4, 6, 12, 5, 10),
            Block.createCuboidShape(5, 5, 6, 13, 6, 10),
            Block.createCuboidShape(5, 5, 5, 13, 6, 6),
            Block.createCuboidShape(5, 5, 10, 13, 6, 11)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
}
