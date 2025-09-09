package by.bonenaut7.configfacade.facade.elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ConfigArray extends ConfigElement implements Iterable<ConfigElement> {
	private List<ConfigElement> list = new ArrayList<>();
	
	@Override
	public ConfigArray asArray() {
		return this;
	}
	
	public boolean containsArrays() {
		for (int index = 0; index < this.list.size(); index++) {
			if (this.list.get(index) instanceof ConfigArray) {
				return true;
			}
		}
		return false;
	}

	public boolean containsCompounds() {
		for (int index = 0; index < this.list.size(); index++) {
			if (this.list.get(index) instanceof ConfigCompound) {
				return true;
			}
		}
		return false;
	}

	public ConfigElement getAt(int index) {
		if (index > -1 && index < this.list.size()) {
			return this.list.get(index);
		}
		return null;
	}

	public ConfigArray getArrayAt(int index) {
		if (index > -1 && index < this.list.size()) {
			ConfigElement wrapper = this.list.get(index);
			return wrapper instanceof ConfigArray ? (ConfigArray)wrapper : null;
		}
		return null;
	}

	public ConfigCompound getCompoundAt(int index) {
		if (index > -1 && index < this.list.size()) {
			ConfigElement wrapper = this.list.get(index);
			return wrapper instanceof ConfigCompound ? (ConfigCompound)wrapper : null;
		}
		return null;
	}

	public ConfigPrimitive getPrimitiveAt(int index) {
		if (index > -1 && index < this.list.size()) {
			ConfigElement wrapper = this.list.get(index);
			return wrapper instanceof ConfigPrimitive ? (ConfigPrimitive)wrapper : null;
		}
		return null;
	}

	public ConfigArray append(ConfigElement element) {
		this.list.add(element);
		return this;
	}
	
	public ConfigArray append(ConfigElement element, String comment) {
		this.list.add(element.setComment(comment));
		return this;
	}
	
	public ConfigArray append(boolean value) {
		this.list.add(new ConfigBoolean(value));
		return this;
	}
	
	public ConfigArray append(boolean value, String comment) {
		this.list.add(new ConfigBoolean(value).setComment(comment));
		return this;
	}

	public ConfigArray append(Number value) {
		this.list.add(new ConfigNumber(value));
		return this;
	}
	
	public ConfigArray append(Number value, String comment) {
		this.list.add(new ConfigNumber(value).setComment(comment));
		return this;
	}

	public ConfigArray append(String value) {
		this.list.add(new ConfigString(value));
		return this;
	}
	
	public ConfigArray append(String value, String comment) {
		this.list.add(new ConfigString(value).setComment(comment));
		return this;
	}
	
	public ConfigElement remove(int index) {
		return this.list.remove(index);
	}

	public ConfigElement removeFirst() {
		return this.list.remove(0);
	}

	public ConfigElement removeLast() {
		return this.list.remove(size() - 1);
	}
	
	public int size() {
		return this.list.size();
	}
	
	@Override
	public List<ConfigElement> getValue() {
		return this.list;
	}
	
	@Override
	public Iterator<ConfigElement> iterator() {
		return this.list.iterator();
	}
}
