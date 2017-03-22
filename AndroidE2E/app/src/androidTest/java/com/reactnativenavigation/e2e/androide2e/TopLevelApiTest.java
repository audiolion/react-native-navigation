package com.reactnativenavigation.e2e.androide2e;

import android.support.test.uiautomator.By;

import org.junit.Ignore;
import org.junit.Test;

public class TopLevelApiTest extends BaseTest {

	@Test
	@Ignore
	public void switchToTabBasedApp() throws Exception {
		launchTheApp();
		assertMainShown();
		elementByText("SWITCH TO TAB BASED APP").click();
		assertExists(By.text("This is tab 1"));
		assertExists(By.text("Hello from a function!"));
	}

	@Test
	public void switchToTabsWithSideMenu() throws Exception {
		launchTheApp();
		assertMainShown();
		elementByText("SWITCH TO APP WITH SIDE MENUS").click();
		assertExists(By.textStartsWith("This is a side menu center screen tab 1"));
		swipeRight();
		assertExists(By.text("This is a left side menu screen"));
	}

	@Test
	@Ignore
	public void screenLifecycle() throws Exception {
		launchTheApp();
		assertMainShown();
		elementByText("PUSH LIFECYCLE SCREEN").click();
		elementByText("onStart!");
		elementByText("Push to test onStop").click();
		elementByText("Alert");
		elementByText("onStop").click();
	}

	@Test
	@Ignore
	public void unmountIsCalledOnPop() throws Exception {
		launchTheApp();
		assertMainShown();
		elementByText("Push lifecycle screen").click();
		elementByText("onStart!");
		elementByText("BACK").click();
		elementByText("onStop");
		elementByText("componentWillUnmount");
	}
}
