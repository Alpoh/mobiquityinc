package com.mobiquityinc.packer;

import com.mobiquityinc.entity.Item;
import com.mobiquityinc.entity.Package;
import com.mobiquityinc.exception.APIException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Packer {

	private static Stream<String> lines = null;

	public Packer() {
	}

	public static String pack(String filePath) throws APIException {
		Path path;
		String data;
		try {
			path = Paths.get(Packer.class.getClassLoader().getResource(filePath).toURI());
			lines = Files.lines(path);
			data = lines.collect(Collectors.joining("\n"));
		} catch (URISyntaxException | IOException | NullPointerException e) {
			throw new APIException(e.getMessage(), e);
		} finally {
			lines.close();
		}
		return data;
	}

	private static List<Package> mapPackages() {
		List<Package> packages = null;
		return packages;
	}

	private static List<Item> mapItems() {
		List<Item> items = null;
		return items;
	}

	public static Stream<String> getLines() {
		return lines;
	}

	public static void setLines(Stream<String> lines) {
		Packer.lines = lines;
	}
}
