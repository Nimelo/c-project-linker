package io;

import files.SourceFile;
import files.SourceFileType;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SourcesReader {
    private static final String STANDARD_INCLUDE_REGEX = "#include <[.a-zA-Z]+>";
    private static final String IGNORE_INCLUDE_REGEX = "#.+";
    private static final String MAIN_FUNCTION_SIGNATURE = "((int)|(void)) (main\\().*\\)";
    private final Set<String> sourcesExtensions;
    private final Set<String> headerExtensions;

    public SourcesReader(Set<String> headerExtensions, Set<String> sourcesExtensions) {
        this.headerExtensions = headerExtensions;
        this.sourcesExtensions = sourcesExtensions;
    }

    public SourceFile readFrom(BufferedReader reader, String identifier, SourceFileType type) throws IOException {
        StringBuilder bodyBuilder = new StringBuilder();
        Set<String> standardIncludes = new HashSet<>();
        String line;

        boolean isMain = false;
        while ((line = reader.readLine()) != null) {
            if (line.matches(STANDARD_INCLUDE_REGEX)) {
                standardIncludes.add(line);
            } else if (line.matches(IGNORE_INCLUDE_REGEX)) {
                continue;
            } else if (line.matches(MAIN_FUNCTION_SIGNATURE)) {
                isMain = true;
            } else {
                bodyBuilder.append(line).append('\n');
            }
        }

        String body = bodyBuilder.toString();
        return new SourceFile(identifier, standardIncludes, body, isMain ? SourceFileType.MAIN : type);
    }

    public List<SourceFile> readFrom(String directory, Set<String> excludedDirectories) throws IOException {
        List<SourceFile> files = new ArrayList<>();
        for (Path path : getSourcesInDirectory(directory, excludedDirectories)) {
            System.out.println(String.format("Processing file=%s", path.getFileName().toString()));
            BufferedReader bufferedReader = Files.newBufferedReader(path);
            files.add(readFrom(bufferedReader, path.getFileName().toString(), getSourceFileType(path)));
            bufferedReader.close();
        }

        return files;
    }

    private boolean isHeaderOrSourceFile(Path x) {
        String extension = FilenameUtils.getExtension(x.getFileName().toString());
        if (headerExtensions.contains(extension)
                || sourcesExtensions.contains(extension))
            return true;
        return false;
    }

    private List<Path> getSourcesInDirectory(String directory, Set<String> excludedDirectories) throws IOException {
        return Files.walk(Paths.get(directory))
                .filter(x -> excludeDirectoriesPredicate(x, excludedDirectories))
                .filter(Files::isRegularFile)
                .filter(this::isHeaderOrSourceFile)
                .collect(Collectors.toList());
    }

    private boolean excludeDirectoriesPredicate(Path path, Set<String> excludedDirectories) {
        String stringPath = path.toAbsolutePath().toString();
        for (String excludedDirectory : excludedDirectories) {
            if (stringPath.contains(excludedDirectory)) return false;
        }
        return true;
    }

    private SourceFileType getSourceFileType(Path path) {
        String extension = FilenameUtils.getExtension(path.getFileName().toString());
        SourceFileType sourceFileType = null;
        if (headerExtensions.contains(extension))
            sourceFileType = SourceFileType.HEADER;
        else if (sourcesExtensions.contains(extension))
            sourceFileType = SourceFileType.SOURCE;
        else
            throw new IllegalArgumentException(extension);
        return sourceFileType;
    }
}
