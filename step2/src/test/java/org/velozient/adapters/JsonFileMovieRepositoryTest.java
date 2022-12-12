package org.velozient.adapters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.velozient.factory.MovieMother;
import org.velozient.infra.JsonInputFileLocator;
import org.velozient.model.Movie;
import org.velozient.ports.out.MovieRepository;
import reactor.core.publisher.Flux;


public class JsonFileMovieRepositoryTest {

    private final MovieJsonFileDao movieDao = Mockito.mock(MovieJsonFileDao.class);

    private final MovieRepository target = new JsonFileMovieRepository(movieDao);

    @Test
    public void search_movie_by_production_year_decade_80s_when_OK(){
        int firstYear80sDecade = 1980;
        int lastYear80sDecade = 1989;
        Flux<Movie> decade80sMovieListExpected =MovieMother.multipleDummyMovieFor80sDecade(10);
        Flux<Movie> decade20sMovieListExpected =MovieMother.multipleDummyMovieFor20sDecade(5);
        Flux<Movie> decade90sMovieListExpected =MovieMother.multipleDummyMovieFor90sDecade(7);
        Flux<Movie> fullListMovies = decade80sMovieListExpected.concatWith(decade20sMovieListExpected).concatWith(decade90sMovieListExpected);
        Mockito.doReturn(fullListMovies).when(movieDao).findAllMovies(Mockito.any(JsonInputFileLocator.class));
        Flux<Movie> movieListResult = target.searchMovieByProductionsYear(firstYear80sDecade,lastYear80sDecade);
        Assertions.assertIterableEquals(decade80sMovieListExpected.collectList().block(),movieListResult.collectList().block());
    }

    @Test
    public void search_movie_by_production_year_decade_80s_when_there_are_no_result(){
        int firstYear80sDecade = 1980;
        int lastYear80sDecade = 1989;
        Flux<Movie> decade20sMovieListExpected =MovieMother.multipleDummyMovieFor20sDecade(8);
        Flux<Movie> decade90sMovieListExpected =MovieMother.multipleDummyMovieFor90sDecade(7);
        Flux<Movie> fullListMovies = decade20sMovieListExpected.concatWith(decade90sMovieListExpected);
        Mockito.doReturn(fullListMovies).when(movieDao).findAllMovies(Mockito.any(JsonInputFileLocator.class));

        Flux<Movie> movieListResult = target.searchMovieByProductionsYear(firstYear80sDecade,lastYear80sDecade);
        Assertions.assertTrue(movieListResult.collectList().block().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
        "1986, 1985",
        "1899, 1909",
        "1920, 1899",
        "2015, 3001",
        "3001, 3001",
    })
    public void search_movie_by_production_year_decade_80s_with_firstYear_higher_than_lastYear(int firstYearOfDecade,int lastYearOfDecade){
        Assertions.assertThrows(IllegalArgumentException.class, ()-> target.searchMovieByProductionsYear(firstYearOfDecade,lastYearOfDecade));
    }



}
