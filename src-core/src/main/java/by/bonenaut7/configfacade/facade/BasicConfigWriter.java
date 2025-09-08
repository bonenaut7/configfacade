package by.bonenaut7.configfacade.facade;

import java.io.File;

public interface BasicConfigWriter<T> {
	boolean write(File file, T type);
}
