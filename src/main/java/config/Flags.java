package config;

public enum Flags {
    OUTPUT_FILE_NAME("o"),
    HEADER_EXTENSIONS("h"),
    SOURCE_EXTENSIONS("s"),
    DIRECTORY("d"),
    EXCLUDED_DIRECTORIES("e");

    private final String value;

    Flags(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
