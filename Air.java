package com.proj2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Air {
	Scanner sc = new Scanner(System.in);
	AirDAO dao = new AirDAO();
	Random rm = new Random();
	List<CustomDTO> lista = new ArrayList<CustomDTO>();
	List<BookVO> listb = new ArrayList<BookVO>();

	// 회원 가입
	public void join() {
		try {

			CustomDTO cdto = new CustomDTO();
			System.out.print("아이디 : ");
			cdto.setId(sc.next());

			while (true) {
				System.out.print("비밀번호 : ");
				cdto.setPw(sc.next());
				System.out.print("비밀번호 확인 : ");
				String checkpw = sc.next();
				if (!cdto.getPw().equals(checkpw)) {
					System.out.println("비밀번호가 일치하지 않습니다.");
				} else {
					break;
				}
			}
			System.out.print("이름 : ");
			cdto.setName(sc.next());
			System.out.print("특별시/도 : ");
			cdto.setAddr1(sc.next());
			System.out.print("구/시 : ");
			cdto.setAddr2(sc.next());
			System.out.print("나머지 세부 주소 : ");
			cdto.setAddr3(sc.next());
			System.out.print("전화번호(숫자만 입력) : ");
			cdto.setTel(sc.next());
			cdto.setGrade("실버");

			lista.add(cdto);

			int result = dao.insertJoin(cdto);
			if (result != 0) {
				System.out.println("회원 가입을 축하합니다*^_^*");

			} else
				System.out.println("insert 실패");

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// 로그인
	public String login() {
		while(true) {
			System.out.print("아이디 > ");
			String id = sc.next();
			System.out.print("비밀번호 > ");
			String pw = sc.next();

			CustomDTO dto = dao.loingData(id, pw);

			if (dto != null) {
				System.out.println(dto.getName() + "님 환영합니다!");
				return id; 
			} else {
				System.out.println("로그인에 실패 하였습니다.");
			}
		}
	}

	// 선택지
	public void city(String city) {
		if(city.equals("김포")) {
			System.out.println("  =================================================================================================");
			System.out.println("||★ 국내 > 부산, 제주                                                                             ||");
			System.out.println("||★ 동북아시아 > 삿포로                                                                           ||");
			System.out.println("  =================================================================================================");
		}else if(city.equals("인천")) {
			System.out.println("  =================================================================================================");
			System.out.println("||★ 동북아시아 > 광저우, 베이징, 도쿄, 상하이, 시즈오카, 후쿠오카                         ||");
			System.out.println("||★ 동남아시아 > 자카르타, 하노이, 다낭, 싱가포르, 델리, 뭄바이, 하얼빈, 방갈로, 호치민시, 두바이 ||");
			System.out.println("||★ 유럽 > 헬싱키,모스크바, 소치, 로마, 바르셀로나, 마드리드, 파리                                ||");
			System.out.println("||★ 미주 > 뉴올리언스, 콰테말라시티, 벤쿠버, 토론토, 마이애미, 뉴욕, 부에노스 아이레스            ||");
			System.out.println("  =================================================================================================");
		}
	}

	// 예약
	public void book() {
		try {
			CustomDTO cdto = new CustomDTO();
			BookVO vo = new BookVO();
			String id = login();

			List<CustomDTO> clist = dao.searchName(id);
			Iterator<CustomDTO> itcn = clist.iterator();
			while(itcn.hasNext()) {
				cdto  = itcn.next();
				vo.setName(cdto.getName());
				vo.setId(cdto.getId());
			}

			System.out.print("출국날짜를 입력해주세요(ex.yy-mm-dd) : ");
			vo.setS_day(sc.next());
			System.out.println("  =========== ");
			System.out.println("||★인천/김포||");
			System.out.println("  =========== ");
			System.out.print("출발지 : ");
			vo.setDepart(sc.next());
			city(vo.getDepart());
			System.out.print("도착지 : ");
			vo.setArrive(sc.next());
			search(vo.getDepart(), vo.getArrive());

			System.out.print("편명 : ");
			vo.setAircraft_num(sc.next());
			String reno = Integer.toString(rm.nextInt(9999) + 1); // 예약 번호
			vo.setReno(reno);

			List<FlightDTO> fplist = dao.searchEnd(vo.getAircraft_num());
			Iterator<FlightDTO> itf = fplist.iterator();
			while (itf.hasNext()) {
				FlightDTO fpdto = itf.next();
				vo.setAirline(fpdto.getAirline());
				vo.setS_time(fpdto.getS_time());
				vo.setE_time(fpdto.getE_time());
			}

			System.out.println("예약하신 것은 아래와 같습니다.");
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("예약번호\t항공사\t편명\t출국일\t출발지\t도착지");
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println(vo.getReno() + "\t" + vo.getAirline() + "\t" + vo.getAircraft_num() + "\t" + vo.getS_day() + "\t" + vo.getDepart() + "\t" + vo.getArrive());
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");

			listb.add(vo);

			int result = dao.insertBook(vo);
			if (result != 0) {
				System.out.println("예약 완료");
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// 출발지 도착지 검색 결과
	public void search(String depart, String arrive) {
		List<FlightDTO> flist = dao.searchCity(depart, arrive);
		Iterator<FlightDTO> it = flist.iterator();
		while (it.hasNext()) {
			FlightDTO fdto = it.next();
			System.out.println(fdto.toString());
		}
	}

	// 편명까지 선택
	public void searchP(String aircraft_num) {
		List<FlightDTO> fplist = dao.searchEnd(aircraft_num);
		Iterator<FlightDTO> it = fplist.iterator();
		while (it.hasNext()) {
			FlightDTO fpdto = it.next();
			System.out.println(fpdto.toString());
		}
	}

	// 예약 수정
	public void updateBook() {
		try {
			BookVO vo = new BookVO();
			System.out.print("예약번호를 입력하세요 : ");
			vo.setReno(sc.next());

			System.out.print("출국날짜를 입력해주세요(ex.yy-mm-dd) : ");
			vo.setS_day(sc.next());
			System.out.println("  =========== ");
			System.out.println("||★인천/김포||");
			System.out.println("  =========== ");
			System.out.print("출발지 : ");
			vo.setDepart(sc.next());
			city(vo.getDepart());
			System.out.print("도착지 : ");
			vo.setArrive(sc.next());
			search(vo.getDepart(), vo.getArrive());

			System.out.print("편명 : ");
			vo.setAircraft_num(sc.next());

			List<FlightDTO> fplist = dao.searchEnd(vo.getAircraft_num());
			Iterator<FlightDTO> itf = fplist.iterator();

			while (itf.hasNext()) {
				FlightDTO fpdto = itf.next();
				vo.setAirline(fpdto.getAirline());
				vo.setS_time(fpdto.getS_time());
				vo.setE_time(fpdto.getE_time());
			}
			System.out.println("수정하신 내용은 아래와 같습니다.");
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("예약번호\t항공사\t편명\t출국일\t출발지\t도착지");
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println(vo.getReno() + "\t" + vo.getAirline() + "\t" + vo.getAircraft_num() + "\t" + vo.getS_day() + "\t" + vo.getDepart() + "\t" + vo.getArrive());
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");

			int result = dao.updateBook(vo);
			if (result != 0) {
				System.out.println("수정 완료");
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	// 예약 취소
	public void deleteBook() {
		try {
			System.out.print("예약 번호를 입력하세요 : ");
			String reno = sc.next();
			int result = dao.delete(reno);

			if (result != 0) {
				System.out.println("예약이 취소되었습니다.");
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	//티켓 출력 메소드
	public void ticket() throws Exception {


	}
	// 예약 확인
	public void checkBook() {

		TicketSound ts = new TicketSound();

		try {
			System.out.print("예약 번호 : ");
			String reno = sc.next();
			ts.start();
			ts.end();

			BookVO vo = dao.check(reno);
			if (vo != null) {
				MyThread1 t1 = new MyThread1(500, "\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						" _________________________________________________________________________\r\n" + 
						"l                                                                        l\r\n" + 
						"l                                                                        l\r\n" + 
						"l       l                                                        l       l\r\n" + 
						"l       l                                                        l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□■□□□□□□□□□■□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□■□□■□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □□■□□□□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □■■■□■□□□■■■□□□■□□□■□□■■■□□■■■□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■■□□□□■■■■■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□□□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□□■□■□□□■■■□□□■□□□■□□■■■□□□□■□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l                                                                        l\r\n" + 
						"l                                                                        l\r\n" + 
						"l------------------------------------------------------------------------l\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"");
				MyThread1 t2 = new MyThread1(500, "\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						" _________________________________________________________________________\r\n" + 
						"l                                                                        l\r\n" + 
						"l                                                                        l\r\n" + 
						"l       l________________________________________________________l       l\r\n" + 
						"l       l                                                        l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□■□□□□□□□□□■□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□■□□■□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □□■□□□□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □■■■□■□□□■■■□□□■□□□■□□■■■□□■■■□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■■□□□□■■■■■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□□□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□□■□■□□□■■■□□□■□□□■□□■■■□□□□■□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l                                                                        l\r\n" + 
						"l                                                                        l\r\n" + 
						"l------------------------------------------------------------------------l\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"");
				MyThread1 t3 = new MyThread1(500, "\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						" _________________________________________________________________________\r\n" + 
						"l                                                                        l\r\n" + 
						"l        _________________________________________________________       l\r\n" + 
						"l       l                                                        l       l\r\n" + 
						"l       l                                                        l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□■□□□□□□□□□■□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□■□□■□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □□■□□□□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □■■■□■□□□■■■□□□■□□□■□□■■■□□■■■□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■■□□□□■■■■■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□□□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□□■□■□□□■■■□□□■□□□■□□■■■□□□□■□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l                                                                        l\r\n" + 
						"l                                                                        l\r\n" + 
						"l------------------------------------------------------------------------l\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"");
				MyThread1 t4 = new MyThread1(500, "\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						" _________________________________________________________________________\r\n" + 
						"l        _________________________________________________________       l\r\n" + 
						"l       l                                                        l       l\r\n" + 
						"l       l                                                        l       l\r\n" + 
						"l       l            ◈Ticekt◈                                  l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□■□□□□□□□□□■□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□■□□■□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □□■□□□□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □■■■□■□□□■■■□□□■□□□■□□■■■□□■■■□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■■□□□□■■■■■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□□□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□□■□■□□□■■■□□□■□□□■□□■■■□□□□■□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l                                                                        l\r\n" + 
						"l                                                                        l\r\n" + 
						"l------------------------------------------------------------------------l\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"");
				MyThread1 t5 = new MyThread1(500, "\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						" _________________________________________________________________________\r\n" + 
						"l       l                                                        l       l\r\n" + 
						"l       l                                                        l       l\r\n" + 
						"l       l            ◈Ticekt◈                                  l       l\r\n" + 
						"l       l     예약번호: "+vo.getReno()+"                                     l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□■□□□□□□□□□■□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□■□□■□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □□■□□□□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □■■■□■□□□■■■□□□■□□□■□□■■■□□■■■□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■■□□□□■■■■■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□□□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□□■□■□□□■■■□□□■□□□■□□■■■□□□□■□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l                                                                        l\r\n" + 
						"l                                                                        l\r\n" + 
						"l------------------------------------------------------------------------l\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"");
				MyThread1 t6 = new MyThread1(500, "\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"        __________________________________________________________\r\n" + 
						" _______l                                                        l________\r\n" + 
						"l       l                                                        l       l\r\n" + 
						"l       l            ◈Ticekt◈                                  l       l\r\n" + 
						"l       l     예약번호: "+vo.getReno()+"                                     l       l\r\n" + 
						"l       l     I   D   : "+vo.getId()+"                                  l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□■□□□□□□□□□■□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□■□□■□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □□■□□□□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □■■■□■□□□■■■□□□■□□□■□□■■■□□■■■□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■■□□□□■■■■■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□□□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□□■□■□□□■■■□□□■□□□■□□■■■□□□□■□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l                                                                        l\r\n" + 
						"l                                                                        l\r\n" + 
						"l------------------------------------------------------------------------l\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"");
				MyThread1 t7 = new MyThread1(500, "\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"        __________________________________________________________       \r\n" + 
						"        l                                                        l       \r\n" + 
						" _______l                                                        l________\r\n" + 
						"l       l            ◈Ticekt◈                                  l       l\r\n" + 
						"l       l     예약번호: "+vo.getReno()+"                                     l       l\r\n" + 
						"l       l     I   D   : "+vo.getId()+"                                  l       l\r\n" + 
						"l       l     이  름  : "+vo.getName()+"                                   l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□■□□□□□□□□□■□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□■□□■□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □□■□□□□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □■■■□■□□□■■■□□□■□□□■□□■■■□□■■■□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■■□□□□■■■■■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□□□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□□■□■□□□■■■□□□■□□□■□□■■■□□□□■□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l                                                                        l\r\n" + 
						"l                                                                        l\r\n" + 
						"l------------------------------------------------------------------------l\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"");
				MyThread1 t8 = new MyThread1(500, "\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"        __________________________________________________________        \r\n" + 
						"        l                                                        l        \r\n" + 
						"        l                                                        l        \r\n" + 
						" _______l            ◈Ticekt◈                                  l________\r\n" + 
						"l       l     예약번호: "+vo.getReno()+"                                     l       l\r\n" + 
						"l       l     I   D   : "+vo.getId()+"                                  l       l\r\n" + 
						"l       l     이  름  : "+vo.getName()+"                                   l       l\r\n" + 
						"l       l     항 공 사: "+vo.getAirline()+"                                 l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□■□□□□□□□□□■□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□■□□■□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □□■□□□□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □■■■□■□□□■■■□□□■□□□■□□■■■□□■■■□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■■□□□□■■■■■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□□□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□□■□■□□□■■■□□□■□□□■□□■■■□□□□■□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l                                                                        l\r\n" + 
						"l                                                                        l\r\n" + 
						"l------------------------------------------------------------------------l\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"");
				MyThread1 t9 = new MyThread1(500, "\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"        __________________________________________________________        \r\n" + 
						"        l                                                        l        \r\n" + 
						"        l                                                        l        \r\n" + 
						"        l            ◈Ticekt◈                                  l        \r\n" + 
						" _______l     예약번호: "+vo.getReno()+"                                     l________\r\n" + 
						"l       l     I   D   : "+vo.getId()+"                                  l       l\r\n" + 
						"l       l     이  름  : "+vo.getName()+"                                   l       l\r\n" + 
						"l       l     항 공 사: "+vo.getAirline()+"                                 l       l\r\n" + 
						"l       l     항 공 편: "+vo.getAircraft_num()+"                                    l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□■□□□□□□□□□■□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□■□□■□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □□■□□□□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □■■■□■□□□■■■□□□■□□□■□□■■■□□■■■□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■■□□□□■■■■■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□□□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□□■□■□□□■■■□□□■□□□■□□■■■□□□□■□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l                                                                        l\r\n" + 
						"l                                                                        l\r\n" + 
						"l------------------------------------------------------------------------l\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"");
				MyThread1 t10 = new MyThread1(500, "\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"        _________________________________________________________        \r\n" + 
						"        l                                                        l        \r\n" + 
						"        l                                                        l        \r\n" + 
						"        l            ◈Ticekt◈                                  l        \r\n" + 
						"        l     예약번호: "+vo.getReno()+"                                     l        \r\n" + 
						" _______l     I   D   : "+vo.getId()+"                                  l________\r\n" + 
						"l       l     이  름  : "+vo.getName()+"                                   l       l\r\n" + 
						"l       l     항 공 사: "+vo.getAirline()+"                                 l       l\r\n" + 
						"l       l     항 공 편: "+vo.getAircraft_num()+"                                    l       l\r\n" + 
						"l       l     날  짜  : "+vo.getS_day()+"                                 l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□■□□□□□□□□□■□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□■□□■□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □□■□□□□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □■■■□■□□□■■■□□□■□□□■□□■■■□□■■■□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■■□□□□■■■■■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□□□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□□■□■□□□■■■□□□■□□□■□□■■■□□□□■□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l                                                                        l\r\n" + 
						"l                                                                        l\r\n" + 
						"l------------------------------------------------------------------------l\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"");
				MyThread1 t11 = new MyThread1(500, "\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"        _________________________________________________________        \r\n" + 
						"        l                                                        l        \r\n" + 
						"        l                                                        l        \r\n" + 
						"        l            ◈Ticekt◈                                  l        \r\n" + 
						"        l     예약번호: "+vo.getReno()+"                                     l\r\n" + 
						"        l     I   D   : "+vo.getId()+"                                  l       \r\n" + 
						" _______l     이  름  : "+vo.getName()+"                                   l________\r\n" + 
						"l       l     항 공 사: "+vo.getAirline()+"                                 l       l\r\n" + 
						"l       l     항 공 편: "+vo.getAircraft_num()+"                                    l       l\r\n" + 
						"l       l     날  짜  : "+vo.getS_day()+"                                 l       l\r\n" + 
						"l       l     출발시간: "+vo.getS_time()+"                                    l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□■□□□□□□□□□■□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□■□□■□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □□■□□□□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □■■■□■□□□■■■□□□■□□□■□□■■■□□■■■□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■■□□□□■■■■■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□□□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□□■□■□□□■■■□□□■□□□■□□■■■□□□□■□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l                                                                        l\r\n" + 
						"l                                                                        l\r\n" + 
						"l------------------------------------------------------------------------l\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"");
				MyThread1 t12 = new MyThread1(500, "\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"        _________________________________________________________        \r\n" + 
						"        l                                                        l        \r\n" + 
						"        l                                                        l        \r\n" + 
						"        l            ◈Ticekt◈                                  l        \r\n" + 
						"        l     예약번호: "+vo.getReno()+"                                     l\r\n" + 
						"        l     I   D   : "+vo.getId()+"                                  l       \r\n" + 
						"        l     이  름  : "+vo.getName()+"                                   l       \r\n" + 
						" _______l     항 공 사: "+vo.getAirline()+"                                 l________\r\n" + 
						"l       l     항 공 편: "+vo.getAircraft_num()+"                                    l       l\r\n" + 
						"l       l     날  짜  : "+vo.getS_day()+"                                 l       l\r\n" + 
						"l       l     출발시간: "+vo.getS_time()+"                                    l       l\r\n" + 
						"l       l     도착시간: "+vo.getE_time()+"                                  l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□■□□□□□□□□□■□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□■□□■□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □□■□□□□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □■■■□■□□□■■■□□□■□□□■□□■■■□□■■■□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■■□□□□■■■■■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□□□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□□■□■□□□■■■□□□■□□□■□□■■■□□□□■□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l                                                                        l\r\n" + 
						"l                                                                        l\r\n" + 
						"l------------------------------------------------------------------------l\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"");
				MyThread1 t13 = new MyThread1(500, "\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"        _________________________________________________________        \r\n" + 
						"        l                                                        l        \r\n" + 
						"        l                                                        l        \r\n" + 
						"        l            ◈Ticekt◈                                  l        \r\n" + 
						"        l     예약번호: "+vo.getReno()+"                                     l\r\n" + 
						"        l     I   D   : "+vo.getId()+"                                  l\r\n" + 
						"        l     이  름  : "+vo.getName()+"                                   l\r\n" + 
						"        l     항 공 사: "+vo.getAirline()+"                                 l\r\n" + 
						" _______l     항 공 편: "+vo.getAircraft_num()+"                                    l________\r\n" + 
						"l       l     날  짜  : "+vo.getS_day()+"                                 l       l\r\n" + 
						"l       l     출발시간: "+vo.getS_time()+"                                    l       l\r\n" + 
						"l       l     도착시간: "+vo.getE_time()+"                                  l       l\r\n" + 
						"l       l     출 발 지: "+vo.getDepart()+"                                     l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□■□□□□□□□□□■□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□■□□■□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □□■□□□□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □■■■□■□□□■■■□□□■□□□■□□■■■□□■■■□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■■□□□□■■■■■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□□□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□□■□■□□□■■■□□□■□□□■□□■■■□□□□■□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l                                                                        l\r\n" + 
						"l                                                                        l\r\n" + 
						"l------------------------------------------------------------------------l\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"");
				MyThread1 t14 = new MyThread1(500, "\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"        _________________________________________________________        \r\n" + 
						"        l                                                        l        \r\n" +
						"        l                                                        l        \r\n" + 
						"        l            ◈Ticekt◈                                  l        \r\n" + 
						"        l     예약번호: "+vo.getReno()+"                                     l\r\n" + 
						"        l     I   D   : "+vo.getId()+"                                  l\r\n" +  
						"        l     이  름  : "+vo.getName()+"                                   l\r\n" + 
						"        l     항 공 사: "+vo.getAirline()+"                                 l\r\n" + 
						"        l     항 공 편: "+vo.getAircraft_num()+"                                    l\r\n" + 
						" _______l     날  짜  : "+vo.getS_day()+"                                 l________\r\n" + 
						"l       l     출발시간: "+vo.getS_time()+"                                    l       l\r\n" + 
						"l       l     도착시간: "+vo.getE_time()+"                                  l       l\r\n" + 
						"l       l     출 발 지: "+vo.getDepart()+"                                     l       l\r\n" + 
						"l       l     도 착 지: "+vo.getArrive()+"                                     l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□■□□□□□□□□□■□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□■□□■□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □□■□□□□□□□□□□□□■□□□□□□□□□□□□■□□     l\r\n" + 
						"l     □■■■□■□□□■■■□□□■□□□■□□■■■□□■■■□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■■□□□□■■■■■□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□□□□■□■□□□■□□□□□□■□□     l\r\n" + 
						"l     □□■□□■□□■□□□■□□■□□■□□■□□□■□□■□□     l\r\n" + 
						"l     □□□■□■□□□■■■□□□■□□□■□□■■■□□□□■□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l     □□□□□□□□□□□□□□□□□□□□□□□□□□□□□□□     l\r\n" + 
						"l                                                                        l\r\n" + 
						"l                                                                        l\r\n" + 
						"l------------------------------------------------------------------------l\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"\r\n" + 
						"");

				t1.start();
				t1.join();
				t2.start();
				t2.join();
				t3.start();
				t3.join();
				t4.start();
				t4.join();
				t5.start();
				t5.join();
				t6.start();
				t6.join();
				t7.start();
				t7.join();
				t8.start();
				t8.join();
				t9.start();
				t9.join();
				t10.start();
				t10.join();
				t11.start();
				t11.join();
				t12.start();
				t12.join();
				t13.start();
				t13.join();
				t14.start();
				t14.join();

			} else
				System.out.println("예약 번호가 존재하지 않습니다.");
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	//비행기 출력 메소드
	public void airplaneBanner() throws Exception {
		AirplaneBanner apb = new AirplaneBanner();
		apb.t1.start();
		apb.t1.join();
		apb.t2.start();
		apb.t2.join();
		apb.t3.start();
		apb.t3.join();
		apb.t4.start();
		apb.t4.join();
		apb.t5.start();
		apb.t5.join();
		apb.t6.start();
		apb.t6.join();
		apb.t7.start();
		apb.t7.join();
		apb.t8.start();
		apb.t8.join();
		apb.t9.start();
		apb.t9.join();
		apb.t10.start();
		apb.t10.join();

	}

}