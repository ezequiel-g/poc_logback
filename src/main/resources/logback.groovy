import ch.qos.logback.classic.boolex.OnMarkerEvaluator
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.ThresholdFilter
import ch.qos.logback.core.filter.EvaluatorFilter

import static ch.qos.logback.classic.Level.*
import static ch.qos.logback.core.spi.FilterReply.*

final APP = "myAppGroovy"
final LOG_PATH = "target/"

final APP_LOG = LOG_PATH + APP
final APP_LOG_TROUBLESHOOTING = APP_LOG + "-troubleshooting"

// date details in logs file
final FILE_PATTERN = "%date %-5level [%thread] %logger{10}.%M:%L -%marker %msg%n"
// date details not needed in console
final CONSOLE_PATTERN = "%-5level [%thread] %logger{10}.%M:%L -%marker %msg%n"

// log level config
final APP_LOG_LEVEL = TRACE
final ROOT_LOG_LEVEL = WARN

// log level high environments
final HIGH_ENV_LEVEL = INFO

// rollover log config ENV dependent
def LOG_FILE_MAX_SIZE = "1MB"
def LOG_DAILY_MAX_HISTORY = 5
def LOG_TOTAL_MAX_SIZE = "2MB"

final FILE_APPENDER = "FILE-ROLLOVER"
appender(FILE_APPENDER, RollingFileAppender) {
    file = APP_LOG + ".log"
    // make sure only INFO level is being send to rolling appender
    filter(ThresholdFilter) {
        level = HIGH_ENV_LEVEL
    }
    // make sure marker confidential is not being sent to rolling appender
    filter(EvaluatorFilter) {
        evaluator(OnMarkerEvaluator) {
            marker = "CONFIDENTIAL"
        }
        onMatch = DENY
        onMismatch = NEUTRAL
    }
    rollingPolicy(SizeAndTimeBasedRollingPolicy) {
        fileNamePattern = APP_LOG + ".%d{yyyy-MM-dd}-%i.log.gz"
        maxFileSize = LOG_FILE_MAX_SIZE
        maxHistory = LOG_DAILY_MAX_HISTORY
        totalSizeCap = LOG_TOTAL_MAX_SIZE
    }
    encoder(PatternLayoutEncoder) {
        pattern = FILE_PATTERN
    }
}

final APPENDER_LIST = [FILE_APPENDER]

if (Objects.nonNull(System.getenv("enableTroubleshooting"))) {
    final FILE_TROUBLESHOOTING = "TROUBLESHOOTING"
    appender(FILE_TROUBLESHOOTING, FileAppender) {
        file = APP_LOG_TROUBLESHOOTING + ".log"
        append = false
        encoder(PatternLayoutEncoder) {
            pattern = FILE_PATTERN
        }
    }

    APPENDER_LIST.add(FILE_TROUBLESHOOTING)
}

if (Objects.nonNull(System.getenv("enableConsole"))) {
    final CONSOLE_APPENDER = "CONSOLE"
    appender(CONSOLE_APPENDER, ConsoleAppender) {
        encoder(PatternLayoutEncoder) {
            pattern = CONSOLE_PATTERN
        }
    }

    APPENDER_LIST.add(CONSOLE_APPENDER)
}

logger("eze.poc.logback", APP_LOG_LEVEL)

root(ROOT_LOG_LEVEL, APPENDER_LIST)