package
{
	
	import flash.desktop.NativeApplication;
	import flash.desktop.SystemIdleMode;
	import flash.display.BitmapData;
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.events.StatusEvent;
	import flash.ui.Keyboard;
	import flash.utils.ByteArray;
	
	import ru.nekit.ane.JAIRBridge;
	import ru.nekit.ane.P2PConnectionEntry;
	import ru.nekit.ane.PublishItem;
	
	public class JAIRMain extends JAIRBridge
	{
		
		private var started:Boolean;
		private var activated:Boolean;
		private var application:NativeApplication;
		
		public function JAIRMain()
		{
			super();
			activated = false;
			started = false;
			visible = false;
			application = NativeApplication.nativeApplication;
			application.systemIdleMode = SystemIdleMode.KEEP_AWAKE;
			application.autoExit = true;
			application.executeInBackground = true;
			stage.addEventListener(MouseEvent.CLICK, clickHandler);
			stage.addEventListener(KeyboardEvent.KEY_DOWN, keyDownHandler);
			if( !started )
			{	
				startUp(false, false, true, true);
				started = true;
				stage.frameRate = 0.01;
			}
		}
		
		private function keyDownHandler(event:KeyboardEvent):void
		{
			if( event.keyCode == Keyboard.SEARCH )
			{
				test();
				restore();
			}
		}
		
		private function deactivateHandler(event:Event):void
		{
			activated = false;
			trace("deactivate");
		}
		
		private function activateHandler(event:Event):void
		{
			trace("activate");	
			if( !activated )
			{
				restore();
				//moveToBack();
				activated = true;
			}	
		}
		
		private function clickHandler(event:MouseEvent):void
		{
			
		}
		
		override public function onStartUp():void
		{
			addEventListener(Event.ACTIVATE, activateHandler);
			addEventListener(Event.DEACTIVATE, deactivateHandler);
			P2P.instance.context = this;
			P2PConnectionEntry.p2pConnection = P2P.instance;
		}
		
		override public function onStatus(event:StatusEvent):void
		{	
			if( event.code == "ru.nekit.check" )
			{
				test();	
			}
			if( event.code == "onBackPressed" )
			{
				moveToBack();	
			}
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