import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class  AdminJDailogGUI extends JDialog implements ActionListener{
   
    JPanel pw=new JPanel(new GridLayout(4,1));
    JPanel pc=new JPanel(new GridLayout(4,1));
    JPanel ps=new JPanel();
 
    JLabel lable_Id = new JLabel("ID");
    JLabel lable_Name=new JLabel("�̸�");
    JLabel lable_Account=new JLabel("���¹�ȣ");
    JLabel lable_type=new JLabel("ȸ������");
 
 
    JTextField id=new JTextField();
    JTextField name=new JTextField();
    JTextField account=new JTextField();
    JTextField adtype=new JTextField();
   
 
    JButton confirm;
    JButton reset=new JButton("���");
 
   Admin me;
 
   JPanel idCkP =new JPanel(new BorderLayout());
   JButton idCkBtn = new JButton("IDCheck");
   
   AdminDAO dao =new AdminDAO();
   
 
    public AdminJDailogGUI(Admin me, String index){
        super(me,"ȸ�� ����");
        this.me=me;
//        if(index.equals("����")){
//            confirm=new JButton(index);
//        }
//        if{
            confirm=new JButton("����"); 
           
            //text�ڽ��� ���õ� ���ڵ��� ���� �ֱ�
            int row = me.jt.getSelectedRow();//���õ� ��
            name.setText( me.jt.getValueAt(row, 0).toString() );
            id.setText( me.jt.getValueAt(row, 1).toString() );
            account.setText( me.jt.getValueAt(row, 2).toString() );
            adtype.setText( me.jt.getValueAt(row, 4).toString() );
           
            //id text�ڽ� ��Ȱ��
            id.setEditable(false);
   
            //IDCheck��ư ��Ȱ��ȭ
            idCkBtn.setEnabled(false);
//        }
       
       
        //Label�߰��κ�
        pw.add(lable_Name);//�̸�
        pw.add(lable_Id);//ID
        pw.add(lable_Account);//����
        pw.add(lable_type);//ȸ������
   
       
        idCkP.add(id,"Center");
        idCkP.add(idCkBtn,"East");
       
        //TextField �߰�
        pc.add(name);
        pc.add(idCkP);
        pc.add(account);
        pc.add(adtype);
       
       
       
        ps.add(confirm);
        ps.add(reset);
   
        add(pw,"West");
        add(pc,"Center");
        add(ps,"South");
       
        setSize(300,250);
        setVisible(true);
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
       
        //�̺�Ʈ���
        confirm.addActionListener(this); //����/���� �̺�Ʈ���
        reset.addActionListener(this); //��� �̺�Ʈ���
        idCkBtn.addActionListener(this);// ID�ߺ�üũ �̺�Ʈ ���
       
    }//�����ڳ�
   
    /**
     * ����/����/���� ��ɿ� ���� �κ�
     * */
    @Override
    public void actionPerformed(ActionEvent e) {
       String btnLabel =e.getActionCommand();//�̺�Ʈ��ü ���� Label ��������
       
    	   if(btnLabel.equals("����")){
    		   int row = me.jt.getSelectedRow();//���õ� ��
    		   Object obj = me.jt.getValueAt(row, 3);
           if(Integer.valueOf((String) obj) > 0) {
        	   
        	   if( dao.userUpdate(this) > 0){
        		   messageBox(this, "�����Ϸ�Ǿ����ϴ�.");
        		   dao.userMemberUpdate(this);
        		   dispose();
        		   dao.userSelectAll(me.dt);
        		   if(me.dt.getRowCount() > 0 ) me.jt.setRowSelectionInterval(0, 0);
        		   
        	   }
           }
            else{
                messageBox(this, "�������� �ʾҽ��ϴ�.");
            }
           
           
           
       }else if(btnLabel.equals("���")){
           dispose();
           
       }else if(btnLabel.equals("IDCheck")){
           //id�ؽ�Ʈ�ڽ��� �� ������ �޼��� ��� ������ DB�����Ѵ�.
           if(id.getText().trim().equals("")){
               messageBox(this,"ID�� �Է����ּ���.");
               id.requestFocus();//��Ŀ���̵�
           }else{
               
              if(  dao.getIdByCheck(id.getText()) ){ //�ߺ��ƴϴ�.(��밡��)
                  messageBox(this, id.getText()+"�� ��밡���մϴ�.");  
              }else{ //�ߺ��̴�.
                  messageBox(this,id.getText()+"�� �ߺ��Դϴ�.");
                 
                  id.setText("");//text�ڽ������
                  id.requestFocus();//Ŀ������
              }
               
           }
           
       }
       
       
    }//actionPerformed��
   
    /**
     * �޽����ڽ� ����ִ� �޼ҵ� �ۼ�
     * */
    public static void messageBox(Object obj , String message){
        JOptionPane.showMessageDialog( (Component)obj , message);
    }
 
}//Ŭ������