package de.rwth.swc.oosc.swcarchitect.circuitbreaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object getAllImages() {
        return imageService.getAllImages();
    }


    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public Object getImagesByName(@RequestParam(value="filter") String filter) {
        return imageService.getImagesByName(filter);
    }

    @RequestMapping("/owner")
    public String ownerInfo(@RequestParam(value="name",defaultValue = "Unknown")String name) {
        return imageService.ownerInfo(name);
    }

    @RequestMapping(value = "/{nr}", params = "format=json")
    public Object showJsonImage(@PathVariable("nr") int nr) {
        return imageService.showJsonImage(nr);
    }

    @RequestMapping(value="/{nr}", params = "format=html")
    public String showImageAsHTML(@PathVariable("nr") int nr) {
        return imageService.showImageAsHTML(nr);
    }

    @RequestMapping(value = "/{nr}", params = "format=image")
    public Object showImage(@PathVariable("nr") int nr) {
        return imageService.showImage(nr);
    }


    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json")
    public Object createNewImage(@RequestBody Image image) {
        return imageService.createNewImage(image);
    }

}
