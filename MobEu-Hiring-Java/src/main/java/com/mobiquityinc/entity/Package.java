package com.mobiquityinc.entity;

import java.math.BigDecimal;
import java.util.List;

public class Package {
	private static Integer MAX_ITEMS = 15;
	private static Integer MAX_WEIGHT = 100;
	private List<Item> content;
	private BigDecimal maxWeight;

	public Package() {
	}

	public Package(List<Item> content, BigDecimal maxWeight) {
		this.content = content;
		this.maxWeight = maxWeight;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Package)) return false;

		Package aPackage = (Package) o;

		if (getContent() != null ? !getContent().equals(aPackage.getContent()) : aPackage.getContent() != null)
			return false;
		return getMaxWeight() != null ? getMaxWeight().equals(aPackage.getMaxWeight()) : aPackage.getMaxWeight() == null;
	}

	@Override
	public int hashCode() {
		int result = getContent() != null ? getContent().hashCode() : 0;
		result = 31 * result + (getMaxWeight() != null ? getMaxWeight().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Package{" +
				"content=" + content +
				", maxWeight=" + maxWeight +
				'}';
	}

	public List<Item> getContent() {
		return content;
	}

	public void setContent(List<Item> content) {
		this.content = content;
	}

	public BigDecimal getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(BigDecimal maxWeight) {
		this.maxWeight = maxWeight;
	}
}
