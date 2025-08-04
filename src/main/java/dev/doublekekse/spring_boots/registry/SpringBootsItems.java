package dev.doublekekse.spring_boots.registry;

import dev.doublekekse.spring_boots.SpringBoots;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.function.Function;

public class SpringBootsItems {
    public static final Item SPRING_BOOTS = register("spring_boots",
        new Item.Properties()
            .humanoidArmor(ArmorMaterials.LEATHER, ArmorType.BOOTS)
            .component(SpringBootsComponents.SPRING_BOOTS, Unit.INSTANCE)
            .component(DataComponents.ATTRIBUTE_MODIFIERS,
                ItemAttributeModifiers.builder()
                    .add(Attributes.SAFE_FALL_DISTANCE, new AttributeModifier(ResourceLocation.withDefaultNamespace("safe_fall_distance"), 100, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.FEET)
                    .build()
            )
    );

    private static Item register(String path, Function<Item.Properties, Item> itemFactory, Item.Properties properties) {
        var id = SpringBoots.id(path);
        var key = ResourceKey.create(Registries.ITEM, id);

        return Items.registerItem(key, itemFactory, properties);
    }

    private static Item register(String path, Item.Properties properties) {
        return register(path, Item::new, properties);
    }

    public static void register() {

    }
}
