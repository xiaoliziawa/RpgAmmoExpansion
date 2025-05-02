package net.prizowo.rpgammoexpansion.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class RpgAmmoExpansionConfig {
    public static final CommonConfig COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static class CommonConfig {
        public final ForgeConfigSpec.IntValue rpgMagazineSize;
        public final ForgeConfigSpec.BooleanValue disableEmptyAfterShot;
        public final ForgeConfigSpec.DoubleValue rpgVelocityMultiplier;
        public final ForgeConfigSpec.BooleanValue enableAutoFireRPG;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.comment("RPG弹药容量扩展设置").push("rpg_ammo");

            rpgMagazineSize = builder
                    .comment("RPG弹药容量")
                    .defineInRange("rpgMagazineSize", 10, 1, 100);

            disableEmptyAfterShot = builder
                    .comment("禁用每次射击后自动标记为空")
                    .define("disableEmptyAfterShot", true);

            rpgVelocityMultiplier = builder
                    .comment("RPG射击速度倍率（默认为3.0，数值越大发射越快）")
                    .defineInRange("rpgVelocityMultiplier", 3.0, 0.1, 20.0);

            enableAutoFireRPG = builder
                    .comment("启用RPG自动发射模式（使RPG变成机关枪）")
                    .define("enableAutoFireRPG", true);

            builder.pop();
        }
    }
} 