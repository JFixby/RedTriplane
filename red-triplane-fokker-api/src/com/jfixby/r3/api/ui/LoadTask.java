package com.jfixby.r3.api.ui;

import com.jfixby.scarabei.api.taskman.TaskProgress;

public interface LoadTask {

	TaskProgress getProgress();

	void launch();

}
