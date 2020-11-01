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

	private static final String SEPARATOR = "########### : {}";
	private static final String FMT_MISC_MODEL = "miscModel: {}";
	private static final String FMT_UNSAFE_CONFIDENTIAL = "confidentialModel: {}";
	private static final String FMT_SAFE_CONFIDENTIAL_ATTR = "safe confidential >>> data id: {}, countryId: {}";

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

		allLevelsMissedMarker();
	}

	/**
	 * this logs should be shown base in the root level configured
	 */
	private void allLevels() {
		LOG.info(SEPARATOR, "misc models does not include private data");

		LOG.trace(FMT_MISC_MODEL, MODEL_MISC);

		LOG.debug(FMT_MISC_MODEL, MODEL_MISC);

		LOG.info(FMT_MISC_MODEL, MODEL_MISC);

		LOG.warn(FMT_MISC_MODEL, MODEL_MISC);

		LOG.error(FMT_MISC_MODEL, MODEL_MISC);
	}

	/**
	 * this logs should only be shown in troubleshooting logs.
	 */
	private void allLevelsConfidential() {
		LOG.info(SEPARATOR, "safe confidential, allow to log confidential model attributes which are safe to shown");
		LOG.info(SEPARATOR, "confidential models include private data, need to include CONFIDENTIAL marker.");

		LOG.trace(FMT_SAFE_CONFIDENTIAL_ATTR, MODEL_CONFIDENTIAL.getId(), MODEL_CONFIDENTIAL.getCountryId());
		LOG.trace(CONFIDENTIAL, FMT_UNSAFE_CONFIDENTIAL, MODEL_CONFIDENTIAL);

		LOG.debug(FMT_SAFE_CONFIDENTIAL_ATTR, MODEL_CONFIDENTIAL.getId(), MODEL_CONFIDENTIAL.getCountryId());
		LOG.debug(CONFIDENTIAL, FMT_UNSAFE_CONFIDENTIAL, MODEL_CONFIDENTIAL);

		LOG.info(FMT_SAFE_CONFIDENTIAL_ATTR, MODEL_CONFIDENTIAL.getId(), MODEL_CONFIDENTIAL.getCountryId());
		LOG.info(CONFIDENTIAL, FMT_UNSAFE_CONFIDENTIAL, MODEL_CONFIDENTIAL);

		LOG.warn(FMT_SAFE_CONFIDENTIAL_ATTR, MODEL_CONFIDENTIAL.getId(), MODEL_CONFIDENTIAL.getCountryId());
		LOG.warn(CONFIDENTIAL, FMT_UNSAFE_CONFIDENTIAL, MODEL_CONFIDENTIAL);

		LOG.error(FMT_SAFE_CONFIDENTIAL_ATTR, MODEL_CONFIDENTIAL.getId(), MODEL_CONFIDENTIAL.getCountryId());
		LOG.error(CONFIDENTIAL, FMT_UNSAFE_CONFIDENTIAL, MODEL_CONFIDENTIAL);
	}

	private void allLevelsMissedMarker() {
		LOG.info(SEPARATOR, "confidential data missing CONFIDENTIAL marker. Wrong usage logging private attributes ' ssn=', ' address='");
		LOG.trace(FMT_UNSAFE_CONFIDENTIAL, MODEL_CONFIDENTIAL);

		LOG.debug(FMT_UNSAFE_CONFIDENTIAL, MODEL_CONFIDENTIAL);

		LOG.info(FMT_UNSAFE_CONFIDENTIAL, MODEL_CONFIDENTIAL);

		LOG.warn(FMT_UNSAFE_CONFIDENTIAL, MODEL_CONFIDENTIAL);

		LOG.error(FMT_UNSAFE_CONFIDENTIAL, MODEL_CONFIDENTIAL);
	}

}
