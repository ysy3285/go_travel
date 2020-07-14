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
               System.out.println("1. 회원 가입 | 2.항공권 예약 | 3. 예약수정 | 4. 예약취소 | 5.예약 확인 | 6. 종료");
               System.out.print("선택 > ");
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
               System.out.println("프로그램 종료");
               System.exit(0);
            }
         }
      } catch (Exception e) {
         System.out.println(e.toString());
      }
   }

}
