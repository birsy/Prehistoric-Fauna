package superlord.prehistoricfauna.common.entities.jurassic.morrison;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
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
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import superlord.prehistoricfauna.common.blocks.DinosaurEggBlock;
import superlord.prehistoricfauna.common.entities.DinosaurEntity;
import superlord.prehistoricfauna.common.entities.cretaceous.djadochta.AepyornithomimusEntity;
import superlord.prehistoricfauna.common.entities.cretaceous.djadochta.CitipatiEntity;
import superlord.prehistoricfauna.common.entities.cretaceous.djadochta.PlesiohadrosEntity;
import superlord.prehistoricfauna.common.entities.cretaceous.djadochta.ProtoceratopsEntity;
import superlord.prehistoricfauna.common.entities.cretaceous.djadochta.TelmasaurusEntity;
import superlord.prehistoricfauna.common.entities.cretaceous.djadochta.VelociraptorEntity;
import superlord.prehistoricfauna.common.entities.cretaceous.hellcreek.AnkylosaurusEntity;
import superlord.prehistoricfauna.common.entities.cretaceous.hellcreek.BasilemysEntity;
import superlord.prehistoricfauna.common.entities.cretaceous.hellcreek.DidelphodonEntity;
import superlord.prehistoricfauna.common.entities.cretaceous.hellcreek.ThescelosaurusEntity;
import superlord.prehistoricfauna.common.entities.cretaceous.hellcreek.TriceratopsEntity;
import superlord.prehistoricfauna.common.entities.cretaceous.hellcreek.TyrannosaurusEntity;
import superlord.prehistoricfauna.common.entities.goal.CrepuscularSleepGoal;
import superlord.prehistoricfauna.common.entities.goal.DinosaurLookAtGoal;
import superlord.prehistoricfauna.common.entities.goal.DinosaurRandomLookGoal;
import superlord.prehistoricfauna.common.entities.goal.HuntGoal;
import superlord.prehistoricfauna.common.entities.jurassic.kayenta.KayentatheriumEntity;
import superlord.prehistoricfauna.common.entities.jurassic.kayenta.MegapnosaurusEntity;
import superlord.prehistoricfauna.common.entities.jurassic.kayenta.SarahsaurusEntity;
import superlord.prehistoricfauna.common.entities.jurassic.kayenta.ScelidosaurusEntity;
import superlord.prehistoricfauna.common.entities.jurassic.kayenta.ScutellosaurusEntity;
import superlord.prehistoricfauna.common.entities.triassic.chinle.CoelophysisEntity;
import superlord.prehistoricfauna.common.entities.triassic.chinle.DesmatosuchusEntity;
import superlord.prehistoricfauna.common.entities.triassic.chinle.PlaceriasEntity;
import superlord.prehistoricfauna.common.entities.triassic.chinle.TrilophosaurusEntity;
import superlord.prehistoricfauna.common.entities.triassic.chinle.TypothoraxEntity;
import superlord.prehistoricfauna.common.entities.triassic.ischigualasto.ChromogisaurusEntity;
import superlord.prehistoricfauna.common.entities.triassic.ischigualasto.ExaeretodonEntity;
import superlord.prehistoricfauna.common.entities.triassic.ischigualasto.HerrerasaurusEntity;
import superlord.prehistoricfauna.common.entities.triassic.ischigualasto.HyperodapedonEntity;
import superlord.prehistoricfauna.common.entities.triassic.ischigualasto.IschigualastiaEntity;
import superlord.prehistoricfauna.common.entities.triassic.ischigualasto.SaurosuchusEntity;
import superlord.prehistoricfauna.config.PrehistoricFaunaConfig;
import superlord.prehistoricfauna.init.PFBlocks;
import superlord.prehistoricfauna.init.PFEntities;
import superlord.prehistoricfauna.init.PFItems;
import superlord.prehistoricfauna.init.SoundInit;

public class CeratosaurusEntity extends DinosaurEntity {

