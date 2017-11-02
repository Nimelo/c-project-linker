package config;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EnvironmentTest {
    @Test
    void shouldParseForValidArguments() throws EnvironmentException, org.apache.commons.cli.ParseException {
        // Arrange
        String[] validArguments = "-d directory".split(" ");
        Environment environment = new Environment(validArguments);

        // Act
        environment.parse();
    }

    @Test
    void parseShouldThrowAnException() throws EnvironmentException {
        // Arrange
        String[] validArguments = "".split(" ");
        Environment environment = new Environment(validArguments);

        // Act & Assert
        assertThrows(org.apache.commons.cli.ParseException.class, () -> environment.parse());
    }

    @Test
    void getSourcesDirectory() throws EnvironmentException, org.apache.commons.cli.ParseException {
        // Arrange
        String[] validArguments = "-d directory".split(" ");
        Environment environment = new Environment(validArguments);
        environment.parse();

        // Act
        String actualDirectory = environment.getSourcesDirectory();

        // Assert
        assertEquals("directory", actualDirectory);
    }

    @Test
    void getOutputFileName() throws EnvironmentException, org.apache.commons.cli.ParseException {
        // Arrange
        String[] validArguments = "-d directory -o file".split(" ");
        Environment environment = new Environment(validArguments);
        environment.parse();

        // Act
        String actualOutputFileName = environment.getOutputFileName();

        // Assert
        assertEquals("file", actualOutputFileName);
    }

    @Test
    void getExcludedDirectories() throws EnvironmentException, org.apache.commons.cli.ParseException {
        // Arrange
        String[] validArguments = "-d . -o file -e 1 2 3".split(" ");
        Environment environment = new Environment(validArguments);
        environment.parse();

        // Act
        Set<String> actualExcludedDirectories = environment.getExcludedDirectories();

        // Assert
        Set<String> expectedExcludedDirectories = new HashSet<String>() {
            {
                add("1");
                add("2");
                add("3");
            }
        };

        assertArrayEquals(expectedExcludedDirectories.toArray(), actualExcludedDirectories.toArray());
    }

    @Test
    void getHeaderExtensions() throws EnvironmentException, org.apache.commons.cli.ParseException {
        // Arrange
        String[] validArguments = "-d . -h a b c d".split(" ");
        Environment environment = new Environment(validArguments);
        environment.parse();

        // Act
        Set<String> actualHeaders = environment.getHeaderExtensions();

        // Assert
        Set<String> expectedHeaders = new HashSet<String>() {
            {
                add("a");
                add("b");
                add("c");
                add("d");
            }
        };

        assertArrayEquals(expectedHeaders.toArray(), actualHeaders.toArray());
    }

    @Test
    void getSourceExtensions() throws EnvironmentException, org.apache.commons.cli.ParseException {
        // Arrange
        String[] validArguments = "-d . -s a b c d".split(" ");
        Environment environment = new Environment(validArguments);
        environment.parse();

        // Act
        Set<String> actualSources = environment.getSourceExtensions();

        // Assert
        Set<String> expectedSources = new HashSet<String>() {
            {
                add("a");
                add("b");
                add("c");
                add("d");
            }
        };

        assertArrayEquals(expectedSources.toArray(), actualSources.toArray());
    }

}