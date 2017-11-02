package core;

import files.SourceFile;
import files.SourceFileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LinkerTest {
    @Test
    void link() {
        // Arrange
        Linker linker = new Linker();
        List<SourceFile> sources = new ArrayList<SourceFile>(){{
            add(new SourceFile("", new HashSet<String>(){{
                add("s1");
                add("s2");
            }}, "body_s1", SourceFileType.SOURCE));
            add(new SourceFile("", new HashSet<String>(){{
                add("h1");
                add("h2");
            }}, "body_h1", SourceFileType.HEADER));
            add(new SourceFile("", new HashSet<String>(){{
                add("m1");
                add("m2");
            }}, "body_m1", SourceFileType.MAIN));
        }};

        // Act
        SourceFile merged = linker.link(sources);

        // Assert
        Set<String> expectedIncludes = new HashSet<String>(){{
            add("h1");
            add("h2");
            add("s1");
            add("s2");
            add("m1");
            add("m2");
        }};

        String expectedBody = "body_h1" + "body_s1" + "body_m1";

        assertArrayEquals(expectedIncludes.toArray(), merged.getStandardIncludes().toArray());
        assertEquals(expectedBody, merged.getBody());
    }

}