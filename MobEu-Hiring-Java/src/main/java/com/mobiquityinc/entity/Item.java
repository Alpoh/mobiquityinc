package com.mobiquityinc.entity;

import java.math.BigDecimal;

public class Item {
	private Integer index;
	private BigDecimal weight;
	private BigDecimal price;

	public Item() {
	}

	public Item(Integer index, BigDecimal weight, BigDecimal price) {
		this.index = index;
		this.weight = weight;
		this.price = price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Item)) return false;

		Item item = (Item) o;

		if (getIndex() != null ? !getIndex().equals(item.getIndex()) : item.getIndex() != null) return false;
		if (getWeight() != null ? !getWeight().equals(item.getWeight()) : item.getWeight() != null) return false;
		return getPrice() != null ? getPrice().equals(item.getPrice()) : item.getPrice() == null;
	}

	@Override
	public int hashCode() {
		int result = getIndex() != null ? getIndex().hashCode() : 0;
		result = 31 * result + (getWeight() != null ? getWeight().hashCode() : 0);
		result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Item{" +
				"index=" + index +
				", weight=" + weight +
				", price=" + price +
				'}';
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
