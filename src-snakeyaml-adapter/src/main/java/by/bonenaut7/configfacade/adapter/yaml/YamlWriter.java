package by.bonenaut7.configfacade.adapter.yaml;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map.Entry;

import by.bonenaut7.configfacade.facade.elements.ConfigArray;
import by.bonenaut7.configfacade.facade.elements.ConfigCompound;
import by.bonenaut7.configfacade.facade.elements.ConfigElement;
import by.bonenaut7.configfacade.facade.elements.ConfigPrimitive;

public final class YamlWriter implements Closeable {
	private static final String LINE_SEPARATOR_REGEX = "\r\n?|\n", COLON = ":", COMMENT = "# ", COLON_SPACER = ": ", DOUBLE_SPACER = "  ", HYPHEN_SPACER = "- ";
	private final Writer writer;
	private final int indentSize;
	private final char indentCharacter;
	private final String lineSeparator;
	private boolean separateFirstCompounds = true;
	private boolean separateCommentsFromAbove = true;
	
	private int indentLevel = 0;
	private ConfigElement lastWritten;
	
	public YamlWriter(Writer writer) {
		this(writer, 2, false);
	}
	
	public YamlWriter(Writer writer, int indentSize, boolean useIndentTabs) {
		this(writer, indentSize, useIndentTabs, System.lineSeparator());
	}
	
	public YamlWriter(Writer writer, int indentSize, boolean useIndentTabs, String lineSeparator) {
		this.writer = writer;
		this.indentSize = indentSize;
		this.indentCharacter = useIndentTabs ? '\t' : ' ';
		this.lineSeparator = lineSeparator;
	}
	
	public void setSeparateFirstCompounds(boolean enable) {
		this.separateFirstCompounds = enable;
	}
	
	public void setSeparateCommentsFromAbove(boolean enable) {
		this.separateCommentsFromAbove = enable;
	}
	
	public void write(ConfigCompound compound) throws IOException {
		for (final Entry<String, ConfigElement> entry : compound) {
			writeElement(entry.getKey(), entry.getValue(), true, true);
			
			if (this.separateFirstCompounds) {
				newLineIndent();
			}
		}
	}

	private void writeElement(String name, ConfigElement wrapper, boolean isUnmarked, boolean handleNewLine) throws IOException {
		if (wrapper == null) {
			newLineIndent();
			lastWritten = null;
			return;
		}

		if (wrapper.hasComment()) {
			writeComment(wrapper);
		}
		
		if (!isUnmarked) { // Arrays writing marked values, so there's a hyphen spacer
			write(HYPHEN_SPACER);
		}
		
		if (wrapper instanceof ConfigCompound) {
			ConfigCompound compound = (ConfigCompound)wrapper;

			this.indentLevel++;
			if (isUnmarked) {
				write(name);
				write(COLON);
				newLineIndent(); // Indent skip if wrapper is child of an array (currently known by 'name == null')
			}
			
			this.lastWritten = wrapper;
			
			final Iterator<Entry<String, ConfigElement>> iterator = compound.iterator();
			while (iterator.hasNext()) {
				final Entry<String, ConfigElement> entry = iterator.next();
				writeElement(entry.getKey(), entry.getValue(), true, false);
				this.lastWritten = entry.getValue();
				
				if (iterator.hasNext()) {
					newLineIndent();
				} else if (handleNewLine) {
					this.indentLevel--;
					newLineIndent();
					this.indentLevel++; // lazy shit to not lose indent level
				}
			}
			
			this.indentLevel--;
			this.lastWritten = wrapper;
		} else if (wrapper instanceof ConfigArray) {
			// Arrays are increasing indent level but not require children to use indent cuz applying its own one
			final ConfigArray array = (ConfigArray)wrapper;
			
			if (array.size() < 1) {
				if (isUnmarked) { // Indent skip if wrapper is child of an array
					write(name);
					write(COLON_SPACER);
				}
				write("[]");
			} else {
				this.indentLevel++;
				if (isUnmarked) { // Indent skip if wrapper is child of an array
					write(name);
					write(COLON);	
					newLineIndent(); // Indent skip if wrapper is child of an array (currently known by 'name == null')
				}
				
				this.lastWritten = wrapper;
				
				final Iterator<ConfigElement> iterator = array.iterator();
				while (iterator.hasNext()) {
					ConfigElement iterable = iterator.next();
					writeElement(null, iterable, false, false);
					this.lastWritten = iterable;
					
					if (iterator.hasNext()) {
						newLineIndent();
					} else if (handleNewLine) {
						this.indentLevel--;
						newLineIndent();
						this.indentLevel++; // lazy shit to not lose indent level
					}
				}
				
				this.indentLevel--;
				this.lastWritten = wrapper;
			}
		} else if (wrapper instanceof ConfigPrimitive) {
			final ConfigPrimitive primitive = (ConfigPrimitive)wrapper;
			
			if (isUnmarked) {
				write(name);
				write(COLON_SPACER);
			}
			
			if (primitive.isLiteral()) { //FIXME add escaped fixing for strings from TOML addEscaped(...)
				write(primitive.getValue() instanceof String ? primitive.getString() : String.valueOf(primitive.getChar()));
			} else if (primitive.isNumber() || primitive.isBoolean()) {
				write(primitive.getValue().toString());
			}
			
			this.lastWritten = wrapper;
			if (handleNewLine) {
				newLineIndent();
			}
		}
	}
	
	private void writeComment(ConfigElement wrapper) throws IOException {
		final String[] commentParts = wrapper.getComment().split(LINE_SEPARATOR_REGEX);
		
		if (this.separateCommentsFromAbove && commentParts.length > 0 && (this.lastWritten != null && !(this.lastWritten instanceof ConfigCompound))) {
			newLineIndent();
		}
		
		for (int i = 0; i < commentParts.length; i++) {
			write(COMMENT);
			write(commentParts[i]);
			newLineIndent();
		}
	}
	
	private void write(char c) throws IOException {
		this.writer.write(c);
	}

	private void write(String str) throws IOException {
		this.writer.write(str);
	}
	
	private void newLineIndent() throws IOException {
		this.writer.write(this.lineSeparator);
		
		for (int i = 0; i < this.indentLevel; i++) {
			for (int j = 0; j < this.indentSize; j++) {
				write(this.indentCharacter);
			}
		}
	}
	
	public void close() throws IOException {
		this.writer.close();
	}

	public void flush() throws IOException {
		this.writer.flush();
	}
}
