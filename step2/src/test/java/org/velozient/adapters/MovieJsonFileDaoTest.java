package org.velozient.adapters;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.velozient.error.MovieAccessException;
import org.velozient.error.MovieExportException;
import org.velozient.factory.MovieMother;
import org.velozient.infra.JsonInputFileLocator;
import org.velozient.infra.JsonOutPutFileLocator;
import org.velozient.model.Movie;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class MovieJsonFileDaoTest {

    private final JsonOutPutFileLocator outPathLocatorMock = Mockito.mock(JsonOutPutFileLocator.class);

    private final JsonInputFileLocator inputPathLocatorMock = Mockito.mock(JsonInputFileLocator.class);

    private final MovieJsonFileDao target = new MovieJsonFileDao();

    @Test
    public void loadData_test_whenOk() {
        Mockito.doReturn(new File("src/test/resources/valid_json_with_3_movies.json")).when(inputPathLocatorMock).moviesInputFilePath();
        List<Movie> movieListResult = new ArrayList<>();
        Flux<Movie> movieStream = target.findAllMovies(inputPathLocatorMock);
        movieStream.doOnNext(movieListResult::add).subscribe();
        Assertions.assertEquals(3,movieListResult.size());
    }

    @Test
    public void loadData_test_when_file_does_not_exist() {
        Mockito.doReturn(new File("src/test/resources/does_not_exist.json")).when(inputPathLocatorMock).moviesInputFilePath();
        Flux<Movie> movieStream = target.findAllMovies(inputPathLocatorMock);
        StepVerifier
                .create(movieStream)
                .expectError(MovieAccessException.class)
                .verify();
    }

    @Test
    public void loadData_test_when_invalid_json_file() {
        Mockito.doReturn(new File("src/test/resources/partial_invalid_json_file.json")).when(inputPathLocatorMock).moviesInputFilePath();
        Flux<Movie> movieStream = target.findAllMovies(inputPathLocatorMock);
        StepVerifier
                .create(movieStream)
                .expectNextCount(1)
                .expectError(MovieAccessException.class)
                .verify();
    }

    @Test
    public void loadData_test_when_valid_json_with_invalid_value_file() {
        Mockito.doReturn(new File("src/test/resources/valid_json_with_invalid_value.json")).when(inputPathLocatorMock).moviesInputFilePath();
        Flux<Movie> movieStream = target.findAllMovies(inputPathLocatorMock);
        StepVerifier
                .create(movieStream)
                .expectNextCount(1)
                .expectError(MovieAccessException.class)
                .verify();
    }

    @Test
    public void loadData_test_when_plain_txt_file() {
        Mockito.doReturn(new File("src/test/resources/plain_txt_file.txt")).when(inputPathLocatorMock).moviesInputFilePath();
        Flux<Movie> movieStream = target.findAllMovies(inputPathLocatorMock);
        StepVerifier
                .create(movieStream)
                .expectError(MovieAccessException.class)
                .verify();
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void save_when_Ok() throws IOException {
        File outPutFile = new File("src/test/resources/output_save_when_Ok.json");
        Files.deleteIfExists(outPutFile.toPath());
        Mockito.doReturn(outPutFile).when(outPathLocatorMock).moviesOutputFilePath();

        Flux<Movie> moviesList = MovieMother.multipleDummyMovieFor90sDecade(5);
        target.save(moviesList,outPathLocatorMock);
        StepVerifier
                .create(moviesList)
                .expectNextCount(5)
                .expectComplete()
                .verify();

        Assertions.assertTrue(Files.exists(outPutFile.toPath()));
        Files.deleteIfExists(outPutFile.toPath());
    }

    @Test
    public void save_when_output_folder_does_not_exist() {
        File outPutFile = new File("/invalid_path/output_invalid_path.json");
        Mockito.doReturn(outPutFile).when(outPathLocatorMock).moviesOutputFilePath();

        Flux<Movie> moviesList = Flux.empty();
        Assertions.assertThrows(MovieExportException.class,()-> target.save(moviesList,outPathLocatorMock));
    }

    @Test
    public void save_with_empty_movie_list() throws IOException {
        File outPutFile = new File("src/test/resources/output_save_when_empty_movie_list.json");
        Files.deleteIfExists(outPutFile.toPath());
        Mockito.doReturn(outPutFile).when(outPathLocatorMock).moviesOutputFilePath();

        Flux<Movie> moviesList = Flux.empty();
        target.save(moviesList,outPathLocatorMock);
        Assertions.assertTrue(Files.exists(outPutFile.toPath()));
        Files.deleteIfExists(outPutFile.toPath());
    }


    @Test
    public void save_with_null_as_movie_list() throws IOException {
        File outPutFile = new File("src/test/resources/output_save_when_null.json");
        Files.deleteIfExists(outPutFile.toPath());
        Mockito.doReturn(outPutFile).when(outPathLocatorMock).moviesOutputFilePath();

        target.save(null,outPathLocatorMock);
        Assertions.assertTrue(Files.exists(outPutFile.toPath()));
        Files.deleteIfExists(outPutFile.toPath());
    }
}
