package org.velozient.factory;

import lombok.RequiredArgsConstructor;
import org.velozient.adapters.MovieServiceManager;
import org.velozient.ports.in.MovieManagement;

@RequiredArgsConstructor
public class MovieManagerFactory {

    private final MovieExporterFactory movieExporterFactory;
    public MovieManagement build(){
        return new MovieServiceManager(MovieFetcherFactory.build(),movieExporterFactory.build());
    }
}
