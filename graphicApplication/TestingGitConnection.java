package graphicApplication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TestingGitConnection {

	private static Logger demoLogger = LogManager.getLogger(TestingGitConnection.class.getName());
	public static void main(String[] args) {
		
		demoLogger.info("Click successfull!");
		demoLogger.error("DB Conection failed");
		
	}
}
