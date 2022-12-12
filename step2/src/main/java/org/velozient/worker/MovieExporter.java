package org.velozient.worker;

import lombok.RequiredArgsConstructor;
import org.velozient.model.Movie;
import reactor.core.publisher.Flux;

/**
 * Class to export result to target storage
 *
 * @author  Javier Santos
 */
@RequiredArgsConstructor
public class MovieExporter {

        private final MovieExportCommand exportCommand;

    /**
     * Save movie list into the target storage
     * @param movieList A movie list to store
     */
    public void export (Flux<Movie> movieList){
        exportCommand.execute(movieList);
    }
}
