package by.bonenaut7.configfacade.adapter.yaml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import by.bonenaut7.configfacade.facade.BasicConfigReader;
import by.bonenaut7.configfacade.facade.BasicConfigWriter;
import by.bonenaut7.configfacade.facade.elements.ConfigCompound;

public class YamlConfigurationAdapter implements BasicConfigReader<ConfigCompound>, BasicConfigWriter<ConfigCompound> {
	private final Yaml yaml;
	private boolean separateFirstLevelObjects = true;
	private boolean separateCommentsFromAbove = true;
	
	public YamlConfigurationAdapter(Yaml yaml) {
		this.yaml = yaml;
	}
	
	/**
	 * Separates objects on the first level of indentation with new line.
	 */
	public void setSeparateFirstLevelObjects(boolean value) {
		this.separateFirstLevelObjects = value;
	}

	/**
	 * Separates object line with comments with new line from object above.
	 */
	public void setSeparateCommentsFromAbove(boolean value) {
		this.separateCommentsFromAbove = value;
	}
	
	@Override
	public boolean write(File file, ConfigCompound type) {
		if (file == null) {
			return false;
		}
		
		try (final YamlWriter writer = new YamlWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8)), 2, false)) {
			if (!file.exists()) {
				file.createNewFile();
			}
			
			writer.setSeparateFirstCompounds(this.separateFirstLevelObjects);
			writer.setSeparateCommentsFromAbove(this.separateCommentsFromAbove);
			writer.write(type);
			
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
			
		try (final Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
			return new ConfigCompound(this.yaml.loadAs(reader, Map.class));
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}