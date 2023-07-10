package superlord.prehistoricfauna.common.world.chunkgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.util.Mth;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import superlord.prehistoricfauna.PrehistoricFauna;
import superlord.prehistoricfauna.common.util.FastNoise;
import superlord.prehistoricfauna.common.world.biome.surfacedecorators.BasicSurfaceDecorator;
import superlord.prehistoricfauna.common.world.biome.surfacedecorators.SurfaceDecorator;
import superlord.prehistoricfauna.common.world.biome.surfacedecorators.SurfaceDecorators;
import superlord.prehistoricfauna.init.PFBlocks;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class TriassicChunkGenerator extends ChunkGenerator {
    public static final Codec<TriassicChunkGenerator> CODEC = RecordCodecBuilder.create((codec) -> commonCodec(codec).and(codec.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter((generator) -> generator.biomeSource),
                    NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter((generator) -> generator.settings)))
            .apply(codec, codec.stable(TriassicChunkGenerator::new)));

    protected final Holder<NoiseGeneratorSettings> settings;
    protected final Climate.Sampler sampler;
    private long seed = 0L;
    private static final FastNoise noise = new FastNoise(0);
    static {
        noise.SetNoiseType(FastNoise.NoiseType.Simplex);
    }
    private float[][][] terrainShapeSamplePoints;

    public TriassicChunkGenerator(Registry<StructureSet> pStructureSets, BiomeSource pBiomeSource, Holder<NoiseGeneratorSettings> settings) {
        this(pStructureSets, pBiomeSource, settings, 0L);
    }

    public TriassicChunkGenerator(Registry<StructureSet> pStructureSets, BiomeSource pBiomeSource, Holder<NoiseGeneratorSettings> settings, long seed) {
        super(pStructureSets, Optional.empty(), pBiomeSource);
        this.settings = settings;
        this.seed = seed;
        this.sampler = new Climate.Sampler(
                new FastNoiseDensityFunction(noise),
                new FastNoiseDensityFunction(noise, 400),
                new FastNoiseDensityFunction(noise, -400),
                new FastNoiseDensityFunction(noise, 800),
                new FastNoiseDensityFunction(noise, -800),
                new FastNoiseDensityFunction(noise, 1600),
                new ArrayList<>());
        initializeNoise(seed);
    }

    public void initializeNoise(long seed) {
        int seedBits = (int)(seed >> 32);
        if (noise.GetSeed() != seedBits) {
            noise.SetSeed(seedBits);
        }
    }

    @Override
    protected Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return new TriassicChunkGenerator(this.structureSets, this.biomeSource.withSeed(seed), this.settings, this.seed);
    }

    @Override
    public Climate.Sampler climateSampler() {
        return this.sampler;
    }

    @Override
    public void applyCarvers(WorldGenRegion region, long seed, BiomeManager manager, StructureFeatureManager structureFeatureManager, ChunkAccess chunk, GenerationStep.Carving genStep) {
        //im not touching this
        //..for now :)
    }

    @Override
    public void buildSurface(WorldGenRegion region, StructureFeatureManager structureFeatureManager, ChunkAccess chunk) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                //when it encounters a new surface, check the biome and generate the corresponding surface.
                int startHeight = chunk.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z);
                pos.set(x + chunk.getPos().getMinBlockX(), startHeight, z + chunk.getPos().getMinBlockZ());

                boolean isInSolid = false;
                boolean visibleToSun = true;
                while (pos.getY() > this.getMinY() + 5) {
                    ///PrehistoricFauna.LOGGER.info("b");
                    if (chunk.getBlockState(pos) == this.settings.value().defaultBlock()) {
                        if (!isInSolid) {
                            ResourceLocation biome = region.getBiome(pos).value().getRegistryName();
                            SurfaceDecorators.getSurfaceDecorator(biome).buildSurface(pos, this.getSeaLevel(), visibleToSun, chunk, settings.value());
                            isInSolid = true;
                            visibleToSun = false;
                        }
                    } else {
                        isInSolid = false;
                    }

                    pos.move(Direction.DOWN);

                }
            }
        }
    }

    @Override
    //stolen from NoiseBasedChunkGenerator
    public void spawnOriginalMobs(WorldGenRegion region) {
        ChunkPos chunkpos = region.getCenter();
        Holder<Biome> holder = region.getBiome(chunkpos.getWorldPosition().atY(region.getMaxBuildHeight() - 1));
        WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(RandomSupport.seedUniquifier()));
        worldgenrandom.setDecorationSeed(region.getSeed(), chunkpos.getMinBlockX(), chunkpos.getMinBlockZ());
        NaturalSpawner.spawnMobsForChunkGeneration(region, holder, chunkpos, worldgenrandom);
    }

    @Override
    //where the magic happens
    public CompletableFuture<ChunkAccess> fillFromNoise(Executor executor, Blender blender, StructureFeatureManager structureFeatureManager, ChunkAccess chunk) {
        fillNoiseSampleArrays(chunk);
        Heightmap[] heightmaps = {chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.OCEAN_FLOOR_WG), chunk.getOrCreateHeightmapUnprimed(Heightmap.Types.WORLD_SURFACE_WG)};
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = this.getMaxY(); y >= this.getMinY(); y--) {
                    pos.set(x, y, z);
                    float sample = sampleDensityFromArray(terrainShapeSamplePoints, x, y, z);

                    BlockState state;
                    if (sample > 0) {
                        state = settings.value().defaultBlock();
                    } else {
                        state =  y > this.getSeaLevel() ? Blocks.AIR.defaultBlockState() : Blocks.WATER.defaultBlockState();
                    }

                    for (Heightmap heightmap : heightmaps) {
                        heightmap.update(x, y, z, state);
                    }

                    chunk.setBlockState(pos, state, false);
                }
            }
        }

        return CompletableFuture.completedFuture(chunk);
    }

    private float sampleDensity(float x, float y, float z) {
        int seaLevel = this.settings.value().seaLevel();
        if (y > seaLevel) y = y + 3;

        float frequency1 = 0.3F;
        float sample = noise.GetNoise(x * frequency1, y * frequency1 * 0.8F, z * frequency1);

        float floor = -0.2F;
        float smoothness = 0.001F;
        float h = Mth.clamp(0.5F + 0.5F * (sample - floor) / smoothness, 0.0F, 1.0F);
        sample = Mth.lerp(sample, floor, h) - smoothness * h * (1.0F - h);

        float bigRockFrequency = 1.0F;
        float rockNoise = noise.GetNoise(x * bigRockFrequency, (y * frequency1) + 512, z * bigRockFrequency);
        float bigRockNoise = Mth.sqrt(sample * sample + rockNoise * rockNoise);
        bigRockNoise = (sample < 0 || rockNoise < 0) ? 1 : bigRockNoise;
        float bigRockStrength = 0.2F;
        bigRockNoise *= bigRockStrength;
        bigRockNoise += (1F - bigRockStrength);

        float hugeCliffFrequency = 0.3F;
        float hugeCliffNoise = noise.GetNoise(x * hugeCliffFrequency, 2834, z * hugeCliffFrequency);
        hugeCliffNoise = (float) Mth.clamp(Math.pow(1.3 * hugeCliffNoise, 12), 0, 1);
        float hugeCliffWobble = -0.5F * Mth.cos(2F * Mth.PI * hugeCliffNoise) + 0.5F;
        hugeCliffWobble *= 1.5F;

        float lumpFrequency = 4.3F;
        float cliffLumpiness = noise.GetNoise(x * lumpFrequency, y * lumpFrequency * 0.8F, z * lumpFrequency);
        cliffLumpiness *= hugeCliffWobble * 0.1F;
        sample += cliffLumpiness;

        float frequency2 = 2.5F;
        sample += Mth.abs(noise.GetNoise(x * frequency2, y * frequency2, z * frequency2) * 0.2F);
        float frequency3 = 3.5F;
        sample += Mth.abs(noise.GetNoise(x * frequency3, y * frequency3, z * frequency3) * 0.05F);

        float riverFrequency = 0.1F;
        float riverNoise = noise.GetNoise(x * riverFrequency, 0, z * riverFrequency);
        riverNoise = 1.0F - riverNoise * riverNoise;
        riverNoise *= 0.1;

        float rockFrequency = 1.2F;
        float pebbleNoise = noise.GetNoise(x * rockFrequency, 0, z * rockFrequency);
        pebbleNoise = Mth.abs(pebbleNoise);
        pebbleNoise *= 1;

        sample += pebbleNoise;
        sample -= riverNoise;
        sample -= 0.15F;
        sample -= (y - this.settings.value().seaLevel() - hugeCliffNoise * 64) / (16.0F / bigRockNoise * (hugeCliffWobble + 1));

       //PrehistoricFauna.LOGGER.info("a");

        return sample;
    }

    public void fillNoiseSampleArrays(ChunkAccess chunk) {
        int hSamplePoints = (int) Math.ceil(16 * 0.3F);
        int vSamplePoints = (int) Math.ceil(this.getGenDepth() * 0.15F);

        float hOffset = (16.0F / (float) hSamplePoints);
        float vOffset = ((float)this.getGenDepth() / (float) vSamplePoints);

        this.terrainShapeSamplePoints = new float[hSamplePoints + 1][vSamplePoints][hSamplePoints + 1];
        for (int sX = 0; sX < hSamplePoints + 1; sX++) {
            for (int sZ = 0; sZ < hSamplePoints + 1; sZ++) {
                for (int sY = 0; sY < vSamplePoints; sY++) {

                    float cX = sX * hOffset;
                    float cY = sY * vOffset;
                    float cZ = sZ * hOffset;

                    float x = cX + chunk.getPos().getMinBlockX();
                    float z = cZ + chunk.getPos().getMinBlockZ();
                    float y = cY + chunk.getMinBuildHeight();

                    terrainShapeSamplePoints[sX][sY][sZ] = sampleDensity(x, y, z);
                }
            }
        }
    }

    public float sampleDensityFromArray(float[][][] densityArray, int localX, int localY, int localZ) {
        int maxXZ = 16;
        int maxY = this.getGenDepth();
        float xzSampleRes = (float)(densityArray.length-1) / (float)maxXZ;
        float ySampleRes =  (float)(densityArray[0].length-1) / (float)maxY;

        float sampleX = localX * xzSampleRes;
        float sxFrac = Mth.frac(sampleX);
        int sxF = Mth.floor(sampleX);
        int sxC = Mth.ceil(sampleX);
        float sampleY = (localY - this.getMinY()) * ySampleRes;
        float syFrac = Mth.frac(sampleY);
        int syF = Mth.floor(sampleY);
        int syC = Mth.ceil(sampleY);
        float sampleZ = localZ * xzSampleRes;
        float szFrac = Mth.frac(sampleZ);
        int szF = Mth.floor(sampleZ);
        int szC = Mth.ceil(sampleZ);

        float xLerp1 = Mth.lerp(sxFrac, densityArray[sxF][syF][szF], densityArray[sxC][syF][szF]);
        float xLerp2 = Mth.lerp(sxFrac, densityArray[sxF][syF][szC], densityArray[sxC][syF][szC]);
        float zLerp1 = Mth.lerp(szFrac, xLerp1, xLerp2);

        float xLerp3 = Mth.lerp(sxFrac, densityArray[sxF][syC][szF], densityArray[sxC][syC][szF]);
        float xLerp4 = Mth.lerp(sxFrac, densityArray[sxF][syC][szC], densityArray[sxC][syC][szC]);
        float zLerp2 = Mth.lerp(szFrac, xLerp3, xLerp4);

        float yLerp = Mth.lerp(Mth.lerp(Mth.clamp((float)(localY - this.getSeaLevel()) / 64F, 0F, 1F), syFrac * syFrac * syFrac, syFrac), zLerp1, zLerp2);

        return yLerp;
    }

    @Override
    public int getSeaLevel() {
        return settings.value().seaLevel();
    }

    @Override
    public int getGenDepth() {
        return this.getMaxY() - this.getMinY();
    }

    @Override
    public int getMinY() {
        return -64;
    }
    public int getMaxY() {
        return 256;
    }

    @Override
    public int getBaseHeight(int p_156153_, int p_156154_, Heightmap.Types p_156155_, LevelHeightAccessor p_156156_) {
        return 0;
    }

    @Override
    //todo: this
    public NoiseColumn getBaseColumn(int p_156150_, int p_156151_, LevelHeightAccessor chunk) {
        BlockState[] states = new BlockState[chunk.getHeight()];
        int iY = 0;
        for (int y = chunk.getMinBuildHeight(); y < chunk.getMaxBuildHeight(); y++) {
            states[iY] = Blocks.AIR.defaultBlockState();
            iY++;
        }

        return new NoiseColumn(chunk.getMinBuildHeight(), states);
    }

    @Override
    public void addDebugScreenInfo(List<String> p_208054_, BlockPos p_208055_) {

    }
}
