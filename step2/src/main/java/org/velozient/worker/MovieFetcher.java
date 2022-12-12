package org.velozient.worker;

import lombok.RequiredArgsConstructor;
import org.velozient.model.Movie;
import org.velozient.ports.out.MovieRepository;
import reactor.core.publisher.Flux;

/**
 * Class to search movie information from datasource
 *
 * @author  Javier Santos
 */
@RequiredArgsConstructor
public class MovieFetcher {

    private final MovieRepository  movieRepository;

    /**
     * Find all movies created between a specific period of time
     * @param firstYear The first year of period of time used to search the movies
     * @param lastYear The last year of period of time used to search the movies
     * @return A stream of movies matching the searching criteria
     */
    public Flux<Movie> searchMovieByProductionsYear(int firstYear, int lastYear){
        return movieRepository.searchMovieByProductionsYear(firstYear,lastYear);
    }
}
