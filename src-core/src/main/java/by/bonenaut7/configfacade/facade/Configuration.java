package by.bonenaut7.configfacade.facade;

import java.io.File;

public interface Configuration<T> {

	File getConfigFile();
	
	void setConfigFile(File configFile);
	
	//
	
	boolean loadFrom(File file);
	
	default boolean load() {
		return loadFrom(getConfigFile());
	}
	
	boolean saveTo(File file);
	
	default boolean save() {
		return saveTo(getConfigFile());
	}
	
	//
	
	T serialize();
	
	void deserialize(T config);
}
