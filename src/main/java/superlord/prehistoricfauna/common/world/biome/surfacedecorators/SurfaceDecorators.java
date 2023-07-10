package superlord.prehistoricfauna.common.world.biome.surfacedecorators;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import superlord.prehistoricfauna.PrehistoricFauna;
import superlord.prehistoricfauna.init.PFBlocks;

import java.util.HashMap;

public class SurfaceDecorators {
    public static final HashMap<ResourceLocation, SurfaceDecorator> BIOME_TO_SURFACE_DECORATOR = new HashMap<>();
    private static final SurfaceDecorator DEFAULT_DECORATOR = new DefaultSurfaceDecorator();
    public static final SurfaceDecorator TEST_DECORATOR = new BasicSurfaceDecorator(Blocks.RED_WOOL.defaultBlockState(), Blocks.GREEN_WOOL.defaultBlockState(), Blocks.BLUE_WOOL.defaultBlockState(), 4);

    static {
        register(new ResourceLocation(PrehistoricFauna.MOD_ID, "chinle_wooded_mountains"),
                 new BasicSurfaceDecorator(PFBlocks.LOAM.get().defaultBlockState(), PFBlocks.PACKED_LOAM.get().defaultBlockState(), PFBlocks.PACKED_LOAM.get().defaultBlockState(), 4));
    }

    public static void register(ResourceLocation biome, SurfaceDecorator decorator) {
        BIOME_TO_SURFACE_DECORATOR.put(biome, decorator);
    }
    public static SurfaceDecorator getSurfaceDecorator(ResourceLocation biomeLocation) {
        return BIOME_TO_SURFACE_DECORATOR.getOrDefault(biomeLocation, DEFAULT_DECORATOR);
    }
}
