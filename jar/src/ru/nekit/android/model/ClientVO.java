package ru.nekit.android.model;

public class ClientVO 
{

	public ClientVO(String clientName, String peerID, String groupID)
	{
		this.clientName = clientName;
		this.peerID 	= peerID;
		this.groupID 	= groupID;
	}
	
	public String clientName;
	public String peerID;  
	public String groupID;

}