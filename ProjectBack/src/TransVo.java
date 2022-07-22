
public class TransVo {
	private String id;
	private int cash;
	private String account;
	static TransVo reciver;
	static TransVo mo;
	
	// 다른 클래스에서도 LoginP에서 입력된 id 얻어 올 수 있게 static으로 저장.
	static void recive(TransVo tv){
		reciver = tv;
	}
	
	static void m(TransVo tv){
		mo = tv;
	}

	public TransVo() {

	}

	public TransVo(String id) {
		this.id = id;
	}
	
	public TransVo(int cash) {
		this.cash = cash;
	}
	
	public String getId() {
		return id;
	}


	public int getCash() {
		return cash;
	}

	

}


