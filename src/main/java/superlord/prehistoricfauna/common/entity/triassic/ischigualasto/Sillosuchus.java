package superlord.prehistoricfauna.common.entity.triassic.ischigualasto;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import superlord.prehistoricfauna.common.blocks.DinosaurEggBlock;
import superlord.prehistoricfauna.common.entity.DinosaurEntity;
import superlord.prehistoricfauna.common.entity.cretaceous.hellcreek.Tyrannosaurus;
import superlord.prehistoricfauna.common.entity.goal.CathemeralSleepGoal;
import superlord.prehistoricfauna.common.entity.goal.DinosaurLookAtGoal;
import superlord.prehistoricfauna.common.entity.goal.DinosaurRandomLookGoal;
import superlord.prehistoricfauna.common.entity.jurassic.morrison.Allosaurus;
import superlord.prehistoricfauna.common.entity.jurassic.morrison.Camarasaurus;
import superlord.prehistoricfauna.config.PrehistoricFaunaConfig;
import superlord.prehistoricfauna.init.PFBlocks;
import superlord.prehistoricfauna.init.PFEntities;
import superlord.prehistoricfauna.init.PFItems;
import superlord.prehistoricfauna.init.PFSounds;

public class Sillosuchus extends DinosaurEntity {

	private static final EntityDataAccessor<Boolean> HAS_EGG = SynchedEntityData.defineId(Sillosuchus.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> IS_DIGGING = SynchedEntityData.defineId(Sillosuchus.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> ALBINO = SynchedEntityData.defineId(Sillosuchus.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> MELANISTIC = SynchedEntityData.defineId(Sillosuchus.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(Sillosuchus.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> NATURAL_LOVE = SynchedEntityData.defineId(Sillosuchus.class, EntityDataSerializers.BOOLEAN);
	private int maxHunger = 150;
	private int currentHunger = 150;
	private int lastInLove = 0;
	int hungerTick = 0;
	private int warningSoundTicks;
	private int isDigging;
	int loveTick = 0;

	public Sillosuchus(EntityType<? extends Sillosuchus> type, Level levelIn) {
		super(type, levelIn);
	}

	protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
		if (this.isBaby()) {
			return 1.75F;
		} else {
			return 3.5F;
		}
	}

	public boolean isDigging() {
		return this.entityData.get(IS_DIGGING);
	}

	private void setDigging(boolean isDigging) {
		this.isDigging = isDigging ? 1 : 0;
		this.entityData.set(IS_DIGGING, isDigging);
	}

	public boolean hasEgg() {
		return this.entityData.get(HAS_EGG);
	}

	private void setHasEgg(boolean hasEgg) {
		this.entityData.set(HAS_EGG, hasEgg);
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

	@Override
	public boolean isFood(ItemStack stack) {
		return stack.getItem() == PFBlocks.DICROIDIUM.get().asItem();
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new Sillosuchus.MeleeAttackGoal());
		this.goalSelector.addGoal(1, new Sillosuchus.PanicGoal());
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new DinosaurLookAtGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(6, new DinosaurRandomLookGoal(this));
		this.targetSelector.addGoal(1, new Sillosuchus.HurtByTargetGoal());
		this.targetSelector.addGoal(2, new Sillosuchus.AttackPlayerGoal());
		this.targetSelector.addGoal(3, new Sillosuchus.ProtectBabyGoal());
		this.goalSelector.addGoal(0, new Sillosuchus.LayEggGoal(this, 1.0D));
		this.goalSelector.addGoal(0, new Sillosuchus.MateGoal(this, 1.0D));
		this.goalSelector.addGoal(0, new Sillosuchus.NaturalMateGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new AvoidEntityGoal<Tyrannosaurus>(this, Tyrannosaurus.class, 7F, 1.5D, 1.75D));
		this.goalSelector.addGoal(8, new AvoidEntityGoal<Allosaurus>(this, Allosaurus.class, 7F, 1.5D, 1.75D));
		this.goalSelector.addGoal(8, new AvoidEntityGoal<Camarasaurus>(this, Camarasaurus.class, 7F, 1.5D, 1.75D));
		this.goalSelector.addGoal(1, new CathemeralSleepGoal(this));
		this.goalSelector.addGoal(0, new Sillosuchus.HerbivoreEatGoal((double)1.2F, 12, 2));
	}

	public void aiStep() {
		super.aiStep();
		if (this.isAsleep()) {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
		} else {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.23D);
		}
		if (!this.isNoAi()) {
			List<? extends Sillosuchus> list = this.level.getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(20.0D, 20.0D, 20.0D));
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
					if (lastInLove == 0 && currentHunger >= getThreeQuartersHunger() && tickCount % 900 == 0 && !this.isBaby() && !this.isInLove() && !this.isAsleep() && list.size() < 6) {
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
				if (lastInLove == 0 && naturalBreedingChance == 0 && !this.isBaby() && !this.isInLove() && !this.isAsleep() && list.size() < 6) {
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
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 60.0D).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.23D).add(Attributes.ATTACK_DAMAGE, 7.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.6D);
	}

	protected SoundEvent getAmbientSound() {
		return this.isAsleep() ? PFSounds.SILLOSUCHUS_SNORES : PFSounds.SILLOSUCHUS_IDLE;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return PFSounds.SILLOSUCHUS_HURT;
	}

	protected SoundEvent getDeathSound() {
		return PFSounds.SILLOSUCHUS_DEATH;
	}

	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
	}

	protected void playWarningSound() {
		if (this.warningSoundTicks <= 0) {
			this.playSound(PFSounds.SILLOSUCHUS_WARN, 1.0F, this.getVoicePitch());
			this.warningSoundTicks = 40;
		}
	}

	protected void defineSynchedData() {
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

	/**
	 * Called to update the entity's position/logic.
	 */
	public void tick() {
		super.tick();
		if (this.warningSoundTicks > 0) {
			--this.warningSoundTicks;
		}
	}

	public boolean onAttackAnimationFinish(Entity entityIn) {
		boolean flag = super.onAttackAnimationFinish(entityIn);
		if (flag) {
			this.doEnchantDamageEffects(this, entityIn);
		}

		return flag;
	}	

	class AttackPlayerGoal extends NearestAttackableTargetGoal<Player> {
		public AttackPlayerGoal() {
			super(Sillosuchus.this, Player.class, 20, true, true, (Predicate<LivingEntity>)null);
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean canUse() {
			if (Sillosuchus.this.isBaby()) {
				return false;
			} else {
				if (super.canUse()) {
					for(@SuppressWarnings("unused") Sillosuchus sillosuchus : Sillosuchus.this.level.getEntitiesOfClass(Sillosuchus.class, Sillosuchus.this.getBoundingBox().inflate(8.0D, 4.0D, 8.0D))) {
						return true;
					}
				}

				return false;
			}
		}

		protected double getFollowDistance() {
			return super.getFollowDistance() * 0.5D;
		}
	}

	class HurtByTargetGoal extends net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal {
		public HurtByTargetGoal() {
			super(Sillosuchus.this);
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void start() {
			super.start();
			if (Sillosuchus.this.isBaby()) {
				this.alertOthers();
				this.stop();
			}

		}

		protected void alertOther(Mob mobIn, LivingEntity targetIn) {
			if (mobIn instanceof Sillosuchus && !mobIn.isBaby()) {
				super.alertOther(mobIn, targetIn);
			}

		}
	}

	class MeleeAttackGoal extends net.minecraft.world.entity.ai.goal.MeleeAttackGoal {
		public MeleeAttackGoal() {
			super(Sillosuchus.this, 1.25D, true);
		}

		protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
			double d0 = this.getAttackReachSqr(enemy);
			if (distToEnemySqr <= d0 && this.isTimeToAttack()) {
				this.resetAttackCooldown();
				this.mob.doHurtTarget(enemy);
			} else if (distToEnemySqr <= d0 * 2.0D) {
				if (this.isTimeToAttack()) {
					this.resetAttackCooldown();
				}

				if (this.getTicksUntilNextAttack() <= 10) {
					Sillosuchus.this.playWarningSound();
				}
			} else {
				this.resetAttackCooldown();
			}

		}

		public boolean canContinueToUse() {
			return super.canContinueToUse();
		}

		public void stop() {
			super.stop();
		}

		protected double getAttackReachSqr(LivingEntity attackTarget) {
			return (double)(10.0F + attackTarget.getBbWidth());
		}
	}

	class PanicGoal extends net.minecraft.world.entity.ai.goal.PanicGoal {
		public PanicGoal() {
			super(Sillosuchus.this, 2.0D);
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean canUse() {
			return !Sillosuchus.this.isBaby() && !Sillosuchus.this.isOnFire() ? false : super.canUse();
		}
	}

	class ProtectBabyGoal extends NearestAttackableTargetGoal<Player> {
		public ProtectBabyGoal() {
			super(Sillosuchus.this, Player.class, 20, true, true, (Predicate<LivingEntity>)null);
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean canUse() {
			if (Sillosuchus.this.isBaby()) {
				return false;
			} else {
				if (super.canUse()) {
					for(Sillosuchus sillosuchus : Sillosuchus.this.level.getEntitiesOfClass(Sillosuchus.class, Sillosuchus.this.getBoundingBox().inflate(8.0D, 4.0D, 8.0D))) {
						if (sillosuchus.isBaby()) {
							return true;
						}
					}
				}

				return false;
			}
		}

		protected double getFollowDistance() {
			return super.getFollowDistance() * 0.5D;
		}
	}

	static class LayEggGoal extends MoveToBlockGoal {
		private final Sillosuchus sillosuchus;

		LayEggGoal(Sillosuchus sillosuchus, double speedIn) {
			super(sillosuchus, speedIn, 16);
			this.sillosuchus = sillosuchus;
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean canUse() {
			return this.sillosuchus.hasEgg() ? super.canUse() : false;
		}

		/**
		 * Returns whether an in-progress AIBase should continue executing
		 */
		public boolean canContinueToUse() {
			return super.canContinueToUse() && this.sillosuchus.hasEgg();
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void tick() {
			super.tick();
			BlockPos blockpos = new BlockPos(this.sillosuchus.position());
			if (!this.sillosuchus.isInWater() && this.isReachedTarget()) {
				if (this.sillosuchus.isDigging < 1) {
					this.sillosuchus.setDigging(true);
				} else if (this.sillosuchus.isDigging > 200) {
					Level level = this.sillosuchus.level;
					level.playSound((Player)null, blockpos, SoundEvents.TURTLE_LAY_EGG, SoundSource.BLOCKS, 0.3F, 0.9F + level.random.nextFloat() * 0.2F);
					level.setBlock(this.blockPos.above(), PFBlocks.SILLOSUCHUS_EGG.get().defaultBlockState().setValue(DinosaurEggBlock.EGGS, Integer.valueOf(this.sillosuchus.random.nextInt(4) + 1)), 3);
					this.sillosuchus.setHasEgg(false);
					this.sillosuchus.setDigging(false);
					this.sillosuchus.setInLoveTime(600);
				}

				if (this.sillosuchus.isDigging()) {
					this.sillosuchus.isDigging++;
				}
			}

		}

		/**
		 * Return true to set given position as destination
		 */
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
		private final Sillosuchus sillosuchus;

		MateGoal(Sillosuchus sillosuchus, double speedIn) {
			super(sillosuchus, speedIn);
			this.sillosuchus = sillosuchus;
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean canUse() {
			return super.canUse() && !this.sillosuchus.hasEgg() && !this.sillosuchus.isInLoveNaturally();
		}

		/**
		 * Spawns a baby animal of the same type.
		 */
		protected void spawnBaby() {
			ServerPlayer serverplayerentity = this.animal.getLoveCause();
			if (serverplayerentity == null && this.partner.getLoveCause() != null) {
				serverplayerentity = this.partner.getLoveCause();
			}

			if (serverplayerentity != null) {
				serverplayerentity.awardStat(Stats.ANIMALS_BRED);
				CriteriaTriggers.BRED_ANIMALS.trigger(serverplayerentity, this.animal, this.partner, (AgeableMob)null);
			}

			this.sillosuchus.setHasEgg(true);
			this.animal.resetLove();
			this.partner.resetLove();
			Random random = this.animal.getRandom();
			if (this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
				this.level.addFreshEntity(new ExperienceOrb(this.level, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
			}

		}
	}

	static class NaturalMateGoal extends BreedGoal {
		private final Sillosuchus sillosuchus;

		NaturalMateGoal(Sillosuchus sillosuchus, double speed) {
			super(sillosuchus, speed);
			this.sillosuchus = sillosuchus;
		}

		public boolean canUse() {
			return super.canUse() && !this.sillosuchus.hasEgg() && this.sillosuchus.getCurrentHunger() >= this.sillosuchus.getThreeQuartersHunger() && this.sillosuchus.tickCount % 60 == 0 && (PrehistoricFaunaConfig.naturalEggBlockLaying || PrehistoricFaunaConfig.naturalEggItemLaying) && this.sillosuchus.isInLoveNaturally();
		}

		protected void spawnBaby() {
			if (PrehistoricFaunaConfig.naturalEggItemLaying) {
				this.sillosuchus.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.sillosuchus.random.nextFloat() - this.sillosuchus.random.nextFloat()) * 0.2F + 1.0F);
				int eggAmount = this.sillosuchus.random.nextInt(4);
				if (eggAmount == 0) {
					this.sillosuchus.spawnAtLocation(PFBlocks.SILLOSUCHUS_EGG.get().asItem());
				}
				if (eggAmount == 1) {
					this.sillosuchus.spawnAtLocation(PFBlocks.SILLOSUCHUS_EGG.get().asItem());
					this.sillosuchus.spawnAtLocation(PFBlocks.SILLOSUCHUS_EGG.get().asItem());
				}
				if (eggAmount == 2) {
					this.sillosuchus.spawnAtLocation(PFBlocks.SILLOSUCHUS_EGG.get().asItem());
					this.sillosuchus.spawnAtLocation(PFBlocks.SILLOSUCHUS_EGG.get().asItem());
					this.sillosuchus.spawnAtLocation(PFBlocks.SILLOSUCHUS_EGG.get().asItem());
				}
				if (eggAmount == 3) {
					this.sillosuchus.spawnAtLocation(PFBlocks.SILLOSUCHUS_EGG.get().asItem());
					this.sillosuchus.spawnAtLocation(PFBlocks.SILLOSUCHUS_EGG.get().asItem());
					this.sillosuchus.spawnAtLocation(PFBlocks.SILLOSUCHUS_EGG.get().asItem());
					this.sillosuchus.spawnAtLocation(PFBlocks.SILLOSUCHUS_EGG.get().asItem());
				}
			} else {
				this.sillosuchus.setHasEgg(true);
			}
			this.animal.resetLove();
			this.partner.resetLove();
		}

	}

	@Override
	public AgeableMob getBreedOffspring(ServerLevel p_241840_1_, AgeableMob p_241840_2_) {
		Sillosuchus entity = new Sillosuchus(PFEntities.SILLOSUCHUS.get(), this.level);
		entity.finalizeSpawn(p_241840_1_, this.level.getCurrentDifficultyAt(new BlockPos(entity.getBlockX(), entity.getBlockY(), entity.getBlockZ())), MobSpawnType.BREEDING, (SpawnGroupData)null, (CompoundTag)null);
		return entity;
	}

	public class HerbivoreEatGoal extends MoveToBlockGoal {
		protected int field_220731_g;

		public HerbivoreEatGoal(double p_i50737_2_, int p_i50737_4_, int p_i50737_5_) {
			super(Sillosuchus.this, p_i50737_2_, p_i50737_4_, p_i50737_5_);
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
					Sillosuchus.this.setEating(true);
				}
				if (this.field_220731_g % 5 == 1) {
					Sillosuchus.this.level.playSound((Player)null, this.blockPos, SoundEvents.GRASS_HIT, SoundSource.NEUTRAL, 1, 1);
				}
			}
			if (Sillosuchus.this.getCurrentHunger() >= 13) {
				Sillosuchus.this.setEating(false);
			}
			super.tick();
		}

		protected void eatBerry() {
			BlockState blockstate = Sillosuchus.this.level.getBlockState(this.blockPos);

			if (blockstate.is(PFBlocks.DEAD_OSMUNDACAULIS.get())) {
				int hunger = Sillosuchus.this.getCurrentHunger();
				if (hunger + 2 >= Sillosuchus.this.maxHunger) {
					Sillosuchus.this.setHunger(Sillosuchus.this.maxHunger);
					Sillosuchus.this.setEating(false);
				} else {
					Sillosuchus.this.setHunger(hunger + 2);
					Sillosuchus.this.setEating(false);
				}
			}
			if (blockstate.is(Blocks.GRASS) || blockstate.is(Blocks.TALL_GRASS) || blockstate.is(BlockTags.FLOWERS)) {
				int hunger = Sillosuchus.this.getCurrentHunger();
				if (hunger + 4 >= Sillosuchus.this.maxHunger) {
					Sillosuchus.this.setHunger(Sillosuchus.this.maxHunger);
					Sillosuchus.this.setEating(false);
				} else {
					Sillosuchus.this.setHunger(hunger + 4);
					Sillosuchus.this.setEating(false);
				}
			}
			if (blockstate.is(Blocks.VINE)) {
				int hunger = Sillosuchus.this.getCurrentHunger();
				if (hunger + 6 >= Sillosuchus.this.maxHunger) {
					Sillosuchus.this.setHunger(Sillosuchus.this.maxHunger);
					Sillosuchus.this.setEating(false);
				} else {
					Sillosuchus.this.setHunger(hunger + 6);
					Sillosuchus.this.setEating(false);
				}
			}
			if (blockstate.is(PFBlocks.MARCHANTIA.get()) || blockstate.is(PFBlocks.OSMUNDACAULIS.get()) || blockstate.is(PFBlocks.OTOZAMITES.get())) {
				int hunger = Sillosuchus.this.getCurrentHunger();
				if (hunger + 8 >= Sillosuchus.this.maxHunger) {
					Sillosuchus.this.setHunger(Sillosuchus.this.maxHunger);
					Sillosuchus.this.setEating(false);
				} else {
					Sillosuchus.this.setHunger(hunger + 8);
					Sillosuchus.this.setEating(false);
				}
			}
			if (blockstate.is(PFBlocks.HORSETAIL.get()) || blockstate.is(PFBlocks.CLUBMOSS.get()) || blockstate.is(PFBlocks.MICHELILLOA.get()) || blockstate.is(PFBlocks.COBBANIA.get()) || blockstate.is(PFBlocks.LAUROZAMITES.get()) || blockstate.is(PFBlocks.CLATHOPTERIS.get())) {
				int hunger = Sillosuchus.this.getCurrentHunger();
				if (hunger + 10 >= Sillosuchus.this.maxHunger) {
					Sillosuchus.this.setHunger(Sillosuchus.this.maxHunger);
					Sillosuchus.this.setEating(false);
				} else {
					Sillosuchus.this.setHunger(hunger + 10);
					Sillosuchus.this.setEating(false);
				}
			}
			if (blockstate.is(PFBlocks.TALL_OSMUNDACAULIS.get()) || blockstate.is(PFBlocks.TALL_OTOZAMITES.get())) {
				int hunger = Sillosuchus.this.getCurrentHunger();
				if (hunger + 12 >= Sillosuchus.this.maxHunger) {
					Sillosuchus.this.setHunger(Sillosuchus.this.maxHunger);
					Sillosuchus.this.setEating(false);
				} else {
					Sillosuchus.this.setHunger(hunger + 12);
					Sillosuchus.this.setEating(false);
				}
			}
			if (blockstate.is(PFBlocks.OSMUNDA.get()) || blockstate.is(Blocks.FERN) || blockstate.is(PFBlocks.CONIOPTERIS.get()) || blockstate.is(PFBlocks.CLADOPHLEBIS.get())) {
				int hunger = Sillosuchus.this.getCurrentHunger();
				if (hunger + 15 >= Sillosuchus.this.maxHunger) {
					Sillosuchus.this.setHunger(Sillosuchus.this.maxHunger);
					Sillosuchus.this.setEating(false);
				} else {
					Sillosuchus.this.setHunger(hunger + 15);
					Sillosuchus.this.setEating(false);
				}
			}
			if (blockstate.is(PFBlocks.TALL_HORSETAIL.get()) || blockstate.is(PFBlocks.SCYTOPHYLLUM.get())) {
				int hunger = Sillosuchus.this.getCurrentHunger();
				if (hunger + 20 >= Sillosuchus.this.maxHunger) {
					Sillosuchus.this.setHunger(Sillosuchus.this.maxHunger);
					Sillosuchus.this.setEating(false);
				} else {
					Sillosuchus.this.setHunger(hunger + 20);
					Sillosuchus.this.setEating(false);
				}
			}
			if (blockstate.is(PFBlocks.TALL_OSMUNDA.get()) || blockstate.is(Blocks.LARGE_FERN) || blockstate.is(PFBlocks.JOHNSTONIA.get())) {
				int hunger = Sillosuchus.this.getCurrentHunger();
				if (hunger + 25 >= Sillosuchus.this.maxHunger) {
					Sillosuchus.this.setHunger(Sillosuchus.this.maxHunger);
					Sillosuchus.this.setEating(false);
				} else {
					Sillosuchus.this.setHunger(hunger + 25);
					Sillosuchus.this.setEating(false);
				}
			}
			if (blockstate.is(PFBlocks.DICROIDIUM.get())) {
				int hunger = Sillosuchus.this.getCurrentHunger();
				if (hunger + 30 >= Sillosuchus.this.maxHunger) {
					Sillosuchus.this.setHunger(Sillosuchus.this.maxHunger);
					Sillosuchus.this.setEating(false);
				} else {
					Sillosuchus.this.setHunger(hunger + 30);
					Sillosuchus.this.setEating(false);
				}
			}
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean canUse() {
			return !Sillosuchus.this.isAsleep() && super.canUse() && Sillosuchus.this.getCurrentHunger() < Sillosuchus.this.getHalfHunger();
		}

		public boolean canContinueToUse() {
			if (Sillosuchus.this.getCurrentHunger() >= Sillosuchus.this.maxHunger || Sillosuchus.this.isAsleep()) {
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
		return new ItemStack(PFItems.SILLOSUCHUS_SPAWN_EGG.get());
	}

}
