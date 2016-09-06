package org.distributed.client;

import org.distributed.serializable.Task;

public class TaskProcessor {

	public static final int PING = 0;
	public static final int ADD_MATRICES = 1;
	public static final int MULTIPLY_MATRICES = 2;
	public static final int DOT_PRODUCT = 3;

	public static void processTask(Task task) {
		switch (task.type) {
		case PING:
			return; // Do nothing
		case ADD_MATRICES:
			addMatrices(task);
			break;
		case MULTIPLY_MATRICES:
			multiplyMatrices(task);
			break;
		case DOT_PRODUCT:
			dotProduct(task);
			break;
		default:
			return;
		}
	}

	private static void dotProduct(Task task) {
		float[][] p = task.p, q = task.q, r = task.r;
		for(int i = 0; i < p.length; i++)
			for(int j = 0; j < p[0].length; j++)
				r[i][0] += p[i][j] * q[i][j];
	}

	private static void addMatrices(Task task) {
		float[][] p = task.p, q = task.q, r = task.r;
		for (int i = 0; i < p.length; i++) {
			for (int j = 0; j < p[i].length; j++) {
				r[i][j] = p[i][j] + q[i][j];
			}
		}
	}

	private static void multiplyMatrices(Task task) {
		float[][] a = task.p, b = task.q, c = task.r;
		int p = a.length, q = a[0].length, r = c[0].length;
		for (int i = 0; i < p; i++) {
			for (int j = 0; j < r; j++) {
				c[i][j] = 0;
				for (int k = 0; k < q; k++)
					c[i][j] += a[i][k] * b[k][j];
			}
		}
	}

}
