package de.rwth.swc.oosc.swcarchitect.webservice.rest;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import de.rwth.swc.oosc.swcarchitect.webservice.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.rwth.swc.oosc.swcarchitect.webservice.domain.Image;

/**
 * Created by andy on 14.01.16.
 */

@RestController
@RequestMapping("/imagesfull")
public class CompleteImageResource {

    private final StorageService storageService;
    private Logger logger;
    private List<Image> data = new ArrayList<>();

    @Autowired
    public CompleteImageResource(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping("/info")
    public String info() {
        return "Your Collection currently consists of " + storageService.loadAll().count() + " images.";
    }

    @RequestMapping("info2")
    public String info2(@RequestParam(value = "owner", required = false, defaultValue = "Unknown") String owner) {
        return info() + " This Collection is owned by " + owner;
    }

    /**
     * Get all Images with sorting an filtering
     *
     * @param filter
     * @return
     */
    @RequestMapping("")
    public ResponseEntity<List<Image>> getAllImages(@RequestParam(value = "filter", defaultValue = "") String filter) {
        try {
            return ResponseEntity.ok(data.stream().filter(image -> image.getName().contains(filter) ).collect(Collectors.toList()));
        } catch (Exception e) {
            logger.error("Some error occured during fullfilling your request: {}", e.getMessage());
            logger.error("Stacktrace", e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


    @RequestMapping(value = "/{nr}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Image> showImageAsJSON(@PathVariable(value="nr") int nr) {
        try {
            return ResponseEntity.ok(data.get(nr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "/{nr}", method = RequestMethod.GET, params = "format=image")
    @ResponseBody
    public ResponseEntity<InputStreamResource> showImageAsJPEG(@PathVariable("nr") int nr) {
        Image image = data.get(nr);
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(image.getLocation().openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
    
    @RequestMapping(value = "/{nr}", method = RequestMethod.GET, params = "format=html")
    @ResponseBody
    public ResponseEntity<String> showImageAsHTML(@PathVariable("nr") int nr) {
        Image image = data.get(nr);
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(this.renderAsHTML(image));
               
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    private String renderAsHTML(Image image) {
    	String imageUrl = "http://localhost:8080/imagesfull/"+(image.getId().intValue()-1)+"?format=image";
		String htmltemplate =  "<!DOCTYPE html>"
							+"<html>"
                    		+"<head>"
                    		+"<meta charset=\"UTF-8\">"
                    		+"<title>REST Exercise - UTM 2016</title>"
                    		+"</head>"
                    		+"<body>"
                    			+"<h1>REST Exercise - UTM 2016</h1>"
                    			+"<p> Showing Image "+ image.getName() + " from "+ image.getLocation().toString() + "</p>"
                    			+"<p>"
                    				+"<code>"
                					+	"&lt;img src=&quot;"+imageUrl+"&quot;&gt;"
                    				+"</code>"
                    			+"</p>"
                    			+"<p>"
                    				+"<img src=\""+ imageUrl + "\">"
                    			+"</p>"
                    		+"</body>"
                    		+"</html>";
		return htmltemplate;
	}

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Image> createNewImage(@RequestBody Image image)
    {
        //image.setId(11);


        data.add(image);
        return ResponseEntity.created(null).body(image);
    }

    @RequestMapping(value = "/{nr}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Image> updateImage(@RequestBody Image image)
    {
        Image oldOne = data.get(image.getId());
        // .... update ...
        return ResponseEntity.ok(null);
    }



    @PostConstruct
    public void setup() throws Exception {
        logger = LoggerFactory.getLogger(this.getClass());

        data.add(new Image(data.size()+1,"Sports", new URL("http://lorempixel.com/400/200/sports/")));
        data.add(new Image(data.size()+1,"City", new URL("http://lorempixel.com/1280/1024/city/")));
        data.add(new Image(data.size()+1,"Food", new URL("http://lorempixel.com/500/500/food/")));
        data.add(new Image(data.size()+1,"Cats", new URL("http://lorempixel.com/640/480/cats/")));
        data.add(new Image(data.size()+1,"Nature", new URL("http://lorempixel.com/800/600/nature/")));
    }

}
