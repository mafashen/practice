package com.mafashen.structure.arithmetic;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * @author mafashen
 * created on 2019/3/29.
 */
public class Dijkstra {

	public static class Graph{
		private LinkedList<Edge> adj[];	//邻接表
		private int v;	//顶点个数

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

		public void dijkstra(int s, int t){
			int[] predecessor = new int[this.v];	//用来还原最短路径
			Vertex[] vertexes = new Vertex[this.v];	//记录起始顶点到这个顶点的距离

			for (int i = 0; i < v; i++) {
				vertexes[i] = new Vertex(i, Integer.MAX_VALUE);	//初始化为无穷大
			}

			PriorityQueue<Vertex> queue = new PriorityQueue<>(); //小顶堆
			boolean[] inQueue = new boolean[this.v];			//标记是否进入过队列

			queue.add(vertexes[s]);	//先把起始顶点放入队列中
			vertexes[s].dist = 0;
			inQueue[s] = true;

			while (!queue.isEmpty()){
				Vertex minVertex = queue.poll();	//取dist最小的顶点
				if (minVertex.id == t){
					break;		//到达目的顶点,最短路径产生
				}

				for (int i = 0; i < adj[minVertex.id].size(); i++) {
					Edge e = adj[minVertex.id].get(i);	//取出一条与minVertex顶点相连的边
					Vertex nextVertex = vertexes[e.tid];	//minVertex --> nextVertex

					//找到一条到nextVertex更短的路径
					if (minVertex.dist + e.w < nextVertex.dist){
						nextVertex.dist = minVertex.dist + e.w;		//更新dist
						predecessor[nextVertex.id] = minVertex.id;	//更新前驱节点

						if (!inQueue[nextVertex.id]){	//如果没有在队列中
							queue.add(nextVertex);
							inQueue[nextVertex.id] = true;
						}
					}
				}
			}

			//输出
			System.out.print(s);
			print(s, t, predecessor);
		}

		public void print(int s, int t, int[] predecessor){
			if (s == t)
				return;
			print(s, predecessor[t], predecessor);
			System.out.print(" -> " + t);
		}
	}

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

	public static class Vertex implements Comparable<Vertex>{
		public int id;	//顶点编号
		public int dist;	//从起始顶点到这个顶点的距离

		public Vertex(int id, int dist) {
			this.id = id;
			this.dist = dist;
		}

		@Override
		public int compareTo(Vertex o) {
			return o.dist > this.dist ?  -1 : 1;
		}
	}

	public static void main(String[] args) {
		Graph graph = new Graph(6);
		graph.addEdge(0, 1, 10);
		graph.addEdge(0, 4, 15);
		graph.addEdge(1, 2, 15);
		graph.addEdge(1, 3, 2);
		graph.addEdge(2, 5, 5);
		graph.addEdge(3, 2, 1);
		graph.addEdge(3, 5, 12);
		graph.addEdge(4, 5, 10);

		graph.dijkstra(0, 5);
	}
}
