package sunsetsatellite.signalindustries.data;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import sunsetsatellite.signalindustries.item.SignaliteCrystalBatteryItem;
import sunsetsatellite.signalindustries.item.attachment.BackpackAttachmentItem;
import sunsetsatellite.signalindustries.item.attachment.base.AttachmentItem;
import sunsetsatellite.signalindustries.util.AttachmentPoint;
import sunsetsatellite.signalindustries.util.Tier;

import java.util.HashMap;
import java.util.List;

import static sunsetsatellite.signalindustries.SignalIndustries.NAMESPACE;

public class SIItems {

    public static HashMap<Item, String> itemTextures = new HashMap<>();

    // Signalite
    public static Item rawSignalumCrystal;
    public static Item signalumCrystal;
    public static Item signalumCrystalEmpty;
    public static Item awakenedSignalumCrystal;
    public static Item awakenedSignalumFragment;
    public static Item volatileSignalumCrystal;

    // Other Minerals
    public static Item dilithiumShard;
    public static Item dimensionalShard;
    public static Item emberCoal;

    // Batteries
    public static Item signalumCrystalBattery;
    public static Item infiniteSignalumCrystal;

    // Plates
    public static Item cobblestonePlate;
    public static Item stonePlate;
    public static Item crystalAlloyPlate;
    public static Item steelPlate;
    public static Item reinforcedCrystalAlloyPlate;
    public static Item saturatedSignalumAlloyPlate;
    public static Item dilithiumPlate;
    public static Item voidAlloyPlate;
    public static Item awakenedAlloyPlate;
    public static Item signalumAlloyMesh;

    // Ingots
    public static Item steelIngot;
    public static Item crystalAlloyIngot;
    public static Item reinforcedCrystalAlloyIngot;
    public static Item saturatedSignalumAlloyIngot;
    public static Item voidAlloyIngot;
    public static Item awakenedAlloyIngot;

    // Dusts
    public static Item coalDust;
    public static Item ironDust;
    public static Item goldDust;
    public static Item emptySignalumCrystalDust;
    public static Item saturatedSignalumCrystalDust;
    public static Item awakenedSignalumCrystalDust;
    public static Item emberCoalDust;

    // Tiny Dusts
    public static Item tinyEmberCoalDust;

    // Gears
    public static Item diamondCuttingGear;
    public static Item signalumCuttingGear;

    // Chips (Crystals)
    public static Item crystalChip;
    public static Item pureCrystalChip;
    public static Item dilithiumChip;
    public static Item dimensionalChip;

    // Chips (Components)
    public static Item blankChip;
    public static Item positionMemoryChip;
    public static Item precisionControlChip;
    public static Item unlimitedChip;

    // Misc Materials
    public static Item realityString;
    public static Item monsterShard;
    public static Item infernalFragment;
    public static Item evilEye;
    public static Item infernalEye;
    public static Item clearKey;
    public static Item saturatedKey;

    // Food (easter egg)
    public static Item condensedMilkCan;
    public static Item bucketCaramel;
    public static Item caramelPlate;
    public static Item krowka;

    // Backpack
    public static AttachmentItem basicBackpack;
    public static AttachmentItem reinforcedBackpack;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        rawSignalumCrystal = simpleItem("raw_signalite_crystal", "rawSignalumCrystal", "raw_signalum_crystal");
        signalumCrystal = simpleItem("signalite_crystal", "signalumCrystal", "signalum_crystal");
        signalumCrystalEmpty = simpleItem("empty_signalite_crystal", "signalumCrystalEmpty", "signalum_crystal_empty");
        awakenedSignalumCrystal = simpleItem("awakened_signalite_crystal", "awakenedSignalumCrystal", "awakened_signalum_crystal");
        awakenedSignalumFragment = simpleItem("awakened_signalite_fragment", "awakenedSignalumFragment", "awakened_signalum_fragment");
        //TODO: implement
        volatileSignalumCrystal = simpleItem("volatile_signalite_crystal", "volatileSignalumCrystal", "volatile_signalum_crystal").setMaxCount(4);

        signalumCrystalBattery = customItem(new SignaliteCrystalBatteryItem(NAMESPACE.id("signalite_crystal_battery"), false).setMaxCount(1), "signalumCrystal.battery", "signalum_crystal_battery");
        infiniteSignalumCrystal = customItem(new SignaliteCrystalBatteryItem(NAMESPACE.id("infinite_signalite_crystal"), true).setMaxCount(1), "infiniteSignalumCrystal", "infinite_signalum_crystal");

        cobblestonePlate = simpleItem("cobblestone_plate", "cobblestonePlate", "cobblestone_plate");
        stonePlate = simpleItem("stone_plate", "stonePlate", "stone_plate");
        crystalAlloyPlate = simpleItem("crystal_alloy_plate", "crystalAlloyPlate", "crystal_alloy_plate");
        steelPlate = simpleItem("steel_plate", "steelPlate", "steel_plate");
        reinforcedCrystalAlloyPlate = simpleItem("reinforced_crystal_alloy_plate", "reinforcedCrystalAlloyPlate", "reinforced_crystal_alloy_plate");
        saturatedSignalumAlloyPlate = simpleItem("saturated_signalum_alloy_plate", "saturatedSignalumAlloyPlate", "saturated_signalum_alloy_plate");
        dilithiumPlate = simpleItem("dilithium_plate", "dilithiumPlate", "dilithium_plate");
        voidAlloyPlate = simpleItem("void_alloy_plate", "voidAlloyPlate", "void_alloy_plate");
        awakenedAlloyPlate = simpleItem("awakened_alloy_plate", "awakenedAlloyPlate", "awakened_alloy_plate");
        signalumAlloyMesh = simpleItem("signalum_alloy_mesh", "signalumAlloyMesh", "signalum_alloy_mesh");

