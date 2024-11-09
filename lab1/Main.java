import static java.lang.Math.*;


public class Main {
	
	public static float calculateValue(short w, float x){
		if (w==14){
			return (float) pow((1-pow(cos(x)/4, 3))/(sin(cbrt(x))), 2);	
		}
		switch(w){
			case 8, 12, 16, 22:
				return (float) sin(pow(0.25 / (cos(x)+0.25), 3));
			default:
				return (float) pow(sin(cos(sin(x))), pow(3/4*(2-2/3*log(abs(x))), sin(log(abs(x)))) / 0.25);
		}
	}
	
	public static void printMatrix(float mat[][]){
		for (float[] row: mat){
			for (float value: row){
				System.out.print(String.format("%6.3f ", value));
			}
			System.out.println();
		}
	}
	
	public static void main(String []args) {

		short[] w = new short[9];
		float[] x = new float[16];
		float[][] z = new float[9][16];
		
		short a = 6;
		for (int i = 0; i<=(22-6)/2; i++){ //заполняем массив числами от 6 до 22
			w[i] = a;
			a+=2;
		}
		
		for (int i = 0; i<16; i++){ //заполняем массив рандомными числами от -2 до 2
			x[i] = (float) random()*2 - 2; //число от [-2, 2]
		}
		
		for (int i = 0; i<9; i++){
			for (int j = 0; j<16; j++){
				z[i][j] = (float) calculateValue(w[i], x[j]);
				
			}
		}
		printMatrix(z);
	}
}