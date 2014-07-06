package simple.foundation;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * http://www.exampledepot.com/egs/java.util.zip/DecompArray.html
 * @author slieer
 */
public class CompressByteArr {
	public static void main(String... args){
		String str = "some some bytes to compress";
		compressByteArr(str);
	}
	
	public static void deCompressByteArr(byte[] compressedData){
	// Create the decompressor and give it the data to compress
		Inflater decompressor = new Inflater();
		decompressor.setInput(compressedData);

		// Create an expandable byte array to hold the decompressed data
		ByteArrayOutputStream bos = new ByteArrayOutputStream(compressedData.length);

		// Decompress the data
		byte[] buf = new byte[1024];
		while (!decompressor.finished()) {
		    try {
		        int count = decompressor.inflate(buf);
		        bos.write(buf, 0, count);
		    } catch (DataFormatException e) {
		    }
		}
		try {
		    bos.close();
		} catch (IOException e) {
		}

		// Get the decompressed data
		byte[] decompressedData = bos.toByteArray();
		
	}

	/**
	 * 压缩string.
	 */
	public static void compressByteArr(String inputStr) {
		byte[] input = inputStr.getBytes();

	// Create the compressor with highest level of compression
	Deflater compressor = new Deflater();
	compressor.setLevel(Deflater.BEST_COMPRESSION);

	// Give the compressor the data to compress
	compressor.setInput(input);
	compressor.finish();

	// Create an expandable byte array to hold the compressed data.
	// You cannot use an array that's the same size as the orginal because
	// there is no guarantee that the compressed data will be smaller than
	// the uncompressed data.
	ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);

	// Compress the data
	byte[] buf = new byte[1024];
	while (!compressor.finished()) {
	    int count = compressor.deflate(buf);
	    bos.write(buf, 0, count);
	}
	try {
	    bos.close();
	} catch (IOException e) {
	}

//Get the compressed data
	byte[] compressedData = bos.toByteArray();
	}
}
