package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class SalesTotallingSystem2 {
	public static void main(String[] args){
		HashMap<String, String> branchDate = new HashMap<String, String>();
		HashMap<String, String> commodityDate = new HashMap<String, String>();
		HashMap<String, Integer> branchSalesDate = new HashMap<String, Integer>();
		HashMap<String, Integer> commoditySalesDate = new HashMap<String, Integer>();
		
		ArrayList<String> readList = new ArrayList<String>();
		ArrayList<String> salesFile = new ArrayList<String>();

		try{	//支店定義読み込み
			File file = new File(args[0],"branch.lst");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String readDate;
			while((readDate = br.readLine()) != null){
				
				if(readDate.contains(",") == false){
					System.out.println("支店定義ファイルのフォーマットが不正です");
					br.close();
					return;
				}
				
				String[] bran = readDate.split(",");
				branchDate.put(bran[0], bran[1]);
				
				if(bran[0].length() != 3 || bran.length != 2){
					System.out.println("支店定義ファイルのフォーマットが不正です");
					br.close();
					return;
				}
			}
			br.close();
		} catch(IOException e) {
			System.out.println("支店定義ファイルが存在しません");
			return;
		}

		try{	//商品定義読み込み
			File file = new File(args[0], "commodity.lst");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String readDate;
			while((readDate = br.readLine()) != null){
				
				if(readDate.contains(",") == false){
					System.out.println("商品定義ファイルのフォーマットが不正です");
					br.close();
					return;
				}
				
				String[] com = readDate.split(",");
				commodityDate.put(com[0], com[1]);
				
				if(com[0].length() != 8 || com.length != 2){
					System.out.println("商品定義ファイルのフォーマットが不正です");
					br.close();
					return;
				}
			}
			br.close();
		} catch(IOException e) {
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
				File readFile = new File(args[0], readList.get(j));
				FileReader fr = new FileReader(readFile);
				BufferedReader br = new BufferedReader(fr);
				String date;
				String[] readSales = new String[5];
				int s = 0;
				while((date = br.readLine()) != null){
					readSales[s]  = date;
					s++;
				}
				
				if(readSales.length >= 4){
					System.out.println(readList.get(j) + "のフォーマットが不正です");
					br.close();
					return;
				}
				
				if(readSales[2].length() >= 10){
					System.out.println("合計金額が10桁を超えました");
					br.close();
					return;
				}
				
				
				if(branchSalesDate.containsKey(readSales[0]) == true){
					int branchTotal = branchSalesDate.get(readSales[0]);
					branchTotal += Integer.parseInt(readSales[2]);
					
					int figures = Math.max(branchTotal, 999999999);	//桁確認
					if(figures > 999999999){
						System.out.println("合計金額が10桁を超えました");
						br.close();
						return;
					}
					
					branchSalesDate.put(readSales[0], branchTotal);
				} else {
					System.out.println(readList.get(j) + "の支店コードが不正です");
					br.close();
					return;
				}
				
				if(commoditySalesDate.containsKey(readSales[1]) == true){
					int commodityTotal = commoditySalesDate.get(readSales[1]);
					commodityTotal += Integer.parseInt(readSales[2]);
					
					int figures = Math.max(commodityTotal, 999999999);	//桁確認
					if(figures > 999999999){
						System.out.println("合計金額が10桁を超えました");
						br.close();
						return;
					}
					
					commoditySalesDate.put(readSales[1], commodityTotal);
				} else {
					System.out.println(readList.get(j) + "の商品コードが不正です");
					br.close();
					return;
				}
				br.close();
			} catch(IOException e) {
				System.out.println(e);
			}
		}
		Collections.sort(branchSalesDate.get("001"));
	    Collections.reverse(branchSalesDate.get("001"));

	}
}