package net.prizowo.rpgammoexpansion.mixin;

import com.atsuishio.superbwarfare.item.gun.GunItem;
import com.atsuishio.superbwarfare.item.gun.data.GunData;
import com.atsuishio.superbwarfare.item.gun.launcher.RpgItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.prizowo.rpgammoexpansion.config.RpgAmmoExpansionConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.function.Consumer;

@Mixin(RpgItem.class)
public abstract class RpgItemMixin extends GunItem {

    public RpgItemMixin(Properties properties) {
        super(properties);
    }

    @Override
    public int getCustomMagazine(ItemStack stack) {
        int configMagazineSize = RpgAmmoExpansionConfig.COMMON.rpgMagazineSize.get();

        return configMagazineSize - 1;
    }

    @Override
    public int getCustomRPM(ItemStack stack) {
        double rpmMultiplier = RpgAmmoExpansionConfig.COMMON.rpgVelocityMultiplier.get();

        return (int)(600 * rpmMultiplier) - 300;
    }

    @Override
    public boolean isAutoWeapon(ItemStack stack) {
        return RpgAmmoExpansionConfig.COMMON.enableAutoFireRPG.get();
    }

    @Override
    public boolean isOpenBolt(ItemStack stack) {
        return RpgAmmoExpansionConfig.COMMON.enableAutoFireRPG.get();
    }

    @Override
    public int getAvailableFireModes() {
        return RpgAmmoExpansionConfig.COMMON.enableAutoFireRPG.get() ? 
            GunItem.FireMode.SEMI.flag | GunItem.FireMode.AUTO.flag : 
            GunItem.FireMode.SEMI.flag;
    }

    @Inject(method = "shootBullet", at = @At("HEAD"), cancellable = false, remap = false)
    private void onBeforeShootBullet(Player player, GunData data, double spread, boolean zoom, CallbackInfoReturnable<Boolean> cir) {
        if (data.ammo.get() > 0) {
            data.isEmpty.set(false);
        }
    }


    @Inject(method = "shootBullet", at = @At("RETURN"), cancellable = false, remap = false)
    private void onAfterShootBullet(Player player, GunData data, double spread, boolean zoom, CallbackInfoReturnable<Boolean> cir) {
        if (data.ammo.get() <= 0) {
            data.isEmpty.set(true);
        } else if (RpgAmmoExpansionConfig.COMMON.enableAutoFireRPG.get()) {
            data.isEmpty.set(false);
        }
    }

    @Inject(method = "addReloadTimeBehavior", at = @At("RETURN"), remap = false)
    private void onAddReloadTimeBehavior(Map<Integer, Consumer<GunData>> behaviors, CallbackInfo ci) {
        behaviors.put(84, data -> {
            data.isEmpty.set(false);
        });
    }
} 