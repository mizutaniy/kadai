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
			String filePlace, String fileName, String outName, int wordCount, String condition){
		BufferedReader br =null;
		try{	//定義読み込み
			File file = new File(filePlace, fileName);
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			String readData;
			while((readData = br.readLine()) != null){
				if(readData.lastIndexOf(",") != wordCount || !readData.matches(condition)){
					System.out.println(outName + "定義ファイルのフォーマットが不正です");
					return false;
				}

				String[] splitData = readData.split(",");
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


	public static boolean totalSales(HashMap<String, Long> totalSales, String readNumberDate,
			String readSalesDate, String readFile){
		if(totalSales.containsKey(readNumberDate) == true){
			long total = totalSales.get(readNumberDate);
			total += Long.parseLong(readSalesDate);

			if(String.valueOf(total).length() > 10){
				System.out.println("合計金額が10桁を超えました");
				return false;
			}
			totalSales.put(readNumberDate, total);
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
			public int compare(Entry<String, Long> entry1, Entry<String, Long> entry2){
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

		if(args.length == 0){
			System.out.println("コマンドライン引数にディレクトリが指定されていないためファイルを読み込めません");
			return;
		}
		if(!readFile(branchData, branchSalesData, args[0], "branch.lst", "支店", 3, "^[0-9]{3},.+$")){
			return;
		}
		if(!readFile(commodityData, commoditySalesData, args[0], "commodity.lst", "商品", 8, "^[0-9a-zA-Z]{8},.+$")){
			return;
		}

		File file = new File(args[0]); //売り上げファイルフォーマット確認
		File[] fileList = file.listFiles();
		for(int i = 0; i < fileList.length; i++){
			String readFile = fileList[i].getName();
			if(readFile.matches("^[0-9]{8}.rcd$") && fileList[i].isFile() == true) {
				String[] check = readFile.split("\\.");
					int number = Integer.parseInt(check[0]);
					if(number != i + 1){
						System.out.println("売上ファイル名が連番になっていません");
						return;
					}
			} else {
				break;
			}

			BufferedReader br = null;
			try{
				File readData = new File(args[0], readFile);//該当ファイル読み込み
				FileReader fr = new FileReader(readData);
				br = new BufferedReader(fr);
				String s;
				while((s = br.readLine()) != null){
					salesFile.add(s);
				}
				if(salesFile.size() != 3 || !salesFile.get(2).matches("^[0-9]{1,10}$")){
					System.out.println(readFile + "のフォーマットが不正です");
					return;
				}

				if(!totalSales(branchSalesData, salesFile.get(0), salesFile.get(2), readFile)){
					return;
				}
				if(!totalSales(commoditySalesData, salesFile.get(1), salesFile.get(2), readFile)){
					return;
				}
				while(salesFile.size() != 0){
					salesFile.remove(0);
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

		if(!outputData(branchSalesData, branchData, args[0], "branch.out")){
			return;
		}
		if(!outputData(commoditySalesData, commodityData, args[0], "commodity.out")){
			return;
		}
	}
}