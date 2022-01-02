package akatsuki.immunizationsystem.model.vaccine;

public enum VaccineType {
    PFIZER("Pfizer-BioNTech"),
    SPUTNIK_V("Sputnik V"),
    SINOPHARM("Sinopharm"),
    ASTRA_ZENECA("AstraZeneca"),
    MODERNA("Moderna");

    public final String label;

    VaccineType(String label) {
        this.label = label;
    }
}
