package com.sheffield.ecommerce.models;

import java.io.Serializable;

public class Edition implements Serializable{
	private static final long serialVersionUID = 7969482339199686289L;
	private int editionId;
	private Volume volume;
	private String name; //TODO remove this. Created purely for testing. Also remove from xml 
	
	public int getEditionId() {
		return editionId;
	}

	public void setEditionId(int editionId) {
		this.editionId = editionId;
	}

	public Volume getVolume() {
		return volume;
	}
	
	public void setVolume(Volume volume) {
		this.volume = volume;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
