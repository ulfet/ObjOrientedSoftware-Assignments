/**
 * file system storage service copied from
 * https://github.com/spring-guides/gs-uploading-files/
 */

package de.rwth.swc.oosc.swcarchitect.webservice.storage;

public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}