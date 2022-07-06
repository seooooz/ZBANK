

public class MemberVo {
	private String id;
	private String password;
	static MemberVo user;
	
	// 다른 클래스에서도 LoginP에서 입력된 id 얻어 올 수 있게 static으로 저장.
	static void userinit(MemberVo v){
		user =   v;
	}

	public MemberVo() {

	}

	public MemberVo(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setId(String id) {
		this.id = id;
	}

}
