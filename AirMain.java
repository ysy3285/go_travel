package com.proj2;

import java.util.Scanner;

import com.db.DBConn;

public class AirMain {

   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      Air ob = new Air();
      AirSound as = new AirSound();  
      
      int n;
      try {
    	  as.start();
          as.end();
          ob.airplaneBanner();
         while(true) {
            do {
               System.out.println("1. ȸ�� ���� | 2.�װ��� ���� | 3. ������� | 4. ������� | 5.���� Ȯ�� | 6. ����");
               System.out.print("���� > ");
               n = sc.nextInt();
            }while(n<1 || n > 6);
            switch(n) {
            case 1:
               ob.join();
               break;
            case 2:
               ob.book();
               
               break;
            case 3:               
               ob.updateBook();
               break;
            case 4:
               ob.deleteBook();
               break;
            case 5:               
               ob.checkBook();
               ob.ticket();

               break;
            default:
               DBConn.close();
               System.out.println("���α׷� ����");
               System.exit(0);
            }
         }
      } catch (Exception e) {
         System.out.println(e.toString());
      }
   }

}
