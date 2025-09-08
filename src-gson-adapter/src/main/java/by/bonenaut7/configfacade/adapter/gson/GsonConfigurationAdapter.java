package by.bonenaut7.configfacade.adapter.gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;

import by.bonenaut7.configfacade.facade.BasicConfigReader;
import by.bonenaut7.configfacade.facade.BasicConfigWriter;
import by.bonenaut7.configfacade.facade.elements.ConfigArray;
import by.bonenaut7.configfacade.facade.elements.ConfigCompound;
import by.bonenaut7.configfacade.facade.elements.ConfigElement;
import by.bonenaut7.configfacade.facade.elements.ConfigPrimitive;

// FIXME buffered readers and writers?
public final class GsonConfigurationAdapter implements BasicConfigReader<ConfigCompound>, BasicConfigWriter<ConfigCompound> {
	private final Gson gson;
	
	public GsonConfigurationAdapter(Gson gson) {
		this.gson = gson;
	}
	
	@Override
	public boolean write(File file, ConfigCompound type) {
		if (file == null) {
			return false;
		}
		
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8));
			writer.write(this.gson.toJson(convertToMap(type)));
			writer.close();
			
			return true;
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	public ConfigCompound read(File file) {
		if (file == null || !file.exists()) {
			return null;
		}
		
		try (final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
			return new ConfigCompound(this.gson.fromJson(reader, Map.class));
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	private static Map<String, Object> convertToMap(ConfigCompound compound) {
		final Map<String, Object> map = new HashMap<>();
		
		for (final Entry<String, ConfigElement> entry : compound.getValue().entrySet()) {
			map.put(entry.getKey(), reverseObject(entry.getValue()));
		}
		
		return map;
	}
	
	private static Object reverseObject(ConfigElement wrapper) {
		if (wrapper instanceof ConfigCompound) {
			final ConfigCompound compound = (ConfigCompound)wrapper;
			final Map<String, Object> map = new HashMap<>();
			
			for (Entry<String, ConfigElement> entry : compound.getValue().entrySet()) {
				map.put(entry.getKey(), reverseObject(entry.getValue()));
			}
			
			return map;
		} else if (wrapper instanceof ConfigArray) {
			final ConfigArray array = (ConfigArray)wrapper;
			final List<Object> objects = new ArrayList<>();
			
			for (int index = 0; index < array.size(); index++) {
				objects.add(reverseObject(array.getAt(index)));
			}
			
			return objects;
		} else if (wrapper instanceof ConfigPrimitive) {
			return wrapper.getValue();
		}
		
		return wrapper;
	}
}
