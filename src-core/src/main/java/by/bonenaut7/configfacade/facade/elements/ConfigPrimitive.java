package by.bonenaut7.configfacade.facade.elements;

public abstract class ConfigPrimitive extends ConfigElement {
	
	public boolean isBoolean() {
		return false;
	}

	public boolean isNumber() {
		return false;
	}

	public boolean isLiteral() {
		return false;
	}
	
	public boolean getBoolean() {
		return getBoolean(false);
	}
	
	public abstract boolean getBoolean(boolean fallback);

	public Number getNumber() {
		return getNumber(NUM_ZERO);
	}

	public abstract Number getNumber(Number fallback);

	public byte getByte() {
		return getByte(BYTE_ZERO);
	}
	
	public abstract byte getByte(byte fallback);
	
	public short getShort() {
		return getShort(SHORT_ZERO);
	}

	public abstract short getShort(short fallback);

	public int getInteger() {
		return getInteger(INT_ZERO);
	}
	
	public abstract int getInteger(int fallback);
	
	public long getLong() {
		return getLong(LONG_ZERO);
	}

	public abstract long getLong(long fallback);

	public float getFloat() {
		return getFloat(FLOAT_ZERO);
	}
	
	public abstract float getFloat(float fallback);

	public double getDouble() {
		return getDouble(DOUBLE_ZERO);
	}
	
	public abstract double getDouble(double fallback);

	public char getChar() {
		return getChar(CHAR_ZERO);
	}
	
	public abstract char getChar(char fallback);
	
	public String getString() {
		return getString(null);
	}
	
	public abstract String getString(String fallback);
}
