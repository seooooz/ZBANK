

public class UserVo {
	private String name;
	private String id;
	private String password;
	static UserVo user;
	
	// 다른 클래스에서도 LoginP에서 입력된 id 얻어 올 수 있게 static으로 저장.
	static void userinit(UserVo v){
		user =   v;
	}

	public UserVo() {

	}

	
	public UserVo(String name, String id, String password) {
		this.name = name;
		this.id = id;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

}


