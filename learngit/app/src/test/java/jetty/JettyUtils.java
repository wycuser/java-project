package jetty;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

import com.google.common.collect.Lists;

public class JettyUtils {
	
	public static Server createServerInSource(int port, String contextPath) {
		Server server = new Server();
		// 设置在JVM退出时关闭Jetty的钩子。
		server.setStopAtShutdown(true);

		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(port);
		// 解决Windows下重复启动Jetty居然不报告端口冲突的问题.
		connector.setReuseAddress(false);
		server.setConnectors(new Connector[] { connector });

		WebAppContext webContext = new WebAppContext(DEFAULT_WEBAPP_PATH, contextPath);
		// 修改webdefault.xml，解决Windows下Jetty Lock住静态文件的问题.
		webContext.setInitParameter("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
		server.setHandler(webContext);

		return server;
	}

	/**
	 * 创建用于Debug的Jetty Server, 以src/main/webapp为Web应用目录.
	 */
	public static Server buildDebugServer(int port, String contextPath) {
		WebAppContext webContext = new WebAppContext();
		Resource resource = new ResourceCollection(DEFAULT_WEBAPP_PATH);
		webContext.setBaseResource(resource);
		webContext.setClassLoader(Thread.currentThread()
				.getContextClassLoader());
		webContext.setContextPath(contextPath);
		// 修改webdefault.xml，解决Windows下Jetty Lock住静态文件的问题.
//		webContext.setDefaultsDescriptor(WINDOWS_WEBDEFAULT_PATH);
		//webContext.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "false");
		webContext.setInitParameter("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
		Server server = new Server();
		server.setHandler(webContext);
		// 设置在JVM退出时关闭Jetty的钩子。
		server.setStopAtShutdown(true);
		SelectChannelConnector connector = new SelectChannelConnector();
//		ServerConnector connector = new ServerConnector(server);
		connector.setPort(port);
		// 解决Windows下重复启动Jetty居然不报告端口冲突的问题.
		connector.setReuseAddress(false);
		server.addConnector(connector);
		return server;
	}

	/**
	 * 创建用于Functional TestJetty Server, 以src/main/webapp为Web应用目录.
	 * 以test/resources/web.xml指向applicationContext-test.xml创建测试环境.
	 */
//	public static Server buildTestServer(int port, String contextPath) {
//		Server server = new Server(port);
//		WebAppContext webContext = new WebAppContext(DEFAULT_WEBAPP_PATH,
//				contextPath);
//		webContext.setClassLoader(Thread.currentThread()
//				.getContextClassLoader());
//		webContext.setDescriptor("src/test/resources/web.xml");
//		server.setHandler(webContext);
//		server.setStopAtShutdown(true);
//		return server;
//	}
	
	
	private static final String DEFAULT_WEBAPP_PATH = "src/main/webapp";
	//private static final String WINDOWS_WEBDEFAULT_PATH = "jetty/webdefault-windows.xml";

	/**
	 * 设置除jstl-*.jar外其他含tld文件的jar包的名称.
	 * jar名称不需要版本号，如sitemesh, shiro-web
	 */
	public static void setTldJarNames(Server server, String... jarNames) {
		WebAppContext context = (WebAppContext) server.getHandler();
		List<String> jarNameExprssions = Lists.newArrayList(".*/[^/]*servlet-api-[^/]*\\.jar$|.*/javax.servlet.jsp.jstl-.*\\.jar$|.*/org.apache.taglibs.taglibs-standard-impl-.*\\.jar$.*/jstl-[^/]*\\.jar$", ".*/.*taglibs[^/]*\\.jar$");
		for (String jarName : jarNames) {
			jarNameExprssions.add(".*/" + jarName + "-[^/]*\\.jar$");
		}

		context.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern",
				StringUtils.join(jarNameExprssions, '|'));

	}

	/**
	 * 快速重新启动application，重载target/classes与target/test-classes.
	 */
	public static void reloadContext(Server server) throws Exception {
		WebAppContext context = (WebAppContext) server.getHandler();

		System.out.println("[INFO] Application reloading");
		context.stop();
		
		WebAppClassLoader classLoader = new WebAppClassLoader(context);
		classLoader.addClassPath("target/classes");
		classLoader.addClassPath("target/test-classes");
		context.setClassLoader(classLoader);

		context.start();

		System.out.println("[INFO] Application reloaded");
	}
}
