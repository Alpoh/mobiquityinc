package com.mobiquityinc.packer;

import com.mobiquityinc.entity.Item;
import com.mobiquityinc.entity.Package;
import com.mobiquityinc.exception.APIException;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Packer {

	public Packer() {
	}

	public static String pack(String filePath) throws APIException {
		Path path;
		List<Package> packages = new ArrayList<>();
		String response = "";
		Stream<String> lines = null;
		try {
			path = Paths.get(Packer.class.getClassLoader().getResource(filePath).toURI());
			lines = Files.lines(path);
			lines.forEach(line -> packages.addAll(mapLineToPackage(line.toString())));
			response = findMostExpensiveAndHeaviestPackage(packages);
		} catch (URISyntaxException | IOException | NullPointerException e) {
			throw new APIException(e.getMessage(), e);
		} finally {
			if (Objects.nonNull(lines)) {
				lines.close();
			}
		}
		return response;
	}

	private static String getOutput(List<Package> packages) {
		String output = "";
		packages.stream().forEach(pckage -> output.concat(findMostExpensiveAndHeaviestPackage(pckage)));
		return "-";
	}

	private static String findMostExpensiveAndHeaviestPackage(Package pckage) {
		return findMostExpensive(findAllWeights(pckage));
	}

	private static String findAllWeights(Package pckage) {
		int NB_ITEMS = pckage.getItems().size();
		// we use a matrix to store the max value at each n-th item
		int[][] matrix = new int[NB_ITEMS + 1][Integer.valueOf(pckage.getCapacity()) + 1];
		// first line is initialized to 0
		for (int i = 0; i <= pckage.getCapacity(); i++)
			matrix[0][i] = 0;
		// we iterate on items
		for (int i = 1; i <= NB_ITEMS; i++) {
			// we iterate on each pckage.getCapacity()
			for (int j = 0; j <= pckage.getCapacity(); j++) {
				if (items[i - 1].weight > j)
					matrix[i][j] = matrix[i - 1][j];
				else
					// we maximize value at this rank in the matrix
					matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i - 1][j - items[i - 1].weight] +
							items[i - 1].value);
			}
		}
		return "";
	}

	private static String findMostExpensive(String weights) {
		int res = matrix[NB_ITEMS][capacity];
		int w = capacity;
		List<Item> itemsSolution = new ArrayList<>();

		for (int i = NB_ITEMS; i > 0  &&  res > 0; i--) {
			if (res != matrix[i-1][w]) {
				itemsSolution.add(items[i-1]);
				// we remove items value and weight
				res -= items[i-1].value;
				w -= items[i-1].weight;
			}
		}
		return "";
	}

	private static List<Package> mapLineToPackage(String line) {
		List<Package> packagesList = null;
		String[] capacityAndItems = line.trim().split(" : ");
		String capacity = capacityAndItems[0];
		String items = capacityAndItems[1];
		packagesList.add(mapStringToPackage(capacity, items));
		return packagesList;
	}

	private static Package mapStringToPackage(String capacity, String items) {
		return new Package(mapItems(items), new BigDecimal(capacity));
	}

	private static List<Item> mapItems(String items) {
		List<Item> itemsList = null;
		String[] stringItems = items.trim()
				.replace("(", "")
				.replace(")", "")
				.replace("€", "")
				.split(" ");
		for (String stringItem : stringItems) {
			String[] arrayItem = stringItem.split(",");
			itemsList.add(mapArrayItemToItem(arrayItem));
		}
		return itemsList;
	}

	private static Item mapArrayItemToItem(String[] arrayItem) {
		return new Item(new Integer(arrayItem[0]), new BigDecimal(arrayItem[1]), new BigDecimal(arrayItem[2]));
	}
}
