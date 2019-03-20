package com.mafashen.structure.arithmetic;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * @author mafashen
 * created on 2019/3/18.
 */
public class HuffmanCoding {

	public static HuffmanNode  huffmanCoding(Map<Character, Integer> characterWeightMap){
		HuffmanNode[] charWeightWarps = new HuffmanNode[characterWeightMap.size()];
		int i = 0;
		for (Map.Entry<Character, Integer> entry : characterWeightMap.entrySet()) {
			charWeightWarps[i++] = new HuffmanNode(entry.getKey(), entry.getValue());
		}
		Comparator<HuffmanNode> comparator = new Comparator<HuffmanNode>() {
			@Override
			public int compare(HuffmanNode o1, HuffmanNode o2) {
				return o2.weight - o1.weight;
			}
		};

		while (charWeightWarps.length > 1){
			//Fixme sort 可以改成第一次sort后,后续的插入新节点 选择排序
			Arrays.sort(charWeightWarps,comparator);
			int oldLength = charWeightWarps.length;
			HuffmanNode left = charWeightWarps[oldLength - 1];
			HuffmanNode right = charWeightWarps[oldLength -2];
			Integer parentWeight = left.getWeight() + right.getWeight();
			HuffmanNode parent = new HuffmanNode(null, parentWeight);
			parent.setLeft(left);
			parent.setRight(right);
			charWeightWarps[oldLength - 2] = parent;
			charWeightWarps[oldLength - 1] = null;
			charWeightWarps = Arrays.copyOf(charWeightWarps, oldLength - 1);

			//build tree
		}

		//权重升序排序
		//每次生成新的节点,插入排序
		print(charWeightWarps[0], "");
		return charWeightWarps[0];
	}

	private static void print(HuffmanNode root, String prefix){
		if(root.getLeft() != null){
			print(root.getLeft(), prefix + "0");
		}
		if (root.getRight() != null){
			print(root.getRight(), prefix + "1");
		}
		if (root.getCharacter() != null){
			System.out.printf("%c => %s \n", root.getCharacter(), prefix);
		}
	}

	@Data
	static class HuffmanNode {
		Character character;
		int weight;
		boolean leaf;
		HuffmanNode left;
		HuffmanNode right;

		public HuffmanNode(Character key, int value) {
			this.character = key;
			this.weight = value;
		}
	}

	public static void main(String[] args) {
		Map<Character, Integer> weightMap = new HashMap<>();
		weightMap.put('f', 40);
		weightMap.put('e', 30);
		weightMap.put('d', 60);
		weightMap.put('c', 90);
		weightMap.put('b', 350);
		weightMap.put('a', 650);

		huffmanCoding(weightMap);
	}
}
