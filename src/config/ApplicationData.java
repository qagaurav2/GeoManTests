package config;

import constants.Constants;
import utilities.FileOperations;

public class ApplicationData {

	FileOperations fileOperations = new FileOperations();
	Constants constants = new Constants();

	public String getBrowserType(){	
		return fileOperations.getValueFromPropertyFile(constants.CONFIG_WEB_FILE_PATH, "browserName");
	}

	public String getUrl(){
		return fileOperations.getValueFromPropertyFile(constants.CONFIG_WEB_FILE_PATH, "url");
	}
	
	public String getSlaveUrl() {
		return fileOperations.getValueFromPropertyFile(constants.CONFIG_WEB_FILE_PATH, "slavepageURL");
	}
	
	public String getGlobalAdminInsuranceUsername(){
		return fileOperations.getValueFromPropertyFile(constants.CONFIG_WEB_FILE_PATH, "globalAdminInsuranceUsername");
	}	
	
	public String getGlobalAdminInsurancePassword(){
		return fileOperations.getValueFromPropertyFile(constants.CONFIG_WEB_FILE_PATH, "globalAdminInsurancePassword");
	}
	
	public String getGlobalAdminTMUsername(){
		return fileOperations.getValueFromPropertyFile(constants.CONFIG_WEB_FILE_PATH, "globalAdminTMUsername");
	}	

	public String getGlobalAdminTMPassword(){
		return fileOperations.getValueFromPropertyFile(constants.CONFIG_WEB_FILE_PATH, "globalAdminTMPassword");
	}
	
	public String getHomePageURL(){
		return fileOperations.getValueFromPropertyFile(constants.CONFIG_WEB_FILE_PATH, "homepageURL");
	}

	public String getJiraUrl(){
		return fileOperations.getValueFromPropertyFile(constants.CONFIG_WEB_FILE_PATH, "jiraURL");
	}

	public String getJiraEmail(){
		return fileOperations.getValueFromPropertyFile(constants.CONFIG_WEB_FILE_PATH, "jiraEmail");
	}

	public String getJiraPassword(){
		return fileOperations.getValueFromPropertyFile(constants.CONFIG_WEB_FILE_PATH, "jiraPassword");
	}


}
