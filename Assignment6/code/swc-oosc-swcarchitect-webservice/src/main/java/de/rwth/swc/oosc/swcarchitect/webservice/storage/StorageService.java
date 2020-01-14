/**
 * Slightly modified file system storage service copied from
 * https://github.com/spring-guides/gs-uploading-files/
 */

package de.rwth.swc.oosc.swcarchitect.webservice.storage;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    int count(String fileExtension);

    void store(InputStream inputStream, String filename);

    Stream<Path> loadAll();

    Path load(String filename);

    void deleteAll();

}