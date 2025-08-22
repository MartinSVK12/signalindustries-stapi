package sunsetsatellite.signalindustries.util;

import net.modificationstation.stationapi.api.util.Formatting;

public enum Tier {
    PROTOTYPE(Formatting.GRAY,0xFF808080 , "0 (Prototype)"),
    BASIC(Formatting.WHITE, 0xFFFFFFFF, "I (Basic)"),
    REINFORCED(Formatting.RED, 0xFFFF2020, "II (Reinforced)"),
    AWAKENED(Formatting.GOLD, 0xFFFF8C00, "III (Awakened)"),
    INFINITE(Formatting.LIGHT_PURPLE, 0xFFFF00FF, "INF (Infinite)");

    private final Formatting textColor;
    private final int color;
    private final String rank;
    Tier(Formatting textColor, int color, String rank){
        this.textColor = textColor;
        this.color = color;
        this.rank = rank;
    }

    public int getColor() {
        return color;
    }

    public Formatting getTextColor(){
        return this.textColor;
    }

    public String getRank() {
        return rank;
    }
}
