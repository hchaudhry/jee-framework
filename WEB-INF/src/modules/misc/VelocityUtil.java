package modules.misc;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class VelocityUtil {

	private VelocityUtil() {
	}

	private static VelocityUtil instance;

	public static VelocityUtil getInstance() {
		if (instance == null) {
			instance = new VelocityUtil();
		}
		return instance;
	}

	public void render(String templatePath, Map<String, Object> context,
			Writer writer) {
		try {
			VelocityContext velocityContext = new VelocityContext(context);
			Template template = null;
			try {
				template = Velocity.getTemplate(templatePath);
			} catch (Exception e) {
				throw new RuntimeException(
						"Internal error : impossible to load the Velocity template: "
								+ templatePath, e);
			}
			template.merge(velocityContext, writer);
		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

	public CharSequence render(String templatePath, Map<String, Object> context) {
		StringWriter writer = new StringWriter(1024);
		render(templatePath, context, writer);
		CharSequence ret = writer.getBuffer();
		return ret;
	}

}
