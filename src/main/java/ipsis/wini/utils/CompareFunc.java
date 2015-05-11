package ipsis.wini.utils;

import cofh.lib.util.helpers.MathHelper;

public enum CompareFunc {
    LT,
    LTE,
    EQ,
    GTE,
    GT;

    public static final CompareFunc[] VALID_TYPES = {LT, LTE, EQ, GTE, GT};

    public CompareFunc getNext() {
        int ord = (ordinal() + 1) % VALID_TYPES.length;
        return VALID_TYPES[ord];
    }

    public static CompareFunc getType(int id) {
        id = MathHelper.clampI(id, 0, VALID_TYPES.length);
        return VALID_TYPES[id];
    }

    public boolean check(int limit, int currValue) {

        boolean triggered = false;
        switch (this) {
            case LT:
                triggered = (currValue < limit) ? true: false;
                break;
            case LTE:
                triggered = (currValue <= limit) ? true: false;
                break;
            case EQ:
                triggered = (currValue == limit) ? true: false;
                break;
            case GTE:
                triggered = (currValue >= limit) ? true: false;
                break;
            case GT:
                triggered = (currValue > limit) ? true: false;
                break;
            default:
                break;
        }

        return triggered;
    }
}

