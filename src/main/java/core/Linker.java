package core;

import files.SourceFile;
import files.SourceFileType;

import java.util.*;
import java.util.stream.Collectors;

public class Linker {
    public SourceFile link(List<SourceFile> sources){
        Map<SourceFileType, List<SourceFile>> groupedSources = sources.stream()
                .collect(Collectors.groupingBy(SourceFile::getSourceFileType));

        Set<String> includes = sources.stream().sorted(Comparator.comparingInt(x -> x.getSourceFileType().getValue()))
                .flatMap(sourceFile -> sourceFile.getStandardIncludes().stream())
                .distinct()
                .collect(Collectors.toSet());

        StringBuilder stringBuilder = new StringBuilder();

        for (SourceFileType sourceFileType : Arrays.stream(SourceFileType.values())
                .sorted(Comparator.comparingInt(SourceFileType::getValue))
                .collect(Collectors.toList())) {

            String body = groupedSources.getOrDefault(sourceFileType, new ArrayList<>())
                    .stream().map(SourceFile::getBody)
                    .collect(Collectors.joining(""));

            stringBuilder.append(body);
        }

        return new SourceFile("", includes, stringBuilder.toString(), SourceFileType.MAIN);
    }
}
