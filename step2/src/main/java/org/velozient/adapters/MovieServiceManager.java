package org.velozient.adapters;

import lombok.RequiredArgsConstructor;
import org.velozient.filter.Decade;
import org.velozient.model.Movie;
import org.velozient.ports.in.MovieManagement;
import org.velozient.worker.MovieExporter;
import org.velozient.worker.MovieFetcher;
import reactor.core.publisher.Flux;

/**
 * Service implementation of user cases for movie management application
 *
 * @author  Javier Santos
 */
@RequiredArgsConstructor
public class MovieServiceManager implements MovieManagement {

    private final MovieFetcher movieFetcher;

    private final MovieExporter movieExporter;

    /**
     * {@inheritDoc}
     */
     @Override
    public void exportMoviesByDecade(int century, Decade decade){
         Flux<Movie> movieList = movieFetcher.searchMovieByProductionsYear(calculateFirstYear(century,decade),calculateLastYear(century,decade));
         movieExporter.export(movieList);
    }

    private int calculateFirstYear(int century, Decade decade){
        return ((century*100) -100) + decade.getValue();
    }

    private int calculateLastYear(int century, Decade decade){
        return this.calculateFirstYear(century,decade) + 9;
    }
}
