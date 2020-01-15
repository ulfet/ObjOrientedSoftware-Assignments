package de.rwth.swc.oosc.swcarchitect.webservice;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * Created by andy on 15.06.16.
 */

//@EnableAutoConfiguration
@Configuration
@ImportResource("classpath:applicationContext.xml")
public class SwcArchitectConfig
{

}
