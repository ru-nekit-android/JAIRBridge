package ru.nekit.ane
{
	import flash.events.StatusEvent;

	public interface IJAIRBridge
	{
		
		function startUp(backgraund:Boolean = true, root:Boolean = true, newTask:Boolean = true, waitingResult:Boolean = false):void;
		function destroy():void;
		function moveToBack(nonRoot:Boolean = false):void;
		function memoryReport():String;
		function get version():String;
		
		function test():void;
		
		function onStatus(status:StatusEvent):void;
		function onStartUp():void;
		function onPublish(item:PublishItem):void;
		function onError(error:String):void;
		
		function getPublishValue(item:PublishItem):Object;
		function dispatchStatusEvent(name:String, ... args):void;
		
	}
}