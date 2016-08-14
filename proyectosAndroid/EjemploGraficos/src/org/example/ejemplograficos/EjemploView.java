package org.example.ejemplograficos;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

public class EjemploView extends View {

	private ShapeDrawable miImagen;

	public EjemploView(Context context) {
		super(context);
		miImagen = new ShapeDrawable(new OvalShape());
		miImagen.getPaint().setColor(0xff0000ff);
	}
	
	@Override
	protected void onSizeChanged(int ancho, int alto, int anchoAnterior, int altoAnterior) {
		miImagen.setBounds( 0, 0, ancho, alto);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		miImagen.draw(canvas);
	}

}
