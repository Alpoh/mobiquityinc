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
			response = findMostExpensivePackage(packages);
		} catch (URISyntaxException | IOException | NullPointerException e) {
			throw new APIException(e.getMessage(), e);
		} finally {
			if (Objects.nonNull(lines)) {
				lines.close();
			}
		}
		return response;
	}

	private static String findMostExpensivePackage(List<Package> packages) {
		return "-";
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
