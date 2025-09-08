package by.bonenaut7.configfacade.facade.elements;

public abstract class ConfigElement {
	protected static final Number NUM_ZERO = 0;
	protected static final byte BYTE_ZERO = 0;
	protected static final short SHORT_ZERO = 0;
	protected static final int INT_ZERO = 0;
	protected static final long LONG_ZERO = 0;
	protected static final float FLOAT_ZERO = 0f;
	protected static final double DOUBLE_ZERO = 0D;
	protected static final char CHAR_ZERO = 0;
	
	protected String comment = null;
	
	ConfigElement() {
	}

	public boolean hasComment() {
		return this.comment != null;
	}
	
	public String getComment() {
		return this.comment;
	}

	public ConfigElement setComment(String comment) {
		this.comment = comment;
		return this;
	}
	
	public abstract Object getValue();
}
