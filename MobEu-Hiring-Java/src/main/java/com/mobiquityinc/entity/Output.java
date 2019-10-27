package com.mobiquityinc.entity;

import java.util.Objects;

public class Output {
	private String solution;

	public Output() {
	}

	public Output(String solution) {
		this.solution = solution;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Output)) return false;

		Output output = (Output) o;

		return Objects.equals(solution, output.solution);
	}

	@Override
	public int hashCode() {
		return solution != null ? solution.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "Output{" +
				"solution='" + solution + '\'' +
				'}';
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
}
