package org.velozient.factory;

import lombok.RequiredArgsConstructor;
import org.velozient.worker.MovieExporter;

@RequiredArgsConstructor
public class MovieExporterFactory {

    private final MovieExporterCommandFactory movieExporterCommandFactory;

    public MovieExporter build(){
        return new MovieExporter(movieExporterCommandFactory.build());
    }

}
