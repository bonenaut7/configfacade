package by.bonenaut7.configfacade.facade.elements;

public final class ConfigString extends ConfigPrimitive {
	private final boolean isCharacter;
	private final Object object;
	
	public ConfigString(String string) {
		this.isCharacter = false;
		this.object = string;
	}
	
	public ConfigString(char character) {
		this.isCharacter = true;
		this.object = character;
	}
	
	@Override
	public ConfigString asString() {
		return this;
	}

	@Override
	public boolean isLiteral() {
		return true;
	}

	@Override
	public boolean getBoolean(boolean fallback) {
		return fallback;
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
		return this.isCharacter ? (char)this.object : fallback;
	}

	@Override
	public String getString(String fallback) {
		return !this.isCharacter ? (String)this.object : fallback;
	}
	
	@Override
	public Object getValue() {
		return this.object;
	}
}
