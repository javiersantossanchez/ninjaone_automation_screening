package org.velozient.ports.out;

import org.velozient.model.Movie;
import reactor.core.publisher.Flux;

/**
 * Interface to define data access functionalities from movie datasource
 *
 * @author Javier Santos
 */
public interface MovieRepository {

    /**
     * Find all movies created between a specific period of time
     * @param firstYear The first year of period of time used to search the movies
     * @param lastYear The last year of period of time used to search the movies
     * @return A movie list matching the searching criteria
     */
    Flux<Movie> searchMovieByProductionsYear(int firstYear, int lastYear);
}
