/**
 * file system storage service copied from
 * https://github.com/spring-guides/gs-uploading-files/
 */

package de.rwth.swc.oosc.swcarchitect.webservice.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}