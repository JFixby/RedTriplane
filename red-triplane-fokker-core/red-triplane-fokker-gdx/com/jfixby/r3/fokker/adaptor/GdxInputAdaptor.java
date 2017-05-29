
package com.jfixby.r3.fokker.adaptor;

import java.util.ArrayList;
import java.util.Iterator;

import com.jfixby.r3.fokker.api.InputEvent;
import com.jfixby.r3.fokker.api.InputQueue;
import com.jfixby.scarabei.adopted.gdx.input.GdxKeys;
import com.jfixby.scarabei.adopted.gdx.input.GdxMouseButtons;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Pool;
import com.jfixby.scarabei.api.collections.PoolElementsSpawner;

public class GdxInputAdaptor implements com.badlogic.gdx.InputProcessor, InputQueue {

	private GdxAdaptor master;
	private ArrayList<InputEvent> public_queue;
	private ArrayList<GdxInputEvent> private_queue;
	private Pool<GdxInputEvent> pool;
	boolean disabled = true;

	public void enable () {
		this.flush();
		this.disabled = false;
	}

	public void disable () {
		this.disabled = true;
		this.flush();
	}

	public void deploy () {
		this.public_queue = new ArrayList<InputEvent>();
		this.private_queue = new ArrayList<GdxInputEvent>();
		this.pool = Collections.newPool(spawner);
		enable();
	}

	public GdxInputAdaptor (GdxAdaptor gdxAdaptor) {
		this.master = gdxAdaptor;
	}

	@Override
	public boolean keyDown (int keycode) {
		if (disabled) {
			return false;
		}
		GdxInputEvent new_event = this.pool.obtain();
		new_event.reset();
		new_event.keyDown(GdxKeys.resolveGdxKeyCode(keycode));
		enqueue(new_event);
		return true;
	}

	@Override
	public boolean keyUp (int keycode) {
		if (disabled) {
			return false;
		}
		GdxInputEvent new_event = this.pool.obtain();
		new_event.reset();
		new_event.keyUp(GdxKeys.resolveGdxKeyCode(keycode));
		enqueue(new_event);
		return true;
	}

	@Override
	public boolean keyTyped (char character) {
		if (disabled) {
			return false;
		}
		GdxInputEvent new_event = this.pool.obtain();
		new_event.reset();
		new_event.keyTyped(character);
		enqueue(new_event);
		return true;
	}

	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		if (disabled) {
			return false;
		}
		GdxInputEvent new_event = this.pool.obtain();
		new_event.reset();
		new_event.touchDown(screenX, screenY, pointer, GdxMouseButtons.resolveGdxMouseButtonCode(button));
		enqueue(new_event);
		return true;

	}

	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button) {
		if (disabled) {
			return false;
		}
		GdxInputEvent new_event = this.pool.obtain();
		new_event.reset();
		new_event.touchUp(screenX, screenY, pointer, GdxMouseButtons.resolveGdxMouseButtonCode(button));
		enqueue(new_event);
		return true;
	}

	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer) {
		if (disabled) {
			return false;
		}
		GdxInputEvent new_event = this.pool.obtain();
		new_event.reset();
		new_event.touchDragged(screenX, screenY, pointer);
		enqueue(new_event);
		return true;
	}

	@Override
	public boolean mouseMoved (int screenX, int screenY) {
		if (disabled) {
			return false;
		}
		GdxInputEvent new_event = this.pool.obtain();
		new_event.reset();
		new_event.mouseMoved(screenX, screenY);
		enqueue(new_event);
		return true;
	}

	@Override
	public boolean scrolled (int amount) {
		if (disabled) {
			return false;
		}
		GdxInputEvent new_event = this.pool.obtain();
		new_event.reset();
		new_event.scrolled(amount);
		enqueue(new_event);
		return true;
	}

	final PoolElementsSpawner<GdxInputEvent> spawner = new PoolElementsSpawner<GdxInputEvent>() {

		@Override
		public GdxInputEvent spawnNewInstance () {
			return new GdxInputEvent();
		}

	};

	private void enqueue (GdxInputEvent new_event) {
		this.private_queue.add(new_event);
		this.public_queue.add(new_event);
	}

	int i;

	public void flush () {
		for (i = 0; i < private_queue.size(); i++) {
			GdxInputEvent t = private_queue.get(i);
			this.pool.free(t);
		}
		// this.pool.freeAll(this.private_queue);
		this.public_queue.clear();
		this.private_queue.clear();
	}

	@Override
	public int size () {
		return this.public_queue.size();
	}

	@Override
	public Iterator<InputEvent> getIterator () {
		return this.public_queue.iterator();
	}

}
