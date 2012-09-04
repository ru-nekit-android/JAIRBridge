package ru.nekit.android.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.adobe.fre.FREASErrorException;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FRENoSuchNameException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

public class ClientProxy implements Parcelable{

	public static String CURRENT = "current";
	
	private ClientVO value;

	public ClientVO getValue()
	{
		return value;
	}

	public ClientProxy(FREObject value)
	{
		try {
			this.value = new ClientVO(
					value.getProperty("clientName").getAsString(),
					value.getProperty("peerID").getAsString(),
					value.getProperty("groupID").getAsString()
					);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FRETypeMismatchException e) {
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			e.printStackTrace();
		} catch (FREASErrorException e) {
			e.printStackTrace();
		} catch (FRENoSuchNameException e) {
			e.printStackTrace();
		}
	}

	public static final Parcelable.Creator<ClientProxy> CREATOR = new Parcelable.Creator<ClientProxy>() {
		public ClientProxy createFromParcel(Parcel in) {
			return new ClientProxy(in);
		}

		public ClientProxy[] newArray(int size) {
			return new ClientProxy[size];
		}
	};

	@Override
	public int describeContents() 
	{
		return 0;
	}

	public void writeToParcel(Parcel parcel, int flags) 
	{
		parcel.writeString(value.clientName);
		parcel.writeString(value.peerID);
		parcel.writeString(value.groupID);
	}

	private ClientProxy(Parcel parcel)
	{
		value = new ClientVO(parcel.readString(), parcel.readString(), parcel.readString());
	}
}