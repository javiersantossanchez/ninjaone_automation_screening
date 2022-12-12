package org.velozient.factory;

import org.velozient.adapters.JsonFileMovieRepository;
import org.velozient.adapters.MovieJsonFileDao;
import org.velozient.ports.out.MovieRepository;

public class MovieRepositoryFactory {
    public static MovieRepository build(){
        return new JsonFileMovieRepository(MovieJsonFileDaoFactory.build());
    }

    private static class MovieJsonFileDaoFactory {
        public static MovieJsonFileDao build (){
            return new MovieJsonFileDao();
        }
    }
}
