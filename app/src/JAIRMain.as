package
{
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.events.StatusEvent;
	import flash.utils.setTimeout;
	
	import ru.nekit.ane.JAIRBridge;
	import ru.nekit.ane.PublishItem;
	
	public class JAIRMain extends JAIRBridge
	{
		
		public function JAIRMain()
		{
			super();
			/*setTimeout(function():void
			{
				startUp(false);
			}, 4900);*/
		}
		
		override public function onStartUp():void
		{
			var version:String = this.version;
			trace(version);
		}
		
		override public function onStatus(event:StatusEvent):void
		{
			test();
		}
		
		override public function onError(error:String):void
		{
			trace(error);
		}
		
		override public function onPublish(item:PublishItem):void
		{
			if( item.isBitmapData )
			{
				var bmp:Bitmap = new Bitmap(item.value as BitmapData);
				addChild(bmp);
			}
			else
			{
				var value:String = item.value as String;
				trace(value);
			}
		}
	}
}