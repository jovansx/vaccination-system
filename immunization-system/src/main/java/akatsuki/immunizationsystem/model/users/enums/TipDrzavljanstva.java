package akatsuki.immunizationsystem.model.users.enums;

public enum TipDrzavljanstva {
    SRPSKO("srpsko"),
    STRANO_SA_BORAVKOM("strano sa boravkom"),
    STRANO_BEZ_BORAVKA("strano bez boravka");

    public final String label;
    TipDrzavljanstva(String label) {
        this.label = label;
    }
}
