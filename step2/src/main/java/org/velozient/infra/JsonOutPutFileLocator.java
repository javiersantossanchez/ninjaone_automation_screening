package org.velozient.infra;

import lombok.RequiredArgsConstructor;

import java.io.File;

/**
 * Define the path and file name used to export the result to the Json file.
 *
 * @author Javier Santos
 */
@RequiredArgsConstructor
public class JsonOutPutFileLocator {



    private final File outPutFile;

    /**
     * get the json file's path used to export the results.
     * @return json file's path
     */
    public File moviesOutputFilePath(){
        return outPutFile;
    }
}
