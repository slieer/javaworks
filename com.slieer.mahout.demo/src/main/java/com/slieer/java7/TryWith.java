package com.slieer.java7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * http://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.
 * html
 * 
 * @author root
 */
public class TryWith {
	public static void readFirstLineFromFile(String path) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		}
	}

	public static void writeToFileZipFileContents(String zipFileName,
			String outputFileName) throws java.io.IOException {

		Charset charset = StandardCharsets.US_ASCII;
		java.nio.file.Path outputFilePath = java.nio.file.Paths
				.get(outputFileName);

		// Open zip file and create output file with
		// try-with-resources statement

		try (ZipFile zf = new ZipFile(zipFileName);
				BufferedWriter writer = Files.newBufferedWriter(outputFilePath,
						charset)) {
			// Enumerate each entry
			for (Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zf
					.entries(); entries.hasMoreElements();) {
				// Get the entry name and write it to the output file
				String newLine = System.getProperty("line.separator");
				String zipEntryName = ((java.util.zip.ZipEntry) entries
						.nextElement()).getName() + newLine;
				writer.write(zipEntryName, 0, zipEntryName.length());
			}
		}
	}
}
