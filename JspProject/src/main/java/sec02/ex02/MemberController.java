package sec02.ex02;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MemberController
 */
@WebServlet("*.do")
public class MemberController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MemberDAO memberDAO;
	
	public void init() throws ServletException{
	
		memberDAO = new MemberDAO(); // memberDAO 객체 초기화
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = null;
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String action = request.getPathInfo(); //URL에서 요청명을 가져온다.
		System.out.println("action:" + action);
		
		if (action == null || action.equals("/listMembers.do")) { //최초 요청이거나 action값이 /listMembers.do면 회원 목록을 출력
			List membersList = memberDAO.listMembers();
			request.setAttribute("membersList", membersList);
			nextPage = "/test03/listMembers.jsp"; //test03 폴더의 listMember.jsp로 포워딩
	
		} else if (action.equals("/addMember.do")) { //action값이 /addMember.do면 전송된 회원정보를 가져와서 테이블에 추가
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			MemberVO memberVO = new MemberVO(id, pwd, name, email);
			memberDAO.addMember(memberVO);
			nextPage = "/member/listMembers.do"; // 회원등록 후 다시 회원 목록을 출력

		} else if (action.equals("/memberForm.do")) { //action값이 /memberForm.do면 회원가입창을 화면에 출력
			nextPage = "/test03/memberForm.jsp"; // test03 폴더의 memberForm.jsp로 포워딩
		
		} else if (action.equals("modMemberForm.do")) { //회원 수정창 요청 시 ID로 회원 정보를 조회한 후 수정창으로 포워딩
			String id = request.getParameter("id");
			MemberVO memInfo = memberDVO.findMember(id); //회원 정보 수정창을 요청하면서 전송된 ID를 이용해 수정 전 회원 정보를 조회
			request.setAttribute("memInfo", memInfo); // request에 바인딩하여 회원 정보 수정창에 수정하기 전 회원 정보를 전달
			nextPage = "/test03/modMemberForm.jsp";
			
		} else if (action.equals("/modMember.do")) { // 테이블의 회원 정보를 수정
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd"); 
			String name = request.getParameter("name"); 
			String email = request.getParameter("email"); //회원정보 수정창에서 전송된 수정 회원 정보를 가져온 후 MemberVO 객체 속성에 설정
			MemberVO memberVO = new MemberVO(id, pwd, name, email);
			memberDAO.modMember(memberVO);
			request.setAttribute("msg","modified"); // 회원 목록창으로 수정 작업 완료 메시지를 전달
			nextPage = "/member/listMembers.do";
		
		} else if (action.equals("/delMember.do")) { // 회원 ID를 SQL문으로 전달해 테이블의 회원 정보를 삭제
			String id = request.getParameter("id"); // 삭제할 회원 ID를 받아옴
			memberDAO.delMember(id);
			request.setAttribute("msg", "deleted"); // 회원 목록창으로 삭제 작업 완료 메시지를 전달
			nextPage = "/member/listMembers.do";
			
		} else { // 그 외 다른 action값은 회원 목록을 출력
			List membersList = memberDAO.listMembers();
			request.setAttribute("membersList", membersList);
			nextPage = "/test03/listMembers.jsp";
		} 
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage); //nextPage에 지정한 요청명으로 다시 서블릿에 요청
		dispatch.forward(request, response);
		
	}
	
	

}
