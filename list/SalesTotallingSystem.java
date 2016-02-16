package sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class SalesTotallingSystem {
	public static boolean readFile(HashMap<String, String> list, HashMap<String, Integer>salesList, String filePlace, String fileName, String outName){
		try{	//定義読み込み
			File file = new File(filePlace,fileName);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String readData;
			while((readData = br.readLine()) != null){
				if(readData.contains(",") == false){
					System.out.println(outName + "定義ファイルのフォーマットが不正です");
					br.close();
					return false;
				}
				String[] splitData = readData.split(",");
				if(splitData.length != 2){
					System.out.println(outName + "定義ファイルのフォーマットが不正です");
					br.close();
					return false;
				}
				list.put(splitData[0], splitData[1]);
				salesList.put(splitData[0], 0);
				
				if(outName == "支店"){
					if(splitData[0].length() != 3 || splitData.length != 2){
						System.out.println("支店定義ファイルのフォーマットが不正です");
						br.close();
						return false;
					}
				}
				
				if(outName == "商品"){
					if(splitData[0].length() != 8 || splitData.length != 2){
						System.out.println("商品定義ファイルのフォーマットが不正です");
						br.close();
						return false;
					}
				}
			}
			br.close();
		} catch(IOException e) {
			System.out.println(outName + "定義ファイルが存在しません");
			return false;
		}
		return true;
	}
	
	public static boolean totalSales(HashMap<String, Integer> totalSales, ArrayList<String> readSales, String readFile, String fileName){
		int i = 0;
		if(fileName == "商品"){
			i = 1;
		}
		if(totalSales.containsKey(readSales.get(i)) == true){
			int total = totalSales.get(readSales.get(i));
			total += Integer.parseInt(readSales.get(2));
	
			int figures = Math.max(total, 999999999);	//桁確認
			if(figures > 999999999){
				System.out.println("合計金額が10桁を超えました");
				return false;
			}
			
			totalSales.put(readSales.get(i), total);
		} else {
			System.out.println(readFile + "の支店コードが不正です");
			return false;
		}
		return true;
	}
	
	public static boolean outputData(HashMap<String, Integer> outputList,HashMap<String, String> listData, String filePlace, String fileName){
		List<Map.Entry<String, Integer>> branchSortList = new ArrayList<Map.Entry<String, Integer>>(outputList.entrySet());
		Collections.sort(branchSortList, new Comparator <Map.Entry<String, Integer>>(){
			public int compare(
				Entry<String, Integer> entry1, Entry<String, Integer> entry2){
				return ((Integer) entry2.getValue()).compareTo((Integer) entry1.getValue());
			}
		});
		try{
			File outFile = new File(filePlace, fileName);
			FileWriter fw = new FileWriter(outFile);
			BufferedWriter bw = new BufferedWriter(fw);
			for (Entry<String, Integer> s : branchSortList) {
				bw.write(s.getKey() + "," + listData.get(s.getKey()) + "," + s.getValue() + "\r\n");
			}
			bw.close();
		} catch(IOException e) {
			System.out.println(e);
			return false;
		}
		return true;
	}
	
	public static void main(String[] args){
		HashMap<String, String> branchData = new HashMap<String, String>();
		HashMap<String, String> commodityData = new HashMap<String, String>();
		HashMap<String, Integer> branchSalesData = new HashMap<String, Integer>();
		HashMap<String, Integer> commoditySalesData = new HashMap<String, Integer>();
		
		ArrayList<String> salesFile = new ArrayList<String>();
		boolean j;
		
		if(args.length == 0){
			System.out.println("コマンドライン引数の設定なし");
			return;
		}
		j = readFile(branchData, branchSalesData, args[0], "branch.lst", "支店");
		if(j == false){
			return;
		}
		j = readFile(commodityData, commoditySalesData, args[0], "commodity.lst", "商品");
		if(j == false){
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
					if(sales2 - sales1 == 1 || (sales2 == 00000002 && sales1 == 0)){
						sales1 = Integer.parseInt(check[0]);
					} else {
						System.out.println("売上ファイル名が連番になっていません");
						return;
					}
					
				} else {
					break;
				}
			} else {
				break;
			}
			
			try{
				File readData = new File(args[0], readFile);//該当ファイル読み込み
				FileReader fr = new FileReader(readData);
				BufferedReader br = new BufferedReader(fr);
				String s;
				while((s = br.readLine()) != null){
					salesFile.add(s);
				}
				if(salesFile.size() != 3){
					System.out.println(readFile + "のフォーマットが不正です");
					br.close();
					return;
				}
				if(salesFile.get(2).length() >= 10){
					System.out.println("合計金額が10桁を超えました");
					br.close();
					return;
				}

				j = totalSales(branchSalesData, salesFile, readFile, "支店");
				if(j == false){
					br.close();
					return;
				}
				j = totalSales(commoditySalesData, salesFile, readFile, "商品");
				if(j == false){
					br.close();
					return;
				}
				while(salesFile.size() != 0){
					salesFile.remove(0);
				}
				br.close();
			} catch(IOException e) {
				System.out.println(e);
				return;
			}
		}
		j = outputData(branchSalesData, branchData, args[0], "branch.out");
		if(j == false){
			return;
		}
		j = outputData(commoditySalesData, commodityData, args[0], "commodity.out");
		if(j == false){
			return;
		}
	}
}