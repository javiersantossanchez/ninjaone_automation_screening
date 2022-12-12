package org.velozient.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.RequiredArgsConstructor;
import org.velozient.error.MovieAccessException;
import org.velozient.error.MovieExportException;
import org.velozient.infra.JsonInputFileLocator;
import org.velozient.infra.JsonOutPutFileLocator;
import org.velozient.model.Movie;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.io.*;
import java.nio.file.Files;

/**
 * Read a json file and parse the information into a list of movie objects
 *
 * @author Javier Santos
 */
@RequiredArgsConstructor
public class MovieJsonFileDao {

    /**
     * Find all movies from a json file <br>
     * The json file is defined on  {@code JsonInputFileLocator}
     * @return movie list exists on json file
     */
    public Flux<Movie> findAllMovies(JsonInputFileLocator pathLocator){
        return  Flux.create(sink -> this.readFile(sink,pathLocator));
    }

    private void readFile(FluxSink<Movie> sink,JsonInputFileLocator pathLocator)  {
        try (
            InputStream inputStream = new FileInputStream(pathLocator.moviesInputFilePath().getAbsolutePath());
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream))
        ) {
            reader.beginArray();
            while (reader.hasNext()) {
                sink.next(new Gson().fromJson(reader, Movie.class));
            }
            sink.complete();
            reader.endArray();
        }catch(IOException  | JsonParseException ex){
            sink.error(new MovieAccessException(ex));
        }
    }

    /**
     * Saves a movie list into json file, The json path is defined by {@Code JsonOutPutFileLocator}
     *
     * @param listMovies Movie list to storage into json file
     * @param pathLocator Define path where the json file will be stored
     */
    public  void save(Flux<Movie> listMovies, JsonOutPutFileLocator pathLocator){
        this.writeJsonFile(listMovies,pathLocator);
    }
    private void writeJsonFile(Flux<Movie> listMovies,JsonOutPutFileLocator pathLocator) {
        Gson jsonConverter = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        try (OutputStream outputStream = new FileOutputStream(pathLocator.moviesOutputFilePath());
             JsonWriter writer = jsonConverter.newJsonWriter(new OutputStreamWriter(outputStream))) {
            writer.beginArray();
            if(listMovies != null){
                listMovies
                .doOnError((current)-> {
                    try {
                        Files.deleteIfExists(pathLocator.moviesOutputFilePath().toPath());
                    } catch (IOException e) {
                        //The system has no logger functionality. We should put a logger entry as warning to indicate a problem was found deleting the file.
                    }
                })
                .subscribe(current -> jsonConverter.toJson(current, Movie.class, writer));
            }
            writer.endArray();
        } catch (Exception e) {
            try {
                Files.deleteIfExists(pathLocator.moviesOutputFilePath().toPath());
            } catch (IOException ex) {
                //The system has no logger functionality. We should put a logger entry as warning to indicate a problem was found deleting the file.
            }
            throw new MovieExportException(e);
        }
    }
}
