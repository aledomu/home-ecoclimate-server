package sensor;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import sensor.common.ResourceHandler;
import sensor.data.Humidity;
import sensor.data.pools.LocalNonPersistent;

/**
 * Esta clase crea un subrouter por cada tipo de recurso y los une a la raíz
 * de las rutas de la API con el nombre de la clase del tipo de recurso en
 * minúscula como prefijo. 
 */
public class Server extends AbstractVerticle {
	
	private static ResourceHandler<?>[] toPublish = {
		new ResourceHandler<Humidity>(Humidity.class, new LocalNonPersistent<Humidity>()),
	};
	
	@Override
	public void start(Promise<Void> startFuture) {
		Router router = Router.router(getVertx());
		
		for (ResourceHandler<?> h : toPublish) {
			router.mountSubRouter("/" + h.resourceName(), h.getRouter(getVertx()));
		}

		getVertx()
			.createHttpServer()
			.requestHandler(router::handle)
			.listen(8080)
			.onSuccess(x -> startFuture.complete())
			.onFailure(startFuture::fail);
	}

}
