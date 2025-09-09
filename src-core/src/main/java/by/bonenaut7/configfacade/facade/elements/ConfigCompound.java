package by.bonenaut7.configfacade.facade.elements;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class ConfigCompound extends ConfigElement implements Iterable<Map.Entry<String, ConfigElement>> {
	private Map<String, ConfigElement> map = new LinkedHashMap<>();
	
	public ConfigCompound() {
	}
	
	// conversion required!
	public ConfigCompound(Map<String, Object> map) {
		if (map != null) {
			for (Entry<String, Object> entry : map.entrySet()) {
				if (entry.getKey() != null) {
					ConfigElement wrapper = convertToElement(entry.getValue());
					if (wrapper != null) {
						this.map.put(entry.getKey(), wrapper);
					}
				}
			}
		}
	}
	
	@Override
	public ConfigCompound asCompound() {
		return this;
	}
	
	public boolean has(String key) {
		return this.map.containsKey(key);
	}

	public ConfigElement get(String key) {
		return this.map.get(key);
	}

	public ConfigArray getArray(String key) {
		Object obj = this.map.get(key);
		return obj instanceof ConfigArray ? (ConfigArray)obj : null;
	}

	public ConfigCompound getCompound(String key) {
		Object obj = this.map.get(key);
		return obj instanceof ConfigCompound ? (ConfigCompound)obj : null;
	}

	public ConfigPrimitive getPrimitive(String key) {
		Object obj = this.map.get(key);
		return obj instanceof ConfigPrimitive ? (ConfigPrimitive)obj : null;
	}
	
	public ConfigCompound append(String key, ConfigElement element) {
		this.map.put(key, element);
		return this;
	}
	
	public ConfigCompound append(String key, ConfigElement element, String comment) {
		this.map.put(key, element.setComment(comment));
		return this;
	}

	public ConfigCompound append(String key, boolean value) {
		this.map.put(key, new ConfigBoolean(value));
		return this;
	}
	
	public ConfigCompound append(String key, boolean value, String comment) {
		this.map.put(key, new ConfigBoolean(value).setComment(comment));
		return this;
	}

	public ConfigCompound append(String key, Number value) {
		this.map.put(key, new ConfigNumber(value));
		return this;
	}
	
	public ConfigCompound append(String key, Number value, String comment) {
		this.map.put(key, new ConfigNumber(value).setComment(comment));
		return this;
	}

	public ConfigCompound append(String key, String value) {
		this.map.put(key, new ConfigString(value));
		return this;
	}
	
	public ConfigCompound append(String key, String value, String comment) {
		this.map.put(key, new ConfigString(value).setComment(comment));
		return this;
	}
	
	public ConfigElement remove(String key) {
		return this.map.remove(key);
	}

	public boolean getBoolean(String key) {
		return getBoolean(key, false);
	}
	
	public boolean getBoolean(String key, boolean fallback) {
		Object obj = this.map.get(key);
		return obj instanceof ConfigPrimitive ? ((ConfigPrimitive)obj).getBoolean(fallback) : fallback;
	}
	
	public Number getNumber(String key) {
		return getNumber(key, NUM_ZERO);
	}

	public Number getNumber(String key, Number fallback) {
		Object obj = this.map.get(key);
		return obj instanceof ConfigPrimitive ? ((ConfigPrimitive)obj).getNumber(fallback) : fallback;
	}

	public byte getByte(String key) {
		return getByte(key, BYTE_ZERO);
	}
	
	public byte getByte(String key, byte fallback) {
		Object obj = this.map.get(key);
		return obj instanceof ConfigPrimitive ? ((ConfigPrimitive)obj).getByte(fallback) : fallback;
	}

	public short getShort(String key) {
		return getShort(key, SHORT_ZERO);
	}
	
	public short getShort(String key, short fallback) {
		Object obj = this.map.get(key);
		return obj instanceof ConfigPrimitive ? ((ConfigPrimitive)obj).getShort(fallback) : fallback;
	}
	
	public int getInteger(String key) {
		return getInteger(key, INT_ZERO);
	}

	public int getInteger(String key, int fallback) {
		Object obj = this.map.get(key);
		return obj instanceof ConfigPrimitive ? ((ConfigPrimitive)obj).getInteger(fallback) : fallback;
	}
	
	public long getLong(String key) {
		return getLong(key, LONG_ZERO);
	}

	public long getLong(String key, long fallback) {
		Object obj = this.map.get(key);
		return obj instanceof ConfigPrimitive ? ((ConfigPrimitive)obj).getLong(fallback) : fallback;
	}
	
	public float getFloat(String key) {
		return getFloat(key, FLOAT_ZERO);
	}

	public float getFloat(String key, float fallback) {
		Object obj = this.map.get(key);
		return obj instanceof ConfigPrimitive ? ((ConfigPrimitive)obj).getFloat(fallback) : fallback;
	}

	public double getDouble(String key) {
		return getDouble(key, DOUBLE_ZERO);
	}
	
	public double getDouble(String key, double fallback) {
		Object obj = this.map.get(key);
		return obj instanceof ConfigPrimitive ? ((ConfigPrimitive)obj).getDouble(fallback) : fallback;
	}

	public char getChar(String key) {
		return getChar(key, CHAR_ZERO);
	}
	
	public char getChar(String key, char fallback) {
		Object obj = this.map.get(key);
		return obj instanceof ConfigPrimitive ? ((ConfigPrimitive)obj).getChar(fallback) : fallback;
	}

	public String getString(String key) {
		return getString(key, null);
	}
	
	public String getString(String key, String fallback) {
		Object obj = this.map.get(key);
		return obj instanceof ConfigPrimitive ? ((ConfigPrimitive)obj).getString(fallback) : fallback;
	}
	
	@Override
	public Map<String, ConfigElement> getValue() {
		return this.map;
	}
	
	@Override
	public Iterator<Entry<String, ConfigElement>> iterator() {
		return this.map.entrySet().iterator();
	}
	
	@SuppressWarnings("rawtypes")
	private static ConfigElement convertToElement(Object object) {
		if (object instanceof Map) {
			final Map<?, ?> map = (Map)object;
			final ConfigCompound compound = new ConfigCompound();
			
			for (Entry<?, ?> entry : map.entrySet()) {
				if (entry.getKey() instanceof String) { // Can't accept non-string keys
					final ConfigElement element = convertToElement(entry.getValue());
					
					if (element != null) {
						compound.append((String)entry.getKey(), element);
					}
				}
			}
			
			return compound;
		} else if (object instanceof Collection) {
			final Collection<?> collection = (Collection)object;
			final Iterator<?> iterator = collection.iterator();
			final ConfigArray array = new ConfigArray();
			
			while (iterator.hasNext()) {
				final ConfigElement element = convertToElement(iterator.next());
				
				if (element != null) {
					array.append(element);
				}
			}
			
			return array;
		} else if (object instanceof Boolean) {
			return new ConfigBoolean((Boolean)object);
		} else if (object instanceof Number) {
			return new ConfigNumber((Number)object);
		} else if (object instanceof String) {
			return new ConfigString((String)object);
		}
		
		return null;
	}
}
