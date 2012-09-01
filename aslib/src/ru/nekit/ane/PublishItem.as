package ru.nekit.ane
{
	
	public class PublishItem
	{
		
		public static var context:ICommunicationable;
		public var name:String;
		public var type:String;
		private var _value:Object;
		
		public function PublishItem(name:String, type:String)
		{
			this.name 	= name;
			this.type 	= type;
			this._value = null;
		}
		
		public function get id():String
		{
			return  type + "." + name;
		}
		
		public static function create(value:String):PublishItem
		{
			var valueArray:Array = value.split(".");
			return new PublishItem(valueArray[1], valueArray[0]);
		}
		
		public function get value():Object
		{
			if( _value )
			{
				return _value;
			}
			return _value = context.getPublishValue(this);
		}
	}
}