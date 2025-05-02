package net.prizowo.rpgammoexpansion.mixin;

import com.atsuishio.superbwarfare.item.gun.data.GunData;
import com.atsuishio.superbwarfare.item.gun.launcher.RpgItem;
import net.minecraft.world.item.ItemStack;
import net.prizowo.rpgammoexpansion.config.RpgAmmoExpansionConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GunData.class)
public class GunDataMixin {

    @Shadow @Final public ItemStack stack;
    @Inject(method = "magazine", at = @At("HEAD"), cancellable = true, remap = false)
    private void onMagazine(CallbackInfoReturnable<Integer> cir) {
        // 只修改RPG的弹药容量
        if (stack.getItem() instanceof RpgItem) {
            GunData self = (GunData)(Object)this;
            if (self.data().contains("CustomMagazineCapacity")) {
                int customCapacity = self.data().getInt("CustomMagazineCapacity");
                cir.setReturnValue(customCapacity);
            } else {
                cir.setReturnValue(RpgAmmoExpansionConfig.COMMON.rpgMagazineSize.get());
            }
        }
    }
} 