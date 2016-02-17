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
	public static boolean readFile(HashMap<String, String> list, HashMap<String, Long>salesList,
			String filePlace, String fileName, String outName){
		BufferedReader br =null;
		try{	//定義読み込み
			File file = new File(filePlace, fileName);
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			String readData;
			while((readData = br.readLine()) != null){
				if(outName == "支店"){
					if(readData.lastIndexOf(",") != 3){
						System.out.println(outName + "定義ファイルのフォーマットが不正です");
						return false;
					}
				} else if(outName == "商品"){
					if(readData.lastIndexOf(",") != 8){
						System.out.println(outName + "定義ファイルのフォーマットが不正です");
						return false;
					}
				}
				
				String[] splitData = readData.split(",");
				if(splitData.length != 2){
					System.out.println(outName + "定義ファイルのフォーマットが不正です");
					return false;
				}

				if(outName == "支店"){
					if(!splitData[0].matches("^[0-9]{3}$")) {
						System.out.println("支店定義ファイルのフォーマットが不正です");
						return false;
					}
				} else if(outName == "商品"){
					if(!splitData[0].matches("^[0-9a-zA-Z]{8}$")) {
						System.out.println("商品定義ファイルのフォーマットが不正です");
						return false;
					}
				}

				list.put(splitData[0], splitData[1]);
				salesList.put(splitData[0], (long)0);
			}
		} catch(IOException e) {
			System.out.println(outName + "定義ファイルが存在しません");
			return false;
		} finally {
			 try {
				 if(br != null){
					 br.close();
				 }
			 } catch (IOException e) {
				 System.out.println(e);
			 }
		}
		return true;
	}


	public static boolean totalSales(HashMap<String, Long> totalSales, ArrayList<String> readSales, 
			String readFile, String fileName){
		int i = 0;
		if(fileName == "商品"){
			i = 1;
		}
			if(totalSales.containsKey(readSales.get(i)) == true){
				long total = totalSales.get(readSales.get(i));
				total += Long.parseLong(readSales.get(2));

				if(String.valueOf(total).length() > 10){
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


	public static boolean outputData(HashMap<String, Long> outputList,HashMap<String, String> listData,
			String filePlace, String fileName){
		String lineS = System.lineSeparator();
		List<Map.Entry<String, Long>> branchSortList = new ArrayList<Map.Entry<String, Long>>(outputList.entrySet());
		Collections.sort(branchSortList, new Comparator <Map.Entry<String, Long>>(){
			public int compare(
				Entry<String, Long> entry1, Entry<String, Long> entry2){
				return ((Long) entry2.getValue()).compareTo((Long) entry1.getValue());
			}
		});
		BufferedWriter bw = null;
		try{
			File outFile = new File(filePlace, fileName);
			FileWriter fw = new FileWriter(outFile);
			bw = new BufferedWriter(fw);
			for (Entry<String, Long> s : branchSortList) {
				bw.write(s.getKey() + "," + listData.get(s.getKey()) + "," + s.getValue() + lineS);
			}
			bw.close();
		} catch(IOException e) {
			System.out.println(e);
			return false;
		} finally {
			 try {
				 if(bw != null){
					 bw.close();
				 }
			 } catch (IOException e) {
				 System.out.println(e);
			 }
		}
		return true;
	}


	public static void main(String[] args){
		HashMap<String, String> branchData = new HashMap<String, String>();
		HashMap<String, String> commodityData = new HashMap<String, String>();
		HashMap<String, Long> branchSalesData = new HashMap<String, Long>();
		HashMap<String, Long> commoditySalesData = new HashMap<String, Long>();
		ArrayList<String> salesFile = new ArrayList<String>();
		boolean j;

		if(args.length == 0){
			System.out.println("コマンドライン引数にディレクトリが指定されていないためファイルを読み込めません");
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
		int remainder = 0;
		for(int i = 0; i < fileList.length; i++){
			String readFile = fileList[i];
			if(readFile.contains(".rcd") == true){
				String[] check = readFile.split("\\.");
				if(check[0].length() == 8){
					int number = Integer.parseInt(check[0]);
					if(i == 0){
						remainder = number - i;
					}
					if(number - i != remainder){
						System.out.println("売上ファイル名が連番になっていません");
						return;
					}
				} else {
					break;
				}
			} else {
				break;
			}

			BufferedReader br = null;
			try{
				File readData = new File(args[0], readFile);//該当ファイル読み込み
				if(readData.isFile() == true){
					FileReader fr = new FileReader(readData);
					br = new BufferedReader(fr);
					String s;
					while((s = br.readLine()) != null){
						salesFile.add(s);
					}
					if(salesFile.size() != 3){
						System.out.println(readFile + "のフォーマットが不正です");
						return;
					}
					if(!salesFile.get(2).matches("^[0-9]{1,10}$")) {
						System.out.println(readFile + "のフォーマットが不正です");
						return;
					}

					j = totalSales(branchSalesData, salesFile, readFile, "支店");
					if(j == false){
						return;
					}
					j = totalSales(commoditySalesData, salesFile, readFile, "商品");
					if(j == false){
						return;
					}
					while(salesFile.size() != 0){
						salesFile.remove(0);
					}
				} else{
					continue;
				}
			} catch(IOException e) {
				System.out.println(e);
				return;
			} finally {
				 try {
					 if(br != null){
						 br.close();
					 }
				 } catch (IOException e) {
					 System.out.println(e);
				 }
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