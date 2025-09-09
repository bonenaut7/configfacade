package by.bonenaut7.configfacade.facade.elements;

public final class ConfigBoolean extends ConfigPrimitive {
	private final boolean value;
	
	public ConfigBoolean(boolean value) {
		this.value = value;
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
	public Object getValue() {
		return this.value;
	}
}
