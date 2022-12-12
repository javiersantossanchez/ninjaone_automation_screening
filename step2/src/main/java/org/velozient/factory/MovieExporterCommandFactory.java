package org.velozient.factory;

import lombok.RequiredArgsConstructor;
import org.velozient.adapters.MovieJsonFileDao;
import org.velozient.infra.JsonOutPutFileLocator;
import org.velozient.worker.ExportToJsonFileCommand;
import org.velozient.worker.MovieExportCommand;

import java.io.File;

@RequiredArgsConstructor
public class MovieExporterCommandFactory {

    private final File outPutFile;

    public MovieExportCommand build (){
        return new ExportToJsonFileCommand(new MovieJsonFileDao(),new JsonOutPutFileLocator(outPutFile));
    }

}
