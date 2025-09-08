package by.bonenaut7.configfacade.facade;

import by.bonenaut7.configfacade.facade.ChunkedConfiguration.ConfigurationChunk;

public interface ChunkedConfiguration<T, PART extends ConfigurationChunk<T>> extends Configuration<T> {
	boolean registerPart(PART part);
	
	public interface ConfigurationChunk<T> {
		String getChunkName();
		
		T serialize();
		
		boolean deserialize(T chunk);
		
		default void onChunkSerialized() {
		}
		
		default void onChunkDeserialized() {
		}
	}
}
