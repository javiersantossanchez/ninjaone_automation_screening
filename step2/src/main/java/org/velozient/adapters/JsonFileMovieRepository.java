package org.velozient.adapters;

import lombok.RequiredArgsConstructor;
import org.velozient.infra.JsonInputFileLocator;
import org.velozient.model.Movie;
import org.velozient.ports.out.MovieRepository;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class JsonFileMovieRepository implements MovieRepository {

    private final MovieJsonFileDao movieDao;

    /**
     *  {@inheritDoc}  <br>
     *
     *  The repository read the data from a json file.
     */
    public Flux<Movie> searchMovieByProductionsYear(int firstYear, int lastYear){
        if(firstYear < 1900){
            throw new IllegalArgumentException("The [firstYear] param cam not be lower than 1900");
        }
        if(lastYear < 1900){
            throw new IllegalArgumentException("The [lastYear] param cam not be lower than 1900");
        }
        if(firstYear > 3000){
            throw new IllegalArgumentException("The [firstYear] param cam not be higher than 3000");
        }
        if(lastYear > 3000){
            throw new IllegalArgumentException("The [lastYear] param cam not be higher than 3000");
        }
        if(firstYear >= lastYear){
            throw new IllegalArgumentException("The [firstYear] param cam not be higher than [lastYear] param");
        }
        return movieDao.findAllMovies(new JsonInputFileLocator()).filter(item ->item.getYear()>=firstYear && item.getYear()<=lastYear);
    }
}
