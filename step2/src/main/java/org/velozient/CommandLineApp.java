package org.velozient;

import org.velozient.factory.MovieExporterCommandFactory;
import org.velozient.factory.MovieExporterFactory;
import org.velozient.factory.MovieManagerFactory;
import org.velozient.filter.Decade;
import org.velozient.ports.in.MovieManagement;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Spec;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * Command line app used to search and export movie information
 *
 * @author  Javier Santos
 */
@Command(
        name = "movie-filter",
        description = "Filters movies based on century and decade parameters",
        mixinStandardHelpOptions = true, version = "filter-movies 1.0"
)
public class CommandLineApp implements Callable<Integer> {

    @Option(names = {"-d", "--decade"}, description = "Decade use to search movies", required = true)
    private Decade decade;


    @Option(names = {"-c", "--century"}, description = "defines the century to which the decade belongs",required = true)
    private int century;

    @Option(names = {"-o", "--output"}, description = "defines File name and path where the export will be generated")
    private File outPutFile;

    private final String DEFAULT_FILE_NAME = "movies_output.json";

    private final String DEFAULT_OUTPUT_DIR = System.getProperty("user.dir");

    @Spec
    CommandLine.Model.CommandSpec spec;

    @Override
    public Integer call()  {
        if(century < 1) {
            throw new CommandLine.ParameterException(spec.commandLine(), "Invalid value for option '--century': expected a positive int");
        }
        if(outPutFile == null){
            outPutFile = new File(DEFAULT_OUTPUT_DIR,DEFAULT_FILE_NAME);
        }
        MovieManagerFactory movieManagerFactory = new MovieManagerFactory(new MovieExporterFactory(new MovieExporterCommandFactory(outPutFile)));
        MovieManagement movieManagement = movieManagerFactory.build();
        try {
            movieManagement.exportMoviesByDecade(century, decade);
        }catch (Exception ex){
            ex.printStackTrace();
            return this.spec.exitCodeOnExecutionException();
        }
        System.out.println(String.format("The movies list are exported to %s",outPutFile.getAbsolutePath()));
        return this.spec.exitCodeOnSuccess();
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new CommandLineApp()).execute(args);
        System.exit(exitCode);
    }
}
