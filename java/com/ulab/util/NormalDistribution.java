package com.ulab.util;

public class NormalDistribution {
	public static double calc(double u) {
		double y = Math.abs(u);
		double y2 = y * y;
		double z = Math.exp(-0.5 * y2) * 0.398942280401432678;
		double p = 0;
		int k = 28;
		double s = -1;
		double fj = k;

		if (y > 3) {
			// 当y>3时
			for (int i = 1; i <= k; i++) {
				p = fj / (y + p);
				fj = fj - 1.0;
			}
			p = z / (y + p);
		} else {
			// 当y<3时
			for (int i = 1; i <= k; i++) {
				p = fj * y2 / (2.0 * fj + 1.0 + s * p);
				s = -s;
				fj = fj - 1.0;
			}
			p = 0.5 - z * y / (1 - p);
		}
		if (u > 0)
			p = 1.0 - p;
		return p;
	}
	/**
	 * 
	 * @time   2017年5月18日 下午6:35:32
	 * @author zuoqb
	 * @todo   标准正态分布密度函数公式
	 * @param  @param x：原始数据
	 * @param  @param u:均值
	 * @param  @param a：方差
	 * @param  @return
	 * @return_type   double
	 */
	public static double calc(double x,double u,double a) {
		double p=1/(a*Math.sqrt(2*Math.PI))*Math.exp(-Math.pow((x-u),2)/(2*Math.pow(a,2)));
		return p;
	}

	public static void main(String[] args) {
		for (double d = 0; d < 5.0; d += 0.1) {
			System.out.println(d + "," + calc(d));
		}
	}
}
