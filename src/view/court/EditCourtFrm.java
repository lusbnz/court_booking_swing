package view.court;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import dao.CourtDAO;
import model.MiniCourt;
import model.User;
import view.user.SellerHomeFrm;

public class EditCourtFrm extends JFrame implements ActionListener{
	private MiniCourt court;
	private JTextField txtId, txtName, txtType, txtPrice, txtDesc;
	private JButton btnUpdate, btnReset;
	private User user;
	
	
	public EditCourtFrm(User user, MiniCourt court){
		super("Sửa thông tin sân");
		this.user = user;
		this.court = court;
		
		JPanel pnMain = new JPanel();
		pnMain.setSize(this.getSize().width-5, this.getSize().height-20);		
		pnMain.setLayout(new BoxLayout(pnMain,BoxLayout.Y_AXIS));
		pnMain.add(Box.createRigidArea(new Dimension(0,10)));
		
		JLabel lblHome = new JLabel("Sửa thông tin sân");
		lblHome.setAlignmentX(Component.CENTER_ALIGNMENT);	
		lblHome.setFont (lblHome.getFont ().deriveFont (20.0f));
		pnMain.add(lblHome);
		pnMain.add(Box.createRigidArea(new Dimension(0,20)));
		
		txtId = new JTextField(15);
		txtId.setEditable(false);
		txtName = new JTextField(15);
		txtType = new JTextField(15);
		txtPrice = new JTextField(15);
		txtDesc = new JTextField(15);
		btnUpdate = new JButton("Cập nhật");
		btnReset = new JButton("Hoàn tác");
		
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(6,2));
		content.add(new JLabel("ID Sân:")); 	content.add(txtId);
		content.add(new JLabel("Tên sân:")); 	content.add(txtName);
		content.add(new JLabel("Loại sân:")); 	content.add(txtType);
		content.add(new JLabel("Giá:")); 	content.add(txtPrice);
		content.add(new JLabel("Mô tả:")); 	content.add(txtDesc);
		content.add(btnUpdate); 	content.add(btnReset);
		pnMain.add(content);		  
		btnUpdate.addActionListener(this);
		btnReset.addActionListener(this);
		
		initForm();		
		this.setContentPane(pnMain);
		this.setSize(600,300);				
		this.setLocation(200,10);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private void initForm(){
		if(court != null){
			txtId.setText(court.getId()+"");
			txtName.setText(court.getName());
			txtType.setText(court.getType());
			txtPrice.setText(court.getPrice()+"");
			txtDesc.setText(court.getDesc());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btnClicked = (JButton)e.getSource();
		if(btnClicked.equals(btnReset)){
			initForm();
			return;
		}
		if(btnClicked.equals(btnUpdate)){
			court.setName(txtName.getText());
			court.setType(txtType.getText());
			court.setPrice(Float.parseFloat(txtPrice.getText()));
			court.setDesc(txtDesc.getText());
			
			CourtDAO cd = new CourtDAO();
			if(cd.updateCourt(court)) {
				JOptionPane.showMessageDialog(this, "Phòng được cập nhật thành công!");
				(new SellerHomeFrm(user)).setVisible(true);
				this.dispose();
			}	
		}
	}
}
