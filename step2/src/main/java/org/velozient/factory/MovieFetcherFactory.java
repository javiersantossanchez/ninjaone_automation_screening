package org.velozient.factory;

import org.velozient.worker.MovieFetcher;

public class MovieFetcherFactory {
    public static MovieFetcher build(){
        return new MovieFetcher(MovieRepositoryFactory.build());
    }
}
