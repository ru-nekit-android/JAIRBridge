package ru.nekit.ane
{
	import flash.events.StatusEvent;

	public interface ICommunicationable
	{
		
		function onStatus(status:StatusEvent):void;
		function onStartUp():void;
		function onPublish(item:PublishItem):void;
		function onError(error:String):void;
		function getPublishValue(item:PublishItem):Object;
		function dispatchStatusEvent(name:String, ... args):void
		function startUp(backgraund:Boolean = true, root:Boolean = true, newTask:Boolean = true, waitingResult:Boolean = false):void
		function get version():String;
		
	}
}