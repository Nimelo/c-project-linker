package files;

import java.util.Set;

public class SourceFile {
    private final String path;
    private final Set<String> standardIncludes;
    private final String body;
    private final SourceFileType sourceFileType;

    public SourceFile(String path, Set<String> standardIncludes, String body, SourceFileType sourceFileType) {
        this.standardIncludes = standardIncludes;
        this.body = body;
        this.sourceFileType = sourceFileType;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public Set<String> getStandardIncludes() {
        return standardIncludes;
    }

    public String getBody() {
        return body;
    }

    public SourceFileType getSourceFileType() {
        return sourceFileType;
    }
}
