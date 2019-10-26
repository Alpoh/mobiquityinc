package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;
import java.util.stream.Stream;

@SpringBootTest
@RunWith(JUnit4ClassRunner.class)
public class PackerTest {

	private String data;

	private static final String expectedData = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)\n" +
			"8 : (1,15.3,€34)\n" +
			"75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)\n" +
			"56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)\n";

	@Test
	public void readFile() throws APIException {
		data = Packer.pack("fileTest.txt");
		Assert.assertEquals(expectedData.trim(), data.trim());
	}

	@Test
	public void processFile() {
		Stream<String> lines = Packer.getLines();
		if (Objects.nonNull(lines)) {
			lines.forEach(l -> System.out.println("::line:: " + l));
		} else {
			System.out.println("::empty or null:: ");
		}
	}

	@Test(expected = NullPointerException.class)
	public void wrongFile() throws NullPointerException, APIException {
		data = Packer.pack("fileWrongTest.txt");
		Assert.assertNotNull(data);
	}

	@Test(expected = APIException.class)
	public void wrongParameters() throws APIException {
		data = Packer.pack("bfileWrongTest.txt");
		Assert.assertNotNull(data);
	}
}
