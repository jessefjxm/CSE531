package com.cse531.pj2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class LCS_Hongyu_Wang_50206502 {
	private static HashSet<String> lcsResults;
	private static int[][] map;

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// input
		File input = new File("input.txt");
		Scanner sc;
		try {
			sc = new Scanner(input);
		} catch (FileNotFoundException e1) {
			System.out.println(e1.getMessage());
			return;
		}
		String s1 = sc.nextLine();
		String s2 = sc.nextLine();

		map = new int[s1.length() + 1][s2.length() + 1];
		lcsResults = new HashSet<>();
		LCS(s1, s2);
		getLCSResult(s1, s2, s1.length(), s2.length(), "");
		
		// output
		File output = new File("output.txt");
		try {
			output.createNewFile();
			FileWriter fileWriter = new FileWriter(output);
			int len = 0;
			if (lcsResults != null && lcsResults.size() > 0) {
				for (String s : lcsResults) {
					if(len == 0){
						len = s.length();
						fileWriter.write(new Integer(len).toString());
					}
					fileWriter.write(System.getProperty("line.separator"));
					fileWriter.write(s);
				}
			} else {
				fileWriter.write(new Integer(0).toString());
			}
			fileWriter.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}

	}

	public static void LCS(String s1, String s2) {
		// calculate LCS
		for (int i = 0; i < s1.length() + 1; i++) {
			for (int j = 0; j < s2.length() + 1; j++) {
				if (i == 0 || j == 0) // initialize map
					map[i][j] = 0;
				else if (s1.charAt(i - 1) == s2.charAt(j - 1))
					map[i][j] = map[i - 1][j - 1] + 1;
				else
					map[i][j] = Math.max(map[i - 1][j], map[i][j - 1]);
			}
		}
	}

	private static void getLCSResult(String s1, String s2, int i, int j, String res) {
		// generate result
		while (i > 0 && j > 0) {
			if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
				res += s1.charAt(i - 1);
				i--;
				j--;
			} else {
				if (map[i - 1][j] > map[i][j - 1])
					i--;
				else if (map[i - 1][j] < map[i][j - 1])
					j--;
				else { // branch results
					getLCSResult(s1, s2, i - 1, j, res);
					getLCSResult(s1, s2, i, j - 1, res);
					return;
				}
			}
		}
		lcsResults.add(new StringBuffer(res).reverse().toString());
	}
}