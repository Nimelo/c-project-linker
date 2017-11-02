package io;

import files.SourceFile;

import java.io.BufferedWriter;
import java.io.IOException;

public class SourcesWriter {
    private final SourceFile sourceFile;

    public SourcesWriter(SourceFile sourceFile) {
        this.sourceFile = sourceFile;
    }

    public void write(BufferedWriter writer) throws IOException {
        for (String include : sourceFile.getStandardIncludes()) {
            writer.write(include);
            writer.newLine();
        }

        writer.write(sourceFile.getBody());
    }
}
