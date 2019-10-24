package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Packer {

	public Packer() {
	}

	public static String pack(String filePath) throws APIException {
	  Path path;
      String data;
      Stream<String> lines = null;
		try {
			path = Paths.get(Packer.class.getClassLoader().getResource(filePath).toURI());
			lines = Files.lines(path);
			data = lines.collect(Collectors.joining("\n"));
		} catch (URISyntaxException | IOException e) {
			throw new APIException(e.getMessage(), e);
		} finally {
          lines.close();
        }
		return data;
	}
}
