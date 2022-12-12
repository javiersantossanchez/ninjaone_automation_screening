package org.velozient.worker;

import lombok.RequiredArgsConstructor;
import org.velozient.adapters.MovieJsonFileDao;
import org.velozient.infra.JsonOutPutFileLocator;
import org.velozient.model.Movie;
import reactor.core.publisher.Flux;

/**
 *  Command to store movie list into json file
 *
 * @author Javier Santos
 */
@RequiredArgsConstructor
public class ExportToJsonFileCommand implements MovieExportCommand {

    private final MovieJsonFileDao dao;

    private final JsonOutPutFileLocator pathLocator;



    /**
     * {@inheritDoc}  <br>
     *
     * The command implementation send the movie list into json file
     */
    @Override
    public void execute(Flux<Movie> movieList) {
        dao.save(movieList,pathLocator);
    }
}
