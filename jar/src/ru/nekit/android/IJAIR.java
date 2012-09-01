package ru.nekit.android;

public interface IJAIR {

	void dispatchServiceEvent(String description);
	void dispatchErrorEvent(String description);
	void dispatchStatusEvent(String level, String code);
	int getResourceId(String id);
	void publishValue(String name, int value);
	void publishValue(String name, double value);
	void publishValue(String name, String value);
	void publishValue(String name, boolean value);
	void registerEventReserver(IJAIREventReceivable receiver);
	void unregisterEventReserver(IJAIREventReceivable receiver);
	
}