package ru.nekit.ane
{
	
	import flash.display.Sprite;
	import flash.events.StatusEvent;
	import flash.external.ExtensionContext;
	
	public  class JAIRBridge extends Sprite implements ICommunicationable
	{
		
		public static const LEVEL_ERROR:String 		= "ru.nekit.error";
		public static const LEVEL_SERVICE:String 	= "ru.nekit.service";
		public static const LEVEL_PUBLISH:String 	= "ru.nekit.publish";		
		
		public static const STARTUP:String = "startup";
		
		private static var _instance:JAIRBridge;
		private static var _instanceAllow:Boolean;
		private static var context:ExtensionContext;
		
		public function JAIRBridge()
		{
			context = ExtensionContext.createExtensionContext("ru.nekit.android.JAIRBridge", null);
			context.addEventListener(StatusEvent.STATUS, _onStatus); 
			PublishItem.context = this;
			super();
		}	
		
		private function _onStatus(event:StatusEvent):void
		{
			onStatus(event);
			var level:String = event.level;
			var code:String = event.code;
			
			switch(level)
			{
				case LEVEL_SERVICE:
				{
					
					if( code == STARTUP )
					{
						onStartUp();
					}
					
					break;
				}
					
				case LEVEL_PUBLISH:
				{
					
					onPublish(PublishItem.create(code));
					
					break;
				}
					
				case LEVEL_ERROR:
				{
					
					onError(code);
					
					break;
				}
					
				default:
					break;
			}
		}
		
		
		public function onStatus(event:StatusEvent):void
		{	
		}
		
		public function onStartUp():void
		{	
		}
		
		public function onPublish(item:PublishItem):void
		{
		}
		
		public function onError(error:String):void
		{
		}
		
		public function test():void
		{
			context.call("test");
		}
		
		public function get version():String
		{
			return context.call("version") as String;
		}
		
		public function dispatchStatusEvent(name:String, ... args):void
		{
			context.call.apply(null, ["dispatchStatusEvent", name].concat(args));
		}
		
		public function getPublishValue(item:PublishItem):Object
		{
			return context.call("getPublishValue", item.id);
		}
		
		public function startUp(backgraund:Boolean = true):void
		{
			context.call("startUp", backgraund);
		}
	}
}