package sensor.common;

import java.util.Collection;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class ResourceHandler<T extends Entry> {

	final private Class<T> resource;
	final private DataPool<T> data;

	final private Gson gson = new GsonBuilder().create();

	public ResourceHandler(Class<T> resource, DataPool<T> data) {
		super();
		this.data = data;
		this.resource = resource;
	}
	
	public Router getRouter(Vertx vertx) {
		Router router = Router.router(vertx);
		router.route("/*").handler(BodyHandler.create());
		router.get("/").handler(this::getAll);
		router.get("/:id").handler(this::getId);
		router.get("/:id/:time").handler(this::getIdTime);
		router.post().handler(this::addRecord);
		
		return router;
	}
	
	public String resourceName() {
		return resource.getSimpleName();
	}

	private <P> void setJSONResponse(HttpServerResponse response, Optional<P> payload) {
		payload.ifPresentOrElse(
			queryResult ->
				response
					.putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(200)
					.end(gson.toJson(queryResult)),
			() -> {
				response
					.putHeader("content-type", "application/json; charset=utf-8")
					.setStatusCode(204)
					.end();
			}
		);
	}
	
	private <P extends Collection<T>> void setJSONCollectionResponse(HttpServerResponse response, P payload) {
		setJSONResponse(response, Optional.of(data.getAll()).filter(p -> !p.isEmpty()));
	}
	
	private void getAll(RoutingContext routingContext) {
		setJSONCollectionResponse(routingContext.response(), data.getAll());
	}

	private void getId(RoutingContext routingContext) {
		String id = routingContext.request().getParam("id");
		
		setJSONCollectionResponse(routingContext.response(), data.getById(id));
	}

	private void getIdTime(RoutingContext routingContext) {
		String id = routingContext.request().getParam("id");
		long time = Long.parseLong(routingContext.request().getParam("time"));
		
		setJSONResponse(routingContext.response(), data.getByIdAndTime(id, time));
	}

	private void addRecord(RoutingContext routingContext) {
		int statusCode = 201;
		
		try {
			String body = routingContext.getBodyAsString();
			
			if (body == null || body.equals(""))
				throw new JsonSyntaxException("The payload for adding an entry cannot be empty");
			
			final T newEntry = gson.fromJson(body, resource);
		
			data.add(newEntry);
		} catch (JsonSyntaxException ex) {
			statusCode = 400;
		}
		
		routingContext
			.response()
			.setStatusCode(statusCode)
			.end();
	}
	
}
