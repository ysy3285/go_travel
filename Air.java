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

	// ȸ�� ����
	public void join() {
		try {

			CustomDTO cdto = new CustomDTO();
			System.out.print("���̵� : ");
			cdto.setId(sc.next());

			while (true) {
				System.out.print("��й�ȣ : ");
				cdto.setPw(sc.next());
				System.out.print("��й�ȣ Ȯ�� : ");
				String checkpw = sc.next();
				if (!cdto.getPw().equals(checkpw)) {
					System.out.println("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
				} else {
					break;
				}
			}
			System.out.print("�̸� : ");
			cdto.setName(sc.next());
			System.out.print("Ư����/�� : ");
			cdto.setAddr1(sc.next());
			System.out.print("��/�� : ");
			cdto.setAddr2(sc.next());
			System.out.print("������ ���� �ּ� : ");
			cdto.setAddr3(sc.next());
			System.out.print("��ȭ��ȣ(���ڸ� �Է�) : ");
			cdto.setTel(sc.next());
			cdto.setGrade("�ǹ�");

			lista.add(cdto);

			int result = dao.insertJoin(cdto);
			if (result != 0) {
				System.out.println("ȸ�� ������ �����մϴ�*^_^*");

			} else
				System.out.println("insert ����");

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// �α���
	public String login() {
		while(true) {
			System.out.print("���̵� > ");
			String id = sc.next();
			System.out.print("��й�ȣ > ");
			String pw = sc.next();

			CustomDTO dto = dao.loingData(id, pw);

			if (dto != null) {
				System.out.println(dto.getName() + "�� ȯ���մϴ�!");
				return id; 
			} else {
				System.out.println("�α��ο� ���� �Ͽ����ϴ�.");
			}
		}
	}

	// ������
	public void city(String city) {
		if(city.equals("����")) {
			System.out.println("  =================================================================================================");
			System.out.println("||�� ���� > �λ�, ����                                                                             ||");
			System.out.println("||�� ���Ͼƽþ� > ������                                                                           ||");
			System.out.println("  =================================================================================================");
		}else if(city.equals("��õ")) {
			System.out.println("  =================================================================================================");
			System.out.println("||�� ���Ͼƽþ� > ������, ����¡, ����, ������, �����ī, �����ī                         ||");
			System.out.println("||�� �����ƽþ� > ��ī��Ÿ, �ϳ���, �ٳ�, �̰�����, ����, ������, �Ͼ��, �氥��, ȣġ�ν�, �ι��� ||");
			System.out.println("||�� ���� > ���Ű,��ũ��, ��ġ, �θ�, �ٸ����γ�, ���帮��, �ĸ�                                ||");
			System.out.println("||�� ���� > ���ø���, ���׸����Ƽ, �����, �����, ���ֹ̾�, ����, �ο��뽺 ���̷���            ||");
			System.out.println("  =================================================================================================");
		}
	}

	// ����
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

			System.out.print("�ⱹ��¥�� �Է����ּ���(ex.yy-mm-dd) : ");
			vo.setS_day(sc.next());
			System.out.println("  =========== ");
			System.out.println("||����õ/����||");
			System.out.println("  =========== ");
			System.out.print("����� : ");
			vo.setDepart(sc.next());
			city(vo.getDepart());
			System.out.print("������ : ");
			vo.setArrive(sc.next());
			search(vo.getDepart(), vo.getArrive());

			System.out.print("��� : ");
			vo.setAircraft_num(sc.next());
			String reno = Integer.toString(rm.nextInt(9999) + 1); // ���� ��ȣ
			vo.setReno(reno);

			List<FlightDTO> fplist = dao.searchEnd(vo.getAircraft_num());
			Iterator<FlightDTO> itf = fplist.iterator();
			while (itf.hasNext()) {
				FlightDTO fpdto = itf.next();
				vo.setAirline(fpdto.getAirline());
				vo.setS_time(fpdto.getS_time());
				vo.setE_time(fpdto.getE_time());
			}

			System.out.println("�����Ͻ� ���� �Ʒ��� �����ϴ�.");
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("�����ȣ\t�װ���\t���\t�ⱹ��\t�����\t������");
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println(vo.getReno() + "\t" + vo.getAirline() + "\t" + vo.getAircraft_num() + "\t" + vo.getS_day() + "\t" + vo.getDepart() + "\t" + vo.getArrive());
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");

			listb.add(vo);

			int result = dao.insertBook(vo);
			if (result != 0) {
				System.out.println("���� �Ϸ�");
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// ����� ������ �˻� ���
	public void search(String depart, String arrive) {
		List<FlightDTO> flist = dao.searchCity(depart, arrive);
		Iterator<FlightDTO> it = flist.iterator();
		while (it.hasNext()) {
			FlightDTO fdto = it.next();
			System.out.println(fdto.toString());
		}
	}

	// ������ ����
	public void searchP(String aircraft_num) {
		List<FlightDTO> fplist = dao.searchEnd(aircraft_num);
		Iterator<FlightDTO> it = fplist.iterator();
		while (it.hasNext()) {
			FlightDTO fpdto = it.next();
			System.out.println(fpdto.toString());
		}
	}

	// ���� ����
	public void updateBook() {
		try {
			BookVO vo = new BookVO();
			System.out.print("�����ȣ�� �Է��ϼ��� : ");
			vo.setReno(sc.next());

			System.out.print("�ⱹ��¥�� �Է����ּ���(ex.yy-mm-dd) : ");
			vo.setS_day(sc.next());
			System.out.println("  =========== ");
			System.out.println("||����õ/����||");
			System.out.println("  =========== ");
			System.out.print("����� : ");
			vo.setDepart(sc.next());
			city(vo.getDepart());
			System.out.print("������ : ");
			vo.setArrive(sc.next());
			search(vo.getDepart(), vo.getArrive());

			System.out.print("��� : ");
			vo.setAircraft_num(sc.next());

			List<FlightDTO> fplist = dao.searchEnd(vo.getAircraft_num());
			Iterator<FlightDTO> itf = fplist.iterator();

			while (itf.hasNext()) {
				FlightDTO fpdto = itf.next();
				vo.setAirline(fpdto.getAirline());
				vo.setS_time(fpdto.getS_time());
				vo.setE_time(fpdto.getE_time());
			}
			System.out.println("�����Ͻ� ������ �Ʒ��� �����ϴ�.");
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println("�����ȣ\t�װ���\t���\t�ⱹ��\t�����\t������");
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println(vo.getReno() + "\t" + vo.getAirline() + "\t" + vo.getAircraft_num() + "\t" + vo.getS_day() + "\t" + vo.getDepart() + "\t" + vo.getArrive());
			System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");

			int result = dao.updateBook(vo);
			if (result != 0) {
				System.out.println("���� �Ϸ�");
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	// ���� ���
	public void deleteBook() {
		try {
			System.out.print("���� ��ȣ�� �Է��ϼ��� : ");
			String reno = sc.next();
			int result = dao.delete(reno);

			if (result != 0) {
				System.out.println("������ ��ҵǾ����ϴ�.");
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	//Ƽ�� ��� �޼ҵ�
	public void ticket() throws Exception {


	}
	// ���� Ȯ��
	public void checkBook() {

		TicketSound ts = new TicketSound();

		try {
			System.out.print("���� ��ȣ : ");
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
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
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
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
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
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
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
						"l       l            ��Ticekt��                                  l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
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
						"l       l            ��Ticekt��                                  l       l\r\n" + 
						"l       l     �����ȣ: "+vo.getReno()+"                                     l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
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
						"l       l            ��Ticekt��                                  l       l\r\n" + 
						"l       l     �����ȣ: "+vo.getReno()+"                                     l       l\r\n" + 
						"l       l     I   D   : "+vo.getId()+"                                  l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
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
						"l       l            ��Ticekt��                                  l       l\r\n" + 
						"l       l     �����ȣ: "+vo.getReno()+"                                     l       l\r\n" + 
						"l       l     I   D   : "+vo.getId()+"                                  l       l\r\n" + 
						"l       l     ��  ��  : "+vo.getName()+"                                   l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
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
						" _______l            ��Ticekt��                                  l________\r\n" + 
						"l       l     �����ȣ: "+vo.getReno()+"                                     l       l\r\n" + 
						"l       l     I   D   : "+vo.getId()+"                                  l       l\r\n" + 
						"l       l     ��  ��  : "+vo.getName()+"                                   l       l\r\n" + 
						"l       l     �� �� ��: "+vo.getAirline()+"                                 l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
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
						"        l            ��Ticekt��                                  l        \r\n" + 
						" _______l     �����ȣ: "+vo.getReno()+"                                     l________\r\n" + 
						"l       l     I   D   : "+vo.getId()+"                                  l       l\r\n" + 
						"l       l     ��  ��  : "+vo.getName()+"                                   l       l\r\n" + 
						"l       l     �� �� ��: "+vo.getAirline()+"                                 l       l\r\n" + 
						"l       l     �� �� ��: "+vo.getAircraft_num()+"                                    l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
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
						"        l            ��Ticekt��                                  l        \r\n" + 
						"        l     �����ȣ: "+vo.getReno()+"                                     l        \r\n" + 
						" _______l     I   D   : "+vo.getId()+"                                  l________\r\n" + 
						"l       l     ��  ��  : "+vo.getName()+"                                   l       l\r\n" + 
						"l       l     �� �� ��: "+vo.getAirline()+"                                 l       l\r\n" + 
						"l       l     �� �� ��: "+vo.getAircraft_num()+"                                    l       l\r\n" + 
						"l       l     ��  ¥  : "+vo.getS_day()+"                                 l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
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
						"        l            ��Ticekt��                                  l        \r\n" + 
						"        l     �����ȣ: "+vo.getReno()+"                                     l\r\n" + 
						"        l     I   D   : "+vo.getId()+"                                  l       \r\n" + 
						" _______l     ��  ��  : "+vo.getName()+"                                   l________\r\n" + 
						"l       l     �� �� ��: "+vo.getAirline()+"                                 l       l\r\n" + 
						"l       l     �� �� ��: "+vo.getAircraft_num()+"                                    l       l\r\n" + 
						"l       l     ��  ¥  : "+vo.getS_day()+"                                 l       l\r\n" + 
						"l       l     ��߽ð�: "+vo.getS_time()+"                                    l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
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
						"        l            ��Ticekt��                                  l        \r\n" + 
						"        l     �����ȣ: "+vo.getReno()+"                                     l\r\n" + 
						"        l     I   D   : "+vo.getId()+"                                  l       \r\n" + 
						"        l     ��  ��  : "+vo.getName()+"                                   l       \r\n" + 
						" _______l     �� �� ��: "+vo.getAirline()+"                                 l________\r\n" + 
						"l       l     �� �� ��: "+vo.getAircraft_num()+"                                    l       l\r\n" + 
						"l       l     ��  ¥  : "+vo.getS_day()+"                                 l       l\r\n" + 
						"l       l     ��߽ð�: "+vo.getS_time()+"                                    l       l\r\n" + 
						"l       l     �����ð�: "+vo.getE_time()+"                                  l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
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
						"        l            ��Ticekt��                                  l        \r\n" + 
						"        l     �����ȣ: "+vo.getReno()+"                                     l\r\n" + 
						"        l     I   D   : "+vo.getId()+"                                  l\r\n" + 
						"        l     ��  ��  : "+vo.getName()+"                                   l\r\n" + 
						"        l     �� �� ��: "+vo.getAirline()+"                                 l\r\n" + 
						" _______l     �� �� ��: "+vo.getAircraft_num()+"                                    l________\r\n" + 
						"l       l     ��  ¥  : "+vo.getS_day()+"                                 l       l\r\n" + 
						"l       l     ��߽ð�: "+vo.getS_time()+"                                    l       l\r\n" + 
						"l       l     �����ð�: "+vo.getE_time()+"                                  l       l\r\n" + 
						"l       l     �� �� ��: "+vo.getDepart()+"                                     l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
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
						"        l            ��Ticekt��                                  l        \r\n" + 
						"        l     �����ȣ: "+vo.getReno()+"                                     l\r\n" + 
						"        l     I   D   : "+vo.getId()+"                                  l\r\n" +  
						"        l     ��  ��  : "+vo.getName()+"                                   l\r\n" + 
						"        l     �� �� ��: "+vo.getAirline()+"                                 l\r\n" + 
						"        l     �� �� ��: "+vo.getAircraft_num()+"                                    l\r\n" + 
						" _______l     ��  ¥  : "+vo.getS_day()+"                                 l________\r\n" + 
						"l       l     ��߽ð�: "+vo.getS_time()+"                                    l       l\r\n" + 
						"l       l     �����ð�: "+vo.getE_time()+"                                  l       l\r\n" + 
						"l       l     �� �� ��: "+vo.getDepart()+"                                     l       l\r\n" + 
						"l       l     �� �� ��: "+vo.getArrive()+"                                     l       l\r\n" + 
						"l                                                                        l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
						"l     ��������������������������������     l\r\n" + 
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
				System.out.println("���� ��ȣ�� �������� �ʽ��ϴ�.");
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	//����� ��� �޼ҵ�
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