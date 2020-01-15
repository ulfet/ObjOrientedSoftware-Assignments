package de.rwth.swc.oosc.swcarchitect.webservice.domain;

import java.net.URL;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by andy on 14.01.16.
 */
@JsonAutoDetect
public class Image {

    @JsonProperty
    private Integer id;

    @JsonProperty
    private String name;
    @JsonProperty
    private URL location;
    @JsonProperty
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date publishedAt;
    @JsonProperty
    @JsonFormat(pattern = "YYYY-MM-dd")
    private Date lastModified;


    public Image(){
        this.publishedAt = new Date();
        this.lastModified = new Date();
    }

    public Image(int id, String name, URL location) {
        this.id = new Integer(id);
        this.name = name;
        this.location = location;

        this.publishedAt = new Date();
        this.lastModified = new Date();

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {

        return name;
    }

    public URL getLocation() {
        return location;
    }

    public void setLocation(URL location) {
        this.location = location;
    }
}
