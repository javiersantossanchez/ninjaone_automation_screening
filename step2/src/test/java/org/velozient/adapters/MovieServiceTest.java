package org.velozient.adapters;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.velozient.factory.MovieMother;
import org.velozient.filter.Decade;
import org.velozient.model.Movie;
import org.velozient.ports.in.MovieManagement;
import org.velozient.worker.MovieExporter;
import org.velozient.worker.MovieFetcher;
import reactor.core.publisher.Flux;

public class MovieServiceTest {

    private final MovieFetcher movieFetcher = Mockito.mock(MovieFetcher.class);

    private final MovieExporter movieExporter = Mockito.mock(MovieExporter.class);

    private final MovieManagement target = new MovieServiceManager(movieFetcher,movieExporter);

    @Test
    public void search_movie_by_decade_when_OK(){
        int firstYear80sDecade = 1940;
        int lastYear80sDecade = 1949;
        Flux<Movie> movieListExpected = MovieMother.multipleDummyMovieFor80sDecade(15);

        Mockito.doReturn(movieListExpected)
                .when(movieFetcher).searchMovieByProductionsYear(firstYear80sDecade,lastYear80sDecade);
       target.exportMoviesByDecade(20,Decade.forties);
    }

}
