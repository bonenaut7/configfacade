package by.bonenaut7.configfacade;

import java.io.File;

import by.bonenaut7.configfacade.facade.BasicConfigReader;
import by.bonenaut7.configfacade.facade.BasicConfigWriter;
import by.bonenaut7.configfacade.facade.Configuration;

public abstract class AbstractConfig<T, R, W> implements Configuration<T> {
	protected File configFile;
	protected BasicConfigReader<R> reader;
	protected BasicConfigWriter<W> writer;
	
	public AbstractConfig() {
	}
	
	public AbstractConfig(File configFile) {
		this.configFile = configFile;
	}
	
	@Override
	public File getConfigFile() {
		return this.configFile;
	}

	@Override
	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}
	
	public BasicConfigReader<R> getReader() {
		return this.reader;
	}
	
	public BasicConfigWriter<W> getWriter() {
		return this.writer;
	}
	
	public void setReader(BasicConfigReader<R> reader) {
		this.reader = reader;
	}
	
	public void setWriter(BasicConfigWriter<W> writer) {
		this.writer = writer;
	}
}