        steelIngot = simpleItem("steel_ingot", "steelIngot", "steel_ingot");
        crystalAlloyIngot = simpleItem("crystal_alloy_ingot", "crystalAlloyIngot", "crystal_alloy");
        reinforcedCrystalAlloyIngot = simpleItem("reinforced_crystal_alloy_ingot", "reinforcedCrystalAlloyIngot", "reinforced_crystal_alloy");
        saturatedSignalumAlloyIngot = simpleItem("saturated_signalum_alloy_ingot", "saturatedSignalumAlloyIngot", "saturated_signalum_alloy");
        voidAlloyIngot = simpleItem("void_alloy_ingot", "voidAlloyIngot", "void_alloy");
        awakenedAlloyIngot = simpleItem("awakened_alloy_ingot", "awakenedAlloyIngot", "awakened_alloy");

        diamondCuttingGear = simpleItem("diamond_cutting_gear", "diamondCuttingGear", "diamond_cutting_gear");
        signalumCuttingGear = simpleItem("signalum_cutting_gear", "signalumCuttingGear", "signalum_cutting_gear");

        dimensionalShard = simpleItem("dimensional_shard", "dimensionalShard", "dimensional_shard");
        dilithiumShard = simpleItem("dilithium_shard", "dilithiumShard", "dilithium_shard");

        crystalChip = simpleItem("crystal_chip", "crystalChip", "crystal_chip");
        pureCrystalChip = simpleItem("pure_crystal_chip", "pureCrystalChip", "pure_crystal_chip");
        dilithiumChip = simpleItem("dilithium_chip", "dilithiumChip", "dilithium_chip");
        dimensionalChip = simpleItem("dimensional_chip", "dimensionalChip", "dimensional_chip");

        realityString = simpleItem("reality_string", "realityString", "string_of_reality");
        monsterShard = simpleItem("monster_shard", "monsterShard", "monster_shard");
        infernalFragment = simpleItem("infernal_fragment", "infernalFragment", "infernal_fragment");
        evilEye = simpleItem("evil_eye", "evilEye", "evil_eye");
        infernalEye = simpleItem("infernal_eye", "infernalEye", "infernal_eye");
        clearKey = simpleItem("clear_key", "clearKey", "clear_key");
        saturatedKey = simpleItem("saturated_key", "saturatedKey", "saturated_key");

        coalDust = simpleItem("coal_dust", "coalDust", "coal_dust");
        ironDust = simpleItem("iron_dust", "ironDust", "iron_dust");
        goldDust = simpleItem("gold_dust", "goldDust", "gold_dust");
        emberCoal = simpleItem("ember_coal", "emberCoal", "nether_coal");
        emberCoalDust = simpleItem("ember_coal_dust", "emberCoalDust", "nether_coal_dust");
        tinyEmberCoalDust = simpleItem("tiny_ember_coal_dust", "tinyEmberCoalDust", "tiny_nether_coal_dust");
        emptySignalumCrystalDust = simpleItem("empty_signalite_crystal_dust", "signalumCrystalDust", "empty_signalum_dust");
        saturatedSignalumCrystalDust = simpleItem("saturated_signalite_crystal_dust", "saturatedSignalumCrystalDust", "saturated_signalum_dust");
        awakenedSignalumCrystalDust = simpleItem("awakened_signalite_crystal_dust", "awakenedSignalumCrystalDust", "awakened_signalum_dust");

        condensedMilkCan = simpleItem("condensed_milk_can", "condensedMilkCan", "condensed_milk_can");
        bucketCaramel = simpleItem("bucket_caramel", "bucketCaramel", "bucket_caramel");
        caramelPlate = simpleItem("caramel_plate", "caramelPlate", "caramel_plate");
        krowka = simpleItem("krowka", "krowka", "krowka");

        blankChip = simpleItem("blank_chip", "romChip.blank", "blank_chip");
        positionMemoryChip = simpleItem("position_memory_chip", "romChip.position", "position_chip");
        precisionControlChip = simpleItem("precision_control_chip", "romChip.precision", "precision_control_chip");
        unlimitedChip = simpleItem("unlimited_chip", "romChip.unlimited", "unlimited_chip");

        basicBackpack = (AttachmentItem) customItem(new BackpackAttachmentItem("basic_backpack", List.of(AttachmentPoint.CORE_BACK), Tier.BASIC).setMaxCount(1),"basic.attachment.backpack", "basic_backpack");
        reinforcedBackpack = (AttachmentItem) customItem(new BackpackAttachmentItem("reinforced_backpack", List.of(AttachmentPoint.CORE_BACK), Tier.REINFORCED).setMaxCount(1),"reinforced.attachment.backpack", "reinforced_backpack");
    }

    public Item simpleItem(String name, String lang, String texture) {
        Item item = new TemplateItem(NAMESPACE.id(name)).setTranslationKey(NAMESPACE, lang);
        itemTextures.put(item, "item/"+texture);
        return item;
    }

    public Item customItem(Item item, String lang, String texture) {
        item.setTranslationKey(NAMESPACE, lang);
        itemTextures.put(item, "item/"+texture);
        return item;
    }

}