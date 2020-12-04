package comp3111.popnames;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TopNNames_Test {

	@Test
	public void testGetNameFromIndexValidCheck1() {
		TopNNames tester = new TopNNames(2000, 2005, "M", 5 , "usa", "human");
		String name1 = tester.getNameFromIndex(0);
		String name2 = tester.getNameFromIndex(1);
		String name3 = tester.getNameFromIndex(2);
		String name4 = tester.getNameFromIndex(3);
		String name5 = tester.getNameFromIndex(4);
		assertTrue(name1.equals("Jacob"));
		assertTrue(name2.equals("Michael"));
		assertTrue(name3.equals("Joshua"));
		assertTrue(name4.equals("Matthew"));
		assertTrue(name5.equals("Andrew"));
	}
	
	@Test
	public void testGetNameFromIndexValidCheck2() {
		TopNNames tester = new TopNNames(1880, 2000, "F", 5 , "usa", "human");
		String name1 = tester.getNameFromIndex(0);
		String name2 = tester.getNameFromIndex(1);
		String name3 = tester.getNameFromIndex(2);
		String name4 = tester.getNameFromIndex(3);
		String name5 = tester.getNameFromIndex(4);
		assertTrue(name1.equals("Mary"));
		assertTrue(name2.equals("Patricia"));
		assertTrue(name3.equals("Linda"));
		assertTrue(name4.equals("Elizabeth"));
		assertTrue(name5.equals("Barbara"));
	}
	
	@Test
	public void testGetNameFromIndexInalidCheck1() {
		TopNNames tester = new TopNNames(1975, 2001, "M", 5 , "usa", "human");
		String name1 = tester.getNameFromIndex(-2);
		String name2 = tester.getNameFromIndex(-1);
		String name3 = tester.getNameFromIndex(0);
		String name4 = tester.getNameFromIndex(1);
		String name5 = tester.getNameFromIndex(2);
		String name6 = tester.getNameFromIndex(1000000);
		assertTrue(name1.equals("-1"));
		assertTrue(name2.equals("-1"));
		assertTrue(name3.equals("Michael"));
		assertTrue(name4.equals("Christopher"));
		assertTrue(name5.equals("Matthew"));
		assertTrue(name6.equals("-1"));
	}
	
	@Test
	public void testGetNameFromIndexInalidCheck2() {
		TopNNames tester = new TopNNames(1975, 2001, "F", 5 , "usa", "human");
		String name1 = tester.getNameFromIndex(-2);
		String name2 = tester.getNameFromIndex(-1);
		String name3 = tester.getNameFromIndex(0);
		String name4 = tester.getNameFromIndex(1);
		String name5 = tester.getNameFromIndex(2);
		assertTrue(name1.equals("-1"));
		assertTrue(name2.equals("-1"));
		assertTrue(name3.equals("Jessica"));
		assertTrue(name4.equals("Jennifer"));
		assertTrue(name5.equals("Ashley"));
	}
	
	@Test
	public void testGetFrequencyFromIndexValidCheck1() {
		TopNNames tester = new TopNNames(1966, 1971, "M", 5 , "usa", "human");
		int freq1 = tester.getFrequencyFromIndex(0);
		int freq2 = tester.getFrequencyFromIndex(1);
		int freq3 = tester.getFrequencyFromIndex(2);
		int freq4 = tester.getFrequencyFromIndex(3);
		int freq5 = tester.getFrequencyFromIndex(4);
		assertTrue(freq1 == 492634);
		assertTrue(freq2 == 375631);
		assertTrue(freq3 == 363950);
		assertTrue(freq4 == 356459);
		assertTrue(freq5 == 340175);
	}
	
	@Test
	public void testGetFrequencyFromIndexValidCheck2() {
		TopNNames tester = new TopNNames(1923, 1999, "F", 5 , "usa", "human");
		int freq1 = tester.getFrequencyFromIndex(0);
		int freq2 = tester.getFrequencyFromIndex(1);
		int freq3 = tester.getFrequencyFromIndex(2);
		int freq4 = tester.getFrequencyFromIndex(3);
		int freq5 = tester.getFrequencyFromIndex(4);
		assertTrue(freq1 == 2975812);
		assertTrue(freq2 == 1537945);
		assertTrue(freq3 == 1435506);
		assertTrue(freq4 == 1389229);
		assertTrue(freq5 == 1376958);
	}
	
	@Test
	public void testGetFrequencyFromIndexInalidCheck1() {
		TopNNames tester = new TopNNames(1945, 1989, "M", 5 , "usa", "human");
		int freq1 = tester.getFrequencyFromIndex(-2);
		int freq2 = tester.getFrequencyFromIndex(-1);
		int freq3 = tester.getFrequencyFromIndex(0);
		int freq4 = tester.getFrequencyFromIndex(1);
		int freq5 = tester.getFrequencyFromIndex(2);
		int freq6 = tester.getFrequencyFromIndex(1000000);
		assertTrue(freq1 == -1);
		assertTrue(freq2 == -1);
		assertTrue(freq3 == 3275814);
		assertTrue(freq4 == 2761633);
		assertTrue(freq5 == 2632544);
		assertTrue(freq6 == -1);
	}
	
	@Test
	public void testGetFrequencyFromIndexInalidCheck2() {
		TopNNames tester = new TopNNames(1901, 1908, "F", 5 , "usa", "human");
		int freq1 = tester.getFrequencyFromIndex(-2);
		int freq2 = tester.getFrequencyFromIndex(-1);
		int freq3 = tester.getFrequencyFromIndex(0);
		int freq4 = tester.getFrequencyFromIndex(1);
		int freq5 = tester.getFrequencyFromIndex(2);
		assertTrue(freq1 == -1);
		assertTrue(freq2 == -1);
		assertTrue(freq3 == 125542);
		assertTrue(freq4 == 53836);
		assertTrue(freq5 == 45258);
	}
	
	@Test
	public void testGetNameIndexValidName() {
		TopNNames tester = new TopNNames(1900, 2000, "F", 500 , "usa", "human");
		int index1 = tester.getNameIndex("Pat");
		int index2 = tester.getNameIndex("Katrina");
		assertTrue(index1 == 499);
		assertTrue(index2 == 304);
	}
	
	@Test
	public void testGetNameIndexInvalidName() {
		TopNNames tester = new TopNNames(1900, 2000, "F", 500 , "usa", "human");
		int index1 = tester.getNameIndex("SomeBogusName");
		int index2 = tester.getNameIndex("WhatKindOfNameIsSoap");
		assertTrue(index1 == -1);
		assertTrue(index2 == -1);
	}
	
}
