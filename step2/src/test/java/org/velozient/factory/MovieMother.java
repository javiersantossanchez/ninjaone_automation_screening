package org.velozient.factory;

import com.github.javafaker.Faker;
import org.velozient.model.Movie;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class MovieMother {

    private final static Faker dataGenerator = new Faker();


    public static Flux<Movie> multipleDummyMovieFor90sDecade(int listSize){
        return multipleDummyMovieByDecade(1990,1999,listSize);
    }

    public static Flux<Movie> multipleDummyMovieFor80sDecade(int listSize){
        return multipleDummyMovieByDecade(1980,1989,listSize);
    }

    public static Flux<Movie> multipleDummyMovieFor20sDecade(int listSize){
        return multipleDummyMovieByDecade(1920,1929,listSize);
    }

    private static Flux<Movie> multipleDummyMovieByDecade(int startYear, int endYear,int listSize){
        List<Movie> dummyMovieList = new ArrayList<>();
        for(int index =0;index < listSize;index++){
            dummyMovieList.add(dummyMovieByDecade(startYear,endYear));
        }
        return Flux.fromIterable(dummyMovieList);
    }

    private static Movie dummyMovieByDecade(int startYear, int endYear){
        return Movie.builder()
                .title(dataGenerator.book().title())
                .year(dataGenerator.number().numberBetween(startYear,endYear))
                .build();
    }
}
