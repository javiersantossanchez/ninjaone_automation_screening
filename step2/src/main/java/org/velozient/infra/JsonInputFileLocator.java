package org.velozient.infra;

import java.io.File;
import java.nio.file.Path;

/**
 * Define the path and file name used as source for movie information.
 *
 * @author Javier Santos
 */
public class JsonInputFileLocator {

    private final String FILE_NAME = "movies.json";

    private final String DATA_INPUT_DIR = System.getProperty("data.input.dir");

    private final  String DEFAULT_DATA_INPUT_DIR = System.getProperty("user.dir");

    /**
     * get the json file's path used as source for movie information.
     * @return json file's path
     */
    public File moviesInputFilePath(){
        if(DATA_INPUT_DIR == null){
            return Path.of(DEFAULT_DATA_INPUT_DIR,FILE_NAME).toFile();
        }
        return Path.of(DATA_INPUT_DIR,FILE_NAME).toFile();
    }
}
