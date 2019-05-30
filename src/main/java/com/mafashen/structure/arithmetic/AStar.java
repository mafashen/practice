package com.mafashen.structure.arithmetic;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * A*算法 -- Dijkstra优化版
 * @author mafashen
 * created on 2019/4/6.
 */
public class AStar {

	public static class Edge{
		public int sid;	//起始顶点编号
		public int tid;	//终止顶点编号
		public int w;	//权重

		public Edge(int sid, int tid, int w) {
			this.sid = sid;
			this.tid = tid;
			this.w = w;
		}
	}

	public static class Graph{
		private LinkedList<Edge> adj[];	//邻接表
		private int v;	//顶点个数
		private Vertex[] vertices = new Vertex[this.v];

		public Graph(int v) {
			this.v = v;
			this.adj = new LinkedList[v];
			for (int i = 0; i < v; i++) {
				this.adj[i] = new LinkedList<>();
			}
		}

		public void addEdge(int s, int t, int w){
			this.adj[s].add(new Edge(s,t,w));
		}

		public void addVertex(int id, int x, int y){
			vertices[id] = new Vertex(id, x, y);
		}

		public void aStar(int s, int t){
			int[] predecessor = new int[this.v];
			PriorityQueue<Vertex> queue = new PriorityQueue<>(); 	//小顶堆
			boolean[] inQueue = new boolean[this.v];						//标记是否进入过队列

			vertices[s].f = 0;
			vertices[s].dist = 0;

			queue.add(vertices[s]);
			inQueue[s] = true;

			while (!queue.isEmpty()){
				Vertex minVertex = queue.poll();
				for (int i = 0; i < adj[minVertex.id].size(); i++) {
					Edge edge = adj[minVertex.id].get(i);
					Vertex nextVertex = vertices[edge.tid];	//minVertex --> nextVertex

					int nextDist = minVertex.dist + edge.w;
					int nextF = minVertex.dist + hManhattan(minVertex, nextVertex);

					if ((nextDist + nextF) < (minVertex.dist + minVertex.f)){
						queue.add(nextVertex);
						nextVertex.dist = nextDist;
						nextVertex.f = nextF;

						predecessor[nextVertex.id] = minVertex.id;
						if (!inQueue[nextVertex.id]){
							queue.add(nextVertex);
							inQueue[nextVertex.id] = true;
						}
					}

					if (nextVertex.id == t) break; // t
				}
			}
		}

		public int hManhattan(Vertex v1, Vertex v2) { // Vertex
			return Math.abs(v1.x - v2.x) + Math.abs(v1.y - v2.y);
		}
	}

	public static class Vertex implements Comparable<Dijkstra.Vertex>{
		public int id;		//顶点编号
		public int dist;	//从起始顶点到这个顶点的距离
		public int f;		//
		public int x , y;	//坐标

		public Vertex(int id, int x, int y) {
			this.id = id;
			this.x = x;
			this.y = y;
			this.dist = Integer.MAX_VALUE;
			this.f = Integer.MAX_VALUE;
		}

		@Override
		public int compareTo(Dijkstra.Vertex o) {
			return o.dist > this.dist ?  -1 : 1;
		}
	}

	public static void main(String[] args) {
		Graph graph = new Graph(11);
		graph.addEdge(0, 1, 20);
		graph.addEdge(1, 2, 20);
		graph.addEdge(2, 3, 10);
		graph.addEdge(0, 4, 60);
		graph.addEdge(3, 12, 40);
		graph.addEdge(4, 12, 40);
		graph.addEdge(8, 9, 50);
		graph.addEdge(5, 9, 80);
		graph.addEdge(5, 8, 70);
		graph.addEdge(0, 1, 20);
		graph.addEdge(0, 1, 20);
		graph.addEdge(0, 1, 20);
		graph.addEdge(0, 1, 20);
	}
}
