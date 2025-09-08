package by.bonenaut7.configfacade;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import by.bonenaut7.configfacade.facade.ChunkedConfiguration;
import by.bonenaut7.configfacade.facade.ChunkedConfiguration.ConfigurationChunk;
import by.bonenaut7.configfacade.facade.elements.ConfigCompound;
import by.bonenaut7.configfacade.facade.elements.ConfigElement;

public class ConfigWrappedParted extends AbstractConfigWrapped implements ChunkedConfiguration<ConfigCompound, ConfigurationChunk<ConfigCompound>> {
	protected List<ConfigurationChunk<ConfigCompound>> parts = new ArrayList<>();
	
	public ConfigWrappedParted() {
	}
	
	public ConfigWrappedParted(File configFile) {
		super(configFile);
	}
	
	@Override
	public boolean registerPart(ConfigurationChunk<ConfigCompound> part) {
		if (!this.parts.contains(part)) {
			return this.parts.add(part);
		}
		
		return false;
	}
	
	@Override
	public ConfigCompound serialize() {
		final ConfigCompound compound = new ConfigCompound();
		for (final ConfigurationChunk<ConfigCompound> part : this.parts) {
			final ConfigCompound serialized = part.serialize();
			if (serialized != null) {
				compound.append(part.getChunkName(), serialized);
			}
			
			part.onChunkSerialized();
		}
		
		return compound;
	}

	@Override
	public void deserialize(ConfigCompound compound) {
		for (final ConfigurationChunk<ConfigCompound> chunk : this.parts) {
			if (compound.has(chunk.getChunkName())) {
				final ConfigElement wrapper = compound.get(chunk.getChunkName());
				if (wrapper instanceof ConfigCompound) {
					chunk.deserialize((ConfigCompound)wrapper);
				}
				
				chunk.onChunkDeserialized();
			}
		}
	}
}
