
public class TransVo {
	private String id;
	private int cash;
	private String account;
	static TransVo reciver;
	
	// �ٸ� Ŭ���������� LoginP���� �Էµ� id ��� �� �� �ְ� static���� ����.
	static void recive(TransVo tv){
		reciver = tv;
	}

	public TransVo() {

	}

	
//	public TransVo(String account) {
//		this.account = account;
//	}
//	public String getAccount() {
//		return account;
//	}
	
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


