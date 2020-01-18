package de.rwth.swc.oosc.swcarchitect.webservice.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.rwth.swc.oosc.swcarchitect.webservice.domain.Image;

import java.net.URL;

@JsonAutoDetect
public class CompleteImage extends Image {

    @JsonProperty
    private boolean approved;

    public CompleteImage() {
        super();
    }

    public CompleteImage(int id, String name, URL location, boolean approved) {
        super(id, name, location);
        this.approved = approved;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
