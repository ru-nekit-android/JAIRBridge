package ru.nekit.ane
{
	import flash.display.BitmapData;
	import flash.display.BitmapDataChannel;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	
	public class PublishItem
	{
		
		public static var context:IJAIRBridge;
		public var name:String;
		public var type:String;
		private var _value:Object;
		
		public function PublishItem(name:String, type:String)
		{
			this.name 	= name;
			this.type 	= type;
			this._value = null;
		}
		
		public function get id():String
		{
			return  type + "." + name;
		}
		
		public static function create(value:String):PublishItem
		{
			var valueArray:Array = value.split(".");
			return new PublishItem(valueArray[1], valueArray[0]);
		}
		
		public function get isBitmapData():Boolean
		{
			return type.indexOf("bitmapData") == 0;
		}
		
		public function destroy():void
		{
			if( isBitmapData  )
			{
				BitmapData(_value).dispose();
				_value = null;
			}
		}
		
		public function get value():Object
		{
			if( _value )
			{
				return _value;
			}
			if( isBitmapData  )
			{
				var zeroPoint:Point = new Point(0, 0);
				var bitmapDataSource:BitmapData = context.getPublishValue(this) as BitmapData;
				var bitmapDataSourceRect:Rectangle = bitmapDataSource.rect;
				var bitmapData:BitmapData = new BitmapData(bitmapDataSource.width, bitmapDataSource.height, true);
				bitmapData.copyChannel(bitmapDataSource, bitmapDataSourceRect, zeroPoint, BitmapDataChannel.RED, BitmapDataChannel.BLUE);
				bitmapData.copyChannel(bitmapDataSource, bitmapDataSourceRect, zeroPoint, BitmapDataChannel.GREEN, BitmapDataChannel.GREEN);
				bitmapData.copyChannel(bitmapDataSource, bitmapDataSourceRect, zeroPoint, BitmapDataChannel.BLUE, BitmapDataChannel.RED);
				bitmapData.copyChannel(bitmapDataSource, bitmapDataSourceRect, zeroPoint, BitmapDataChannel.ALPHA, BitmapDataChannel.ALPHA);
				_value = bitmapData;
			}
			else
			{
				_value = context.getPublishValue(this);
			}
			return _value
		}
	}
}