package com.proj2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.db.DBConn;

public class AirDAO {

	// 회원 가입
	public int insertJoin(CustomDTO dto) {
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		String sql;
		int result = 0;
		try {
			sql = "insert into custom(id,pw,name,addr1,addr2,addr3,tel,grade) values (?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPw());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.getAddr1());
			pstmt.setString(5, dto.getAddr2());
			pstmt.setString(6, dto.getAddr3());
			pstmt.setString(7, dto.getTel());
			pstmt.setString(8, dto.getGrade());

			result = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}
	
	// 로그인
	   public CustomDTO loingData(String id, String pw){
		      CustomDTO dto = null;
		      Connection conn = DBConn.getConnection();
		      PreparedStatement pstmt = null;
		      ResultSet rs = null;
		      String sql;
		      
		      try {
		         sql="select name from custom where id=? and pw=?";
		         
		         pstmt = conn.prepareStatement(sql);
		         
		         pstmt.setString(1, id);
		         pstmt.setString(2, pw);
		         
		         rs = pstmt.executeQuery();
		         
		         if(rs.next()) {
		            dto = new CustomDTO();
		            dto.setName(rs.getString("name"));
		         }
		      
		   } catch (Exception e) {
		      System.out.println(e.toString());
		   }
		      
		      return dto;
		}

	// 예약
	public int insertBook(BookVO vo) {
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		String sql;
		int result = 0;
		try {
			sql = "insert into reservation(reno,id,name,airline,aircraft_num,s_day,s_time,e_time,depart,arrive) values (?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getReno());
			pstmt.setString(2, vo.getId());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getAirline());
			pstmt.setString(5, vo.getAircraft_num());
			pstmt.setString(6, vo.getS_day());
			pstmt.setString(7, vo.getS_time());
			pstmt.setString(8, vo.getE_time());
			pstmt.setString(9, vo.getDepart());
			pstmt.setString(10, vo.getArrive());

			result = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}
	// 예약 수정
		public int updateBook(BookVO vo) {
			Connection conn = DBConn.getConnection();
			PreparedStatement pstmt = null;
			String sql;
			int result = 0;
			try {
				sql = "update reservation set airline=?,aircraft_num=?,s_day=?,depart=?,arrive=?,s_time=?,e_time=? where reno=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getAirline());
				pstmt.setString(2, vo.getAircraft_num());
				pstmt.setString(3, vo.getS_day());
				pstmt.setString(4, vo.getDepart());
				pstmt.setString(5, vo.getArrive());
				pstmt.setString(6, vo.getS_time());
				pstmt.setString(7, vo.getE_time());
				pstmt.setString(8, vo.getReno());

				result = pstmt.executeUpdate();
				pstmt.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			return result;
		}

	// 편명 검색
	public List<FlightDTO> searchEnd(String aircraft_num) {
		List<FlightDTO> fplist = new ArrayList<FlightDTO>();
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "select airline,aircraft_num,depart,arrive,s_time,e_time from flight where aircraft_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, aircraft_num);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				FlightDTO fpdto = new FlightDTO();
				fpdto.setAirline(rs.getString("airline"));
				fpdto.setAircraft_num(rs.getString("aircraft_num"));
				fpdto.setDepart(rs.getString("depart"));
				fpdto.setArrive(rs.getString("arrive"));
				fpdto.setS_time(rs.getString("s_time"));
				fpdto.setE_time(rs.getString("e_time"));

				fplist.add(fpdto);

			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return fplist;
	}
	
	// 출발지 도착지 검색
	public List<FlightDTO> searchCity(String depart, String arrive) {
		List<FlightDTO> flist = new ArrayList<FlightDTO>();
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "select airline,aircraft_num,depart,arrive,s_time,e_time from flight where depart=? and arrive=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, depart);
			pstmt.setString(2, arrive);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				FlightDTO fdto = new FlightDTO();
				fdto.setAirline(rs.getString("airline"));
				fdto.setAircraft_num(rs.getString("aircraft_num"));
				fdto.setDepart(rs.getString("depart"));
				fdto.setArrive(rs.getString("arrive"));
				fdto.setS_time(rs.getString("s_time"));
				fdto.setE_time(rs.getString("e_time"));

				flist.add(fdto);

			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return flist;
	}
	// 로그인에서 이름 가져오기
	public List<CustomDTO> searchName(String id) {
		List<CustomDTO> clist = new ArrayList<CustomDTO>();
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "select name, id from custom where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CustomDTO cdto = new CustomDTO();
				cdto.setName(rs.getString("name"));
				cdto.setId(rs.getString("id"));
				
				clist.add(cdto);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return clist;
	}

	// 예약 취소
	public int delete(String reno) {
		int result = 0;
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "delete reservation where reno=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reno);
			result = pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}

	

	// 예약 확인
	public BookVO check(String reno) {
		BookVO vo = null;
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		try {
			sql = "select reno,id,name,airline,aircraft_num,depart,arrive,s_day,s_time,e_time from reservation where reno=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reno);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo = new BookVO();
				vo.setReno(rs.getString("reno"));
				vo.setId(rs.getString("id"));
				vo.setName(rs.getString("name"));
				vo.setAirline(rs.getString("airline"));
				vo.setAircraft_num(rs.getString("aircraft_num"));
				vo.setDepart(rs.getString("depart"));
				vo.setArrive(rs.getString("arrive"));
				vo.setS_day(rs.getString("s_day"));
				vo.setS_time(rs.getString("s_time"));
				vo.setE_time(rs.getString("e_time"));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return vo;
	}
}
