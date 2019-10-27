package com.mobiquityinc.packer;

import com.mobiquityinc.entity.Item;
import com.mobiquityinc.entity.Output;
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
import java.util.stream.Collectors;

public class Packer {

	public Packer() {
	}

	public static String pack(String filePath) throws APIException {
		Path path;
		List<Package> packages = new ArrayList<>();
		String response = "";
		List<String> lines = null;
		try {
			path = Paths.get(Packer.class.getClassLoader().getResource(filePath).toURI());
			lines = Files.lines(path).collect(Collectors.toList());
			for (String line : lines) {
				packages.add(mapLineToPackage(line));
			}
//			lines.forEach(line -> packages.add(mapLineToPackage(line.toString())));
			response = getOutput(packages);
		} catch (URISyntaxException | IOException | NullPointerException e) {
			throw new APIException(e.getMessage(), e);
		}
		return response;
	}

	private static String getOutput(List<Package> packages) {
		String output = "";
		for (Package pckage : packages) {
			output += findMostExpensiveAndHeaviestPackage(pckage).getSolution();
		}
		//I TRIED TO USE A LAMBDA BUT DIDNT WORK SO I CHANGE TO A FOR
		//		packages.forEach(pckage-> output.concat(findMostExpensiveAndHeaviestPackage(pckage).getSolution()));
		return output;
	}

	private static Output findMostExpensiveAndHeaviestPackage(Package pckage) {
		return findAllWeights(pckage);
	}

	private static Output findAllWeights(Package pckage) {
		int NB_ITEMS = pckage.getItems().size();
		// we use a matrix to store the max value at each n-th item
		int[][] matrix = new int[NB_ITEMS + 1][pckage.getCapacity().intValue() + 1];
		// first line is initialized to 0
		for (int i = 0; i <= pckage.getCapacity().intValue(); i++)
			matrix[0][i] = 0;
		// we iterate on items
		for (int i = 1; i <= NB_ITEMS; i++) {
			// we iterate on each pckage.getCapacity()
			for (int j = 0; j <= pckage.getCapacity().intValue(); j++) {
				if (pckage.getItems().get(i - 1).getWeight().intValue() > j)
					matrix[i][j] = matrix[i - 1][j];
				else
					// we maximize value at this rank in the matrix
					matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i - 1][j - pckage.getItems().get(i - 1).getWeight().intValue()] +
							pckage.getItems().get(i - 1).getPrice().intValue());
			}
		}
		return findMostExpensive(matrix, NB_ITEMS, pckage);
	}

	private static Output findMostExpensive(int[][] matrix, int NB_ITEMS, Package pckage) {
		int res = matrix[NB_ITEMS][pckage.getCapacity().intValue()];
		int w = pckage.getCapacity().intValue();
		List<Item> itemsSolution = new ArrayList<>();
		for (int i = NB_ITEMS; i > 0 && res > 0; i--) {
			if (res != matrix[i - 1][w]) {
				itemsSolution.add(pckage.getItems().get(i - 1));
				// we remove items value and weight
				res -= pckage.getItems().get(i - 1).getPrice().intValue();
				w -= pckage.getItems().get(i - 1).getWeight().intValue();
			}
		}
		if (Objects.isNull(itemsSolution) || itemsSolution.isEmpty()) {
			return new Output("-");
		}
		String output = "";
		for (int i = itemsSolution.size() - 1; i >= 0; i--) {
			output += " " + itemsSolution.get(i).getIndex();
		}
		return new Output(output.trim().replace(" ", ","));
	}

	private static Package mapLineToPackage(String line) {
		Package pckage = null;
		String[] capacityAndItems = line.trim().split(" : ");
		String capacity = capacityAndItems[0];
		String items = capacityAndItems[1];
		pckage = mapStringToPackage(capacity, items);
		return pckage;
	}

	private static Package mapStringToPackage(String capacity, String items) {
		return new Package(mapItems(items), new BigDecimal(capacity));
	}

	private static List<Item> mapItems(String items) {
		List<Item> itemsList = new ArrayList<>();
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
