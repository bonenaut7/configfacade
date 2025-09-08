package by.bonenaut7.configfacade.facade.elements;

public final class ConfigNumber extends ConfigPrimitive {
	private final Number value;
	
	public ConfigNumber(Number number) {
		this.value = number;
	}

	@Override
	public boolean isNumber() {
		return true;
	}

	@Override
	public boolean getBoolean(boolean fallback) {
		return fallback;
	}

	@Override
	public Number getNumber(Number fallback) {
		return this.value;
	}

	@Override
	public byte getByte(byte fallback) {
		return this.value.byteValue();
	}

	@Override
	public short getShort(short fallback) {
		return this.value.shortValue();
	}

	@Override
	public int getInteger(int fallback) {
		return this.value.intValue();
	}

	@Override
	public long getLong(long fallback) {
		return this.value.longValue();
	}

	@Override
	public float getFloat(float fallback) {
		return this.value.floatValue();
	}

	@Override
	public double getDouble(double fallback) {
		return this.value.doubleValue();
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
