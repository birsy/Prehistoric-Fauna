package superlord.prehistoricfauna.common.entity.triassic.ischigualasto;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superlord.prehistoricfauna.common.blocks.DinosaurEggBlock;
import superlord.prehistoricfauna.common.entity.DinosaurEntity;
import superlord.prehistoricfauna.common.entity.cretaceous.hellcreek.Ankylosaurus;
import superlord.prehistoricfauna.common.entity.cretaceous.hellcreek.Dakotaraptor;
import superlord.prehistoricfauna.common.entity.cretaceous.hellcreek.Thescelosaurus;
import superlord.prehistoricfauna.common.entity.cretaceous.hellcreek.Triceratops;
import superlord.prehistoricfauna.common.entity.cretaceous.hellcreek.Tyrannosaurus;
import superlord.prehistoricfauna.common.entity.goal.CrepuscularSleepGoal;
import superlord.prehistoricfauna.common.entity.goal.DinosaurLookAtGoal;
import superlord.prehistoricfauna.common.entity.goal.DinosaurRandomLookGoal;
import superlord.prehistoricfauna.common.entity.jurassic.kayenta.Dilophosaurus;
import superlord.prehistoricfauna.common.entity.jurassic.kayenta.Megapnosaurus;
import superlord.prehistoricfauna.common.entity.jurassic.morrison.Allosaurus;
import superlord.prehistoricfauna.common.entity.jurassic.morrison.Camarasaurus;
import superlord.prehistoricfauna.common.entity.jurassic.morrison.Ceratosaurus;
import superlord.prehistoricfauna.common.entity.jurassic.morrison.Dryosaurus;
import superlord.prehistoricfauna.common.entity.jurassic.morrison.Hesperornithoides;
import superlord.prehistoricfauna.common.entity.jurassic.morrison.Stegosaurus;
import superlord.prehistoricfauna.common.entity.triassic.chinle.Coelophysis;
import superlord.prehistoricfauna.common.entity.triassic.chinle.Poposaurus;
import superlord.prehistoricfauna.common.entity.triassic.chinle.Postosuchus;
import superlord.prehistoricfauna.config.PrehistoricFaunaConfig;
import superlord.prehistoricfauna.init.PFBlocks;
import superlord.prehistoricfauna.init.PFEntities;
import superlord.prehistoricfauna.init.PFItems;
import superlord.prehistoricfauna.init.PFSounds;

public class Hyperodapedon extends DinosaurEntity {

