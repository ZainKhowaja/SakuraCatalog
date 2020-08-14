package com.app.sakura.enums;

public enum SakuraScreen {
	LOGIN("Login.fxml"), MAIN_SCREEN("MainView.fxml");

	private String screenName;

	SakuraScreen(String screenName) {
		this.screenName = screenName;
	}
	
	public String getScreenName() {
		return this.screenName;
	}
}
