package
{
	
	import flash.desktop.NativeApplication;
	import flash.desktop.SystemIdleMode;
	import flash.display.BitmapData;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.events.StatusEvent;
	import flash.net.NetGroupReplicationStrategy;
	import flash.utils.ByteArray;
	import flash.utils.setTimeout;
	
	import ru.nekit.ane.JAIRBridge;
	import ru.nekit.ane.P2PConnectionEntry;
	import ru.nekit.ane.PublishItem;
	
	public class JAIRMain extends JAIRBridge
	{
		
		private var activated:Boolean;
		private var application:NativeApplication;
		
		public function JAIRMain()
		{
			super();
			activated = true;
			application = NativeApplication.nativeApplication;
			application.systemIdleMode = SystemIdleMode.KEEP_AWAKE;
			application.autoExit = false;
			application.executeInBackground = true;
			setTimeout(function():void
			{
				startUp(true, true, true, false);
			}, 1);
			stage.addEventListener(MouseEvent.CLICK ,clickHandler);
			addEventListener(Event.ACTIVATE, activateHandler);
			addEventListener(Event.DEACTIVATE, deactivateHandler);
		}
		
		private function deactivateHandler(event:Event):void
		{
			activated = false;
		}
		
		private function activateHandler(event:Event):void
		{
			if( !activated )
			{
				startUp(false, true, true, false);	
			}
			trace(0);		
		}
		
		private function clickHandler(event:MouseEvent):void
		{
			trace(0);
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