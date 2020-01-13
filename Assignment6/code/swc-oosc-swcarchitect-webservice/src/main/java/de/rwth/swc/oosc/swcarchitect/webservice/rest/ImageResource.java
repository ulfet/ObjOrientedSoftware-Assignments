package de.rwth.swc.oosc.swcarchitect.webservice.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.rwth.swc.oosc.swcarchitect.webservice.domain.Image;
import de.rwth.swc.oosc.swcarchitect.webservice.storage.StorageService;
import org.glassfish.jersey.server.Uri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.UriBuilder;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 14.01.16.
 */

@RestController
@RequestMapping("/images")
public class ImageResource {

    private final URI base;
    private final StorageService storageService;
    private final ObjectMapper objectMapper;
    private final PathMatcher matcher;

    private Logger logger;

    @Autowired
    public ImageResource(StorageService storageService) {
        this.storageService = storageService;
        this.objectMapper = new ObjectMapper();

        this.base = UriBuilder.fromPath("http://localhost:8080/images").build();
        this.matcher = FileSystems.getDefault().getPathMatcher("glob:*.json");
    }

    /**
     * Get all images in the standard representation format defined by the Domain Entities
     **/
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Image> getAllImages() {
        logger.info("Sending {} Images", storageService.count(".json"));

        return getImagesByName("");
    }
    
    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public List<Image> getImagesByName(@RequestParam(value="filter") String filter) {
        List<Image> images = new ArrayList<>();

        storageService
                .loadAll()
                .filter(f -> matcher.matches(f))
                .filter(f -> f.toString().contains(filter))
                .forEachOrdered(f ->
        {
            try {
                Image i = objectMapper.readValue(storageService.load(f.toString()).toFile(), Image.class);

                logger.info("image {} found", i.getId());

                images.add(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return images;
    }
    
    @RequestMapping("/owner")
    public String ownerInfo(@RequestParam(value="name",defaultValue = "Unkown")String name)
    {
    	return "This Collection is owned by " + name;
    }
    
    @RequestMapping(value="/{nr}", params = "format=html")
    public String showImageAsHTML(@PathVariable("nr") int nr)
    {
        Path path = storageService.load(nr + ".json");
        try {
            Image image = objectMapper.readValue(path.toFile(), Image.class);

            return "<html><body><img src=\"" + base.toString() + "/" + nr + "?format=image\"></body></html>";
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("no image {} found", nr);
        return "<html><body>No image " + nr + " found</body></html>";
    }

    @RequestMapping(value = "/{nr}", params = "format=image")
    public ResponseEntity<InputStreamResource> showImage(@PathVariable("nr") int nr) {
        Path path = storageService.load(nr + ".json");
        try {
            Image image = objectMapper.readValue(path.toFile(), Image.class);


            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new InputStreamResource(image.getLocation().openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("no image {} found", nr);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Image> createNewImage(@RequestBody Image image) {
        try {
            String json = objectMapper.writeValueAsString(image);

            storageService.store(new ByteArrayInputStream(json.getBytes()), image.getId() + ".json");

            URI uri = UriBuilder
                    .fromUri(base)
                    .path("images/" + image.getId())
                    .queryParam("format=html").build();

            return ResponseEntity.created(uri).body(image);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body(null);
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public ResponseEntity<InputStreamResource> createNewImage(@RequestBody byte[] file, @RequestParam("filename") String filename) {
        try {
            InputStream stream = new ByteArrayInputStream(file);
            storageService.store(stream, filename);

            String absoluteFilePath = Paths.get("").toAbsolutePath().toString() + "/" + storageService.load(filename);
            URI uri = UriBuilder.fromPath("file://" + absoluteFilePath).build();

            return ResponseEntity
                    .created(uri)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new InputStreamResource(stream));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body(null);
    }

    @PostConstruct
    public void setup() throws Exception {

        logger = LoggerFactory.getLogger(this.getClass());
    }

}
