package config;

import org.apache.commons.cli.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static config.Flags.*;
import static org.apache.commons.cli.Option.UNLIMITED_VALUES;

public class Environment {
    private static final String DEFAULT_OUTPUT_FILE_NAME = "merged-main.cc";
    private static final Set<String> DEFAULT_EXCLUDED_DIRS = new HashSet<>();
    private static final Set<String> HEADERS_EXTENSIONS = new HashSet<>();
    private static final Set<String> SOURCES_EXTENSIONS = new HashSet<>();

    static {
        DEFAULT_EXCLUDED_DIRS.addAll(Arrays.asList("/test", "/build"));
        Collections.addAll(HEADERS_EXTENSIONS, "h", "hpp");
        Collections.addAll(SOURCES_EXTENSIONS, "c", "cpp", "cc");
    }

    private final String[] arguments;
    private String sourcesDirectory;
    private String outputFileName;
    private Set<String> excludedDirectories;
    private Set<String> headerExtensions;
    private Set<String> sourceExtensions;
    private final Options options;

    public Environment(String[] arguments) {
        this.arguments = arguments;
        this.options = getOptions();
    }

    public void parse() throws ParseException {
        BasicParser basicParser = new BasicParser();
        CommandLine commandLine = basicParser.parse(options, arguments);
        extractExistingParameters(commandLine);
    }

    private void extractExistingParameters(CommandLine commandLine) {
        sourcesDirectory = commandLine.getOptionValue(DIRECTORY.getValue());

        if (commandLine.hasOption((OUTPUT_FILE_NAME.getValue()))) {
            outputFileName = commandLine.getOptionValue(OUTPUT_FILE_NAME.getValue());
        } else {
            outputFileName = DEFAULT_OUTPUT_FILE_NAME;
        }

        if (commandLine.hasOption(EXCLUDED_DIRECTORIES.getValue())) {
            excludedDirectories = new HashSet<>(Arrays.asList(commandLine.getOptionValues(EXCLUDED_DIRECTORIES.getValue())));
        } else {
            excludedDirectories = DEFAULT_EXCLUDED_DIRS;
        }

        if (commandLine.hasOption(HEADER_EXTENSIONS.getValue())) {
            headerExtensions = new HashSet<>(Arrays.asList(commandLine.getOptionValues(HEADER_EXTENSIONS.getValue())));
        } else {
            headerExtensions = HEADERS_EXTENSIONS;
        }

        if (commandLine.hasOption(SOURCE_EXTENSIONS.getValue())) {
            sourceExtensions = new HashSet<>(Arrays.asList(commandLine.getOptionValues(SOURCE_EXTENSIONS.getValue())));
        } else {
            sourceExtensions = SOURCES_EXTENSIONS;
        }
    }

    public void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("c-project-linker", options);
    }

    private static Options getOptions() {
        Options options = new Options();

        Option directory = new Option(DIRECTORY.getValue(), true, "Directory of files");
        directory.setRequired(true);
        options.addOption(directory);

        Option outputFileName = new Option(OUTPUT_FILE_NAME.getValue(), true, "Output file name");
        outputFileName.setRequired(false);
        options.addOption(outputFileName);

        Option headerExtensions = new Option(HEADER_EXTENSIONS.getValue(), true, "Header extensions");
        headerExtensions.setRequired(false);
        headerExtensions.setArgs(UNLIMITED_VALUES);
        options.addOption(headerExtensions);

        Option sourceExtensions = new Option(SOURCE_EXTENSIONS.getValue(), true, "Source extensions");
        sourceExtensions.setArgs(UNLIMITED_VALUES);
        sourceExtensions.setRequired(false);
        options.addOption(sourceExtensions);

        Option excludedDirectories = new Option(EXCLUDED_DIRECTORIES.getValue(), true, "Excluded directories");
        excludedDirectories.setArgs(UNLIMITED_VALUES);
        excludedDirectories.setRequired(false);
        options.addOption(excludedDirectories);

        return options;
    }

    @Override
    public java.lang.String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(java.lang.String.format("Sources directory=%s\n", getSourcesDirectory()));
        stringBuilder.append(java.lang.String.format("Output file name=%s\n", getOutputFileName()));
        stringBuilder.append(java.lang.String.format("Excluded directories=%s\n", getExcludedDirectories()));
        stringBuilder.append(java.lang.String.format("Header extensions=%s\n", getHeaderExtensions()));
        stringBuilder.append(java.lang.String.format("Sources extensions=%s\n", getSourceExtensions()));
        return stringBuilder.toString();
    }

    public String getSourcesDirectory() {
        return sourcesDirectory;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public Set<String> getExcludedDirectories() {
        return excludedDirectories;
    }

    public Set<String> getHeaderExtensions() {
        return headerExtensions;
    }

    public Set<String> getSourceExtensions() {
        return sourceExtensions;
    }
}
