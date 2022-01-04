package superlord.prehistoricfauna.common.entities;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.horse.DonkeyEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.passive.horse.MuleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import superlord.prehistoricfauna.common.blocks.TyrannosaurusEggBlock;
import superlord.prehistoricfauna.common.entities.goal.HuntGoal;
import superlord.prehistoricfauna.init.PFBlocks;
import superlord.prehistoricfauna.init.PFEffects;
import superlord.prehistoricfauna.init.PFEntities;
import superlord.prehistoricfauna.init.PFItems;
import superlord.prehistoricfauna.init.SoundInit;

public class TyrannosaurusEntity extends DinosaurEntity {
	private static final DataParameter<Boolean> HAS_EGG = EntityDataManager.createKey(TyrannosaurusEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_DIGGING = EntityDataManager.createKey(TyrannosaurusEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_JUVENILE = EntityDataManager.createKey(TyrannosaurusEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> ALBINO = EntityDataManager.createKey(TyrannosaurusEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> MELANISTIC = EntityDataManager.createKey(TyrannosaurusEntity.class, DataSerializers.BOOLEAN);
	private int warningSoundTicks;
	private int isDigging;
	public int attackTick = 0;
	private Goal panicGoal;

	public TyrannosaurusEntity(EntityType<? extends TyrannosaurusEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public boolean isDigging() {
		return this.dataManager.get(IS_DIGGING);
	}

	private void setDigging(boolean isDigging) {
		this.isDigging = isDigging ? 1 : 0;
		this.dataManager.set(IS_DIGGING, isDigging);
	}

	public boolean hasEgg() {
		return this.dataManager.get(HAS_EGG);
	}

	private void setHasEgg(boolean hasEgg) {
		this.dataManager.set(HAS_EGG, hasEgg);
	}

	public boolean isJuvenile() {
		return this.dataManager.get(IS_JUVENILE);
	}

	private void setJuvenile(boolean isJuvenile) {
		this.dataManager.set(IS_JUVENILE, isJuvenile);
	}
	
	public boolean isAlbino() {
		return this.dataManager.get(ALBINO);
	}

	private void setAlbino(boolean isAlbino) {
		this.dataManager.set(ALBINO, isAlbino);
	}

	public boolean isMelanistic() {
		return this.dataManager.get(MELANISTIC);
	}

	private void setMelanistic(boolean isMelanistic) {
		this.dataManager.set(MELANISTIC, isMelanistic);
	}

	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == PFItems.RAW_LARGE_MARGINOCEPHALIAN_MEAT.get();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void registerGoals() {
		super.registerGoals();

		panicGoal = new TyrannosaurusEntity.PanicGoal();
		this.goalSelector.addGoal(1, panicGoal);
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(4, new TyrannosaurusEntity.TyrannosaurusFollowParentGoal(this, 1.25D));
		this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(9, new AvoidEntityGoal(this, AnkylosaurusEntity.class, 7F, 1.25D, 1.25D));
		this.goalSelector.addGoal(1, new TyrannosaurusEntity.MeleeAttackGoal());
		this.targetSelector.addGoal(1, new TyrannosaurusEntity.HurtByTargetGoal());
		this.targetSelector.addGoal(2, new TyrannosaurusEntity.AttackPlayerGoal(this));
		this.targetSelector.addGoal(2, new TyrannosaurusEntity.TerritoryAttackGoal());
		this.goalSelector.addGoal(0, new TyrannosaurusEntity.LayEggGoal(this, 1.0D));
		this.goalSelector.addGoal(0, new TyrannosaurusEntity.MateGoal(this, 1.0D));
		this.targetSelector.addGoal(1, new JuvenileHuntGoal(this, AnimalEntity.class, 10, false, false, (p_213487_0_) -> {
			return p_213487_0_ instanceof ThescelosaurusEntity || p_213487_0_ instanceof DryosaurusEntity || p_213487_0_ instanceof IschigualastiaEntity || p_213487_0_ instanceof CowEntity || p_213487_0_ instanceof SheepEntity || p_213487_0_ instanceof HorseEntity || p_213487_0_ instanceof DonkeyEntity || p_213487_0_ instanceof MuleEntity || p_213487_0_ instanceof PlayerEntity;
		}));
		this.targetSelector.addGoal(1, new HuntGoal(this, AnimalEntity.class, 10, false, false, (p_213487_0_) -> {
			return p_213487_0_ instanceof TriceratopsEntity || p_213487_0_ instanceof StegosaurusEntity || p_213487_0_ instanceof CowEntity || p_213487_0_ instanceof SheepEntity || p_213487_0_ instanceof HorseEntity || p_213487_0_ instanceof DonkeyEntity || p_213487_0_ instanceof MuleEntity || p_213487_0_ instanceof PolarBearEntity || p_213487_0_ instanceof PandaEntity || p_213487_0_ instanceof PlayerEntity;
		}));
	}
	
	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 100.0D).createMutableAttribute(Attributes.FOLLOW_RANGE, 20.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 14.0D).createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.75D);
	}

	@Override
	public void setGrowingAge(int age) {
		super.setGrowingAge(age);
		if (this.getGrowingAge() >= -12000 && this.getGrowingAge() < 0) {
			this.setJuvenile(true);
		} else if(this.getGrowingAge() >= 0) {
			this.setJuvenile(false);
		}
	}

	protected SoundEvent getAmbientSound() {
		return SoundInit.TYRANNOSAURUS_IDLE;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundInit.TYRANNOSAURUS_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundInit.TYRANNOSAURUS_DEATH;
	}

	protected void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
	}

	protected void playWarningSound() {
		if (this.warningSoundTicks <= 0) {
			this.playSound(SoundInit.TYRANNOSAURUS_WARN, 1.0F, this.getSoundPitch());
			this.warningSoundTicks = 40;
		}
	}

	protected void registerData() {
		super.registerData();
		this.dataManager.register(HAS_EGG, false);
		this.dataManager.register(IS_DIGGING, false);
		this.dataManager.register(IS_JUVENILE, false);
		this.dataManager.register(ALBINO, false);
		this.dataManager.register(MELANISTIC, false);
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putBoolean("HasEgg", this.hasEgg());
		compound.putBoolean("IsAlbino", this.isAlbino());
		compound.putBoolean("IsMelanistic", this.isMelanistic());
	}

	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.setHasEgg(compound.getBoolean("HasEgg"));
		this.setAlbino(compound.getBoolean("IsAlbino"));
		this.setMelanistic(compound.getBoolean("IsMelanistic"));
	}
	
