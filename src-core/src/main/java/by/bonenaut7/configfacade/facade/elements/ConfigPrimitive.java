package by.bonenaut7.configfacade.facade.elements;

public abstract class ConfigPrimitive extends ConfigElement {
	
	@Override
	public ConfigPrimitive asPrimitive() {
		return this;
	}
	
	public boolean isLiteral() {
		return false;
	}
	
	public boolean isNumber() {
		return false;
	}
	
	public boolean isBoolean() {
		return false;
	}
	
	public boolean getBoolean() {
		return getBoolean(false);
	}
	
	public boolean getBoolean(boolean fallback) {
		return fallback;
	}

	public Number getNumber() {
		return getNumber(NUM_ZERO);
	}

	public Number getNumber(Number fallback) {
		return fallback;
	}

	public byte getByte() {
		return getByte(BYTE_ZERO);
	}
	
	public byte getByte(byte fallback) {
		return fallback;
	}
	
	public short getShort() {
		return getShort(SHORT_ZERO);
	}

	public short getShort(short fallback) {
		return fallback;
	}

	public int getInteger() {
		return getInteger(INT_ZERO);
	}
	
	public int getInteger(int fallback) {
		return fallback;
	}
	
	public long getLong() {
		return getLong(LONG_ZERO);
	}

	public long getLong(long fallback) {
		return fallback;
	}

	public float getFloat() {
		return getFloat(FLOAT_ZERO);
	}
	
	public float getFloat(float fallback) {
		return fallback;
	}

	public double getDouble() {
		return getDouble(DOUBLE_ZERO);
	}
	
	public double getDouble(double fallback) {
		return fallback;
	}

	public char getChar() {
		return getChar(CHAR_ZERO);
	}
	
	public char getChar(char fallback) {
		return fallback;
	}
	
	public String getString() {
		return getString(null);
	}
	
	public String getString(String fallback) {
		return fallback;
	}
}
