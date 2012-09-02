package ru.nekit.ane 
{
	
	import com.projectcocoon.p2p.LocalNetworkDiscovery;
	import com.projectcocoon.p2p.events.ClientEvent;
	import com.projectcocoon.p2p.events.GroupEvent;
	
	public class P2PConnectionEntry
	{
		
		public static var CONNECT:String = "connectP2P";
		
		public static var context:JAIRBridge;
		
		private static var p2p:LocalNetworkDiscovery;
		private var connected:Boolean = false;
		private var groupSuffix:String;
		
		public function P2PConnectionEntry()
		{
		}
		
		public function init():void
		{
			trace("P2P connection created.");
		}
		
		public function connect(value:String):void
		{
			groupSuffix = value;
			if( groupSuffix == null )
			{
				groupSuffix = "none";
			}
			if( !p2p )
			{
				p2p = new LocalNetworkDiscovery;
				p2p.autoConnect = false;
			}
			else
			{
				p2p.close();
			}
			connected = !connected;
			if( connected )
			{
				p2p.clientName = "mobile";
				p2p.key = "fa34a2a9cd8864b67cdb154d-ac8a93ff4df0";
				p2p.useCirrus = true;
				var groupName:String = "ru.nekit.p2p.group_" + groupSuffix;
				p2p.groupName = groupName;
				trace("P2P channel set group name: " + groupName);
				p2p.addEventListener(ClientEvent.CLIENT_ADDED, clientAddedHandler);
				p2p.addEventListener(ClientEvent.CLIENT_UPDATE, clientUpdateHandler);
				p2p.addEventListener(ClientEvent.CLIENT_REMOVED, clientRemoveHandler);
				p2p.addEventListener(GroupEvent.GROUP_CONNECTED, groupConnectedHandler);
				p2p.addEventListener(GroupEvent.GROUP_CLOSED, groupClosedHandler);
				p2p.connect();
				trace("Local P2P client init connection");	
			}
			else
			{
				trace("Local P2P client disconnected");
			}
		}
		
		private function groupConnectedHandler(event:GroupEvent):void
		{
			//main event
			context.dispatchP2PStatusEvent(CONNECT);
			trace("P2P client connect to group");
		}
		
		private function clientUpdateHandler(event:ClientEvent):void
		{
			trace("P2P client update: " + event.client.clientName);
		}
		
		private function clientRemoveHandler(event:ClientEvent):void
		{
			trace("P2P client removed: " + event.client.clientName);
		}
		
		private function groupClosedHandler(event:GroupEvent):void
		{
			trace("P2P group cloused...");
		}
		
		private function clientAddedHandler(event:ClientEvent):void
		{
			if( event.client.isLocal )
			{
				trace("Local P2P client added: " + event.client.peerID);
			}else{
				trace("P2P client added: " + event.client.peerID);
			}
		}
	}
}