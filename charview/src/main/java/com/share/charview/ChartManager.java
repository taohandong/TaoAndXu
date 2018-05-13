package com.share.charview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.share.charview.animation.AnimationManager;
import com.share.charview.animation.data.AnimationValue;
import com.share.charview.draw.DrawManager;
import com.share.charview.draw.data.Chart;

public class ChartManager implements AnimationManager.AnimationListener {

	private DrawManager drawManager;
	private AnimationManager animationManager;
	private AnimationListener listener;

	public interface AnimationListener {

		void onAnimationUpdated();
	}


	public ChartManager(@NonNull Context context, @Nullable AnimationListener listener) {
		this.drawManager = new DrawManager(context);
		this.animationManager = new AnimationManager(drawManager.chart(), this);
		this.listener = listener;
	}

	public Chart chart() {
		return drawManager.chart();
	}

	public DrawManager drawer() {
		return drawManager;
	}

	public void animate() {
		if (!drawManager.chart().getDrawData().isEmpty()) {
			animationManager.animate();
		}
	}

	@Override
	public void onAnimationUpdated(@NonNull AnimationValue value) {
		drawManager.updateValue(value);
		if (listener != null) {
			listener.onAnimationUpdated();
		}
	}
}
