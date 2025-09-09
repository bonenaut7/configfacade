package by.bonenaut7.configfacade.facade.elements;

public final class ConfigBoolean extends ConfigPrimitive {
	private final boolean value;
	
	public ConfigBoolean(boolean bool) {
		this.value = bool;
	}
	
	@Override
	public ConfigBoolean asBoolean() {
		return this;
	}
	
	@Override
	public boolean isBoolean() {
		return true;
	}

	@Override
	public boolean getBoolean(boolean fallback) {
		return this.value;
	}

	@Override
	public Number getNumber(Number fallback) {
		return fallback;
	}

	@Override
	public byte getByte(byte fallback) {
		return fallback;
	}

	@Override
	public short getShort(short fallback) {
		return fallback;
	}

	@Override
	public int getInteger(int fallback) {
		return fallback;
	}

	@Override
	public long getLong(long fallback) {
		return fallback;
	}
	
	@Override
	public float getFloat(float fallback) {
		return fallback;
	}

	@Override
	public double getDouble(double fallback) {
		return fallback;
	}

	@Override
	public char getChar(char fallback) {
		return fallback;
	}

	@Override
	public String getString(String fallback) {
		return fallback;
	}
	
	@Override
	public Object getValue() {
		return this.value;
	}
}
