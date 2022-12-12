package org.velozient.worker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.velozient.factory.MovieMother;
import org.velozient.model.Movie;
import org.velozient.ports.out.MovieRepository;
import reactor.core.publisher.Flux;

public class MovieFetcherTest {

    private final MovieRepository movieRepository = Mockito.mock(MovieRepository.class);

    private final MovieFetcher target = new MovieFetcher(movieRepository);

    @Test
    public void search_movie_By_decade_test_with_90s_as_filter_when_ok(){
        int firstYear = 1990;
        int lastYear = 1999;
        Flux<Movie> movieFrom90sList = MovieMother.multipleDummyMovieFor90sDecade(10);
        Mockito.doReturn(movieFrom90sList).when(movieRepository).searchMovieByProductionsYear(firstYear,lastYear);
        Flux<Movie> movieListResult = target.searchMovieByProductionsYear(firstYear,lastYear);
        Assertions.assertEquals(movieListResult.count().block(),movieFrom90sList.count().block());
    }




}
