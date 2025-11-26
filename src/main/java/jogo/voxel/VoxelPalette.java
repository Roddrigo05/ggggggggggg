package jogo.voxel;

import jogo.voxel.blocks.*;

import java.util.ArrayList;
import java.util.List;

public class VoxelPalette {
    private final List<VoxelBlockType> types = new ArrayList<>();

    public byte register(VoxelBlockType type) {
        types.add(type);
        int id = types.size() - 1;
        if (id > 255) throw new IllegalStateException("Too many voxel block types (>255)");
        return (byte) id;
    }

    public VoxelBlockType get(byte id) {
        int idx = Byte.toUnsignedInt(id);
        if (idx < 0 || idx >= types.size()) return new AirBlockType();
        return types.get(idx);
    }

    public int size() { return types.size(); }

    public static VoxelPalette defaultPalette() {
        VoxelPalette p = new VoxelPalette();
        p.register(new AirBlockType());   // id 0
        p.register(new StoneBlockType()); // id 1
        p.register(new bedrock());// id 2
        p.register(new water());// id 3
        p.register(new grass());// id 4
        p.register(new dirt());// id 5
        p.register(new wood());// id 6
        p.register(new folhas());// id 7
        p.register(new ferro());// id 8
        p.register(new RedstoneBlock());// id 9
        p.register(new LapisLazuliBlock());// id 10
        p.register(new gold());// id 11
        p.register(new diamond());// id 12
        p.register(new emerald());// id 13
        return p;
    }

    public static final byte AIR_ID = 0;
    public static final byte STONE_ID = 1;
    public static final byte BEDROCK_ID = 2;
    public static final byte WATER_ID = 3;
    public static final byte GRASS_ID = 4;
    public static final byte DIRT_ID = 5;
    public static final byte WOOD_ID = 6;
    public static final byte LEAVES_ID = 7;
    public static final byte IRON_ID = 8;
    public static final byte REDSTONE_ID = 9;
    public static final byte LAPIS_ID = 10;
    public static final byte GOLD_ID = 11;
    public static final byte DIAMOND_ID = 12;
    public static final byte EMERALD_ID = 13;
}
