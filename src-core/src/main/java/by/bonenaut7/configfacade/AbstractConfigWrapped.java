package by.bonenaut7.configfacade;

import java.io.File;

import by.bonenaut7.configfacade.facade.BasicConfigReader;
import by.bonenaut7.configfacade.facade.BasicConfigWriter;
import by.bonenaut7.configfacade.facade.elements.ConfigCompound;

public abstract class AbstractConfigWrapped extends AbstractConfig<ConfigCompound, ConfigCompound, ConfigCompound> {
	protected boolean saveOnReadFailure = true;
	
	public AbstractConfigWrapped() {
	}
	
	public AbstractConfigWrapped(File configFile) {
		super(configFile);
	}
	
	public <T extends BasicConfigReader<ConfigCompound> & BasicConfigWriter<ConfigCompound>> void setReadWriter(T readWriter) {
		this.reader = readWriter;
		this.writer = readWriter;
	}
	
	public void setSaveOnReadFailure(boolean saveOnReadFailure) {
		this.saveOnReadFailure = saveOnReadFailure;
	}
	
	@Override
	public boolean loadFrom(File file) {
		if (this.reader == null) {
			throw new NullPointerException("\'reader\' cannot be null if you're trying to write something!");
		}
		
		if (file != null) {
			if (file.exists()) {
				final ConfigCompound type = this.reader.read(file);
				if (type != null) {
					this.deserialize(type);
					return true;
				}
			} else {
				// Creating config in case if it's not created
				return this.saveOnReadFailure ? this.saveTo(file) : false;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean saveTo(File file) {
		if (this.writer == null) {
			throw new NullPointerException("\'writer\' cannot be null if you're trying to write something!");
		}
		
		if (file != null) {
			ConfigCompound type = this.serialize();
			if (type != null) {
				return this.writer.write(file, type);
			}
		}
		
		return false;
	}
}
