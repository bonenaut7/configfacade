package by.bonenaut7.configfacade.facade;

import java.io.File;

public interface BasicConfigReader<T> {
	T read(File file);
}
