import config.Environment;
import config.EnvironmentException;
import core.Linker;
import files.SourceFile;
import io.SourcesReader;
import io.SourcesWriter;
import org.apache.commons.cli.ParseException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Environment environment = new Environment(args);
        try {
            environment.parse();
            System.out.println(environment.toString());

            if (Files.notExists(Paths.get(environment.getSourcesDirectory()))) {
                throw new EnvironmentException("Directory not exists.");
            }

            SourcesReader sourcesReader = new SourcesReader(environment.getHeaderExtensions(), environment.getSourceExtensions());
            List<SourceFile> sourceFiles = sourcesReader.readFrom(environment.getSourcesDirectory(), environment.getExcludedDirectories());

            Linker linker = new Linker();
            SourceFile link = linker.link(sourceFiles);

            SourcesWriter sourcesWriter = new SourcesWriter(link);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(environment.getOutputFileName()));
            sourcesWriter.write(bufferedWriter);
            bufferedWriter.flush();
            bufferedWriter.close();

            System.out.println("Successfully saved linked file.");

        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (EnvironmentException ee) {
            System.out.println(ee.getMessage());
        } catch (ParseException pe) {
            environment.printHelp();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