	private static final DataParameter<Boolean> HAS_EGG = EntityDataManager.createKey(CeratosaurusEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> IS_DIGGING = EntityDataManager.createKey(CeratosaurusEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> ALBINO = EntityDataManager.createKey(CeratosaurusEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> MELANISTIC = EntityDataManager.createKey(CeratosaurusEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> NATURAL_LOVE = EntityDataManager.createKey(CeratosaurusEntity.class, DataSerializers.BOOLEAN);
	private int maxHunger = 100;
	private int lastInLove = 0;
	private int currentHunger;
	int hungerTick = 0;
	private int warningSoundTicks;
	private int isDigging;
	private Goal attackAnimals;
	int loveTick = 0;

	public CeratosaurusEntity(EntityType<? extends CeratosaurusEntity> type, World worldIn) {
		super(type, worldIn);
	}

	public boolean isDigging() {
		return this.getDataManager().get(IS_DIGGING);
	}

	private void setDigging(boolean isDigging) {
		this.isDigging = isDigging ? 1 : 0;
		this.dataManager.set(IS_DIGGING, isDigging);
	}

	public boolean hasEgg() {
		return this.getDataManager().get(HAS_EGG);
	}

	private void setHasEgg(boolean hasEgg) {
		this.dataManager.set(HAS_EGG, hasEgg);
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

	public boolean isInLoveNaturally() {
		return this.dataManager.get(NATURAL_LOVE);
	}

	private void setInLoveNaturally(boolean isInLoveNaturally) {
		this.dataManager.set(NATURAL_LOVE, isInLoveNaturally);
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

	@Nullable
	public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		Random rand = new Random();
		int birthNumber = rand.nextInt(799);
		if (birthNumber >= 0 && birthNumber < 4) {
			this.setAlbino(true);
		} else if (birthNumber >= 4 && birthNumber < 7) {
			this.setMelanistic(true);
		}
		this.setHunger(this.maxHunger);
		return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == PFItems.RAW_SMALL_ORNITHISCHIAN_MEAT.get();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.attackAnimals = new HuntGoal(this, AnimalEntity.class, 10, false, false, (p_213487_1_) -> {
			return p_213487_1_ instanceof AepyornithomimusEntity || p_213487_1_ instanceof BasilemysEntity || p_213487_1_ instanceof ChromogisaurusEntity || p_213487_1_ instanceof CitipatiEntity || p_213487_1_ instanceof DryosaurusEntity || p_213487_1_ instanceof ExaeretodonEntity || p_213487_1_ instanceof HerrerasaurusEntity || p_213487_1_ instanceof IschigualastiaEntity || p_213487_1_ instanceof PlesiohadrosEntity || p_213487_1_ instanceof ProtoceratopsEntity || p_213487_1_ instanceof SaurosuchusEntity || p_213487_1_ instanceof TelmasaurusEntity || p_213487_1_ instanceof ThescelosaurusEntity || p_213487_1_ instanceof VelociraptorEntity || p_213487_1_ instanceof CatEntity || p_213487_1_ instanceof OcelotEntity || p_213487_1_ instanceof CowEntity || p_213487_1_ instanceof AbstractHorseEntity || p_213487_1_ instanceof FoxEntity || p_213487_1_ instanceof MooshroomEntity || p_213487_1_ instanceof PigEntity || p_213487_1_ instanceof AbstractVillagerEntity || p_213487_1_ instanceof WanderingTraderEntity || p_213487_1_ instanceof AbstractIllagerEntity || p_213487_1_ instanceof LlamaEntity || p_213487_1_ instanceof PandaEntity || p_213487_1_ instanceof WolfEntity || p_213487_1_ instanceof TurtleEntity || p_213487_1_ instanceof PlayerEntity || p_213487_1_ instanceof ScelidosaurusEntity || p_213487_1_ instanceof SarahsaurusEntity || p_213487_1_ instanceof MegapnosaurusEntity || p_213487_1_ instanceof KayentatheriumEntity || p_213487_1_ instanceof SheepEntity || p_213487_1_ instanceof CoelophysisEntity || p_213487_1_ instanceof DesmatosuchusEntity || p_213487_1_ instanceof PlaceriasEntity || p_213487_1_ instanceof TrilophosaurusEntity || p_213487_1_ instanceof TypothoraxEntity;
		});
		this.goalSelector.addGoal(1, new CeratosaurusEntity.MeleeAttackGoal());
		this.goalSelector.addGoal(1, new CeratosaurusEntity.PanicGoal());
		this.targetSelector.addGoal(1, new CeratosaurusEntity.HurtByTargetGoal());
		this.targetSelector.addGoal(2, new CeratosaurusEntity.AttackPlayerGoal());
		this.targetSelector.addGoal(2, new CeratosaurusEntity.TerritoryAttackGoal());
		this.goalSelector.addGoal(0, new CeratosaurusEntity.MateGoal(this, 1.0D));
		this.goalSelector.addGoal(0, new CeratosaurusEntity.NaturalMateGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new DinosaurLookAtGoal(this, PlayerEntity.class, 6.0F));
		this.goalSelector.addGoal(6, new DinosaurRandomLookGoal(this));
		this.goalSelector.addGoal(0, new CeratosaurusEntity.LayEggGoal(this, 1.0D));
		this.goalSelector.addGoal(9, new AvoidEntityGoal(this, AllosaurusEntity.class, 7F, 1.25D, 1.25D));
		this.goalSelector.addGoal(9, new AvoidEntityGoal(this, StegosaurusEntity.class, 7F, 1.25D, 1.25D));
		this.goalSelector.addGoal(9, new AvoidEntityGoal(this, CamarasaurusEntity.class, 7F, 1.25D, 1.25D));
		this.goalSelector.addGoal(9, new AvoidEntityGoal(this, TriceratopsEntity.class, 7F, 1.25D, 1.25D));
		this.goalSelector.addGoal(9, new AvoidEntityGoal(this, AnkylosaurusEntity.class, 7F, 1.25D, 1.25D));
		this.goalSelector.addGoal(9, new AvoidEntityGoal(this, TyrannosaurusEntity.class, 7F, 1.25D, 1.25D));
		this.goalSelector.addGoal(1, new CrepuscularSleepGoal(this));
		this.goalSelector.addGoal(0, new CeratosaurusEntity.CarnivoreHuntGoal(this, LivingEntity.class, 10, false, false, (p_213487_1_) -> {
			return p_213487_1_ instanceof AepyornithomimusEntity || p_213487_1_ instanceof BasilemysEntity || p_213487_1_ instanceof ChromogisaurusEntity || p_213487_1_ instanceof CitipatiEntity || p_213487_1_ instanceof DryosaurusEntity || p_213487_1_ instanceof ExaeretodonEntity || p_213487_1_ instanceof HerrerasaurusEntity || p_213487_1_ instanceof IschigualastiaEntity || p_213487_1_ instanceof PlesiohadrosEntity || p_213487_1_ instanceof ProtoceratopsEntity || p_213487_1_ instanceof SaurosuchusEntity || p_213487_1_ instanceof TelmasaurusEntity || p_213487_1_ instanceof ThescelosaurusEntity || p_213487_1_ instanceof VelociraptorEntity || p_213487_1_ instanceof CatEntity || p_213487_1_ instanceof OcelotEntity || p_213487_1_ instanceof CowEntity || p_213487_1_ instanceof AbstractHorseEntity || p_213487_1_ instanceof FoxEntity || p_213487_1_ instanceof MooshroomEntity || p_213487_1_ instanceof PigEntity || p_213487_1_ instanceof AbstractVillagerEntity || p_213487_1_ instanceof WanderingTraderEntity || p_213487_1_ instanceof AbstractIllagerEntity || p_213487_1_ instanceof LlamaEntity || p_213487_1_ instanceof PandaEntity || p_213487_1_ instanceof WolfEntity || p_213487_1_ instanceof TurtleEntity || p_213487_1_ instanceof PlayerEntity || p_213487_1_ instanceof ScelidosaurusEntity || p_213487_1_ instanceof SarahsaurusEntity || p_213487_1_ instanceof MegapnosaurusEntity || p_213487_1_ instanceof KayentatheriumEntity || p_213487_1_ instanceof SheepEntity || p_213487_1_ instanceof CoelophysisEntity || p_213487_1_ instanceof DesmatosuchusEntity || p_213487_1_ instanceof PlaceriasEntity || p_213487_1_ instanceof TrilophosaurusEntity || p_213487_1_ instanceof TypothoraxEntity;
		}));
		this.targetSelector.addGoal(0, new BabyCarnivoreHuntGoal(this, LivingEntity.class, 10, true, false, (p_213487_1_) -> {
			return p_213487_1_ instanceof DidelphodonEntity || p_213487_1_ instanceof EilenodonEntity || p_213487_1_ instanceof HyperodapedonEntity || p_213487_1_ instanceof TelmasaurusEntity || p_213487_1_ instanceof RabbitEntity || p_213487_1_ instanceof ChickenEntity || p_213487_1_ instanceof HesperornithoidesEntity || p_213487_1_ instanceof ScutellosaurusEntity;
		}));
	}

	public void livingTick() {
		super.livingTick();
		if (this.isAsleep()) {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
		} else {
			this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
		}
		if (!this.isAIDisabled()) {
			List<CeratosaurusEntity> list = this.world.getEntitiesWithinAABB(this.getClass(), this.getBoundingBox().grow(20.0D, 20.0D, 20.0D));
			if (PrehistoricFaunaConfig.advancedHunger) {
				hungerTick++;
				if (hungerTick == 600 && !this.isChild() || hungerTick == 300 && this.isChild()) {
					hungerTick = 0;
					if (currentHunger != 0 || !this.isAsleep()) {
						this.setHunger(currentHunger - 1);
					}
					if (currentHunger == 0 && PrehistoricFaunaConfig.hungerDamage && this.getHealth() > (this.getMaxHealth() / 2)) {
						this.damageEntity(DamageSource.STARVE, 1);
					}
					if (currentHunger == 0 && PrehistoricFaunaConfig.hungerDamage && world.getDifficulty() == Difficulty.HARD) {
						this.damageEntity(DamageSource.STARVE, 1);
					}
				}
				if (this.getCurrentHunger() >= this.getThreeQuartersHunger() && hungerTick % 150 == 0) {
					if (this.getHealth() < this.getMaxHealth() && this.getHealth() != 0 && this.getAttackTarget() == null && this.getRevengeTarget() == null) {
						float currentHealth = this.getHealth();
						this.setHealth(currentHealth + 1);
					}
				}
				if (PrehistoricFaunaConfig.naturalEggBlockLaying || PrehistoricFaunaConfig.naturalEggItemLaying) {
					if (lastInLove == 0 && currentHunger >= getThreeQuartersHunger() && ticksExisted % 900 == 0 && !this.isChild() && !this.isInLove() && !this.isAsleep() && list.size() < 3) {
						loveTick = 600;
						this.setInLoveNaturally(true);
						this.setInLove(600);
						lastInLove = 28800;
					}
					if (loveTick != 0) {
						loveTick--;
					} else {
						this.setInLoveNaturally(false);
					}
				}
			} else if (PrehistoricFaunaConfig.naturalEggBlockLaying || PrehistoricFaunaConfig.naturalEggItemLaying) {
				int naturalBreedingChance = rand.nextInt(1000);
				if (lastInLove == 0 && naturalBreedingChance == 0 && !this.isChild() && !this.isInLove() && !this.isAsleep() && list.size() < 3) {
					loveTick = 600;
					this.setInLoveNaturally(true);
					this.setInLove(600);
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

	public static AttributeModifierMap.MutableAttribute createAttributes() {
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 40.0D).createMutableAttribute(Attributes.FOLLOW_RANGE, 20.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0D).createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.25D);
	}

	private void setAttackGoals() {
		this.targetSelector.addGoal(4, this.attackAnimals);
	}

	protected SoundEvent getAmbientSound() {
		return this.isAsleep() ? null : SoundInit.CERATOSAURUS_IDLE;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundInit.CERATOSAURUS_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundInit.CERATOSAURUS_DEATH;
	}

	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1F);
	}

	protected void playWarningSound() {
		if (this.warningSoundTicks <= 0) {
			this.playSound(SoundInit.CERATOSAURUS_WARN, 1.0F, this.getSoundPitch());
			this.warningSoundTicks = 40;
		}
	}

	protected void registerData() {
		super.registerData();
		this.dataManager.register(HAS_EGG, false);
		this.dataManager.register(IS_DIGGING, false);
		this.dataManager.register(ALBINO, false);
		this.dataManager.register(MELANISTIC, false);
		this.dataManager.register(NATURAL_LOVE, false);
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putBoolean("HasEgg", this.hasEgg());
		compound.putBoolean("IsAlbino", this.isAlbino());
		compound.putBoolean("IsMelanistic", this.isMelanistic());
		compound.putInt("MaxHunger", this.currentHunger);
		compound.putBoolean("InNaturalLove", this.isInLoveNaturally());
	}

	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		this.setHasEgg(compound.getBoolean("HasEgg"));
		this.setAlbino(compound.getBoolean("IsAlbino"));
		this.setMelanistic(compound.getBoolean("IsMelanistic"));
		this.setAttackGoals();
		this.setHunger(compound.getInt("MaxHunger"));
		this.setInLoveNaturally(compound.getBoolean("InNaturalLove"));
	}

	public void tick() {
		super.tick();
		if (this.warningSoundTicks > 0) {
			--this.warningSoundTicks;
		}
	}

	public boolean attackEntityAsMob(Entity entity) {
		boolean flag = super.attackEntityAsMob(entity);
		if (flag) {
			this.applyEnchantments(this, entity);
		}
		return flag;
	}

	class AttackPlayerGoal extends NearestAttackableTargetGoal<PlayerEntity> {
		public AttackPlayerGoal() {
			super(CeratosaurusEntity.this, PlayerEntity.class, 20, true, true, (Predicate<LivingEntity>)null);
		}

		public boolean shouldExecute() {
			if (CeratosaurusEntity.this.isChild()) {
				return false;
			} else {
				if (super.shouldExecute()) {
					for (@SuppressWarnings("unused") CeratosaurusEntity ceratosaurus : CeratosaurusEntity.this.world.getEntitiesWithinAABB(CeratosaurusEntity.class, CeratosaurusEntity.this.getBoundingBox().grow(8.0D, 4.0D, 8.0D))) {	
						if (CeratosaurusEntity.this.isChild()) {
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

	class TerritoryAttackGoal extends NearestAttackableTargetGoal<PlayerEntity> {
		public TerritoryAttackGoal() {
			super(CeratosaurusEntity.this, PlayerEntity.class, 20, true, true, (Predicate<LivingEntity>)null);
		}

		/**
		 * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
		 * method as well.
		 */
		public boolean shouldExecute() {
			if (CeratosaurusEntity.this.isChild()) {
				return false;
			} else {
				if (super.shouldExecute()) {
					for(@SuppressWarnings("unused") CeratosaurusEntity ceratosaurus : CeratosaurusEntity.this.world.getEntitiesWithinAABB(CeratosaurusEntity.class, CeratosaurusEntity.this.getBoundingBox().grow(8.0D, 4.0D, 8.0D))) {
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

	class HurtByTargetGoal extends net.minecraft.entity.ai.goal.HurtByTargetGoal {
		public HurtByTargetGoal() {
			super(CeratosaurusEntity.this);
		}

		public void startExecuting() {
			super.startExecuting();
			if(CeratosaurusEntity.this.isChild()) {
				this.alertOthers();
				this.resetTask();
			}
		}

		protected void setAttackTarget(MobEntity entity, LivingEntity target) {
			if (entity instanceof CeratosaurusEntity && !entity.isChild()) {
				super.setAttackTarget(entity, target);
			}
		}

	}

	class MeleeAttackGoal extends net.minecraft.entity.ai.goal.MeleeAttackGoal {
		public MeleeAttackGoal() {
			super(CeratosaurusEntity.this, 1.25D, true);
		}

		protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
			double d0 = this.getAttackReachSqr(enemy);
			if (distToEnemySqr <= d0 && this.func_234040_h_()) {
				this.func_234039_g_();
				CeratosaurusEntity.this.playSound(SoundInit.CERATOSAURUS_BITE, 1.0F, CeratosaurusEntity.this.getSoundPitch());
				this.attacker.attackEntityAsMob(enemy);
			} else if (distToEnemySqr <= d0 * 2.0D) {
				if (this.func_234040_h_()) {
					this.func_234039_g_();
				}

				if (this.func_234041_j_() <= 10) {
					CeratosaurusEntity.this.playWarningSound();
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
			return (double)(4.0F + attackTarget.getWidth());
		}
	}

	class PanicGoal extends net.minecraft.entity.ai.goal.PanicGoal {
		public PanicGoal() {
			super(CeratosaurusEntity.this, 2.0D);
		}

		public boolean shouldExecute() {
			return !CeratosaurusEntity.this.isChild() && !CeratosaurusEntity.this.isBurning() ? false : super.shouldExecute();
		}

	}

	static class LayEggGoal extends MoveToBlockGoal {
		private final CeratosaurusEntity ceratosaurus;

		LayEggGoal(CeratosaurusEntity ceratosaurus, double speed) {
			super(ceratosaurus, speed, 16);
			this.ceratosaurus = ceratosaurus;
		}

		public boolean shouldExecute() {
			return this.ceratosaurus.hasEgg() ? super.shouldExecute() : false;
		}

		public boolean shouldContinueExecuting() {
			return super.shouldContinueExecuting() && ceratosaurus.hasEgg();
		}

		public void tick() {
			super.tick();
			BlockPos blockpos = new BlockPos(this.ceratosaurus.getPositionVec());
			if (!this.ceratosaurus.isInWater() && this.getIsAboveDestination()) {
				if (this.ceratosaurus.isDigging < 1) {
					this.ceratosaurus.setDigging(true);
				} else if (this.ceratosaurus.isDigging > 200) {
					World world = this.ceratosaurus.world;
					world.playSound((PlayerEntity)null, blockpos, SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS, 0.3F, 0.9F + world.rand.nextFloat() * 0.2F);
					world.setBlockState(this.destinationBlock.up(), PFBlocks.CERATOSAURUS_EGG.getDefaultState().with(DinosaurEggBlock.EGGS, Integer.valueOf(this.ceratosaurus.rand.nextInt(4) + 1)), 3);
					this.ceratosaurus.setHasEgg(false);
					this.ceratosaurus.setDigging(false);
					this.ceratosaurus.setInLove(600);
				}
				if (this.ceratosaurus.isDigging()) {
					this.ceratosaurus.isDigging++;
				}
			}
		}

		public boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
			if (!worldIn.isAirBlock(pos.up())) {
				return false;
			} else {
				Block block = worldIn.getBlockState(pos).getBlock();
				return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL || block == Blocks.MYCELIUM || block == Blocks.SAND || block == Blocks.RED_SAND || block == PFBlocks.MOSSY_DIRT || block == PFBlocks.MOSS_BLOCK || block == PFBlocks.LOAM || block == PFBlocks.PACKED_LOAM || block == PFBlocks.SILT || block == PFBlocks.PACKED_LOAM || block == BlockTags.LEAVES;
			}
		}

	}

	static class MateGoal extends BreedGoal {
		private final CeratosaurusEntity ceratosaurus;

		MateGoal(CeratosaurusEntity ceratosaurus, double speed) {
			super(ceratosaurus, speed);
			this.ceratosaurus = ceratosaurus;
		}

		public boolean shouldExecute() {
			return super.shouldExecute() && !this.ceratosaurus.hasEgg() && !this.ceratosaurus.isInLoveNaturally();
		}

		protected void spawnBaby() {
			ServerPlayerEntity serverPlayerEntity = this.animal.getLoveCause();
			if (serverPlayerEntity == null && this.targetMate.getLoveCause() == null) {
				serverPlayerEntity = this.targetMate.getLoveCause();
			}
			if (serverPlayerEntity != null) {
				serverPlayerEntity.addStat(Stats.ANIMALS_BRED);
				CriteriaTriggers.BRED_ANIMALS.trigger(serverPlayerEntity, this.animal, this.targetMate, (AgeableEntity)null);
			}
			this.ceratosaurus.setHasEgg(true);
			this.animal.resetInLove();
			this.targetMate.resetInLove();
			Random random = this.animal.getRNG();
			if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
				this.world.addEntity(new ExperienceOrbEntity(this.world, this.animal.getPosX(), this.animal.getPosY(), this.animal.getPosZ(), random.nextInt(7) + 1));
			}
		}

	}

	static class NaturalMateGoal extends BreedGoal {
		private final CeratosaurusEntity ceratosaurus;

		NaturalMateGoal(CeratosaurusEntity ceratosaurus, double speed) {
			super(ceratosaurus, speed);
			this.ceratosaurus = ceratosaurus;
		}

		public boolean shouldExecute() {
			return super.shouldExecute() && !this.ceratosaurus.hasEgg() && this.ceratosaurus.getCurrentHunger() >= this.ceratosaurus.getThreeQuartersHunger() && this.ceratosaurus.ticksExisted % 60 == 0 && (PrehistoricFaunaConfig.naturalEggBlockLaying || PrehistoricFaunaConfig.naturalEggItemLaying) && this.ceratosaurus.isInLoveNaturally();
		}

		protected void spawnBaby() {
			if (PrehistoricFaunaConfig.naturalEggItemLaying) {
				this.ceratosaurus.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.ceratosaurus.rand.nextFloat() - this.ceratosaurus.rand.nextFloat()) * 0.2F + 1.0F);
				int eggAmount = this.ceratosaurus.rand.nextInt(4);
				if (eggAmount == 0) {
					this.ceratosaurus.entityDropItem(PFBlocks.CERATOSAURUS_EGG.asItem());
				}
				if (eggAmount == 1) {
					this.ceratosaurus.entityDropItem(PFBlocks.CERATOSAURUS_EGG.asItem());
					this.ceratosaurus.entityDropItem(PFBlocks.CERATOSAURUS_EGG.asItem());
				}
				if (eggAmount == 2) {
					this.ceratosaurus.entityDropItem(PFBlocks.CERATOSAURUS_EGG.asItem());
					this.ceratosaurus.entityDropItem(PFBlocks.CERATOSAURUS_EGG.asItem());
					this.ceratosaurus.entityDropItem(PFBlocks.CERATOSAURUS_EGG.asItem());
				}
				if (eggAmount == 3) {
					this.ceratosaurus.entityDropItem(PFBlocks.CERATOSAURUS_EGG.asItem());
					this.ceratosaurus.entityDropItem(PFBlocks.CERATOSAURUS_EGG.asItem());
					this.ceratosaurus.entityDropItem(PFBlocks.CERATOSAURUS_EGG.asItem());
					this.ceratosaurus.entityDropItem(PFBlocks.CERATOSAURUS_EGG.asItem());
				}
			} else {
				this.ceratosaurus.setHasEgg(true);
			}
			this.animal.resetInLove();
			this.targetMate.resetInLove();
		}

	}

	@Override
	public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
		CeratosaurusEntity entity = new CeratosaurusEntity(PFEntities.CERATOSAURUS_ENTITY, this.world);
		entity.onInitialSpawn((IServerWorld) this.world, this.world.getDifficultyForLocation(new BlockPos(entity.getPositionVec())), SpawnReason.BREEDING, (ILivingEntityData)null, (CompoundNBT)null);
		return entity;
	}

	@SuppressWarnings("rawtypes")
	public class CarnivoreHuntGoal extends NearestAttackableTargetGoal {

		@SuppressWarnings("unchecked")
		public CarnivoreHuntGoal(MobEntity goalOwnerIn, Class targetClassIn, int targetChanceIn, boolean checkSight, boolean nearbyOnly, @Nullable Predicate<LivingEntity> targetPredicate) {
			super(goalOwnerIn, targetClassIn, targetChanceIn, checkSight, nearbyOnly, targetPredicate);
		}

		public boolean shouldExecute() {
			return super.shouldExecute() && CeratosaurusEntity.this.getCurrentHunger() <= CeratosaurusEntity.this.getHalfHunger() && !CeratosaurusEntity.this.isChild();
		}

		public boolean shouldContinueExecuting() {
			return CeratosaurusEntity.this.getCurrentHunger() < CeratosaurusEntity.this.maxHunger;
		}

		public void tick() {
			LivingEntity target = CeratosaurusEntity.this.getAttackTarget();
			if (target instanceof ChromogisaurusEntity || target instanceof TelmasaurusEntity || target instanceof MegapnosaurusEntity || target instanceof KayentatheriumEntity) {
				if (target.getHealth() == 0) {
					if (CeratosaurusEntity.this.getCurrentHunger() + 6 >= CeratosaurusEntity.this.maxHunger) {
						CeratosaurusEntity.this.setHunger(CeratosaurusEntity.this.maxHunger);
					} else {
						CeratosaurusEntity.this.setHunger(currentHunger + 6);
					}
				}
			}
			if (target instanceof VelociraptorEntity || target instanceof ExaeretodonEntity || target instanceof BasilemysEntity || target instanceof WolfEntity || target instanceof SheepEntity) {
				if (target.getHealth() == 0) {
					if (CeratosaurusEntity.this.getCurrentHunger() + 8 >= CeratosaurusEntity.this.maxHunger) {
						CeratosaurusEntity.this.setHunger(CeratosaurusEntity.this.maxHunger);
					} else {
						CeratosaurusEntity.this.setHunger(currentHunger + 8);
					}
				}
			}
			if (target instanceof CatEntity || target instanceof FoxEntity || target instanceof CowEntity || target instanceof MooshroomEntity || target instanceof PigEntity || target instanceof AepyornithomimusEntity || target instanceof OcelotEntity || target instanceof CoelophysisEntity || target instanceof ProtoceratopsEntity || target instanceof TrilophosaurusEntity || target instanceof TypothoraxEntity) {
				if (target.getHealth() == 0) {
					if (CeratosaurusEntity.this.getCurrentHunger() + 10 >= CeratosaurusEntity.this.maxHunger) {
						CeratosaurusEntity.this.setHunger(CeratosaurusEntity.this.maxHunger);
					} else {
						CeratosaurusEntity.this.setHunger(currentHunger + 10);
					}
				}
			}
			if (target instanceof CitipatiEntity || target instanceof DryosaurusEntity || target instanceof ThescelosaurusEntity || target instanceof SarahsaurusEntity || target instanceof ScelidosaurusEntity) {
				if (target.getHealth() == 0) {
					if (CeratosaurusEntity.this.getCurrentHunger() + 15 >= CeratosaurusEntity.this.maxHunger) {
						CeratosaurusEntity.this.setHunger(CeratosaurusEntity.this.maxHunger);
					} else {
						CeratosaurusEntity.this.setHunger(currentHunger + 15);
					}
				}
			}
			if (target instanceof SaurosuchusEntity || target instanceof AbstractHorseEntity || target instanceof DesmatosuchusEntity || target instanceof WanderingTraderEntity || target instanceof PlayerEntity || target instanceof AbstractVillagerEntity || target instanceof AbstractIllagerEntity || target instanceof LlamaEntity || target instanceof PandaEntity || target instanceof HerrerasaurusEntity || target instanceof PlaceriasEntity) {
				if (target.getHealth() == 0) {
					if (CeratosaurusEntity.this.getCurrentHunger() + 20 >= CeratosaurusEntity.this.maxHunger) {
						CeratosaurusEntity.this.setHunger(CeratosaurusEntity.this.maxHunger);
					} else {
						CeratosaurusEntity.this.setHunger(currentHunger + 20);
					}
				}
			}
			if (target instanceof TurtleEntity) {
				if (target.getHealth() == 0) {
					if (CeratosaurusEntity.this.getCurrentHunger() + 30 >= CeratosaurusEntity.this.maxHunger) {
						CeratosaurusEntity.this.setHunger(CeratosaurusEntity.this.maxHunger);
					} else {
						CeratosaurusEntity.this.setHunger(currentHunger + 30);
					}
				}
			}
			if (target instanceof IschigualastiaEntity || target instanceof PlesiohadrosEntity) {
				if (target.getHealth() == 0) {
					if (CeratosaurusEntity.this.getCurrentHunger() + 40 >= CeratosaurusEntity.this.maxHunger) {
						CeratosaurusEntity.this.setHunger(CeratosaurusEntity.this.maxHunger);
					} else {
						CeratosaurusEntity.this.setHunger(currentHunger + 40);
					}
				}
			}
			super.tick();
		}

	}

	@SuppressWarnings("rawtypes")
	public class BabyCarnivoreHuntGoal extends NearestAttackableTargetGoal {

		@SuppressWarnings("unchecked")
		public BabyCarnivoreHuntGoal(MobEntity goalOwnerIn, Class targetClassIn, int targetChanceIn, boolean checkSight, boolean nearbyOnly, @Nullable Predicate<LivingEntity> targetPredicate) {
			super(goalOwnerIn, targetClassIn, targetChanceIn, checkSight, nearbyOnly, targetPredicate);
		}

		public boolean shouldExecute() {
			return super.shouldExecute() && CeratosaurusEntity.this.getCurrentHunger() <= CeratosaurusEntity.this.getHalfHunger() && CeratosaurusEntity.this.isChild();
		}

		public boolean shouldContinueExecuting() {
			return CeratosaurusEntity.this.getCurrentHunger() < CeratosaurusEntity.this.maxHunger || !CeratosaurusEntity.this.isChild();
		}

		public void tick() {
			LivingEntity target = CeratosaurusEntity.this.getAttackTarget();
			if (target instanceof RabbitEntity) {
				if (target.getHealth() == 0) {
					if (CeratosaurusEntity.this.getCurrentHunger() + 3 >= CeratosaurusEntity.this.maxHunger) {
						CeratosaurusEntity.this.setHunger(CeratosaurusEntity.this.maxHunger);
					} else {
						CeratosaurusEntity.this.setHunger(currentHunger + 3);
					}
				}
			}
			if (target instanceof DidelphodonEntity || target instanceof EilenodonEntity || target instanceof HyperodapedonEntity || target instanceof ChickenEntity || target instanceof HesperornithoidesEntity || target instanceof ScutellosaurusEntity) {
				if (target.getHealth() == 0) {
					if (CeratosaurusEntity.this.getCurrentHunger() + 4 >= CeratosaurusEntity.this.maxHunger) {
						CeratosaurusEntity.this.setHunger(CeratosaurusEntity.this.maxHunger);
					} else {
						CeratosaurusEntity.this.setHunger(currentHunger + 4);
					}
				}
			}
			if (target instanceof TelmasaurusEntity) {
				if (target.getHealth() == 0) {
					if (CeratosaurusEntity.this.getCurrentHunger() + 6 >= CeratosaurusEntity.this.maxHunger) {
						CeratosaurusEntity.this.setHunger(CeratosaurusEntity.this.maxHunger);
					} else {
						CeratosaurusEntity.this.setHunger(currentHunger + 6);
					}
				}
			}
			super.tick();
		}

	}


}
