
public class TransVo {
	private String id;
	private int cash;
	private String account;
	static TransVo reciver;
	static TransVo mo;
	
	// �ٸ� Ŭ���������� LoginP���� �Էµ� id ��� �� �� �ְ� static���� ����.
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


