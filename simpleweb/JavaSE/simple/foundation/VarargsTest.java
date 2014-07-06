package simple.foundation;

import java.util.Arrays;

import org.junit.Test;


public class VarargsTest {
	
	@Test
	public void testNull(){
		querySecond(0,0);
	}
	
	/**
	 * long[]...可接受二维数组
	 */
	@Test
	public void test1(){
		long[][]refIDs = new long[2][];
		
		long[]refID1 = new long[2];
		refID1[0] = 11;
		refID1[1] = 112;
		
		long[]refID2 = new long[2];
		refID2[0] = 4;
		refID2[1] = 5;
		
		refIDs[0] = refID1;
		refIDs[1] = refID2;
		
		querySecond(0,0, refIDs);
	}
	
	/**
	 * long[]...也可以可接受一维数组
	 */
	@Test
	public void test2(){
		long[]refID1 = new long[2];
		refID1[0] = 11;
		refID1[1] = 112;
				
		queryFirst(0, 0, refID1);
	}
	
	/**
	 * Long[] 和long[] 的大区别测试
	 */
	@Test
	public void test3(){
		Long[] refID = new Long[]{11L,1L,3L,3L};
		long[] temp = new long[]{12L,1L,2L,2L};
		
		System.out.println(Arrays.asList(refID));
		System.out.println(Arrays.asList(temp));
		
	}
	
	/**
	 * long... 类型做为参数，也能传给long[]...
	 * @param serviceId
	 * @param refType
	 * @param refID
	 */
	public static void queryFirst(int serviceId,int refType, long... refID){
		querySecond(serviceId, refType, refID);
		return;
	}
	
	public static void querySecond(int serviceId,int refType, long[]... refID){
		for (int i = 0; i < refID.length; i++) {
			long[] r = refID[i];
			for (int j = 0; r != null && j < r.length; j++) {
				System.out.print(r[j] + ",");
			}
			System.out.println();
		}

		return;
	}
	
}
