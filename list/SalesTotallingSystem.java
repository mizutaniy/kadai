package sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class SalesTotallingSystem {
	public static void main(String[] args){
		ArrayList<String> branchNo = new ArrayList<String>();
		ArrayList<String> branchName = new ArrayList<String>();

		ArrayList<String> commodityNo = new ArrayList<String>();
		ArrayList<String> commodityName = new ArrayList<String>();

		ArrayList<String> readList = new ArrayList<String>();
		ArrayList<String> salesFile = new ArrayList<String>();

		int[] branchSales = new int[10];
		int[] commoditySales = new int[10];

		try{	//支店定義読み込み
			File file = new File(args[0],"branch.lst");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String s;
			while((s = br.readLine()) != null){
				
				if(s.contains(",") == false){
					System.out.println("支店定義ファイルのフォーマットが不正です");
					br.close();
					return;
				}
				
				String[] bran = s.split(",");
				branchNo.add(bran[0]);
				branchName.add(bran[1]);
				
				if(bran[0].length() > 3 || bran.length != 2){
					System.out.println("支店定義ファイルのフォーマットが不正です");
					br.close();
					return;
				}
			}
			br.close();
		}
		catch(IOException e){
			System.out.println("支店定義ファイルが存在しません");
			return;
		}

		try{	//商品定義読み込み
			File file = new File(args[0] + "\\commodity.lst");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String s;
			while((s = br.readLine()) != null){
				
				if(s.contains(",") == false){
					System.out.println("商品定義ファイルのフォーマットが不正です");
					br.close();
					return;
				}
				
				String[] com = s.split(",");
				commodityNo.add(com[0]);
				commodityName.add(com[1]);
				
				if(com[0].length() > 8 || com.length != 2){
					System.out.println("商品定義ファイルのフォーマットが不正です");
					br.close();
					return;
				}
			}
			br.close();
		}
		catch(IOException e){
			System.out.println("商品定義ファイルが存在しません");
			return;
		}

		File file = new File(args[0]); //売り上げファイルフォーマット確認
		String[] fileList = file.list();
		int sales1 = 0;
		
		for(int i = 0; i < fileList.length; i++){
			String readFile = fileList[i];
			if(readFile.contains(".rcd") == true){
				String[] check = readFile.split("\\.");

				if(check[0].length() == 8){
					int sales2 = Integer.parseInt(check[0]);
					if(sales2 - sales1 != 1){
						System.out.println("売上ファイル名が連番になっていません");
						return;
					}
					sales1 = Integer.parseInt(check[0]);
					readList.add(readFile);
				}
			}
		}

		for(int j = 0; j < readList.size(); j++){	//該当ファイル読み込み
			try{
				File readFile = new File(args[0] + "\\" + readList.get(j));
				FileReader fr = new FileReader(readFile);
				BufferedReader br = new BufferedReader(fr);
				String s;
				while((s = br.readLine()) != null){
					salesFile.add(s);
				}

				int k = 0;	// 支店別合計
				for(int i = 0; i < branchNo.size(); i++){
					if(salesFile.get(0).equals(branchNo.get(i))){
						int sales = Integer.parseInt(salesFile.get(2));
						branchSales[i] += sales;
						k = 1;

						int sLeng = Math.max(branchSales[i], 999999999);	//桁確認
						if(sLeng > 999999999){
							System.out.println("合計金額が10桁を超えました");
							return;
						}
					}
				}

				if(k == 0){
					System.out.println(readList.get(j) + "の支店コードが不正です");
					br.close();
					return;
				}

				k = 0;	//商品別合計
				for(int i = 0; i < commodityNo.size(); i++){
					if(salesFile.get(1).equals(commodityNo.get(i))){
						int sales = Integer.parseInt(salesFile.get(2));
						commoditySales[i] += sales;
						k = 1;

						int sLeng = Math.max(commoditySales[i], 999999999);	//桁確認
						if(sLeng > 999999999){
							System.out.println("合計金額が10桁を超えました");
							return;
						}
					}

				}

				if(k == 0){
					System.out.println(readList.get(j) + "の商品コードが不正です");
					br.close();
					return;
				}
				if(salesFile.size() >= 4){
					System.out.println(readList.get(j) + "のフォーマットが不正です");
					br.close();
					return;
				}
				while(salesFile.size() != 0){
					salesFile.remove(0);
				}
				br.close();
			}
			catch(IOException e) {
				System.out.println(e);
			}
		}

		try{
			File outFile = new File(args[0],"branch.out");
			FileWriter fw = new FileWriter(outFile);
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i = 0; i < branchNo.size(); i++){
				bw.write(branchNo.get(i) + "," + branchName.get(i) + "," + branchSales[i] + "\r\n");
			}
			bw.close();
		}
		catch(IOException e){
			System.out.println(e);
		}

		try{
			File outFile = new File(args[0],"commodity.out");
			FileWriter fw = new FileWriter(outFile);
			BufferedWriter bw = new BufferedWriter(fw);
			for(int i = 0; i < commodityNo.size(); i++){
				bw.write(commodityNo.get(i) + "," + commodityName.get(i) + "," + commoditySales[i] + "\r\n");
			}
			bw.close();
		}
		catch(IOException e){
			System.out.println(e);
		}
	}
}