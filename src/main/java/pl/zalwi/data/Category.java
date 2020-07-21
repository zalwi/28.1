package pl.zalwi.data;

public enum Category {
    HOME("Zadanie domowe"),
    JOB("Zadanie zawodowe"),
    TRAINING("Szkolenie"),
    OTHER("Inne");

    private String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