	private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(Hyperodapedon.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_DIGGING = SynchedEntityData.defineId(Hyperodapedon.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> ALBINO = SynchedEntityData.defineId(Hyperodapedon.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> MELANISTIC = SynchedEntityData.defineId(Hyperodapedon.class, EntityDataSerializers.BOOLEAN);
	private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(PFBlocks.SCYTOPHYLLUM.get().asItem());
	private static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(Hyperodapedon.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> NATURAL_LOVE = SynchedEntityData.defineId(Hyperodapedon.class, EntityDataSerializers.BOOLEAN);
	private int maxHunger = 10;
	private int currentHunger = 10;
	int hungerTick = 0;
	private int lastInLove = 0;
	private int isDigging;
	int loveTick = 0;

	public Hyperodapedon(EntityType<? extends Hyperodapedon> type, Level level) {
		super(type, level);
	}

	public boolean hasEgg() {
		return this.entityData.get(HAS_EGG);
	}

	private void setHasEgg(boolean hasEgg) {
		this.entityData.set(HAS_EGG, hasEgg);
	}

	public boolean isDigging() {
		return this.entityData.get(IS_DIGGING);
	}

	private void setDigging(boolean isDigging) {
		this.isDigging = isDigging ? 1 : 0;
		this.entityData.set(IS_DIGGING, isDigging);
	}

	public boolean isFood(ItemStack stack) {
		return stack.getItem() == PFBlocks.SCYTOPHYLLUM.get().asItem();
	}

	public boolean isAlbino() {
		return this.entityData.get(ALBINO);
	}

	private void setAlbino(boolean isAlbino) {
		this.entityData.set(ALBINO, isAlbino);
	}

	public boolean isMelanistic() {
		return this.entityData.get(MELANISTIC);
	}

	private void setMelanistic(boolean isMelanistic) {
		this.entityData.set(MELANISTIC, isMelanistic);
	}

	public boolean isInLoveNaturally() {
		return this.entityData.get(NATURAL_LOVE);
	}

	private void setInLoveNaturally(boolean isInLoveNaturally) {
		this.entityData.set(NATURAL_LOVE, isInLoveNaturally);
	}

	public int getCurrentHunger() {
		return this.currentHunger;
	}

	private void setHunger(int currentHunger) {
		this.currentHunger = currentHunger;
	}

	public int getHalfHunger() {
		return maxHunger / 2;
	}

	public int getThreeQuartersHunger() {
		return (maxHunger / 4) * 3;
	}

	public boolean isEating() {
		return this.entityData.get(EATING);
	}

	private void setEating(boolean isEating) {
		this.entityData.set(EATING, isEating);
	}

	public void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(HAS_EGG, false);
		this.entityData.define(IS_DIGGING, false);
		this.entityData.define(ALBINO, false);
		this.entityData.define(MELANISTIC, false);
		this.entityData.define(EATING, false);
		this.entityData.define(NATURAL_LOVE, false);
	}

	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putBoolean("HasEgg", this.hasEgg());
		compound.putBoolean("IsAlbino", this.isAlbino());
		compound.putBoolean("IsMelanistic", this.isMelanistic());
		compound.putInt("MaxHunger", this.currentHunger);
		compound.putBoolean("IsEating", this.isEating());
		compound.putBoolean("InNaturalLove", this.isInLoveNaturally());
	}

	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setHasEgg(compound.getBoolean("HasEgg"));
		this.setAlbino(compound.getBoolean("IsAlbino"));
		this.setMelanistic(compound.getBoolean("IsMelanistic"));
		this.setEating(compound.getBoolean("IsEating"));
		this.setHunger(compound.getInt("MaxHunger"));
		this.setInLoveNaturally(compound.getBoolean("InNaturalLove"));
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
		Random random = new Random();
		int birthNumber = random.nextInt(799);
		if (birthNumber >= 0 && birthNumber < 4) {
			this.setAlbino(true);
		} else if (birthNumber >= 4 && birthNumber < 7) {
			this.setMelanistic(true);
		}
		this.setHunger(this.maxHunger);
		return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25F));
		this.goalSelector.addGoal(0, new Hyperodapedon.MateGoal(this, 1.0D));
		this.goalSelector.addGoal(0, new Hyperodapedon.NaturalMateGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, TEMPTATION_ITEMS, false));
		this.goalSelector.addGoal(5, new DinosaurLookAtGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(6, new DinosaurRandomLookGoal(this));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Player>(this, Player.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Tyrannosaurus>(this, Tyrannosaurus.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Dakotaraptor>(this, Dakotaraptor.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Allosaurus>(this, Allosaurus.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Ceratosaurus>(this, Ceratosaurus.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Camarasaurus>(this, Camarasaurus.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Triceratops>(this, Triceratops.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Ankylosaurus>(this, Ankylosaurus.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Stegosaurus>(this, Stegosaurus.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Thescelosaurus>(this, Thescelosaurus.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Dryosaurus>(this, Dryosaurus.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Hesperornithoides>(this, Hesperornithoides.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Herrerasaurus>(this, Herrerasaurus.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Exaeretodon>(this, Exaeretodon.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Chromogisaurus>(this, Chromogisaurus.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Saurosuchus>(this, Saurosuchus.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Dilophosaurus>(this, Dilophosaurus.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Megapnosaurus>(this, Megapnosaurus.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Poposaurus>(this, Poposaurus.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Coelophysis>(this, Coelophysis.class, 10F, 1.7D, 1.5D));
		this.goalSelector.addGoal(7, new AvoidEntityGoal<Postosuchus>(this, Postosuchus.class, 10F, 1.5D, 1.75D));
		this.goalSelector.addGoal(0, new Hyperodapedon.LayEggGoal(this, 1.0D));
		this.goalSelector.addGoal(1, new CrepuscularSleepGoal(this));
		this.goalSelector.addGoal(0, new Hyperodapedon.HerbivoreEatGoal((double)1.2F, 12, 2));
	}

	protected SoundEvent getAmbientSound() {
		return this.isAsleep() ? null : PFSounds.HYPERODAPEDON_IDLE;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return PFSounds.HYPERODAPEDON_HURT;
	}

	protected SoundEvent getDeathSound() {
		return PFSounds.HYPERODAPEDON_DEATH;
	}

	@Override
	protected void customServerAiStep() {
		super.customServerAiStep();
	}

	@Override
	public void aiStep() {
		super.aiStep();
		if (this.isAsleep()) {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
		} else {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.24D);
		}
		if (!this.isNoAi()) {
			List<? extends Hyperodapedon> list = this.level.getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(20.0D, 20.0D, 20.0D));
			if (PrehistoricFaunaConfig.advancedHunger) {
				hungerTick++;
				if (hungerTick == 600 && !this.isBaby() || hungerTick == 300 && this.isBaby()) {
					if (!this.isAsleep()) {
						if (currentHunger != 0) {
							this.setHunger(currentHunger - 1);
						}
						if (currentHunger == 0 && PrehistoricFaunaConfig.hungerDamage == true && this.getHealth() > (this.getMaxHealth() / 2)) {
							this.hurt(DamageSource.STARVE, 1);
						}
						if (currentHunger == 0 && PrehistoricFaunaConfig.hungerDamage == true && level.getDifficulty() == Difficulty.HARD && this.getHealth() <= (this.getMaxHealth() / 2)) {
							this.hurt(DamageSource.STARVE, 1);
						}
					}
					hungerTick = 0;
				}
				if (this.getCurrentHunger() >= this.getThreeQuartersHunger() && hungerTick % 150 == 0) {
					if (this.getHealth() < this.getMaxHealth() && this.getHealth() != 0 && this.getTarget() == null && this.getLastHurtByMob() == null) {
						float currentHealth = this.getHealth();
						this.setHealth(currentHealth + 1);
					}
				}
				if (PrehistoricFaunaConfig.naturalEggBlockLaying || PrehistoricFaunaConfig.naturalEggItemLaying) {
					if (lastInLove == 0 && currentHunger >= getThreeQuartersHunger() && tickCount % 900 == 0 && !this.isBaby() && !this.isInLove() && !this.isAsleep() && list.size() < 5) {
						loveTick = 600;
						this.setInLoveNaturally(true);
						this.setInLoveTime(600);
						lastInLove = 28800;
					}
					if (loveTick != 0) {
						loveTick--;
					} else {
						this.setInLoveNaturally(false);
					}
				}
			} else if (PrehistoricFaunaConfig.naturalEggBlockLaying || PrehistoricFaunaConfig.naturalEggItemLaying) {
				int naturalBreedingChance = random.nextInt(1000);
				if (lastInLove == 0 && naturalBreedingChance == 0 && !this.isBaby() && !this.isInLove() && !this.isAsleep() && list.size() < 5) {
					loveTick = 600;
					this.setInLoveNaturally(true);
					this.setInLoveTime(600);
					lastInLove = 28800;
				}
				if (loveTick != 0) {
					loveTick--;
				} else {
					this.setInLoveNaturally(false);
				}
			}
			if (lastInLove != 0) {
				lastInLove--;
			}
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, 0.24D);
	}

	@OnlyIn(Dist.CLIENT)
	public void handleEntityEvent(byte id) {
		super.handleEntityEvent(id);
	}

	static class LayEggGoal extends MoveToBlockGoal {
		private final Hyperodapedon hyperodapedon;

		LayEggGoal(Hyperodapedon hyperodapedon, double speed) {
			super(hyperodapedon, speed, 16);
			this.hyperodapedon = hyperodapedon;
		}

		public boolean canUse() {
			return this.hyperodapedon.hasEgg() ? super.canUse() : false;
		}

		public boolean canContinueToUse() {
			return super.canContinueToUse() && this.hyperodapedon.hasEgg();
		}

		public void tick() {
			super.tick();
			BlockPos blockpos = new BlockPos(this.hyperodapedon.position());
			if (!this.hyperodapedon.isInWater() && this.isReachedTarget()) {
				if (this.hyperodapedon.isDigging < 1) {
					this.hyperodapedon.setDigging(true);
				} else if (this.hyperodapedon.isDigging > 200) {
					Level level = this.hyperodapedon.level;
					level.playSound((Player)null, blockpos, SoundEvents.TURTLE_LAY_EGG, SoundSource.BLOCKS, 0.3F, 0.9F + level.random.nextFloat() * 0.2F);
					level.setBlock(this.blockPos.above(), PFBlocks.HYPERODAPEDON_EGG.get().defaultBlockState().setValue(DinosaurEggBlock.EGGS, Integer.valueOf(this.hyperodapedon.random.nextInt(4) + 1)), 3);
					this.hyperodapedon.setHasEgg(false);
					this.hyperodapedon.setDigging(false);
					this.hyperodapedon.setInLoveTime(600);
				}
				if (this.hyperodapedon.isDigging()) {
					this.hyperodapedon.isDigging++;
				}
			}
		}

		protected boolean isValidTarget(LevelReader worldIn, BlockPos pos) {
			if (!worldIn.isEmptyBlock(pos.above())) {
				return false;
			} else {
				Block block = worldIn.getBlockState(pos).getBlock();
				BlockState state = worldIn.getBlockState(pos);
				return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.MYCELIUM || block == Blocks.SAND || block == Blocks.RED_SAND || block == PFBlocks.MOSSY_DIRT.get() || block == PFBlocks.MOSS_BLOCK.get() || block == PFBlocks.LOAM.get() || block == PFBlocks.PACKED_LOAM.get() || block == PFBlocks.SILT.get() || block == PFBlocks.PACKED_LOAM.get() || state.is(BlockTags.LEAVES);
			}
		}

	}

	static class MateGoal extends BreedGoal {
		private final Hyperodapedon hyperodapedon;

		MateGoal(Hyperodapedon hyperodapedon, double speed) {
			super(hyperodapedon, speed);
			this.hyperodapedon = hyperodapedon;
		}

		public boolean canUse() {
			return super.canUse() && !this.hyperodapedon.hasEgg() && !this.hyperodapedon.isInLoveNaturally();
		}

		protected void spawnBaby() {
			ServerPlayer serverPlayer = this.animal.getLoveCause();
			if (serverPlayer == null && this.partner.getLoveCause() != null) {
				serverPlayer = this.partner.getLoveCause();
			}
			if (serverPlayer != null) {
				serverPlayer.awardStat(Stats.ANIMALS_BRED);
				CriteriaTriggers.BRED_ANIMALS.trigger(serverPlayer, this.animal, this.partner, (AgeableMob)null);
			}
			this.hyperodapedon.setHasEgg(true);
			this.animal.resetLove();
			this.partner.resetLove();
			Random random = this.animal.getRandom();
			if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
				this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
			}
		}

	}

	static class NaturalMateGoal extends BreedGoal {
		private final Hyperodapedon hyperodapedon;

		NaturalMateGoal(Hyperodapedon hyperodapedon, double speed) {
			super(hyperodapedon, speed);
			this.hyperodapedon = hyperodapedon;
		}

		public boolean canUse() {
			return super.canUse() && !this.hyperodapedon.hasEgg() && this.hyperodapedon.getCurrentHunger() >= this.hyperodapedon.getThreeQuartersHunger() && this.hyperodapedon.tickCount % 60 == 0 && (PrehistoricFaunaConfig.naturalEggBlockLaying || PrehistoricFaunaConfig.naturalEggItemLaying) && this.hyperodapedon.isInLoveNaturally();
		}

		protected void spawnBaby() {
			if (PrehistoricFaunaConfig.naturalEggItemLaying) {
				this.hyperodapedon.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.hyperodapedon.random.nextFloat() - this.hyperodapedon.random.nextFloat()) * 0.2F + 1.0F);
				int eggAmount = this.hyperodapedon.random.nextInt(4);
				if (eggAmount == 0) {
					this.hyperodapedon.spawnAtLocation(PFBlocks.HYPERODAPEDON_EGG.get().asItem());
				}
				if (eggAmount == 1) {
					this.hyperodapedon.spawnAtLocation(PFBlocks.HYPERODAPEDON_EGG.get().asItem());
					this.hyperodapedon.spawnAtLocation(PFBlocks.HYPERODAPEDON_EGG.get().asItem());
				}
				if (eggAmount == 2) {
					this.hyperodapedon.spawnAtLocation(PFBlocks.HYPERODAPEDON_EGG.get().asItem());
					this.hyperodapedon.spawnAtLocation(PFBlocks.HYPERODAPEDON_EGG.get().asItem());
					this.hyperodapedon.spawnAtLocation(PFBlocks.HYPERODAPEDON_EGG.get().asItem());
				}
				if (eggAmount == 3) {
					this.hyperodapedon.spawnAtLocation(PFBlocks.HYPERODAPEDON_EGG.get().asItem());
					this.hyperodapedon.spawnAtLocation(PFBlocks.HYPERODAPEDON_EGG.get().asItem());
					this.hyperodapedon.spawnAtLocation(PFBlocks.HYPERODAPEDON_EGG.get().asItem());
					this.hyperodapedon.spawnAtLocation(PFBlocks.HYPERODAPEDON_EGG.get().asItem());
				}
			} else {
				this.hyperodapedon.setHasEgg(true);
			}
			this.animal.resetLove();
			this.partner.resetLove();
		}

	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_241840_1_, AgeableMob p_241840_2_) {
		Hyperodapedon entity = new Hyperodapedon(PFEntities.HYPERODAPEDON.get(), this.level);
		entity.finalizeSpawn(p_241840_1_, this.level.getCurrentDifficultyAt(new BlockPos(entity.getBlockX(), entity.getBlockY(), entity.getBlockZ())), MobSpawnType.BREEDING, (SpawnGroupData)null, (CompoundTag)null);
		return entity;
	}

	public class HerbivoreEatGoal extends MoveToBlockGoal {
		protected int field_220731_g;

		public HerbivoreEatGoal(double p_i50737_2_, int p_i50737_4_, int p_i50737_5_) {
			super(Hyperodapedon.this, p_i50737_2_, p_i50737_4_, p_i50737_5_);
		}

		public double getTargetDistanceSq() {
			return 2.0D;
		}

		public boolean shouldMove() {
			return this.tryTicks % 100 == 0;
		}

		/**
		 * Return true to set given position as destination
		 */
		protected boolean isValidTarget(LevelReader worldIn, BlockPos pos) {
			BlockState blockstate = worldIn.getBlockState(pos);
			return blockstate.is(PFBlocks.HORSETAIL.get()) || blockstate.is(PFBlocks.TALL_HORSETAIL.get()) || blockstate.is(PFBlocks.OSMUNDA.get()) || blockstate.is(PFBlocks.TALL_OSMUNDA.get()) || blockstate.is(PFBlocks.CLUBMOSS.get()) || blockstate.is(PFBlocks.MARCHANTIA.get()) || blockstate.is(PFBlocks.CONIOPTERIS.get()) || blockstate.is(PFBlocks.OSMUNDACAULIS.get()) || blockstate.is(PFBlocks.TALL_OSMUNDACAULIS.get()) || blockstate.is(PFBlocks.DICROIDIUM.get()) || blockstate.is(PFBlocks.JOHNSTONIA.get()) || blockstate.is(PFBlocks.CLADOPHLEBIS.get()) || blockstate.is(PFBlocks.SCYTOPHYLLUM.get()) || blockstate.is(PFBlocks.MICHELILLOA.get()) || blockstate.is(PFBlocks.DEAD_OSMUNDACAULIS.get()) || blockstate.is(PFBlocks.COBBANIA.get()) || blockstate.is(PFBlocks.OTOZAMITES.get()) || blockstate.is(PFBlocks.TALL_OTOZAMITES.get()) || blockstate.is(PFBlocks.LAUROZAMITES.get()) || blockstate.is(Blocks.GRASS) || blockstate.is(Blocks.VINE) || blockstate.is(BlockTags.FLOWERS) || blockstate.is(Blocks.TALL_GRASS) || blockstate.is(Blocks.FERN) || blockstate.is(Blocks.LARGE_FERN);
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			if (this.isReachedTarget()) {
				if (this.field_220731_g >= 20) {
					this.eatBerry();
				} else {
					++this.field_220731_g;
					Hyperodapedon.this.setEating(true);
				}
				if (this.field_220731_g % 5 == 1) {
					Hyperodapedon.this.level.playSound((Player)null, this.blockPos, SoundEvents.GRASS_HIT, SoundSource.NEUTRAL, 1, 1);
				}
			}
			if (Hyperodapedon.this.getCurrentHunger() >= 13) {
				Hyperodapedon.this.setEating(false);
			}
			super.tick();
		}

		protected void eatBerry() {
			BlockState blockstate = Hyperodapedon.this.level.getBlockState(this.blockPos);

			if (blockstate.is(PFBlocks.DEAD_OSMUNDACAULIS.get())) {
				int hunger = Hyperodapedon.this.getCurrentHunger();
				if (hunger + 2 >= Hyperodapedon.this.maxHunger) {
					Hyperodapedon.this.setHunger(Hyperodapedon.this.maxHunger);
					Hyperodapedon.this.setEating(false);
				} else {
					Hyperodapedon.this.setHunger(hunger + 2);
					Hyperodapedon.this.setEating(false);
				}
			}
			if (blockstate.is(Blocks.GRASS) || blockstate.is(Blocks.TALL_GRASS) || blockstate.is(BlockTags.FLOWERS)) {
				int hunger = Hyperodapedon.this.getCurrentHunger();
				if (hunger + 4 >= Hyperodapedon.this.maxHunger) {
					Hyperodapedon.this.setHunger(Hyperodapedon.this.maxHunger);
					Hyperodapedon.this.setEating(false);
				} else {
					Hyperodapedon.this.setHunger(hunger + 4);
					Hyperodapedon.this.setEating(false);
				}
			}
			if (blockstate.is(Blocks.VINE)) {
				int hunger = Hyperodapedon.this.getCurrentHunger();
				if (hunger + 6 >= Hyperodapedon.this.maxHunger) {
					Hyperodapedon.this.setHunger(Hyperodapedon.this.maxHunger);
					Hyperodapedon.this.setEating(false);
				} else {
					Hyperodapedon.this.setHunger(hunger + 6);
					Hyperodapedon.this.setEating(false);
				}
			}
			if (blockstate.is(PFBlocks.MARCHANTIA.get()) || blockstate.is(PFBlocks.OSMUNDACAULIS.get()) || blockstate.is(PFBlocks.OTOZAMITES.get())) {
				int hunger = Hyperodapedon.this.getCurrentHunger();
				if (hunger + 8 >= Hyperodapedon.this.maxHunger) {
					Hyperodapedon.this.setHunger(Hyperodapedon.this.maxHunger);
					Hyperodapedon.this.setEating(false);
				} else {
					Hyperodapedon.this.setHunger(hunger + 8);
					Hyperodapedon.this.setEating(false);
				}
			}
			if (blockstate.is(PFBlocks.HORSETAIL.get()) || blockstate.is(PFBlocks.CLUBMOSS.get()) || blockstate.is(PFBlocks.MICHELILLOA.get()) || blockstate.is(PFBlocks.COBBANIA.get()) || blockstate.is(PFBlocks.LAUROZAMITES.get()) || blockstate.is(PFBlocks.CLATHOPTERIS.get())) {
				int hunger = Hyperodapedon.this.getCurrentHunger();
				if (hunger + 10 >= Hyperodapedon.this.maxHunger) {
					Hyperodapedon.this.setHunger(Hyperodapedon.this.maxHunger);
					Hyperodapedon.this.setEating(false);
				} else {
					Hyperodapedon.this.setHunger(hunger + 10);
					Hyperodapedon.this.setEating(false);
				}
			}
			if (blockstate.is(PFBlocks.TALL_OSMUNDACAULIS.get()) || blockstate.is(PFBlocks.TALL_OTOZAMITES.get())) {
				int hunger = Hyperodapedon.this.getCurrentHunger();
				if (hunger + 12 >= Hyperodapedon.this.maxHunger) {
					Hyperodapedon.this.setHunger(Hyperodapedon.this.maxHunger);
					Hyperodapedon.this.setEating(false);
				} else {
					Hyperodapedon.this.setHunger(hunger + 12);
					Hyperodapedon.this.setEating(false);
				}
			}
			if (blockstate.is(PFBlocks.OSMUNDA.get()) || blockstate.is(Blocks.FERN) || blockstate.is(PFBlocks.CONIOPTERIS.get()) || blockstate.is(PFBlocks.CLADOPHLEBIS.get())) {
				int hunger = Hyperodapedon.this.getCurrentHunger();
				if (hunger + 15 >= Hyperodapedon.this.maxHunger) {
					Hyperodapedon.this.setHunger(Hyperodapedon.this.maxHunger);
					Hyperodapedon.this.setEating(false);
				} else {
					Hyperodapedon.this.setHunger(hunger + 15);
					Hyperodapedon.this.setEating(false);
				}
			}
			if (blockstate.is(PFBlocks.TALL_HORSETAIL.get()) || blockstate.is(PFBlocks.SCYTOPHYLLUM.get())) {
				int hunger = Hyperodapedon.this.getCurrentHunger();
				if (hunger + 20 >= Hyperodapedon.this.maxHunger) {
					Hyperodapedon.this.setHunger(Hyperodapedon.this.maxHunger);
					Hyperodapedon.this.setEating(false);
				} else {
					Hyperodapedon.this.setHunger(hunger + 20);
					Hyperodapedon.this.setEating(false);
				}
			}
			if (blockstate.is(PFBlocks.TALL_OSMUNDA.get()) || blockstate.is(Blocks.LARGE_FERN) || blockstate.is(PFBlocks.JOHNSTONIA.get())) {
				int hunger = Hyperodapedon.this.getCurrentHunger();
				if (hunger + 25 >= Hyperodapedon.this.maxHunger) {
					Hyperodapedon.this.setHunger(Hyperodapedon.this.maxHunger);
					Hyperodapedon.this.setEating(false);
				} else {
					Hyperodapedon.this.setHunger(hunger + 25);
					Hyperodapedon.this.setEating(false);
				}
			}
			if (blockstate.is(PFBlocks.DICROIDIUM.get())) {
				int hunger = Hyperodapedon.this.getCurrentHunger();
				if (hunger + 30 >= Hyperodapedon.this.maxHunger) {
					Hyperodapedon.this.setHunger(Hyperodapedon.this.maxHunger);
					Hyperodapedon.this.setEating(false);
				} else {
					Hyperodapedon.this.setHunger(hunger + 30);
					Hyperodapedon.this.setEating(false);
				}
			}
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean canUse() {
			return !Hyperodapedon.this.isAsleep() && super.canUse() && Hyperodapedon.this.getCurrentHunger() < Hyperodapedon.this.getHalfHunger();
		}

		public boolean canContinueToUse() {
			if (Hyperodapedon.this.getCurrentHunger() >= Hyperodapedon.this.maxHunger || Hyperodapedon.this.isAsleep()) {
				return false;
			} else return super.canContinueToUse();
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void start() {
			this.field_220731_g = 0;
			super.start();
		}
	}
	
	@Override
	public ItemStack getPickedResult(HitResult target) {
		return new ItemStack(PFItems.HYPERODAPEDON_SPAWN_EGG.get());
	}

}
