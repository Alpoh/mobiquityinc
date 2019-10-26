package com.mobiquityinc.entity;

import java.math.BigDecimal;
import java.util.List;

public class Package {
	private static Integer MAX_ITEMS = 15;
	private static Integer MAX_WEIGHT = 100;
	private List<Item> items;
	private BigDecimal capacity;

	public Package() {
	}

	public Package(List<Item> content, BigDecimal capacity) {
		this.items = content;
		this.capacity = capacity;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Package)) return false;

		Package aPackage = (Package) o;

		if (getItems() != null ? !getItems().equals(aPackage.getItems()) : aPackage.getItems() != null)
			return false;
		return getCapacity() != null ? getCapacity().equals(aPackage.getCapacity()) : aPackage.getCapacity() == null;
	}

	@Override
	public int hashCode() {
		int result = getItems() != null ? getItems().hashCode() : 0;
		result = 31 * result + (getCapacity() != null ? getCapacity().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Package{" +
				"content=" + items +
				", capacity=" + capacity +
				'}';
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public BigDecimal getCapacity() {
		return capacity;
	}

	public void setCapacity(BigDecimal capacity) {
		this.capacity = capacity;
	}
}
