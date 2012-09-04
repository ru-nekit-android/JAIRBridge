package
{
	
	import flash.display.BitmapData;
	import flash.events.StatusEvent;
	import flash.utils.ByteArray;
	import flash.utils.setTimeout;
	
	import ru.nekit.ane.JAIRBridge;
	import ru.nekit.ane.P2PConnectionEntry;
	import ru.nekit.ane.PublishItem;
	
	public class JAIRMain extends JAIRBridge
	{
		
		public function JAIRMain()
		{
			super();
			setTimeout(function():void
			{
				startUp(true, true, false, false);
			}, 1);
		}
		
		override public function onStartUp():void
		{
			P2P.instance.context = this;
			P2PConnectionEntry.p2pConnection = P2P.instance;
		}
		
		override public function onStatus(event:StatusEvent):void
		{	
			trace(event.code);
		}
		
		override public function onError(error:String):void
		{
			trace(error);
		}
		
		override public function onPublish(item:PublishItem):void
		{
			if( item.isBitmapData )
			{
				var name:String = item.name;
				var data:BitmapData = item.value as BitmapData;
				var bytes:ByteArray = new ByteArray();
				bytes.writeUnsignedInt(data.width);
				bytes.writeUnsignedInt(data.height);
				bytes.writeBytes(data.getPixels(data.rect));
				P2P.instance.shareWith(null, bytes, name);
				data.dispose();
			}
		}
	}
}