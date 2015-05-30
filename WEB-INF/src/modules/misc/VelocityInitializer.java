package modules.misc;

import java.util.Properties;

import org.apache.velocity.app.Velocity;

public class VelocityInitializer {

	private boolean isInitialized = false;

	// SINGLETON
	public static VelocityInitializer getInstance() {
		return SingletonHolder.instance;
	}

	// ---------------------------------------- INNER CLASS
	private static class SingletonHolder {
		private static final VelocityInitializer instance = new VelocityInitializer();
	}

	// ---------------------------------------- CONSTRUCTOR
	private VelocityInitializer() {
	}

	// INITIALIZATION METHOD

	/**
	 * Initialize Velocity (template engine library)
	 */
	public void initializeVelocity() {
		if (!isInitialized) {
			// We configure Velocity to load template
			// files from the classpath
			Properties velocityProps = new Properties();
			velocityProps.setProperty("resource.loader", "file");
			// File logging disabled
			velocityProps
					.setProperty("file.resource.loader.class",
							"org.apache.velocity.runtime.resource.loader.FileResourceLoader");
			velocityProps.setProperty("file.resource.loader.path", "/home/hussam/Bureau/jee/FrontControllerProject/WEB-INF/views/");
			velocityProps.setProperty("runtime.log", "");
			velocityProps.setProperty(
					"directive.foreach.counter.initial.value", "0");
			try {
				Velocity.init(velocityProps);
			} catch (Exception e) {
				throw new RuntimeException(
						"Internal error : impossible to initialize velocity.",
						e);
			}
			isInitialized = true;
		}
	}
}
