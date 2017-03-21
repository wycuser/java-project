package jetty;

import org.eclipse.jetty.server.Server;

public class JettyStart {

	public static final int PORT = 8200;

	public static final String CONTEXT = "/app";

	public static final String[] TLD_JAR_NAMES = new String[] { "sitemesh", "spring-webmvc", "shiro-web", "org.apache.jasper.glassfish", "org.apache.taglibs.standard.glassfish"};

	public static final String ACTIVE_PROFILE = "spring.profiles.active";
	public static final String DEFAULT_PROFILE = "spring.profiles.default";

	public static final String PRODUCTION = "production";
	public static final String DEVELOPMENT = "development";
	public static final String UNIT_TEST = "test";
	public static final String FUNCTIONAL_TEST = "functional";

	public static void main(String[] args) throws Exception {
		// 设定Spring的profile
		System.setProperty(ACTIVE_PROFILE, DEVELOPMENT);
		Server server = JettyUtils.createServerInSource(PORT, CONTEXT);
		JettyUtils.setTldJarNames(server, TLD_JAR_NAMES);

		server.start();
		System.out.println("输入回车键后重启jetty服务");
		while (true) {
			char c = (char) System.in.read();
			if (c == '\n') {
				JettyUtils.reloadContext(server);
			}
		}
	}
}
