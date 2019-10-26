package com.mobiquityinc.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Package {
	private static Integer MAX_ITEMS = 15;
	private static Integer MAX_WEIGHT = 100;
	private Integer index;
	private List<Item> content;
	private BigDecimal maxWeight;

	public Package() {
	}

	public Package(Integer index, List<Item> content, BigDecimal maxWeight) {
		this.index = index;
		this.content = content;
		this.maxWeight = maxWeight;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Package)) return false;

		Package aPackage = (Package) o;

		if (!Objects.equals(index, aPackage.index)) return false;
		if (!Objects.equals(content, aPackage.content)) return false;
		return Objects.equals(maxWeight, aPackage.maxWeight);
	}

	@Override
	public int hashCode() {
		int result = index != null ? index.hashCode() : 0;
		result = 31 * result + (content != null ? content.hashCode() : 0);
		result = 31 * result + (maxWeight != null ? maxWeight.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Package{" +
				"index=" + index +
				", content=" + content +
				", maxWeight=" + maxWeight +
				'}';
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
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
