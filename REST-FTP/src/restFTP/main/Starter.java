package restFTP.main;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import restFTP.config.AppConfig;

public class Starter {

	public final static String hostname = "localhost";
	public final static int port = 9999;
	private static FTPClient client = null;
	public final static String userName = "arctarus";
	public final static String password = "test";

	public static void main(final String[] args) throws Exception {
		final Server server = new Server(8080);

		// Register and map the dispatcher servlet
		final ServletHolder servletHolder = new ServletHolder(new CXFServlet());
		final ServletContextHandler context = new ServletContextHandler();
		context.setContextPath("/");
		context.addServlet(servletHolder, "/rest/*");
		context.addEventListener(new ContextLoaderListener());

		context.setInitParameter("contextClass",
				AnnotationConfigWebApplicationContext.class.getName());
		context.setInitParameter("contextConfigLocation",
				AppConfig.class.getName());

		server.setHandler(context);
		server.start();
		server.join();
	}
}
