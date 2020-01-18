package de.rwth.swc.oosc.swcarchitect.webservice.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.rwth.swc.oosc.swcarchitect.webservice.domain.Image;
import de.rwth.swc.oosc.swcarchitect.webservice.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.UriBuilder;
import java.io.*;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

/**
 * Created by andy on 14.01.16.
 */

@RestController
@RequestMapping("/images")
public class ImageResource {

    private final String base = "http://localhost:8080/images";
    private final StorageService storageService;
    private final ObjectMapper objectMapper;
    private final PathMatcher matcher;

    private Logger logger;

    /**
     * Create a new ImageResource controller
     *
     * @param storageService The storage service
     */
    @Autowired
    public ImageResource(StorageService storageService) {
        // DI of the storage service
        this.storageService = storageService;

        // Create object mapper and matcher for later usage
        this.objectMapper = new ObjectMapper();
        this.matcher = FileSystems.getDefault().getPathMatcher("glob:*.json");
    }

    /**
     * Get all images in the standard representation format defined by the Domain Entities
     *
     * @return A list of all images
     **/
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Image> getAllImages() {
        logger.info("Sending {} Images", storageService.count(".json"));

        // Just call the filter method without filter
        return getImagesByName("");
    }

    /**
     * Get all images in the standard representation format which matches the filter
     *
     * @param filter The filter
     * @return A list of images
     */
    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public List<Image> getImagesByName(@RequestParam(value="filter") String filter) {
        List<Image> images = new ArrayList<>();

        // Load all '.json' files from the storage service
        storageService
                .loadAll()
                .filter(f -> matcher.matches(f))
                .forEachOrdered(f ->
        {
            try {
                // Convert it to an image class
                Image i = objectMapper.readValue(storageService.load(f.toString()).toFile(), Image.class);

                logger.info("image {} found", i.getId());
                logger.info("image with name {} found", i.getName());

                // Only add if matches the filter
                if (i.getName().contains(filter)) {
                    images.add(i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return images;
    }
    
    @RequestMapping("/owner")
    public String ownerInfo(@RequestParam(value="name",defaultValue = "Unknown")String name)
    {
    	return "This Collection is owned by " + name;
    }

    /**
     * Prints a image in JSON
     *
     * @param nr The id of the image
     * @return The requested image
     */
    @RequestMapping(value = "/{nr}", params = "format=json")
    public ResponseEntity<Image> showJsonImage(@PathVariable("nr") int nr) {
        try {
            // Load the path of the image
            Path path = storageService.load(nr + ".json");

            // Create an object for the stored file
            Image image = objectMapper.readValue(path.toFile(), Image.class);

            // Return the image
            return ResponseEntity.ok()
                    .body(image);
        } catch (FileNotFoundException e) {
            logger.info("no image {} found", nr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Prints a website which contains the image
     *
     * @param nr The id of the image
     * @return A HTML website with the image
     */
    @RequestMapping(value="/{nr}", params = "format=html")
    public String showImageAsHTML(@PathVariable("nr") int nr)
    {
        // loads the path from the storage
        Path path = storageService.load(nr + ".json");
        try {
            // load the image to an object
            Image image = objectMapper.readValue(path.toFile(), Image.class);
            String imageUrl = base + "/" + nr + "?format=image";

            // Use fluent api to create the website
            String html = new StringBuilder()
                    .append("<!DOCTYPE html>")
                    .append("<html>")
                        .append("<head>")
                            .append("<meta charset=\"UTF-8\">")
                            .append("<title>REST Exercise - OOSC 2020</title>")
                        .append("</head>")
                        .append("<body>")
                            .append("<h1>REST Exercise - OOSC 2020</h1>")
                            .append("<p>Showing Image ")
                                .append(image.getName())
                                .append(" from ")
                                .append(image.getLocation().toString())
                            .append("</p>")
                            .append("<p><code>&lt;img src=&quot;")
                                .append(imageUrl)
                            .append("&quot;&gt;</code></p>")
                            .append("<p><img src=\"")
                                .append(imageUrl)
                            .append("\"></p>")
                        .append("</body>")
                    .append("</html>")
                .toString();

            return html;
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("no image {} found", nr);
        return "<html><body>No image " + nr + " found</body></html>";
    }

    /**
     * Prints an image
     *
     * @param nr The id of the image
     * @return The image
     */
    @RequestMapping(value = "/{nr}", params = "format=image")
    public ResponseEntity<InputStreamResource> showImage(@PathVariable("nr") int nr) {
        try {
            // Load the path of the image
            Path path = storageService.load(nr + ".json");

            // Create an object for the stored file
            Image image = objectMapper.readValue(path.toFile(), Image.class);

            // Return the image
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new InputStreamResource(image.getLocation().openStream()));
        } catch (FileNotFoundException e) {
            logger.info("no image {} found", nr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Creates a new image
     *
     * @param image The image
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Image> createNewImage(@RequestBody Image image) {
        // Check if the actual image is available
        File file = new File(image.getLocation().getFile());
        if (!file.exists()) {
            // Return a not found status, if the linked image is not available locally
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Set a new id, so there will be nothing overridden.
        image.setId(getNextId(true));

        try {
            // Converts image to text and store it
            String json = objectMapper.writeValueAsString(image);
            storageService.store(new ByteArrayInputStream(json.getBytes()), image.getId() + ".json");

            URI uri = UriBuilder
                    .fromUri(base + "/" + image.getId())
                    .queryParam("format=html").build();

            return ResponseEntity.created(uri).body(image);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body(null);
    }

    /**
     * Create a new image file
     *
     * @param file The image as byte array
     * @return
     */
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public ResponseEntity<InputStreamResource> createNewImage(@RequestBody byte[] file) {

        String filename = getNextId(false) + ".png";

        try {
            // Store the file
            InputStream stream = new ByteArrayInputStream(file);
            storageService.store(stream, filename);

            // Get the path
            String absoluteFilePath = Paths.get("").toAbsolutePath().toString() + "/" + storageService.load(filename);
            URI uri = UriBuilder.fromPath("file://" + absoluteFilePath).build();

            // return the uri as header
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

    /**
     * Get the next id for a json or png file
     *
     * @param imageClass Is the next id for a json file?
     * @return The next id
     */
    private int getNextId(boolean imageClass) {
        final int extSize = imageClass ? 5 : 4;

        // Get "max" filename
        OptionalInt opt = storageService
                .loadAll()
                .filter(f -> (imageClass && matcher.matches(f)) ||  // *.json for imageClass
                             (!imageClass && !matcher.matches(f)))  // != *.json for !imageClass
                .mapToInt(f -> {
                    String name = f.toFile().toString();
                    return Integer.parseInt(name.substring(0, name.length() - extSize));
                })
                .max();

        int max = 0;
        if (opt.isPresent()) {
            max = opt.getAsInt();
        }

        // create new filename
        return max + 1;
    }
}
