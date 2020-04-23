package hr.fer.zemris.java.hw16.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw16.gallery.GalleryInfo;
import hr.fer.zemris.java.hw16.gallery.Image;

/**
 * Maps all paths to the tags and returns appropriate data.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@Path("/tag")
public class TagJSON {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTags() {

		List<String> tags = GalleryInfo.getAllTags();

		Gson gson = new Gson();
		String jsonText = gson.toJson(tags);

		return Response.status(Status.OK).entity(jsonText).build();
	}

	@Path("{tagName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getImagesForTag(@PathParam("tagName") String tagName) {
		List<Image> images = GalleryInfo.getImagesForTag(tagName);

		Gson gson = new Gson();
		String jsonText = gson.toJson(images);

		return Response.status(Status.OK).entity(jsonText).build();
	}

}