	@Nullable
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		Random rand = new Random();
		int birthNumber = rand.nextInt(799);
		if (birthNumber >= 0 && birthNumber < 4) {
			this.setAlbino(true);
		} else if (birthNumber >= 4 && birthNumber < 7) {
			this.setMelanistic(true);
		}
		return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	public void tick() {
		super.tick();
		if (this.warningSoundTicks > 0) {
			--this.warningSoundTicks;
		}
	}

	public boolean attackEntityAsMob(Entity entityIn) {
		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getAttribute(Attributes.ATTACK_DAMAGE).getValue()));
		if (flag) {
			this.applyEnchantments(this, entityIn);
			((LivingEntity)entityIn).addPotionEffect(new EffectInstance(PFEffects.BLEEDING.get(), 300, 0, true, false));
		}

		return flag;
	}	
	
	class AttackPlayerGoal extends NearestAttackableTargetGoal<PlayerEntity> {
		TyrannosaurusEntity tyrannosaurus;
		public AttackPlayerGoal(TyrannosaurusEntity tyrannosaurus) {
			super(TyrannosaurusEntity.this, PlayerEntity.class, 20, true, true, (Predicate<LivingEntity>)null);
			this.tyrannosaurus = tyrannosaurus;
		}

		public boolean shouldExecute() {
			if (TyrannosaurusEntity.this.isChild()) {
				return false;
			} else {
				if (super.shouldExecute()) {
					for(TyrannosaurusEntity tyrannosaurus : TyrannosaurusEntity.this.world.getEntitiesWithinAABB(TyrannosaurusEntity.class, TyrannosaurusEntity.this.getBoundingBox().grow(8.0D, 4.0D, 8.0D))) {
						if (tyrannosaurus.isChild()) {
							return true;
						}
					}
				}

				return false;
			}
		}
		
		protected double getTargetDistance() {
			return super.getTargetDistance() * 0.5D;
		}
	}

	class HurtByTargetGoal extends net.minecraft.entity.ai.goal.HurtByTargetGoal {
		public HurtByTargetGoal() {
			super(TyrannosaurusEntity.this);
		}

		public void startExecuting() {
			super.startExecuting();
			if (TyrannosaurusEntity.this.isChild()) {
				this.alertOthers();
				this.resetTask();
			}

		}

		protected void setAttackTarget(MobEntity mobIn, LivingEntity targetIn) {
			if (mobIn instanceof TyrannosaurusEntity && !mobIn.isChild()) {
				super.setAttackTarget(mobIn, targetIn);
			}

		}
	}

	class MeleeAttackGoal extends net.minecraft.entity.ai.goal.MeleeAttackGoal {
		public MeleeAttackGoal() {
			super(TyrannosaurusEntity.this, 1.25D, true);
		}

		protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
			double d0 = this.getAttackReachSqr(enemy);
			if (distToEnemySqr <= d0 && this.func_234040_h_()) {
				this.func_234039_g_();
				this.attacker.attackEntityAsMob(enemy);
			} else if (distToEnemySqr <= d0 * 2.0D) {
				if (this.func_234040_h_()) {
					this.func_234039_g_();
				}

				if (this.func_234041_j_() <= 10) {
					TyrannosaurusEntity.this.playWarningSound();
				}
			} else {
				this.func_234039_g_();
			}

		}
		
		public boolean shouldContinueExecuting() {
			float f = this.attacker.getBrightness();
			if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
				this.attacker.setAttackTarget((LivingEntity)null);
				return false;
			} else {
				return super.shouldContinueExecuting();
			}
		}

		public void resetTask() {
			super.resetTask();
		}

		protected double getAttackReachSqr(LivingEntity attackTarget) {
			return (double)(15.0F + attackTarget.getWidth());
		}
	}

	class PanicGoal extends net.minecraft.entity.ai.goal.PanicGoal {
		public PanicGoal() {
			super(TyrannosaurusEntity.this, 2.0D);
		}

		public boolean shouldExecute() {
			if (!TyrannosaurusEntity.this.isChild() && !TyrannosaurusEntity.this.isBurning()) {
				return false;
			} else if (TyrannosaurusEntity.this.isJuvenile() && !TyrannosaurusEntity.this.isBurning()) {
				return false;
			} else {
				return super.shouldExecute();
			}
		}
	}

	static class LayEggGoal extends MoveToBlockGoal {
		private final TyrannosaurusEntity tyrannosaurus;

		LayEggGoal(TyrannosaurusEntity tyrannosaurus, double speedIn) {
			super(tyrannosaurus, speedIn, 16);
			this.tyrannosaurus = tyrannosaurus;
		}

		public boolean shouldExecute() {
			return this.tyrannosaurus.hasEgg() ? super.shouldExecute() : false;
		}

		public boolean shouldContinueExecuting() {
			return super.shouldContinueExecuting() && this.tyrannosaurus.hasEgg();
		}

		public void tick() {
			super.tick();
			BlockPos blockpos = new BlockPos(this.tyrannosaurus.getPositionVec());
			if (!this.tyrannosaurus.isInWater() && this.getIsAboveDestination()) {
				if (this.tyrannosaurus.isDigging < 1) {
					this.tyrannosaurus.setDigging(true);
				} else if (this.tyrannosaurus.isDigging > 200) {
					World world = this.tyrannosaurus.world;
					world.playSound((PlayerEntity)null, blockpos, SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS, 0.3F, 0.9F + world.rand.nextFloat() * 0.2F);
					world.setBlockState(this.destinationBlock.up(), PFBlocks.TYRANNOSAURUS_EGG.getDefaultState().with(TyrannosaurusEggBlock.EGGS, Integer.valueOf(this.tyrannosaurus.rand.nextInt(4) + 1)), 3);
					this.tyrannosaurus.setHasEgg(false);
					this.tyrannosaurus.setDigging(false);
					this.tyrannosaurus.setInLove(600);
				}

				if (this.tyrannosaurus.isDigging()) {
					this.tyrannosaurus.isDigging++;
				}
			}

		}

		protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
			if (!worldIn.isAirBlock(pos.up())) {
				return false;
			} else {
				Block block = worldIn.getBlockState(pos).getBlock();
				return block == PFBlocks.LOAM || block == PFBlocks.MOSSY_DIRT || block == Blocks.PODZOL;
			}
		}
	}

	static class MateGoal extends BreedGoal {
		private final TyrannosaurusEntity tyrannosaurus;

		MateGoal(TyrannosaurusEntity tyrannosaurus, double speedIn) {
			super(tyrannosaurus, speedIn);
			this.tyrannosaurus = tyrannosaurus;
		}

		public boolean shouldExecute() {
			return super.shouldExecute() && !this.tyrannosaurus.hasEgg();
		}

		protected void spawnBaby() {
			ServerPlayerEntity serverplayerentity = this.animal.getLoveCause();
			if (serverplayerentity == null && this.targetMate.getLoveCause() != null) {
				serverplayerentity = this.targetMate.getLoveCause();
			}

			if (serverplayerentity != null) {
				serverplayerentity.addStat(Stats.ANIMALS_BRED);
			}

			this.tyrannosaurus.setHasEgg(true);
			this.animal.resetInLove();
			this.targetMate.resetInLove();
			Random random = this.animal.getRNG();
			if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
				this.world.addEntity(new ExperienceOrbEntity(this.world, this.animal.getPosX(), this.animal.getPosY(), this.animal.getPosZ(), random.nextInt(7) + 1));
			}

		}
	}
	
	class TyrannosaurusFollowParentGoal extends Goal {
		private final TyrannosaurusEntity babyTyrannosaurusEntity;
		private TyrannosaurusEntity parentTyrannosaurusEntity;
		private final double moveSpeed;
		private int delayCounter;

		public TyrannosaurusFollowParentGoal(TyrannosaurusEntity tyrannosaurus, double speed) {
			this.babyTyrannosaurusEntity = tyrannosaurus;
			this.moveSpeed = speed;
		}

		public boolean shouldExecute() {
			if (this.babyTyrannosaurusEntity.isChild() && !this.babyTyrannosaurusEntity.isJuvenile()) {
				List<TyrannosaurusEntity> list = this.babyTyrannosaurusEntity.world.getEntitiesWithinAABB(this.babyTyrannosaurusEntity.getClass(), this.babyTyrannosaurusEntity.getBoundingBox().grow(8.0D, 4.0D, 8.0D));
				TyrannosaurusEntity tyrannosaurusEntity = null;
				double d0 = Double.MAX_VALUE;
				for (TyrannosaurusEntity tyrannosaurusEntity1 : list) {
					if (!tyrannosaurusEntity1.isChild()) {
						double d1 = this.babyTyrannosaurusEntity.getDistanceSq(tyrannosaurusEntity1);
						if (!(d1 > d0)) {
							d0 = d1;
							tyrannosaurusEntity = tyrannosaurusEntity1;
						}
					}
				}
				if (tyrannosaurusEntity == null) {
					return false;
				} else if (d0 < 9.0D) {
					return false;
				} else {
					this.parentTyrannosaurusEntity = tyrannosaurusEntity;
					return true;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinueExecuting() {
			if (!this.babyTyrannosaurusEntity.isJuvenile() || !this.babyTyrannosaurusEntity.isChild()) {
				return false;
			} else if (!this.parentTyrannosaurusEntity.isAlive()) {
				return false;
			} else  if(this.babyTyrannosaurusEntity.isChild() && !this.babyTyrannosaurusEntity.isJuvenile()){
				double d0 = this.babyTyrannosaurusEntity.getDistanceSq(this.parentTyrannosaurusEntity);
				return !(d0 < 9.0D) && !(d0 > 256.0D);
			} else {
				return false;
			}
		}

		public void startExecuting() {
			this.delayCounter = 0;
		}

		public void resetTask() {
			this.parentTyrannosaurusEntity = null;
		}

		public void tick() {
			if (--this.delayCounter <= 0) {
				this.delayCounter = 10;
				this.babyTyrannosaurusEntity.getNavigator().tryMoveToEntityLiving(this.parentTyrannosaurusEntity, this.moveSpeed);
			}
		}
	}
	
	class TerritoryAttackGoal extends NearestAttackableTargetGoal<PlayerEntity> {
		public TerritoryAttackGoal() {
			super(TyrannosaurusEntity.this, PlayerEntity.class, 20, true, true, (Predicate<LivingEntity>)null);
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean shouldExecute() {
			if (TyrannosaurusEntity.this.isChild()) {
				return false;
			} else {
				if (super.shouldExecute()) {
					for(@SuppressWarnings("unused") TyrannosaurusEntity tyrannosaurus : TyrannosaurusEntity.this.world.getEntitiesWithinAABB(TyrannosaurusEntity.class, TyrannosaurusEntity.this.getBoundingBox().grow(8.0D, 4.0D, 8.0D))) {
						return true;
					}
				}

				return false;
			}
		}

		protected double getTargetDistance() {
			return super.getTargetDistance() * 0.5D;
		}
	}

	class JuvenileHuntGoal extends HuntGoal {

		private TyrannosaurusEntity tyrannosaurus;

		@SuppressWarnings("rawtypes")
		public JuvenileHuntGoal(MobEntity goalOwnerIn, Class targetClassIn, int targetChanceIn, boolean checkSight, boolean nearbyOnlyIn, Predicate targetPredicate) {
			super(goalOwnerIn, targetClassIn, targetChanceIn, checkSight, nearbyOnlyIn, targetPredicate);
			this.tyrannosaurus = (TyrannosaurusEntity) goalOwnerIn;
		}

		public boolean shouldExecute() {
			if (super.shouldExecute() && tyrannosaurus.isJuvenile()) {
				return true;
			} else {
				return false;
			}
		}

	}

	@Override
	public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		TyrannosaurusEntity entity = new TyrannosaurusEntity(PFEntities.TYRANNOSAURUS_ENTITY, this.world);
		entity.onInitialSpawn(p_241840_1_, this.world.getDifficultyForLocation(new BlockPos(entity.getPositionVec())), SpawnReason.BREEDING, (ILivingEntityData)null, (CompoundNBT)null);
		return entity;
	}

}