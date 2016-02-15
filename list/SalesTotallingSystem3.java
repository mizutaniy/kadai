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


public class SalesTotallingSystem3 {
	public static ArrayList<String> readFile(String filePlace, String fileName, String outName){
		ArrayList<String> readDataList = new ArrayList<String>();
		String readData;
		try{	//定義読み込み
			File file = new File(filePlace ,fileName);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			while((readData = br.readLine()) != null){
				readDataList.add(readData);
			}
			br.close();
		} catch(IOException e) {
			if(outName == "支店" || outName == "商品"){
				System.out.println(outName + "定義ファイルが存在しません");
			}
			System.exit(1);
		}
		return readDataList;
	}

	public static String[] checkData(String outName, String readData){
		if(readData.contains(",") == false){
			System.out.println(outName + "定義ファイルのフォーマットが不正です");
			System.exit(1);
		}
		
		String[] splitData = readData.split(",");

		if(outName == "支店"){
			if(splitData[0].length() != 3 || splitData.length != 2){
				System.out.println(outName + "定義ファイルのフォーマットが不正です");
				System.exit(1);
			} else if(outName == "商品"){
				if(splitData[0].length() != 8 || splitData.length != 2){
					System.out.println("商品定義ファイルのフォーマットが不正です");
					System.exit(1);
				}
			}
		}
		return splitData;
	}
	
	public static HashMap<String, Integer> checkSalesList(HashMap<String, String> numberList, ArrayList<String> salesFile, int total, HashMap<String, Integer> totalList, String fileName, String dataName){
		int i = 0;
		if(dataName == "商品"){
			i = 1;
		}
		if(numberList.containsKey(salesFile.get(i)) == true){
			if(totalList.get(salesFile.get(i)) != null){
				total = totalList.get(salesFile.get(0));
			} else {
				total = 0;
			}
			
			total += Integer.parseInt(salesFile.get(2));
			
			int figures = Math.max(total, 999999999);	//桁確認
			if(figures > 999999999){
				System.out.println("合計金額が10桁を超えました");
				System.exit(1);
			}
			totalList.put(salesFile.get(i), total);
		} else {
			System.out.println(fileName + "の" + dataName + "コードが不正です");
			System.exit(1);
		}
		return totalList;
	}
	
	public static void outputData(HashMap<String, Integer> outputList,HashMap<String, String> listData, String filePlace, String fileName){
		List<Map.Entry<String, Integer>> branchSortList = new ArrayList<Map.Entry<String, Integer>>(outputList.entrySet());
		Collections.sort(branchSortList, new Comparator <Map.Entry<String, Integer>>(){
			public int compare(
					Entry<String, Integer> entry1, Entry<String, Integer> entry2){
				return ((Integer) entry2.getValue()).compareTo((Integer) entry1.getValue());
			}
		});
		try{
			File outFile = new File(filePlace + fileName);
			FileWriter fw = new FileWriter(outFile);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (Entry<String, Integer> s : branchSortList) {
				bw.write(s.getKey() + "," + listData.get(s.getKey()) + "," + s.getValue() + "\r\n");
			}
			bw.close();
		} catch(IOException e) {
			System.out.println(e);
		}

	}
	
	public static void main(String[] args){
		HashMap<String, String> branchData = new HashMap<String, String>();
		HashMap<String, String> commodityData = new HashMap<String, String>();
		HashMap<String, Integer> branchSalesData = new HashMap<String, Integer>();
		HashMap<String, Integer> commoditySalesData = new HashMap<String, Integer>();
		
		ArrayList<String> readDataList = new ArrayList<String>();
		ArrayList<String> readList = new ArrayList<String>();
		ArrayList<String> salesFile = new ArrayList<String>();
		int branchTotal = 0;
		int commodityTotal = 0;
		String[] correctData;
		

		readDataList = readFile(args[0], "branch.lst", "支店");
			for(int i = 0; i < readDataList.size(); i++){
				correctData = checkData("支店", readDataList.get(i));
				branchData.put(correctData[0], correctData[1]);
			}
		readDataList = readFile(args[0] ,"commodity.lst", "商品");
			for(int i = 0; i < readDataList.size(); i++){
				correctData = checkData("商品", readDataList.get(i));
				commodityData.put(correctData[0], correctData[1]);
			}

		File file = new File(args[0]); //売り上げファイルフォーマット確認
		String[] fileList = file.list();
		int sales1 = 0;
		
		for(int i = 0; i < fileList.length; i++){
			String readFile = fileList[i];
			if(readFile.contains(".rcd") == true){
				String[] check = readFile.split("\\.");

				if(check[0].length() != 8){
					break;
				} else {
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
			salesFile = readFile(args[0] ,readList.get(j), readList.get(j));
				if(salesFile.size() != 3){
					System.out.println(readList.get(j) + "のフォーマットが不正です");
					System.exit(1);
				}
				
				if(salesFile.get(2).length() >= 10){
					System.out.println("合計金額が10桁を超えました");
					System.exit(1);
				}
				System.out.println("◎" +branchData);
				System.out.println("★" +salesFile);
				System.out.println("□" +branchTotal);
				System.out.println("▲" +branchSalesData);
				System.out.println("◎" +commodityData);
				System.out.println("□" +commodityTotal);
				System.out.println("▲" +commoditySalesData);
				branchSalesData = checkSalesList(branchData, salesFile, branchTotal, branchSalesData, readList.get(j), "支店");
				commoditySalesData = checkSalesList(commodityData, salesFile, commodityTotal, commoditySalesData, readList.get(j), "商品");

				while(salesFile.size() != 0){
					salesFile.remove(0);
				}
		}
		
		outputData(branchSalesData, branchData, args[0], "branch.out");
		outputData(commoditySalesData, commodityData, args[0], "commodity.out");

	}
}