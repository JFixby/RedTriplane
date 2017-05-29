
package com.jfixby.r3.fokker.adaptor;

import com.jfixby.r3.api.Screen;
import com.jfixby.r3.api.ScreenDimentionsChecker;

public class RedScreenDimentionsChecker implements ScreenDimentionsChecker {

	long previous_check = -1;

	@Override
	public String toString () {
		return "RedScreenDimentionsChecker [previous_check=" + previous_check + "]";
	}

	@Override
	public boolean screenDimentionsHaveChanged () {
		return Screen.getLastUpdateCycleNumber() != this.previous_check;
	}

	@Override
	public void okGotIt () {
		this.previous_check = Screen.getLastUpdateCycleNumber();
	}

}
