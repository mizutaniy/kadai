package sample;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class HelloWorld {
	public static void main(String[] args){
		ArrayList<String> branchNo = new ArrayList<String>();
		ArrayList<String> branchName = new ArrayList<String>();
		
		ArrayList<String> commodityNo = new ArrayList<String>();
		ArrayList<String> commodityName = new ArrayList<String>();
		
		ArrayList<String> salesFile = new ArrayList<String>();
		
		ArrayList<Integer> branchSales = new ArrayList<Integer>();
		branchSales.add(0);
		ArrayList<Integer> commoditySales = new ArrayList<Integer>();
		
		
		try{	//支店定義読み込み
			File file = new File(args[0] + "\\branch.lst");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String s;
			while((s = br.readLine()) != null){
				String[] bran = s.split(",");
				branchNo.add(bran[0]);
				branchName.add(bran[1]);
			}
			br.close();
		} 
		catch(IOException e){
			System.out.println("支店定義ファイルが存在しません");
		
			File branchFile = new File(args[0]);
			String[] branchCheck = branchFile.list();
			for(String b : branchCheck){
				if(b.contains("branch.") == true){
					System.out.println("支店定義ファイルのフォーマットが不正です");
				}
			}
		}

		try{	//商品定義読み込み
			File file = new File(args[0] + "\\commodity.lst");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String s;
			while((s = br.readLine()) != null){
				String[] com = s.split(",");
				commodityNo.add(com[0]);
				commodityName.add(com[1]);
			}
			br.close();
		}
		catch(IOException e){
			System.out.println("商品定義ファイルが存在しません");
			
			File commodityFile = new File(args[0]);
			String[] commodityCheck = commodityFile.list();
			for(String b : commodityCheck){
				if(b.contains("commodity.") == true){
				System.out.println("商品定義ファイルのフォーマットが不正です");
				}
			}
		}
		
		File file = new File(args[0]); //売り上げファイルフォーマット確認
		String[] fileList = file.list();
		int sales1 = 0;
		for(String b : fileList){
			if(b.contains(".rcd") == true){
				String[] check = b.split("\\.");
				
				if(check[0].length() == 8){
					int sales2 = Integer.parseInt(check[0]);
					if(sales2 - sales1 != 1){
						System.out.println("売上ファイル名が連番になっていません");
					}
					sales1 = Integer.parseInt(check[0]);
				}
				
				try{	//該当ファイル読み込み
					File readFile = new File(args[0] + "\\" + b);
					FileReader fr = new FileReader(readFile);
					BufferedReader br = new BufferedReader(fr);
					String s;
					while((s = br.readLine()) != null){
						salesFile.add(s);
					}

					for(int j = 0; j < branchNo.size(); j++){
						int branchNoCheck1 = Integer.parseInt(salesFile.get(0));
						int branchNoCheck2 = Integer.parseInt(branchNo.get(j));
						if(branchNoCheck1 == branchNoCheck2){
							int k = Integer.parseInt(salesFile.get(2));
							
							int i = branchSales.get(j);
							branchSales.add(k + i);
						}
					}
					br.close();
				}
				catch(IOException e) {
					System.out.println(e);
				}
			}
		}
		
		System.out.println("--------------------------");
		for(int i = 0; i < branchNo.size(); i++){
			System.out.println(branchNo.get(i));
			System.out.println(branchName.get(i));
		}
	
		System.out.println("--------------------------");
		for(int i = 0; i < commodityNo.size(); i++){
			System.out.println(commodityNo.get(i));
			System.out.println(commodityName.get(i));
		}
		System.out.println(branchSales);
	}
}
