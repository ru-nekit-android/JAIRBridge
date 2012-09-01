package ru.nekit.android;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.adobe.fre.FREASErrorException;
import com.adobe.fre.FREBitmapData;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class GetPublishValue implements FREFunction {

	private JAIRBridgeContext context;

	@Override
	public FREObject call(FREContext _context, FREObject[] args) 
	{
		String id = null;
		try {
			id = args[0].getAsString();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FRETypeMismatchException e) {
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		}
		String type = id.split("\\.")[0];
		context = (JAIRBridgeContext)_context;
		if( id != null )
		{
			if( context.getPublishMap().containsKey(id) )
			{
				try {
					if( type.equals("int") )
					{
						return FREObject.newObject(((Integer)context.getPublishMap().remove(id)).intValue());
					}
					if( type.equals("boolean") )
					{
						return FREObject.newObject(((Boolean)context.getPublishMap().remove(id)).booleanValue());
					}
					if( type.equals("string") )
					{
						return FREObject.newObject((String)context.getPublishMap().remove(id));
					}
					if( type.equals("double") )
					{
						return FREObject.newObject(((Double)context.getPublishMap().remove(id)).doubleValue());
					}
					if( type.equals("view::bitmapData") )
					{
						View view = (View)context.getPublishMap().remove(id);
						Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
						Canvas canvas = new Canvas(bitmap);
						Drawable bgDrawable = view.getBackground();
						if (bgDrawable!=null) 
							bgDrawable.draw(canvas);
						else 
							canvas.drawColor(Color.WHITE);
						view.draw(canvas);
						Byte[] fillcolor = {0, 0, 0, 0};
						FREBitmapData data = null;
						try {
							data = FREBitmapData.newBitmapData(view.getWidth(), view.getHeight(), true, fillcolor);
							data.acquire();
							bitmap.copyPixelsToBuffer(data.getBits());
							data.release();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (FREASErrorException e) {
							e.printStackTrace();
						} catch (IllegalStateException e) {
							e.printStackTrace();
						} catch (FREInvalidObjectException e) {
							e.printStackTrace();
						}
						return data;
					}
				} catch (FREWrongThreadException e) {
					e.printStackTrace();
				}
			}
			else
			{
				context.dispatchErrorEvent("No pulished value by id: " + id);
			}
		}
		return null;
	}
}