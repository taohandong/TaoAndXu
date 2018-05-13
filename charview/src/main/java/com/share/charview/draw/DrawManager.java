package com.share.charview.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.share.charview.animation.data.AnimationValue;
import com.share.charview.draw.data.Chart;

public class DrawManager {

	private DrawController controller;
	private Chart chart;

	public DrawManager(@NonNull Context context) {
		chart = new Chart();
		controller = new DrawController(context, chart);
	}

	public Chart chart() {
		return chart;
	}

	public void updateTitleWidth() {
		controller.updateTitleWidth();
	}

	public void draw(@NonNull Canvas canvas) {
		controller.draw(canvas);
	}

	public void setLineColor(){
		controller.setLinColor();
	}

	public void updateValue(@NonNull AnimationValue value) {
		controller.updateValue(value);
	}

	public void setLineColor1() {
		controller.setLineColor1();
	}
}
