package de.mymiggi.discordbotapi;

import java.util.Scanner;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * NotFoundExepptionMapper
 */
@Provider
public class NotFoundExeptionMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException exception) {
    	Scanner scanner = new Scanner(this.getClass().getResourceAsStream("/META-INF/resources/404.html"), "UTF-8");
        String text = scanner.useDelimiter("\\A").next();
        scanner.close();
        return Response.status(404).entity(text).build();
    }
}