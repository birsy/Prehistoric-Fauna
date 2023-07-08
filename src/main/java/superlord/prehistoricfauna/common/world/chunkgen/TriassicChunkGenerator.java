package superlord.prehistoricfauna.common.world.chunkgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.server.level.WorldGenRegion;
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
import superlord.prehistoricfauna.common.util.FastNoise;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        //or this
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
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        int minX = chunk.getPos().getMinBlockX();
        int minZ = chunk.getPos().getMinBlockZ();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = this.getMinY(); y < this.getMaxY(); y++) {
                    pos.set(x, y, z);
                    float sample = noise.GetNoise(minX + x, y, minZ + z);
                    sample -= (y - this.settings.value().seaLevel()) / 16.0F;

                    if (sample > 0) {
                        chunk.setBlockState(pos, settings.value().defaultBlock(), false);
                    } else {
                        chunk.setBlockState(pos, Blocks.AIR.defaultBlockState(), false);
                    }
                }
            }
        }
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getSeaLevel() {
        return 64;
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
