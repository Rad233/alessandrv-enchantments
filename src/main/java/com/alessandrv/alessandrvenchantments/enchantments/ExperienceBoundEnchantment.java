package com.alessandrv.alessandrvenchantments.enchantments;

import com.alessandrv.alessandrvenchantments.AlessandrvEnchantments;
import com.alessandrv.alessandrvenchantments.util.config.ModConfig;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.enchantment.BindingCurseEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.EnchantRandomlyLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;


public class ExperienceBoundEnchantment extends Enchantment {
    private static final ModConfig.SoulboundOptions CONFIG = AlessandrvEnchantments.getConfig().soulboundOptions;

    public ExperienceBoundEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentTarget.BREAKABLE, EquipmentSlot.values());
    }
    @Override
    public int getMinPower(int level) {
        return 15;
    }

    @Override
    public int getMaxLevel() {
        return CONFIG.isEnabled ? 1 : 0;
    }

    @Override
    public boolean isTreasure() {
        return CONFIG.isTreasure;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return CONFIG.randomSelection;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return CONFIG.bookOffer;
    }



    static
    {


        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, builder, source) ->
        {
            if(!id.equals(LootTables.END_CITY_TREASURE_CHEST))
                return;

            // Adding enchanted book to ancient city loot table3
            builder.pool(LootPool.builder()
                    .rolls(ConstantLootNumberProvider.create(1.0F))
                    .with(ItemEntry.builder(Items.BOOK)
                            .weight(5)
                            .apply(EnchantRandomlyLootFunction.create().add(ModEnchantments.SOULBOUND)))
                    .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0F)))
                    .with(EmptyEntry.builder()
                            .weight(10))
                    .build());
        }));
    }
}
