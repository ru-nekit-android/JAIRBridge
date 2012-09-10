package 
{
	
	import com.projectcocoon.p2p.LocalNetworkDiscovery;
	import com.projectcocoon.p2p.events.ClientEvent;
	import com.projectcocoon.p2p.events.GroupEvent;
	import com.projectcocoon.p2p.vo.ClientVO;
	
	import flash.errors.IllegalOperationError;
	import flash.utils.ByteArray;
	
	import ru.nekit.ane.IP2P;
	import ru.nekit.ane.JAIRBridge;
	
	public class P2P implements IP2P
	{
		
		public static var CONNECT_TO_GROUP:String = "connect_to_group";
		public static var CLIENT_CONNECT:String = "connect_client";
		
		private static var _instance:P2P;
		private static var _instanceAllow:Boolean;
		
		public  var context:JAIRBridge;
		
		private  var p2p:LocalNetworkDiscovery;
		private  var connected:Boolean = false;
		private  var groupSuffix:String;
		private  var currentClient:ClientVO;
		
		public static function get instance():P2P
		{
			if( !_instance )
			{
				_instanceAllow 	= true;
				_instance		= new P2P;
				_instanceAllow	= false;
			}
			return _instance;
		}
		
		public function P2P()
		{
			if( _instanceAllow )
			{
				
			}
			else
			{
				throw new IllegalOperationError("You must use P2P.instance.");
			}
		}
		
		public function shareWith(client:ClientVO, data:ByteArray, name:String):void
		{
			if( client == null )
			{
				client = currentClient;
			}
			p2p.shareWithClient(data, client.groupID, name);
		}
		
		public function setCurrentClient(value:String):void
		{
			for each(var client:ClientVO in p2p.clients)
			{
				if( client.peerID == value )
				{
					currentClient = client;
					break;
				}
			}
		}
		
		public function disconnect():void
		{
			if( !p2p )
			{
				if( connected )
				{
					p2p.close();
					connected = false;
				}
			}
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
			p2p.clientName = "mobile";
			p2p.key = "fa34a2a9cd8864b67cdb154d-ac8a93ff4df0";
			p2p.useCirrus = true;
			var groupName:String = "ru.nekit.p2p.group_" + groupSuffix;
			p2p.groupName = groupName;
			trace("P2P channel set group name: " + groupName);
			p2p.addEventListener(ClientEvent.CLIENT_ADDED, 		clientAddedHandler);
			p2p.addEventListener(ClientEvent.CLIENT_UPDATE, 	clientUpdateHandler);
			p2p.addEventListener(ClientEvent.CLIENT_REMOVED, 	clientRemoveHandler);
			p2p.addEventListener(GroupEvent.GROUP_CONNECTED, 	groupConnectedHandler);
			p2p.addEventListener(GroupEvent.GROUP_CLOSED, 		groupClosedHandler);
			p2p.connect();
			trace("Local P2P client init connection");	
		}
		
		private function groupConnectedHandler(event:GroupEvent):void
		{
			context.dispatchP2PStatusEvent(CONNECT_TO_GROUP);
			trace("P2P client connect to group");
		}
		
		private function clientUpdateHandler(event:ClientEvent):void
		{
			context.dispatchP2PStatusEvent(CLIENT_CONNECT, event.client);
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