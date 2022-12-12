package org.velozient.worker;

import org.velozient.model.Movie;
import reactor.core.publisher.Flux;

/**
 * Command interface to export movie list into a data storage resource
 *
 * @author Javier Santos
 */
public interface MovieExportCommand {

    /**
     * Execute command to store the movie list a data storage resource.
     * @param movieList A movie list to store
     */
    void execute(Flux<Movie> movieList);
}
