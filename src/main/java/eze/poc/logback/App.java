package eze.poc.logback;

import eze.poc.logback.model.ModelConfidential;
import eze.poc.logback.model.ModelMisc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class App {

	private static final Marker CONFIDENTIAL = MarkerFactory.getMarker("CONFIDENTIAL");
	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	private static final String SEPARATOR = "##################################################";

	private static final ModelConfidential MODEL_CONFIDENTIAL = new ModelConfidential();
	private static final ModelMisc MODEL_MISC = new ModelMisc();

	static {
		MODEL_CONFIDENTIAL.setId(123L);
		MODEL_CONFIDENTIAL.setAddress(" Any Street ");
		MODEL_CONFIDENTIAL.setCountryId(" Any Country Id ");
		MODEL_CONFIDENTIAL.setSsn(" Any Security Number ");
		MODEL_CONFIDENTIAL.setOtherField(" Any Other Field ");

		MODEL_MISC.setKey(321);
		MODEL_MISC.setValue(" Any Value ");
	}

	public static void main(final String[] args) {
		final App app = new App();
		app.run();
	}

	public void run() {
		LOG.info("enableConsole: {}", System.getenv("enableConsole"));
		LOG.info("enableTroubleshooting: {}", System.getenv("enableTroubleshooting"));

		allLevels();

		allLevelsConfidential();

		allLevelsToBe();

		allLevelsHighlightConfidentials();
	}

	/**
	 * this logs should be shown base in the root level configured
	 */
	private void allLevels() {
		LOG.info(SEPARATOR);
		LOG.trace("my trace: {}", MODEL_MISC);
		LOG.debug("my debug: {}", MODEL_MISC);
		LOG.info("my info: {}", MODEL_MISC);
		LOG.warn("my warn: {}", MODEL_MISC);
		LOG.error("my error: {}", MODEL_MISC);
	}

	/**
	 * this logs should only be shown in troubleshooting logs.
	 */
	private void allLevelsConfidential() {
		LOG.info(SEPARATOR);
		LOG.info("my safe log id: {}, countryId: {}", MODEL_CONFIDENTIAL.getId(), MODEL_CONFIDENTIAL.getCountryId());
		LOG.trace(CONFIDENTIAL, "---> trace: {}", MODEL_CONFIDENTIAL);
		LOG.info("my safe log id: {}, countryId: {}", MODEL_CONFIDENTIAL.getId(), MODEL_CONFIDENTIAL.getCountryId());
		LOG.debug(CONFIDENTIAL, "---> debug: {}", MODEL_CONFIDENTIAL);
		LOG.info("my safe log id: {}, countryId: {}", MODEL_CONFIDENTIAL.getId(), MODEL_CONFIDENTIAL.getCountryId());
		LOG.info(CONFIDENTIAL, "---> info: {}", MODEL_CONFIDENTIAL);
		LOG.info("my safe log id: {}, countryId: {}", MODEL_CONFIDENTIAL.getId(), MODEL_CONFIDENTIAL.getCountryId());
		LOG.warn(CONFIDENTIAL, "---> warn: {}", MODEL_CONFIDENTIAL);
		LOG.info("my safe log id: {}, countryId: {}", MODEL_CONFIDENTIAL.getId(), MODEL_CONFIDENTIAL.getCountryId());
		LOG.error(CONFIDENTIAL, "---> error: {}", MODEL_CONFIDENTIAL);
	}

	/**
	 * this is the main idea about how it should be used in the app. using debug/trace for adding extra details.
	 */
	private void allLevelsToBe() {
		LOG.info(SEPARATOR);
		LOG.info("my safe log id: {}, countryId: {}", MODEL_CONFIDENTIAL.getId(), MODEL_CONFIDENTIAL.getCountryId());
		LOG.trace(CONFIDENTIAL, "---> trace: {}", MODEL_CONFIDENTIAL);
		LOG.info("my safe log id: {}, countryId: {}", MODEL_CONFIDENTIAL.getId(), MODEL_CONFIDENTIAL.getCountryId());
		LOG.debug(CONFIDENTIAL, "---> debug: {}", MODEL_CONFIDENTIAL);
		LOG.info("my safe info: {}", MODEL_MISC);
		LOG.warn("my safe warn: {}", MODEL_MISC);
		LOG.error("my safe error: {}", MODEL_MISC);
	}


	private void allLevelsHighlightConfidentials() {
		LOG.info(SEPARATOR);
		// TODO THIS SHOULD BE HIGHLIGHTED IN THE LOGS.
		LOG.trace("---> trace: {}", MODEL_CONFIDENTIAL);
		LOG.debug("---> debug: {}", MODEL_CONFIDENTIAL);
		LOG.info("---> info: {}", MODEL_CONFIDENTIAL);
		LOG.warn("---> warn: {}", MODEL_CONFIDENTIAL);
		LOG.error( "---> error: {}", MODEL_CONFIDENTIAL);
	}

}
