import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
       
       System.out.print("Masukkan Nama File (Namafile): ");
       String filename = scanner.nextLine();
       String filePath = "../test/" + filename + ".txt";

       try {
           File file = new File(filePath);
           Scanner fileScanner = new Scanner(file);
           
           String[] dimensions = fileScanner.nextLine().split(" ");
           int rows = Integer.parseInt(dimensions[0]);
           int cols = Integer.parseInt(dimensions[1]);
           int jumlahblock = Integer.parseInt(dimensions[2]);
           
           String Jeniskasus = fileScanner.nextLine();
           
           if (!Jeniskasus.equals("DEFAULT")){
                System.out.println("Jenis kasus yang Valid: DEFAULT");
                System.exit(0);
           }
           
           // List untuk menyimpan semua blok puzzle
           List<List<String>> Blocks = new ArrayList<>();
           List<String> currentBlock = new ArrayList<>();
           String currentLetter = "";

           int countblock = 0;
           
           while (fileScanner.hasNextLine()) {
               String line = fileScanner.nextLine().trim();
               
               if (!line.isEmpty()) {
                   String firstLetter = String.valueOf(line.charAt(0));
                   
                   if (!firstLetter.equals(currentLetter)) {
                       if (!currentBlock.isEmpty()) {
                           Blocks.add(new ArrayList<>(currentBlock));
                           currentBlock.clear();
                           countblock++;
                       }
                       currentLetter = firstLetter;
                   }
                   
                   currentBlock.add(line);
               }
           }
           
           countblock++;
           if (!currentBlock.isEmpty()) {
               Blocks.add(new ArrayList<>(currentBlock));
           }
           
           fileScanner.close();

           if(countblock != jumlahblock){
               System.out.println("Jumlah blok yang dimasukkan tidak sesuai");
               System.exit(0);
           }
           
           // ubah blok yang dibaca menjadi objek Block
           List<Block> blocks = new ArrayList<>();
           for (List<String> blockLines : Blocks) {
               if (!blockLines.isEmpty()) {
                   char id = blockLines.get(0).charAt(0);
                   blocks.add(new Block(blockLines, id));
               }
           }
           
           Board board = new Board(rows, cols);
           Solver solver = new Solver(board, blocks);
           
           System.out.println("\n=== Starting Solver ===");
           long startTime = System.nanoTime();
           boolean solved = solver.solve();
           long endTime = System.nanoTime();
           long executionTime = (endTime - startTime) / 1000000;
           
           if (solved && board.isBoardFull()) {
               System.out.println("\nSolusi ditemukan:");
               board.printBoard();
               System.out.println("\nWaktu pencarian: " + executionTime + " ms");
               System.out.println("Banyak kasus yang ditinjau: " + solver.getIterationCount());
               
               System.out.print("\nApakah anda ingin menyimpan solusi? (y/n): ");
               String savefile = scanner.nextLine();
               
               if (savefile.equalsIgnoreCase("y")) {
                   System.out.print("Masukkan nama file (Hasil.txt): ");
                   String FileName = scanner.nextLine();
                   saveSolution(board, solver.getIterationCount(), executionTime, FileName);
                   System.out.println("Solusi berhasil disimpan ke " + FileName);
               }
           } else {
               System.out.println("\nTidak ditemukan solusi.");
               System.out.println("Waktu pencarian: " + executionTime + " ms");
               System.out.println("Banyak kasus yang ditinjau: " + solver.getIterationCount());
           }
           
       } catch (FileNotFoundException e) {
           System.out.println("File tidak ditemukan: " + filePath);
       } catch (Exception e) {
           System.out.println("Terjadi kesalahan: " + e.getMessage());
           e.printStackTrace();
       }
       
       scanner.close();
   }
   
   private static void saveSolution(Board board, long iterations, long executionTime, String fileName) {
       try {
           FileWriter writer = new FileWriter("../test/" + fileName + ".txt");
           writer.write(board.toString() + "\n");
           writer.write("Waktu pencarian: " + executionTime + " ms\n");
           writer.write("Banyak kasus yang ditinjau: " + iterations + "\n");
           writer.close();
       } catch (IOException e) {
           System.out.println("Gagal menyimpan solusi: " + e.getMessage());
       }
   }
}