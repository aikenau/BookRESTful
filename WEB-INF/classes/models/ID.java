package models;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "id")
public class ID {
	private int value;

	public int getValue() {
		return value;
	}

	@XmlValue
	public void setValue(int value) {
		this.value = value;
	}
}
