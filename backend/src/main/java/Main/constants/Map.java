package Main.constants;

import java.util.List;
public enum Map {
    ANCIENT,
    ANUBIS,
    INFERNO,
    MIRAGE,
    NUKE,
    OVERPASS,
    DUST2,
    VERTIGO;
    private static final List<Map> allValues = List.of(values());

    public static List<Map> allValues() {
        return allValues;
    }
}

