package webservice;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Publication;
import model.Reservation;
import model.exceptions.DateNotAvailableException;
import service.PublicationService;


@Path("/publications")
public class PublicationController {
	public static final int NUMBER_OF_PUBLICATIONS = 10;
	
	 private PublicationService publicationService;

	   @GET
	   @Path("/")
	   @Produces("application/json")
	   public Response getAllPublication() throws JsonProcessingException {
	       List<Publication> all = this.publicationService.retriveAll();
	       String serialized = new ObjectMapper().writeValueAsString(all);
	       return Response.ok(serialized).build();
	   }
	   
	   @GET
	   @Path("/{id}")
	   @Produces("application/json")
	   public Response getPublication(@PathParam("id") int id) throws JsonProcessingException{
		   Publication publication = this.publicationService.findById(id);
		   String serialized = new ObjectMapper().writeValueAsString(publication);
	       return Response.ok(serialized).build();
	   }
	   
	   @GET
	   @Path("/find/{text}")
	   @Produces("application/json")
	   public Response findPublication(@PathParam("text") String text) throws JsonProcessingException {
	       Set<Publication> all = this.publicationService.find(text);
	       String serialized = new ObjectMapper().writeValueAsString(all);
	       return Response.ok(serialized).build();
	   }
	   
	   @POST
	   @Path("/new")
	   @Produces("application/json")
	   public Response newPublication(@RequestBody Publication publication){
		   this.publicationService.save(publication);
		   return Response.ok().build();
	   }
	   
	   @PUT
	   @Path("/{idPublication}/newReservation")
	   @Produces("application/json")
	   public Response newReservation(@RequestBody String reservationString, @PathParam("idPublication") int idPublication) throws JsonParseException, JsonMappingException, IOException, DateNotAvailableException{
		   ObjectMapper mapper = new ObjectMapper();
		   System.out.println("11111111111111111111111111111111111111111111111111");
		   Reservation reservation = mapper.readValue(reservationString, Reservation.class);
		   System.out.println("LASSSSSSSSSSSSSSSSSSSSTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
		   this.publicationService.newReservation(reservation, idPublication);
		   return Response.ok().build();
	   }
	   
	   @GET
	   @Path("/byemail/{email}")
	   @Produces("application/json")
	   public Response getByEmailPublication(@PathParam("email") String email) throws JsonProcessingException{
		   Set<Publication> publications = this.publicationService.getPublicationsByEmail(email);
		   String serialized = new ObjectMapper().writeValueAsString(publications);
		   return Response.ok(serialized).build();
	   }
	   
	   @PUT
	   @Path("/edit/{id}")
	   @Produces("application/json")
	   public void updatePublication(@RequestBody Publication publication){
		   this.publicationService.update(publication);
	   }
	   
	   @DELETE
	   @Path("/{id}")
	   @Produces("application/json")
	   @Consumes("application/json")
	   public void deletePublication(@PathParam("id") int id){
		   Publication publication = this.publicationService.findById(id);
		   this.publicationService.delete(publication);
	   }
	   
	   public void setPublicationService(final PublicationService publicatioService) {
		   this.publicationService = publicatioService;
	   }
}
