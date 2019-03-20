package com.mafashen.structure.arithmetic;

/**
 * 回溯算法解八皇后问题
 * @author mafashen
 * created on 2019/3/20.
 */
public class EightQueen {
	static int[] rows = new int[8];

	public static void cal8Queen(int currentRow){
		//8个棋子都放好了
		if (currentRow == 8){
			for (int row : rows) {
				System.out.print(row);
			}
			System.out.println();
			return;
		}

		for (int cow = 0; cow < 8; cow++) {
			//符合条件
			if (isOk(currentRow, cow)){
				rows[currentRow] = cow;
				//开始放下一行
				cal8Queen(currentRow+1);
			}
		}
	}

	public static boolean isOk(int row, int cow){
		int leftUp = cow - 1;
		int rightUp = cow + 1;
		//从最近一行开始向上查看是否符合
		for (int i = row - 1; i >= 0; i--) {
			//第i行的cow列已经有值了
			if (rows[i] == cow){
				return false;
			}
			//左上角对角线存在值
			if (leftUp > 0 && rows[i] == leftUp){
				return false;
			}
			//右上角对角线存在值
			if (rightUp < 8 && rows[i] == rightUp){
				return false;
			}
			leftUp--;	//向上过程中,对角线列值变化
			rightUp--;
		}
		return true;
	}

	public static void main(String[] args) {
		cal8Queen(0);
	}
}
