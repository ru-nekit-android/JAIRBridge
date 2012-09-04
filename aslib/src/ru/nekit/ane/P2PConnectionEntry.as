package ru.nekit.ane 
{
	
	public class P2PConnectionEntry
	{
		
		public static var p2pConnection:IP2P;
		
		public function P2PConnectionEntry()
		{
		}
		
		public function init():void
		{
			trace("P2P connection created.");
		}
		
		public function connect(value:String):void
		{
			p2pConnection.connect(value);
		}
		
		public function disconnect():void
		{
			p2pConnection.disconnect();
		}
		
		public function setCurrentClient(value:String):void
		{
			p2pConnection.setCurrentClient(value);
		}
	}
}