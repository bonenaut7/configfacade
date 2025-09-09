package by.bonenaut7.configfacade.facade.elements;

public final class ConfigString extends ConfigPrimitive {
	private final boolean isCharacter;
	private final String string;
	private final char character;
	
	public ConfigString(String string) {
		this.isCharacter = false;
		this.string = string;
		this.character = 0;
	}
	
	public ConfigString(char character) {
		this.isCharacter = true;
		this.string = null;
		this.character = character;
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
	public char getChar(char fallback) {
		return this.isCharacter ? this.character : fallback;
	}

	@Override
	public String getString(String fallback) {
		return !this.isCharacter ? this.string : fallback;
	}
	
	@Override
	public Object getValue() {
		return this.isCharacter ? this.string : this.character;
	}
}
