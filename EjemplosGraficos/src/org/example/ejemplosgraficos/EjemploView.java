package org.example.ejemplosgraficos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;

public class EjemploView extends View {
	// private Drawable miImagen;
	private ShapeDrawable miImagen;

	public EjemploView(Context context,AttributeSet attrs) {
		super(context,attrs);
		// Resources res=context.getResources();
		// miImagen=res.getDrawable(R.drawable.mimagen);
		// miImagen.setBounds(30,30,200,200);
		miImagen = new ShapeDrawable(new OvalShape());
		miImagen.
		miImagen.getPaint().setColor(0xff0000ff);
//		miImagen.setBounds(10, 10, 310, 60);
	}

	protected void onSizeChanged(int ancho,int alto,int ancho_anterior,int alto_anterior){
	miImagen.setBounds(0,0,ancho,alto);	
	}
	protected void onDraw(Canvas canvas) {
		// //Dibujo
		// Paint pincel=new Paint();
		// pincel.setColor(Color.BLUE);
		// pincel.setStrokeWidth(8);
		// pincel.setStyle(Style.STROKE);
		// canvas.drawCircle(150, 150, 100, pincel);
		// pincel.setColor(Color.argb(127,255,0,0));
		// canvas.drawCircle(150, 150, 100, pincel);

		// Path trazo=new Path();
		// trazo.addCircle(150,150,100,Direction.CCW);
		// canvas.drawColor(Color.WHITE);
		// Paint pincel=new Paint();
		// pincel.setColor(Color.BLUE);
		// pincel.setStrokeWidth(8);
		// pincel.setStyle(Style.STROKE);
		// canvas.drawPath(trazo,pincel);
		// pincel.setStrokeWidth(1);
		// pincel.setStyle(Style.FILL);
		// pincel.setTextSize(20);
		// pincel.setTypeface(Typeface.SANS_SERIF);
		// canvas.drawTextOnPath("oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo",
		// trazo, 0, 40, pincel);

		miImagen.draw(canvas);

	}
}
