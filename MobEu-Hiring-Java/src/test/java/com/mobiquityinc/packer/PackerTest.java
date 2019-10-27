package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class PackerTest {

	private final String testingPath = "/desarrollo/fuentes/mobiquityinc/MobEu-Hiring-Java/src/main/resources/";

	private String data;

	private static final String expectedData = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)"+System.lineSeparator() +
			"8 : (1,15.3,€34)"+System.lineSeparator() +
			"75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)"+System.lineSeparator() +
			"56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)"+System.lineSeparator();


	public void readFile() throws APIException, IOException {
		data = Packer.pack(testingPath+"fileTest.txt");
		Assert.assertEquals(expectedData.trim(), data.trim());
	}

	public void processFile() {
//		Stream<String> lines = Packer.getLines();
//		if (Objects.nonNull(lines)) {
//			lines.forEach(l -> System.out.println("::line:: " + l));
//		} else {
//			System.out.println("::empty or null:: ");
//		}
	}

	@Test
	public void bestValueOneLine() throws APIException, IOException {
		String expectedOutput = "4";
		String data = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
		String output = Packer.pack(testingPath+"fileOneLineTest.txt");
		Assert.assertEquals(expectedOutput, output);

	}

	@Test
	public void bestValueFile() throws APIException, IOException {
		String expectedOutput = "4-2,78,9";
		String output = Packer.pack(testingPath+"fileTest.txt");
		Assert.assertEquals(expectedOutput, output);
	}

	@Test(expected = IOException.class)
	public void fileDoesntExist() throws APIException, IOException {
		data = Packer.pack(testingPath+"fileWrongTest.txt");
		Assert.assertNotNull(data);
	}

	@Test(expected = APIException.class)
	public void wrongCapacity() throws APIException, IOException {
		data = Packer.pack(testingPath+ "bfileTest.txt");
		Assert.assertNotNull(data);
	}

	@Test(expected = APIException.class)
	public void wrongNumberOfItems() throws APIException, IOException {
		data = Packer.pack(testingPath+ "cfileTest.txt");
		Assert.assertNotNull(data);
	}
}
