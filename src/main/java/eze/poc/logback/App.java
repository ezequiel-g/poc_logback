package eze.poc.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class App {

	private static final Logger LOG = LoggerFactory.getLogger(App.class);
	public static final String SEPARATOR = "#####";

	private int count = 0;

	private static final Marker CONFIDENTIAL = MarkerFactory.getMarker("CONFIDENTIAL");

	public static void main(final String[] args) {
		LOG.info("enableConsole: {}", System.getenv("enableConsole"));
		LOG.info("enableTroubleshooting: {}", System.getenv("enableTroubleshooting"));

		final App app = new App();

		app.allLevels();

		app.allLevelsConfidential();

		app.allLevelsToBe();
	}

	/**
	 * this logs should be shown base in the root level configured
	 */
	private void allLevels() {
		LOG.info(SEPARATOR);
		LOG.trace("my trace: {}", count++);
		LOG.debug("my debug: {}", count++);
		LOG.info("my info: {}", count++);
		LOG.warn("my warn: {}", count++);
		LOG.error("my error: {}", count++);
	}

	/**
	 * this logs should only be shown in troubleshooting logs.
	 */
	private void allLevelsConfidential() {
		LOG.info(SEPARATOR);
		LOG.trace(CONFIDENTIAL, "---> CONFIDENTIAL trace: {}", count++);
		LOG.debug(CONFIDENTIAL, "---> CONFIDENTIAL debug: {}", count++);
		LOG.info(CONFIDENTIAL, "---> CONFIDENTIAL info: {}", count++);
		LOG.warn(CONFIDENTIAL, "---> CONFIDENTIAL warn: {}", count++);
		LOG.error(CONFIDENTIAL, "---> CONFIDENTIAL error: {}", count++);
	}

	/**
	 * this is the main idea about how it should be used in the app. using debug/trace for adding extra details.
	 */
	private void allLevelsToBe() {
		LOG.info(SEPARATOR);
		LOG.trace(CONFIDENTIAL, "my CONFIDENTIAL trace: {}", count++);
		LOG.debug(CONFIDENTIAL, "my CONFIDENTIAL debug: {}", count++);
		LOG.info("my safe info: {}", count++);
		LOG.warn("my safe warn: {}", count++);
		LOG.error("my safe error: {}", count++);
	}

}
