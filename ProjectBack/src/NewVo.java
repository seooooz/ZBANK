

public class NewVo {
	private String name;
	private String id;
	private String password;
	static NewVo newuser;

	
	static void newuserinit(NewVo nv){
		newuser =   nv;
	}

	public NewVo() {

	}

	public NewVo(String name, String id, String password) {
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
