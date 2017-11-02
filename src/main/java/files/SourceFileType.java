package files;

public enum SourceFileType {
    HEADER(0),
    SOURCE(1),
    MAIN(2);

    private final int value;

    SourceFileType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
