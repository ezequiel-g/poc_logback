# poc_logback

Probe of concept created to check some capabilities related to logback.

* As Developer when I am running the application locally, I would like to:
    * get logs with full details in the IDE console.
    * get logs with full details in a log file.
    * be notified/highlighted when confidential data is being send to the logs
* As Developer when I deploy the application in a server, I would like to:
    * be able to enable extra logs details without needed changing the application source code.
    * be able to enable confidential data logging ad hoc to a separated log for troubleshooting
    so that I do not need to review the logs to remove confidential data later.
    I just want to remove the file used while troubleshooting.



to enable console log, it just required not to be null
enableConsole=true

to enable troubleshooting log,
enableTroubleshooting=true

# Links
* http://logback.qos.ch/manual/groovy.html
* http://logback.qos.ch/manual/filters.html
* https://www.wlangiewicz.com/2019/03/28/effective-way-of-using-conditional-expressions-in-logback/
* https://www.schibsted.pl/blog/logback-pattern-gdpr/
* https://stackoverflow.com/questions/4165558/best-practices-for-using-markers-in-slf4j-logback
* https://stackoverflow.com/questions/14209701/error-using-logback-marker-in-fileappender
* https://examples.javacodegeeks.com/enterprise-java/slf4j/slf4j-markers-example/
* https://owasp.org/www-project-security-logging/