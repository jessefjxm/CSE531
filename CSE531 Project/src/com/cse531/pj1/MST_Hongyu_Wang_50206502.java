package com.cse531.pj1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class MST_Hongyu_Wang_50206502 {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// input
		File input = new File("input.txt");
		Scanner sc;
		try {
			sc = new Scanner(input);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return;
		}
		int n, m;
		n = sc.nextInt();
		m = sc.nextInt();

		boolean[] visited;
		LinkedVertexList[] vList;
		ArrayList<Edge> MST = new ArrayList<>();

		Heap heap = new Heap();
		vList = new LinkedVertexList[n + 1];
		visited = new boolean[n + 1];

		// initialize nodes
		for (int i = 1; i <= n; i++) {
			vList[i] = new LinkedVertexList();
			visited[i] = false;
		}

		// save edge
		for (int i = 0, from, to, weight; i < m; i++) {
			from = sc.nextInt();
			to = sc.nextInt();
			weight = sc.nextInt();
			vList[from].addEdge(new Edge(from, to, weight));
			vList[to].addEdge(new Edge(to, from, weight));
		}

		int sum = 0;
		int count = 1;
		Edge shortestEdge;

		// save edges from 1st node
		for (int i = 0; i < vList[1].getEdgeCounts(); i++) {
			heap.add(vList[1].getEdge(i));
		}
		visited[1] = true;

		// Prim part
		while (count < n) {
			shortestEdge = heap.pop();
			if (visited[shortestEdge.getToVertex()] == true)
				continue;
			visited[shortestEdge.getToVertex()] = true;
			sum += shortestEdge.getWeight();
			MST.add(shortestEdge);
			count++;

			int toVertex = shortestEdge.getToVertex();
			for (int i = 0; i < vList[toVertex].getEdgeCounts(); i++) {
				Edge edge = vList[toVertex].getEdge(i);
				if (visited[edge.getToVertex()] == false) {
					heap.add(edge);
				}
			}
		}

		// output
		File output = new File("output.txt");
		try {
			output.createNewFile();
			FileWriter fileWriter = new FileWriter(output);
			fileWriter.write(new Integer(sum).toString());
			for (Edge e : MST) {
				fileWriter.write(e.toString());
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

class Heap {
	ArrayList<Edge> heap = new ArrayList<Edge>();

	public Heap() {
		heap.add(new Edge(0, 0, -1));
	}

	public void add(Edge node) {
		heap.add(node);
		this.up(heap.size() - 1);
	}

	public Edge pop() {
		int n = heap.size() - 1;
		if (n == 0) {
			return null;
		}

		Edge edge = heap.get(1);
		heap.set(1, heap.get(n));
		heap.remove(n);
		this.down(1);

		return edge;
	}

	public void up(int i) {
		int tIndex = i / 2;
		while (tIndex > 0) {
			if (heap.get(tIndex).getWeight() > heap.get(i).getWeight()) {
				this.swap(tIndex, i);
				i = tIndex;
			}
			tIndex /= 2;
		}
	}

	public void down(int i) {
		int tIndex = i;
		int n = heap.size();
		while (true) {
			if (i * 2 < n) {
				if (heap.get(i * 2).getWeight() < heap.get(i).getWeight()) {
					tIndex = i * 2;
				}
			}
			if (i * 2 + 1 < n) {
				if (heap.get(i * 2 + 1).getWeight() < heap.get(tIndex).getWeight()) {
					tIndex = i * 2 + 1;
				}
			}
			if (i != tIndex) {
				this.swap(tIndex, i);
				i = tIndex;
			} else {
				break;
			}
		}
	}

	public void swap(int a, int b) {
		Edge edge = heap.get(a);
		heap.set(a, heap.get(b));
		heap.set(b, edge);
	}
}

class LinkedVertexList {
	private LinkedList<Edge> edges = new LinkedList<Edge>();

	public int getEdgeCounts() {
		return edges.size();
	}

	public void addEdge(Edge to) {
		edges.addLast(to);
	}

	public Edge getEdge(int i) {
		return edges.get(i);
	}
}

class Edge {
	private int fromVertex;
	private int toVertex;
	private int weight;

	Edge(int fromVertex, int toVertex, int weightVertex) {
		this.fromVertex = fromVertex;
		this.toVertex = toVertex;
		this.weight = weightVertex;
	}

	public int getWeight() {
		return weight;
	}

	public int getFromVertex() {
		return fromVertex;
	}

	public int getToVertex() {
		return toVertex;
	}

	public String toString() {
		return fromVertex + " " + toVertex;
	}
}