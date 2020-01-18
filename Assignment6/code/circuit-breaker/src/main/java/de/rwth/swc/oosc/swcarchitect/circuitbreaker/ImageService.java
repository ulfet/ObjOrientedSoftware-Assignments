package de.rwth.swc.oosc.swcarchitect.circuitbreaker;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    private final RestTemplate restTemplate;
    private static String baseUrl = "http://localhost:8080/images";

    public ImageService(RestTemplate rest) {
        this.restTemplate = rest;
    }

    @HystrixCommand(fallbackMethod = "imageListFallback")
    public Object getAllImages() {
        URI uri = URI.create(baseUrl);
        return this.restTemplate.getForObject(uri, List.class);
    }

    public Object imageListFallback() {
        return "The service is not available at the moment";
    }

    @HystrixCommand(fallbackMethod = "imageListFallback")
    public Object getImagesByName(String filter) {
        URI uri = URI.create(baseUrl + "/filter?filter=" + filter);
        return this.restTemplate.getForObject(uri, List.class);
    }

    @HystrixCommand(fallbackMethod = "ownerInfoFallback")
    public String ownerInfo(String name) {
        URI uri = URI.create(baseUrl + "/owner?name=" + name);
        return this.restTemplate.getForObject(uri, String.class);
    }

    public String ownerInfoFallback(String name) {
        return "Could not get owner information at the moment";
    }

    @HystrixCommand(fallbackMethod = "imageFallback")
    public Object showJsonImage(int nr) {
        URI uri = URI.create(baseUrl + "/" + nr + "?format=json");
        return this.restTemplate.getForEntity(uri, Image.class);
    }

    @HystrixCommand(fallbackMethod = "showImageAsHTMLFallback")
    public String showImageAsHTML(int nr) {
        URI uri = URI.create(baseUrl + "/" + nr + "?format=html");
        return this.restTemplate.getForObject(uri, String.class);
    }

    public String showImageAsHTMLFallback(int nr) {
        return "<html><body>No image " + nr + " found at the moment</body></html>";
    }

    @HystrixCommand(fallbackMethod = "imageFallback")
    public Object showImage(int nr) {
        URI uri = URI.create(baseUrl + "/" + nr + "?format=image");
        return this.restTemplate.getForEntity(uri, InputStreamResource.class);
    }

    public Object imageFallback(int nr) {
        return "No image " + nr + " found at the moment";
    }


    @HystrixCommand(fallbackMethod = "createNewImageFallback")
    public Object createNewImage(Image image) {
        URI uri = URI.create(baseUrl);
        return this.restTemplate.postForEntity(uri, image, Image.class);
    }

    public Object createNewImageFallback(Image image) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not create image at the moment");
    }

}
