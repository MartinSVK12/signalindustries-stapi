package sunsetsatellite.signalindustries.util;

import net.glasslauncher.mods.alwaysmoreitems.api.Rarity;

import java.awt.*;

public enum TierRarity {
    PROTOTYPE("Prototype", '0', 100, Tier.PROTOTYPE.getColor()),
    BASIC("Basic", '1', 101, Tier.BASIC.getColor()),
    REINFORCED("Reinforced", '2', 102, Tier.REINFORCED.getColor()),
    AWAKENED("Awakened", '3', 103, Tier.AWAKENED.getColor()),
    INFINITE("Infinite", '4', 104, Tier.INFINITE.getColor());

    private final Rarity rarity;

    TierRarity(String name, char code, int value, int color) {
        this.rarity = new Rarity(name, code, value, new Color(color, false), Rarity.HeaderCode.NONE);
    }

    public Rarity getRarity() {
        return rarity;
    }
}
