package
{
	
	import flash.events.StatusEvent;
	import flash.utils.setTimeout;
	
	import ru.nekit.ane.JAIRBridge;
	import ru.nekit.ane.PublishItem;
	
	public class JAIRMain extends JAIRBridge
	{
		
		public function JAIRMain()
		{
			super();
			setTimeout(function():void
			{
				startUp(true, true, true, true);
			}, 1);
		}
		
		override public function onStartUp():void
		{
			
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
		}
	}
}