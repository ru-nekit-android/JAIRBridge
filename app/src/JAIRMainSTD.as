package
{
	
	import com.projectcocoon.p2p.LocalNetworkDiscovery;
	import com.projectcocoon.p2p.events.ClientEvent;
	import com.projectcocoon.p2p.events.GroupEvent;
	
	import flash.display.Sprite;
	
	
	public class JAIRMainSTD extends Sprite
	{
		
		private static var p2p:LocalNetworkDiscovery;
		
		public function JAIRMainSTD()
		{
			p2p = new LocalNetworkDiscovery;
			p2p.clientName = "mobile";
			p2p.key = "fa34a2a9cd8864b67cdb154d-ac8a93ff4df0";
			p2p.useCirrus = true;
			p2p.groupName = "nelch";
			p2p.addEventListener(ClientEvent.CLIENT_ADDED, clientAddedHandler);
			p2p.addEventListener(ClientEvent.CLIENT_UPDATE, clientUpdateHandler);
			p2p.addEventListener(GroupEvent.GROUP_CONNECTED, groupConnectedHandler);
			p2p.connect();
			super();
		}
		
		private function groupConnectedHandler(event:GroupEvent):void
		{
			trace("groupConnected");
		}
		
		private function clientUpdateHandler(event:ClientEvent):void
		{
			trace("update");
		}
		
		private function clientAddedHandler(event:ClientEvent):void
		{
			trace("added");
		}
	}
}