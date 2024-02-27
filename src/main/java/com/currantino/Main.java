package com.currantino;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		if (args.length != 1) {
			System.out.println("Usage: java -jar <app.jar> <input-file>");
			return;
		}

		String filename = args[0];

		Set<String> uniqueLines = new HashSet<>(readLinesFromFile(filename));

		List<List<String>> groups = createGroups(uniqueLines);
		groups.sort((group1, group2) -> Integer.compare(group2.size(), group1.size()));
		writeGroupsToFile(groups, "output.txt");
		long numGroups = groups.stream().filter(group -> group.size() > 1).count();
		System.out.println("Количество групп с более чем одним элементом: " + numGroups);
		System.out.println("Затрачено времени: " + (System.currentTimeMillis() - start) + " ms");
	}

	private static List<String> readLinesFromFile(String filename) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			List<String> lines = new ArrayList<>();
			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			reader.close();
			return lines;
		} catch (IOException e) {
			System.err.println("Could not read file " + filename);
		}
		return Collections.emptyList();
	}

	private static List<List<String>> createGroups(Set<String> uniqueLines) {
		Map<String, List<String>> groupsMap = new HashMap<>();
		for (String line : uniqueLines) {
			String[] parts = line.split(";");
			for (String part : parts) {
				if (!groupsMap.containsKey(part)) {
					groupsMap.put(part, new ArrayList<>());
				}
				groupsMap.get(part).add(line);
			}
		}
		return new ArrayList<>(groupsMap.values());
	}

	private static void writeGroupsToFile(List<List<String>> groups, String filename) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
			int groupNum = 1;
			for (List<String> group : groups) {
				if (group.size() > 1) {
					writer.write("Группа " + groupNum + "\n");
					for (String line : group) {
						writer.write(line + "\n");
					}
					writer.write("\n");
					groupNum++;
				}
			}
		} catch (IOException e) {
			System.err.println("Не удалось записать данные в файл " + filename);
		}
	}
}