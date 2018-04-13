package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import controller.MainController;

public class MainUI extends JFrame {
	// test code
	private static MainController mc;

	private static JPanel cards;
	private static Container c;

	private JPanel projectSet, versionSet, opinionSet;
	private NoticeContent noticeContent;

	private LoginPanel loginPanel;
	private ProjectListPanel projectListPanel;
	private PwdChgPanel pwdChgPanel;
	private ProjectAddPanel projectAddPanel;
	private VerListPanel verListPanel;
	private OpinionListPanel opinionListPanel;
	private FileAddPanel fileAddPanel;
	private VerAddPanel verAddPanel;

	private Font font, bigFont;

	private static final int w_left = 80;
	private static final int w_right = 220;
	private static final int w_height = 500;

	// ** 현재 선택한 프로젝트, 버전, 의견 넘버 알기 위해 지정.
	private String curr_proj, curr_verNum, curr_opiNum, last_verNum;

	public MainUI() {

		// test code
		mc = new MainController();

		c = getContentPane();

		cards = new JPanel(new CardLayout(0, 0));

		loginPanel = new LoginPanel();
		projectListPanel = new ProjectListPanel();
		pwdChgPanel = new PwdChgPanel();
		projectAddPanel = new ProjectAddPanel();
		verListPanel = new VerListPanel();
		opinionListPanel = new OpinionListPanel();
		fileAddPanel = new FileAddPanel();
		verAddPanel = new VerAddPanel();

		cards.add(loginPanel, "Window-1");
		cards.add(projectListPanel, "Window-2");
		cards.add(pwdChgPanel, "Window-3");
		cards.add(projectAddPanel, "Window-4");
		cards.add(verListPanel, "Window-5");
		cards.add(opinionListPanel, "Window-6");
		cards.add(fileAddPanel, "Window-7");
		cards.add(verAddPanel, "Window-8");
		c.add(cards);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img;
		try {
			img = ImageIO.read(getClass().getResource("icon.png"));
			this.setIconImage(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* Main Display */
		setTitle("Version Admin CPT");
		this.setBounds(30, 30, 420, 700);
		this.setResizable(false);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/* CardLayout */
	public static JPanel getCards() {
		return cards;
	}

	public static void setCards(JPanel cards) {
		MainUI.cards = cards;
	}

	public Image setImage(String path) {
		Image tempImage = null;
		try {
			tempImage = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempImage;
	}

	class LoginPanel extends JPanel {
		Image bgImage;
		Image startImage, exitImage, logoImage;
		JTextField idTxt;
		JPasswordField pwdTxt;
		JLabel id, pwd, logo, programVer;
		JButton startButton;
		JButton exitButton;

		public LoginPanel() {
			this.setLayout(null);

			font = new Font("12롯데마트행복Medium", Font.PLAIN, 14);
			LineBorder lb = new LineBorder(Color.GRAY);

			/* back ground */
			bgImage = setImage("resource/windows1/background.png");

			/* Logo */
			logo = new JLabel("");
			logoImage = setImage("resource/windows1/logo.png");
			logo.setIcon(new ImageIcon(logoImage));
			logo.setBounds(100, 150, 247, 115);
			this.add(logo);

			/* program version */
			programVer = new JLabel("Ver 1.0");
			programVer.setBounds(350, 10, 50, 30);
			programVer.setFont(new Font("돋움", Font.PLAIN, 11));
			this.add(programVer);

			/* id */
			id = new JLabel("ID : ");
			id.setBounds(90, 320, 30, 30);
			idTxt = new JTextField("");
			idTxt.setBounds(130, 320, 190, 30);
			idTxt.setBorder(lb);
			id.setFont(font);
			idTxt.setFont(font);
			this.add(idTxt);
			this.add(id);

			/* password */
			pwd = new JLabel("PW : ");
			pwd.setBounds(80, 380, 50, 30);
			pwdTxt = new JPasswordField("");
			pwdTxt.setBounds(130, 380, 190, 30);
			pwdTxt.setBorder(lb);
			pwd.setFont(font);
			pwdTxt.setFont(font);
			this.add(pwdTxt);
			this.add(pwd);

			/* start */
			startButton = new JButton("Start");
			startImage = setImage("resource/windows1/start.png");
			startButton.setIcon(new ImageIcon(startImage));
			startButton.setBounds(w_left, w_height, 120, 60);
			setButton(startButton);
			this.add(startButton);

			/* exit */
			exitButton = new JButton("Exit");
			exitImage = setImage("resource/windows1/exit.png");
			exitButton.setIcon(new ImageIcon(exitImage));
			exitButton.setBounds(w_right, w_height, 120, 60);
			setButton(exitButton);
			this.add(exitButton);

			/* Button Listener */
			class BtnListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					Object source = e.getSource();
					if (source.equals(loginPanel.startButton)) {
						String id = idTxt.getText();
						String pwd = pwdTxt.getText();

						boolean status = mc.getAdminU().login(id, pwd);
						if (status) {
							setPList();
							((CardLayout) getCards().getLayout()).show(getCards(), "Window-2");
						} else {
							JOptionPane.showMessageDialog(null, "Login Error", "ERROR", JOptionPane.WARNING_MESSAGE,
									null);
						}

					} else if (source.equals(loginPanel.exitButton)) {
						System.exit(0);
					}
				}

			}

			BtnListener btnListner = new BtnListener();
			startButton.addActionListener(btnListner);
			exitButton.addActionListener(btnListner);

		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bgImage, 0, 0, null);
		}
	}

	class ProjectListPanel extends JPanel {
		Image bgImage;
		Image addImage, projectImage, delImage, pwdChgImage, homeImage;
		JButton homeButton, pwdChgButton, addButton;

		public ProjectListPanel() {

			this.setLayout(null);

			/* background */
			bgImage = setImage("resource/windows2/back.png");

			/* home */
			homeButton = new JButton("");
			homeImage = setImage("resource/windows2/logo.png");
			homeButton.setIcon(new ImageIcon(homeImage));
			homeButton.setBounds(10, 10, homeImage.getWidth(null), homeImage.getHeight(null));
			homeButton.setBorderPainted(false);
			homeButton.setOpaque(false);
			homeButton.setContentAreaFilled(false);
			this.add(homeButton);

			/* pwd change Button */
			pwdChgButton = new JButton("");

			pwdChgImage = setImage("resource/windows2/chgPW.png");
			pwdChgButton.setIcon(new ImageIcon(pwdChgImage));
			pwdChgButton.setBounds(360, 10, pwdChgImage.getWidth(null), pwdChgImage.getHeight(null));
			pwdChgButton.setBorderPainted(false);
			pwdChgButton.setOpaque(false);
			pwdChgButton.setContentAreaFilled(false);
			this.add(pwdChgButton);

			/* add */
			addButton = new JButton("add");
			addImage = setImage("resource/windows2/add.png");
			addButton.setIcon(new ImageIcon(addImage));
			addButton.setBounds(30, 120, addImage.getWidth(null), addImage.getHeight(null));
			addButton.setBorderPainted(false);
			addButton.setOpaque(false);
			addButton.setContentAreaFilled(false);
			this.add(addButton);

			/* Project 넣을 panel */
			JPanel listPanel = new JPanel();
			listPanel.setBounds(5, 130, 400, 500);
			listPanel.setOpaque(false);
			listPanel.setBorder(null);
			this.add(listPanel);

			/* opinion 애들 추가될 panel */
			GridBagConstraints frameConstraints = new GridBagConstraints();

			projectSet = new JPanel();
			projectSet.setLayout(new BoxLayout(projectSet, BoxLayout.Y_AXIS));
			// projectSet.setLayout(new GridLayout(0, 1, 0, 1));
			projectSet.setBounds(20, 195, 380, 500);

			JScrollPane scrollFrame = new JScrollPane(projectSet, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollFrame.setBounds(20, 195, 380, 440);
			scrollFrame.setPreferredSize(new Dimension(380, 380));
			scrollFrame.setViewportView(projectSet);
			scrollFrame.setBorder(null);

			frameConstraints.gridx = 0;
			frameConstraints.gridy = 1;
			frameConstraints.weighty = 1;
			this.add(scrollFrame, frameConstraints);
			scrollFrame.setBackground(new Color(1, 0, 0, 0));
			scrollFrame.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
			scrollFrame.getViewport().setBorder(null);
			scrollFrame.setViewportBorder(null);
			scrollFrame.setOpaque(false);
			scrollFrame.setVisible(true);

			/* Button Listener */
			class BtnListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					Object source = e.getSource();
					if (source.equals(projectListPanel.addButton)) {
						((CardLayout) getCards().getLayout()).show(getCards(), "Window-4");
					} else if (source.equals(projectListPanel.pwdChgButton)) {
						((CardLayout) getCards().getLayout()).show(getCards(), "Window-3");
					} else if (source.equals(projectListPanel.homeButton)) {
						setPList();
						((CardLayout) getCards().getLayout()).show(getCards(), "Window-2");
					}
				}

			}

			BtnListener btnListener = new BtnListener();
			addButton.addActionListener(btnListener);
			homeButton.addActionListener(btnListener);
			pwdChgButton.addActionListener(btnListener);

		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bgImage, 0, 0, null);
		}
	}

	class PwdChgPanel extends JPanel {

		Image bgImage;
		Image homeImage, cuffentImage, newImage, newConfirmImage, confirmImage, cancelImage;
		JLabel currentLabel, newLabel, confirmLabel;
		JButton homeButton, confirmButton, cancelButton;
		JTextField currentTxt;
		JPasswordField newTxt, newConfirmTxt;

		public PwdChgPanel() {

			this.setLayout(null);
			font = new Font("12롯데마트행복Medium", Font.PLAIN, 14);
			LineBorder lb = new LineBorder(Color.GRAY);

			/* background */
			bgImage = setImage("resource/windows3/back.png");

			/* home button */
			homeButton = new JButton("");

			homeImage = setImage("resource/windows3/logo.png");
			homeButton.setBounds(10, 10, homeImage.getWidth(null), homeImage.getHeight(null));
			homeButton.setIcon(new ImageIcon(homeImage));

			setButton(homeButton);
			this.add(homeButton);

			/* current PW field */
			currentLabel = new JLabel("현재 비밀번호 : ");
			currentLabel.setBounds(50, 280, 100, 30);
			currentLabel.setFont(font);
			this.add(currentLabel);

			currentTxt = new JTextField("");
			currentTxt.setBounds(180, 280, 180, 30);
			currentTxt.setFont(font);
			currentTxt.setBorder(lb);
			this.add(currentTxt);

			/* new password field */
			newLabel = new JLabel("새 비밀번호 : ");
			newLabel.setBounds(50, 330, 100, 30);
			newLabel.setFont(font);
			this.add(newLabel);
			newTxt = new JPasswordField("");
			newTxt.setBounds(180, 330, 180, 30);
			newTxt.setFont(font);
			newTxt.setBorder(lb);
			this.add(newTxt);

			/* new password confirm field */
			confirmLabel = new JLabel("새 비밀번호 확인 : ");
			confirmLabel.setBounds(50, 380, 140, 30);
			confirmLabel.setFont(font);
			this.add(confirmLabel);
			newConfirmTxt = new JPasswordField("");
			newConfirmTxt.setBounds(180, 380, 180, 30);
			newConfirmTxt.setBorder(lb);
			newConfirmTxt.setFont(font);
			this.add(newConfirmTxt);

			/* confirm button */
			confirmButton = new JButton("");

			confirmImage = setImage("resource/windows3/confirm.png");
			confirmButton.setBounds(w_left, w_height, confirmImage.getWidth(null), confirmImage.getHeight(null));
			confirmButton.setIcon(new ImageIcon(confirmImage));

			setButton(confirmButton);
			this.add(confirmButton);

			/* cancel button */
			cancelButton = new JButton("");

			cancelImage = setImage("resource/windows3/cancel.png");
			cancelButton.setBounds(w_right, w_height, cancelImage.getWidth(null), cancelImage.getHeight(null));
			cancelButton.setIcon(new ImageIcon(cancelImage));

			setButton(cancelButton);
			this.add(cancelButton);

			/* Button Listener */
			class BtnListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					Object source = e.getSource();
					if (source.equals(pwdChgPanel.homeButton)) {
						setPList();
						((CardLayout) getCards().getLayout()).show(getCards(), "Window-2");

					} else if (source.equals(pwdChgPanel.confirmButton)) {
						int status = mc.getAdminU().chgPW(currentTxt.getText(), newTxt.getText(),
								newConfirmTxt.getText());
						switch (status) {
						case 0: // 변경 OK
							JOptionPane.showMessageDialog(null, "비밀번호가 변경되었습니다!");
							setPList();
							((CardLayout) getCards().getLayout()).show(getCards(), "Window-2");
							break;

						case 1: // "새 비밀번호와 새 비밀번호 확인이 다릅니다!"
							JOptionPane.showMessageDialog(null, "새 비밀번호와 새 비밀번호 확인이 다릅니다!");
							break;

						case 2: // 비밀번호가 틀린 경우
							JOptionPane.showMessageDialog(null, "비밀번호가 맞지 않습니다");
							break;

						default:
							break;
						}
					} else if (source.equals(pwdChgPanel.cancelButton)) {
						setPList();
						((CardLayout) getCards().getLayout()).show(getCards(), "Window-2");
					}
				}

			}

			BtnListener btnListener = new BtnListener();
			confirmButton.addActionListener(btnListener);
			homeButton.addActionListener(btnListener);
			cancelButton.addActionListener(btnListener);

		}

	}

	/* project */

	class Project extends JPanel {

		Image projectImage;
		JButton projectButton, deleteButton;
		JLabel projectNum;
		Image deleteImage;

		public Project(String prjName, int count) {

			font = new Font("12롯데마트행복Medium", Font.PLAIN, 20);
			bigFont = new Font("12롯데마트행복Medium", Font.PLAIN, 30);
			Font realBig = new Font("12롯데마트행복Medium", Font.PLAIN, 40);
			this.setLayout(null);

			/* background */
			projectImage = setImage("resource/windows2/project.png");

			/* project number */
			projectNum = new JLabel("");
			projectNum.setText("P" + count);
			projectNum.setBounds(40, 40, 50, 50);
			projectNum.setFont(realBig);
			projectNum.setForeground(Color.white);
			this.add(projectNum);

			/* project Button */
			projectButton = new JButton("");

			projectButton.setText(prjName);
			projectButton.setBounds(140, 10, 200, 100);
			setButton(projectButton);
			projectButton.setFont(realBig);
			projectButton.setActionCommand("project");

			this.add(projectButton);

			/* delete button */
			deleteButton = new JButton("");

			deleteImage = setImage("resource/windows2/delete.png");
			deleteButton.setIcon(new ImageIcon(deleteImage));
			deleteButton.setBounds(335, 20, deleteImage.getWidth(null), deleteImage.getHeight(null));

			setButton(deleteButton);
			deleteButton.setActionCommand("delete");
			this.add(deleteButton);

			/* Button Listener */
			class BtnListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					String action = e.getActionCommand();
					switch (action) {
					case "project":
						curr_proj = getProject().projectButton.getText();
						setVList();
						((CardLayout) getCards().getLayout()).show(getCards(), "Window-5");
						break;
					case "delete":
						boolean status = mc.getAdminP().delProject(getProject().projectButton.getText());
						if (status) {
							((CardLayout) getCards().getLayout()).show(getCards(), "Window-4");
							((CardLayout) getCards().getLayout()).show(getCards(), "Window-2");

							setPList();

						} else {
							JOptionPane.showMessageDialog(null, "you cannot delete this project", "ERROR",
									JOptionPane.WARNING_MESSAGE, null);
						}

						break;
					default:
						break;
					}
				}
			}

			BtnListener btnListener = new BtnListener();
			projectButton.addActionListener(btnListener);
			deleteButton.addActionListener(btnListener);
		}

		public Project getProject() {
			return this;
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(projectImage, 0, 0, null);
		}

	}

	class ProjectAddPanel extends JPanel {
		Image bgImage;

		Image nameImage, projectImage, addImage, cancelImage;
		JLabel nameLabel, projectLabel;
		JButton addButton, cancelButton;
		JTextField projectTxt;

		public ProjectAddPanel() {

			this.setLayout(null);

			font = new Font("12롯데마트행복Medium", Font.PLAIN, 20);

			/* background */
			bgImage = setImage("resource/windows4/back.png");

			/* name button */
			nameLabel = new JLabel("");

			nameImage = setImage("resource/windows4/windowName.png");
			nameLabel.setBounds(10, 10, nameImage.getWidth(null), nameImage.getHeight(null));
			nameLabel.setIcon(new ImageIcon(nameImage));
			this.add(nameLabel);

			/* project num label */
			// !-project Num 가져와서 출력해야햄

			projectLabel = new JLabel("");
			projectImage = setImage("resource/windows4/projectnum.png");
			projectLabel.setBounds(120, 220, projectImage.getWidth(null), projectImage.getHeight(null));
			projectLabel.setIcon(new ImageIcon(projectImage));
			projectLabel.setIconTextGap(-projectImage.getWidth(null));
			projectLabel.setOpaque(true);
			projectLabel.setLayout(null);
			this.add(projectLabel);

			// !- project num 가져와야해
			String a = "[Project]";
			projectLabel.setText(a);
			projectLabel.setHorizontalTextPosition(JLabel.CENTER);
			projectLabel.setFont(font);
			projectLabel.setForeground(Color.white);
			setVisible(true);

			/* project name Field */
			projectTxt = new JTextField("");
			projectTxt.setBounds(10, 300, 390, 40);
			projectTxt.setBorder(null);
			projectTxt.setFont(font);
			projectTxt.setHorizontalAlignment(JTextField.CENTER);
			this.add(projectTxt);

			/* add button */
			addButton = new JButton("");

			addImage = setImage("resource/windows4/add.png");
			addButton.setBounds(100, 360, addImage.getWidth(null), addImage.getHeight(null));
			addButton.setIcon(new ImageIcon(addImage));
			setButton(addButton);
			this.add(addButton);

			/* cancel button */
			cancelButton = new JButton("");
			cancelImage = setImage("resource/windows4/cancel.png");
			cancelButton.setBounds(220, 360, cancelImage.getWidth(null), cancelImage.getHeight(null));
			cancelButton.setIcon(new ImageIcon(cancelImage));

			setButton(cancelButton);
			this.add(cancelButton);

			/* Button Listener */
			class BtnListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {

					Object source = e.getSource();
					if (source.equals(projectAddPanel.addButton)) {

						/* text field에서 project이름 가져옴 */
						String prjName = projectAddPanel.projectTxt.getText();
						if (prjName.length() != 0) {
							System.out.println(prjName);
							if (mc.getAdminP().addProject(prjName)) {
								setPList();
								// change window
								((CardLayout) getCards().getLayout()).show(getCards(), "Window-2");

							} else {
								JOptionPane.showMessageDialog(null, "Project Add Error", "ERROR",
										JOptionPane.WARNING_MESSAGE, null);
							}
						} else {

							JOptionPane.showMessageDialog(null, "No Project Name", "Project Add Error",
									JOptionPane.WARNING_MESSAGE, null);

						}

					} else if (source.equals(projectAddPanel.cancelButton)) {
						setPList();
						((CardLayout) getCards().getLayout()).show(getCards(), "Window-2");
					}
				}

			}

			BtnListener btnListener = new BtnListener();
			addButton.addActionListener(btnListener);
			cancelButton.addActionListener(btnListener);

		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bgImage, 0, 0, null);
		}
	}
	
	/* Version */
	class Version extends JPanel {

		Image versionImage, imgHigh, imgLow, fileImage, deleteImage, profileImage;
		String ctgNum, choiceNum, caseNum, date;
		JTextPane contentTxt;

		public Version(String num, String ctgNum, String choiceNum, String caseNum) {

			JButton fileButton, deleteButton, profileButton;
			JTextField dateTxt;
			JLabel verTxt;

			font = new Font("12롯데마트행복Medium", Font.PLAIN, 13);
			bigFont = new Font("12롯데마트행복Medium", Font.PLAIN, 30);
			this.setLayout(null);

			/* background */
			imgHigh = setImage("resource/windows5/version.png");
			imgLow = setImage("resource/windows5/lower.png");

			String tempArr[] = num.split("\\.");
			int a = Integer.parseInt(tempArr[0]);
			int b = Integer.parseInt(tempArr[1]);

			if (b != 0) {
				versionImage = imgLow;
			} else {
				versionImage = imgHigh;
			}

			/* File download Button */
			fileButton = new JButton();
			fileImage = setImage("resource/windows5/file.png");
			fileButton.setIcon(new ImageIcon(fileImage));
			fileButton.setBounds(20, 20, fileImage.getWidth(null), fileImage.getHeight(null));
			fileButton.setActionCommand("download");
			setButton(fileButton);
			this.add(fileButton);

			/* version Label */
			verTxt = new JLabel(num);
			verTxt.setBounds(20, 90, 35, 20);
			verTxt.setHorizontalAlignment(JLabel.CENTER);
			verTxt.setFont(font);

			this.add(verTxt);

			/* contentTxt */
			contentTxt = new JTextPane();
			/*
			 * String ctgNum = "8"; String choiceNum = "90"; String caseNum =
			 * "10";
			 */
			String content = "\nCategory       " + ctgNum + "\nChoice         " + choiceNum + "\nTest Case       "
					+ caseNum;

			contentTxt.setText(content);
			contentTxt.setBounds(80, 10, 220, 100);

			StyledDocument doc = contentTxt.getStyledDocument();
			SimpleAttributeSet center = new SimpleAttributeSet();
			StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
			doc.setParagraphAttributes(0, doc.getLength(), center, false);

			contentTxt.setOpaque(false);
			contentTxt.setBorder(null);
			contentTxt.setEditable(false);
			contentTxt.setFont(font);

			this.add(contentTxt);

			/* profile Button */
			profileButton = new JButton("");

			profileImage = setImage("resource/windows5/profile.png");
			profileButton.setBounds(280, 20, 80, 80);
			profileButton.setIcon(new ImageIcon(profileImage));

			profileButton.setActionCommand("opinion");
			setButton(profileButton);
			this.add(profileButton);

			/* ver 업로드 시간 */
			dateTxt = new JTextField("");

			dateTxt.setText(date);
			dateTxt.setBounds(250, 95, 100, 20);
			dateTxt.setOpaque(false);
			dateTxt.setEditable(false);
			dateTxt.setBorder(null);
			dateTxt.setFont(font);
			this.add(dateTxt);

			/* delete button */
			deleteButton = new JButton("");
			deleteImage = setImage("resource/windows5/delete.png");
			deleteButton.setIcon(new ImageIcon(deleteImage));
			deleteButton.setBounds(345, 0, 40, 40);

			setButton(deleteButton);
			deleteButton.setActionCommand("delete");
			this.add(deleteButton);

			/* Button Listener */
			class BtnListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					String action = e.getActionCommand();
					switch (action) {
					case "opinion":
						curr_verNum = verTxt.getText();
						// #mc.getAdminV().rcvOpinion(curr_proj, curr_verNum);
						setOList();
						((CardLayout) getCards().getLayout()).show(getCards(), "Window-6");
						break;

					case "delete":
						curr_verNum = verTxt.getText();
						System.out.println(curr_proj + " " + curr_verNum);
						if (mc.getAdminV().delVer(curr_proj, curr_verNum)) {
							((CardLayout) getCards().getLayout()).show(getCards(), "Window-4");
							((CardLayout) getCards().getLayout()).show(getCards(), "Window-5");

							setVList();
						} else {
							JOptionPane.showMessageDialog(null, "You dont have right to delete version", "ERROR",
									JOptionPane.WARNING_MESSAGE, null);
						}
						break;

					case "download":
						curr_verNum = verTxt.getText();
						File file = mc.getAdminV().downloadFile(curr_proj, curr_verNum);

						FileDialog fd = new FileDialog(new JFrame(), "Save file", FileDialog.SAVE);
						fd.setFile(".txt");
						fd.setVisible(true);
						String filePath = fd.getDirectory() + fd.getFile();
						if (!filePath.contains(".txt")) {
							filePath += ".txt";
						}

						FileReader frd = null;
						BufferedReader brd = null;
						BufferedWriter writer = null;
						String str = null;
						boolean hasMore = true;

						try {
							writer = new BufferedWriter(new FileWriter(filePath));
							frd = new FileReader(file);
							brd = new BufferedReader(frd);

							while (hasMore) {
								if ((str = brd.readLine()) != null) {
									writer.write(str);
									writer.write("\r\n");
									hasMore = true;
								} else
									hasMore = false;
							}

							frd.close();
							brd.close();
							writer.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}

						break;
					default:
						break;
					}
				}
			}

			BtnListener btnListener = new BtnListener();
			profileButton.addActionListener(btnListener);
			deleteButton.addActionListener(btnListener);
			fileButton.addActionListener(btnListener);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(versionImage, 0, 0, null);
		}

		public Version getVersion() {
			return this;
		}

		public void setCtgNum(int num) {
			this.ctgNum = Integer.toString(num);
		}

		public void setCaseNum(int num) {
			this.caseNum = Integer.toString(num);
		}

		public void setChoiceNum(int num) {
			this.choiceNum = Integer.toString(num);
		}
	}

	class VerListPanel extends JPanel {
		JPanel listPanel;
		Image bgImage;
		Image homeImage, addImage, fileImage;
		JButton homeButton, addButton, fileButton, verButton, delButton;

		public VerListPanel() {

			this.setLayout(null);
			/* background */
			bgImage = setImage("resource/windows2/back.png");

			/* home */
			homeButton = new JButton("");
			homeImage = setImage("resource/windows5/logo.png");
			homeButton.setIcon(new ImageIcon(homeImage));
			homeButton.setBounds(10, 10, homeImage.getWidth(null), homeImage.getHeight(null));
			setButton(homeButton);
			this.add(homeButton);

			/* add */
			addButton = new JButton("add");
			addImage = setImage("resource/windows2/add.png");
			addButton.setIcon(new ImageIcon(addImage));
			addButton.setBounds(30, 100, addImage.getWidth(null), addImage.getHeight(null));
			setButton(addButton);
			this.add(addButton);

			/* Version 넣을 panel */
			listPanel = new JPanel();
			listPanel.setBounds(5, 100, 400, 600);
			listPanel.setOpaque(false);
			listPanel.setBorder(null);
			this.add(listPanel);

			/* Version 애들 추가될 panel */
			GridBagConstraints frameConstraints = new GridBagConstraints();

			versionSet = new JPanel();

			versionSet.setLayout(new GridLayout(0, 1, 0, 1));
			versionSet.setBounds(20, 150, 380, 500);

			JScrollPane scrollFrame = new JScrollPane(versionSet, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollFrame.setBounds(20, 150, 380, 500);
			scrollFrame.setPreferredSize(new Dimension(380, 500));
			scrollFrame.setViewportView(versionSet);
			scrollFrame.setBorder(null);

			frameConstraints.gridx = 0;
			frameConstraints.gridy = 1;
			frameConstraints.weighty = 1;
			this.add(scrollFrame, frameConstraints);
			scrollFrame.setBackground(new Color(1, 0, 0, 0));
			scrollFrame.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
			scrollFrame.getViewport().setBorder(null);
			scrollFrame.setViewportBorder(null);
			scrollFrame.setOpaque(false);
			scrollFrame.setVisible(true);

			/* Button Listener */
			class BtnListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					Object source = e.getSource();
					if (source.equals(verListPanel.homeButton)) {
						setPList();
						((CardLayout) getCards().getLayout()).show(getCards(), "Window-2");

					} else if (source.equals(verListPanel.addButton)) {
						((CardLayout) getCards().getLayout()).show(getCards(), "Window-7");
					}
				}

			}

			BtnListener btnListener = new BtnListener();
			addButton.addActionListener(btnListener);
			homeButton.addActionListener(btnListener);

		}

		public JPanel getListPanel() {
			return listPanel;
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bgImage, 0, 0, null);
		}
	}

	class NoticeContent extends JPanel {
		Image noticeImage;
		JTextPane noticeTxt;

		public NoticeContent() {

			this.setLayout(null);

			font = new Font("12롯데마트행복Medium", Font.PLAIN, 18);

			noticeImage = setImage("resource/windows8/content.png");
			this.setBounds(15, 290, noticeImage.getWidth(null), noticeImage.getHeight(null));

			noticeTxt = new JTextPane();
			noticeTxt.setBounds(50, 50, 250, 150);
			noticeTxt.setBorder(null);
			noticeTxt.setFont(font);
			this.add(noticeTxt);

		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(noticeImage, 0, 0, null);
		}

		public JTextPane getNoticeContent() {
			return this.noticeTxt;
		}

	}

	class VerAddPanel extends JPanel {
		Image bgImage;
		Image nameImage, backImage, lowerImage, higherImage;
		JLabel nameLabel;
		JButton backButton, lowerButton, higherButton;
		ContentPanel contentPanel;

		public VerAddPanel() {

			this.setLayout(null);
			font = new Font("12롯데마트행복Medium", Font.BOLD, 20);

			/* background */
			bgImage = setImage("resource/windows8/back.png");

			/* name Label */
			nameLabel = new JLabel("");
			nameImage = setImage("resource/windows8/name.png");
			nameLabel.setBounds(10, 10, nameImage.getWidth(null), nameImage.getHeight(null));
			nameLabel.setIcon(new ImageIcon(nameImage));
			this.add(nameLabel);

			/* Content Panel */

			contentPanel = new ContentPanel();
			this.add(contentPanel);
			noticeContent = new NoticeContent();
			this.add(noticeContent);

			/* 뒤로가기 */
			backButton = new JButton("뒤로");
			backImage = setImage("resource/windows8/cancel.png");
			backButton.setBounds(20, 550, backImage.getWidth(null), backImage.getHeight(null));
			backButton.setIcon(new ImageIcon(backImage));
			backButton.setFont(font);
			setButton(backButton);
			backButton.setHorizontalTextPosition(JButton.CENTER);
			this.add(backButton);

			/* lower version button */
			lowerButton = new JButton("");
			lowerImage = setImage("resource/windows8/lower.png");
			lowerButton.setBounds(150, 550, lowerImage.getWidth(null), lowerImage.getHeight(null));
			lowerButton.setIcon(new ImageIcon(lowerImage));
			lowerButton.setIconTextGap(-lowerImage.getWidth(null));
			lowerButton.setOpaque(true);
			lowerButton.setLayout(null);
			this.add(lowerButton);

			String lowerVerNum = "";
			lowerButton.setText(lowerVerNum);
			lowerButton.setHorizontalTextPosition(JButton.CENTER);
			lowerButton.setFont(font);
			setButton(lowerButton);
			setVisible(true);

			/* higher version button */
			higherButton = new JButton("");
			higherImage = setImage("resource/windows8/higher.png");
			higherButton.setBounds(280, 550, higherImage.getWidth(null), higherImage.getHeight(null));
			higherButton.setIcon(new ImageIcon(higherImage));
			higherButton.setIconTextGap(-higherImage.getWidth(null));

			higherButton.setOpaque(true);
			higherButton.setLayout(null);
			this.add(higherButton);

			String higherVerNum = "";
			higherButton.setText(higherVerNum);
			higherButton.setHorizontalTextPosition(JButton.CENTER);
			higherButton.setFont(font);
			setButton(higherButton);
			setVisible(true);

			/* Button Listener */
			class BtnListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					Object source = e.getSource();
					if (source.equals(verAddPanel.backButton)) {
						// setVList();
						((CardLayout) getCards().getLayout()).show(getCards(), "Window-7");

					} else if (source.equals(verAddPanel.lowerButton)) {

						curr_verNum = lowerButton.getText();

						// # 원래는 tmpFile 만들어진 것 가져와서 rename하며 사용.

						LinkedList<model.Opinion> tmpO = mc.getAdminV().getTempVer().getOpinionList();

						model.Opinion opinion = new model.Opinion(noticeContent.getNoticeContent().getText());
						tmpO.add(opinion);
						mc.getAdminV().getTempVer().setOpinionList(tmpO);
						System.out.println(mc.getAdminV().getTempVer().getOpinionList().size() + "AAAA");

						mc.getAdminV().getTempVer().setVerNum(curr_verNum);
						mc.getAdminV().uploadVer(curr_proj, mc.getAdminV().getTempVer());

						noticeContent.noticeTxt.setText(null);
						((CardLayout) getCards().getLayout()).show(getCards(), "Window-5");
						setVList();

					} else if (source.equals(verAddPanel.higherButton)) {

						curr_verNum = higherButton.getText();
						LinkedList<model.Opinion> tmpO = mc.getAdminV().getTempVer().getOpinionList();

						model.Opinion opinion = new model.Opinion(noticeContent.getNoticeContent().getText());
						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Date date = new Date();
						opinion.setDate(dateFormat.format(date));
						tmpO.add(opinion);

						mc.getAdminV().getTempVer().setVerNum(curr_verNum);
						mc.getAdminV().uploadVer(curr_proj, mc.getAdminV().getTempVer());

						setVList();
						// setOList();
						noticeContent.noticeTxt.setText(null);
						((CardLayout) getCards().getLayout()).show(getCards(), "Window-5");
					}
				}

			}

			BtnListener btnListener = new BtnListener();
			backButton.addActionListener(btnListener);
			lowerButton.addActionListener(btnListener);
			higherButton.addActionListener(btnListener);

		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bgImage, 0, 0, null);
		}

		public JPanel getContent() {
			return noticeContent;
		}

	}

	class FileAddPanel extends JPanel {
		Image bgImage, nameImage, addImage, testImage, cancelImage, pathImage;
		JLabel nameLabel;
		JButton addButton, testButton, cancelButton, pathButton;
		FileDialog fd;

		public FileAddPanel() {

			this.setLayout(null);

			font = new Font("12롯데마트행복Medium", Font.BOLD, 20);

			/* background */
			bgImage = setImage("resource/windows7/back.png");

			/* name Label */
			nameLabel = new JLabel("");

			nameImage = setImage("resource/windows7/Name.png");
			nameLabel.setBounds(10, 10, nameImage.getWidth(null), nameImage.getHeight(null));
			nameLabel.setIcon(new ImageIcon(nameImage));

			this.add(nameLabel);

			/* path txt field */
			pathButton = new JButton("");
			pathButton.setBounds(20, 180, 280, 47);
			pathButton.setBackground(Color.white);
			pathButton.setOpaque(true);
			pathButton.setBorder(null);
			pathButton.setFont(font);
			pathButton.setFocusable(false);

			this.add(pathButton);

			/* add button */
			addButton = new JButton("");

			addImage = setImage("resource/windows7/add.png");
			addButton.setBounds(300, 180, addImage.getWidth(null), addImage.getHeight(null));
			addButton.setIcon(new ImageIcon(addImage));

			setButton(addButton);
			this.add(addButton);

			/* test button */
			testButton = new JButton("");
			testImage = setImage("resource/windows7/test.png");
			testButton.setBounds(20, 300, testImage.getWidth(null), testImage.getHeight(null));
			testButton.setIcon(new ImageIcon(testImage));

			setButton(testButton);
			this.add(testButton);

			/* cancel button */
			cancelButton = new JButton("");
			cancelImage = setImage("resource/windows7/cancel.png");
			cancelButton.setBounds(220, 300, cancelImage.getWidth(null), cancelImage.getHeight(null));
			cancelButton.setIcon(new ImageIcon(cancelImage));
			setButton(cancelButton);
			this.add(cancelButton);

			/* Button Listener */
			class BtnListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					Object source = e.getSource();

					// #파일추가... AddVer
					if (source.equals(fileAddPanel.testButton)) {

						String filePath = fileAddPanel.pathButton.getText();

						try {
							if (mc.getAdminV().addVer(new File(filePath))) {
								mc.getAdminV().setTempVer(mc.getAdminV().analyzeFile(filePath));
								mc.getAdminV().makeCase(mc.getAdminV().getTempVer());

								String lowerVer, higherVer;
								if (last_verNum.equals("0.0")) {
									lowerVer = "1.0";

									higherVer = "0.0";
									verAddPanel.higherButton.setEnabled(false);
									// !!!!아무것도 없어야 해 higher button
								} else {
									String strArr[] = last_verNum.split("\\.");
									int a = Integer.parseInt(strArr[0]);
									int b = Integer.parseInt(strArr[1]);

									higherVer = Integer.toString(++a) + "." + Integer.toString(0);
									lowerVer = Integer.toString(--a) + "." + Integer.toString(++b);
									verAddPanel.higherButton.setEnabled(true);
								}

								verAddPanel.contentPanel.caseTxt
										.setText(Integer.toString(mc.getAdminV().getTempVer().getCaseNum()));
								verAddPanel.contentPanel.choiceTxt
										.setText(Integer.toString(mc.getAdminV().getTempVer().getChoiceNum()));
								verAddPanel.contentPanel.ctgTxt
										.setText(Integer.toString(mc.getAdminV().getTempVer().getCtgNum()));
								verAddPanel.lowerButton.setText(lowerVer);
								verAddPanel.higherButton.setText(higherVer);

								((CardLayout) getCards().getLayout()).show(getCards(), "Window-8");
							} else {
								JOptionPane.showMessageDialog(null, "txt파일이 아닙니다.");
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					} else if (source.equals(fileAddPanel.cancelButton)) {
						setVList();
						((CardLayout) getCards().getLayout()).show(getCards(), "Window-5");

					} else if (source.equals(fileAddPanel.pathButton)) {
						selectFolder();
					}
				}

			}

			BtnListener btnListener = new BtnListener();
			testButton.addActionListener(btnListener);
			cancelButton.addActionListener(btnListener);
			pathButton.addActionListener(btnListener);

		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bgImage, 0, 0, null);
		}

		public void selectFolder() {
			FileDialog fd = new FileDialog(new JFrame(), "Upload file", FileDialog.LOAD);
			fd.setFile("*.txt");
			fd.setVisible(true);
			if (fd.getDirectory() != null && fd.getFile() != null) {
				pathButton.setText(fd.getDirectory() + fd.getFile());
			}
		}

	}

	/* opinion 각각의 Panel */
	class Opinion extends JPanel {
		Image opinionImage, profileImage, contentImage, deleteImage;
		int opinionNum;
		String id, date, content;

		public Opinion(String id, String date, String content, int opinionNum) {

			this.opinionNum = opinionNum;
			this.id = id;
			this.date = date;
			this.content = content;

			JLabel profileLabel;
			JButton deleteButton;
			JTextField nameTxt, dateTxt;
			JTextPane contentTxt, opinionTxt;

			font = new Font("12롯데마트행복Medium", Font.PLAIN, 20);
			bigFont = new Font("12롯데마트행복Medium", Font.PLAIN, 30);
			Font smallFont = new Font("NanumGothic", Font.PLAIN, 12);
			this.setLayout(null);

			/* background */
			opinionImage = setImage("resource/windows6/opinion.png");

			/* opinion profile Label */
			profileLabel = new JLabel("");
			profileImage = setImage("resource/windows6/profile.png");
			profileLabel.setBounds(20, 20, 80, 80);
			profileLabel.setIcon(new ImageIcon(profileImage));
			this.add(profileLabel);

			/* opinion writer txt */
			nameTxt = new JTextField(id);
			nameTxt.setBounds(35, 100, 40, 20);
			nameTxt.setOpaque(false);
			nameTxt.setEditable(false);
			nameTxt.setBorder(null);
			nameTxt.setFont(smallFont);
			this.add(nameTxt);

			/* Opinion 내용 */
			contentTxt = new JTextPane();
			contentTxt.setText(content);
			contentTxt.setBounds(120, 20, 200, 80);
			contentTxt.setOpaque(false);
			contentTxt.setEditable(false);
			contentTxt.setBorder(null);
			contentTxt.setFont(smallFont);
			this.add(contentTxt);

			/* Opinion 작성 시간 */
			dateTxt = new JTextField(date);
			dateTxt.setBounds(250, 95, 100, 20);
			dateTxt.setOpaque(false);
			dateTxt.setEditable(false);
			dateTxt.setBorder(null);
			dateTxt.setFont(smallFont);
			this.add(dateTxt);

			/* Delete Button */
			deleteButton = new JButton("");
			deleteImage = setImage("resource/windows6/delete.png");
			deleteButton.setBounds(330, 10, deleteImage.getWidth(null), deleteImage.getHeight(null));
			deleteButton.setIcon(new ImageIcon(deleteImage));
			setButton(deleteButton);
			deleteButton.setActionCommand("delete");
			this.add(deleteButton);

			/* Button Listener */
			class BtnListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					String action = e.getActionCommand();
					switch (action) {
					case "delete":
						if (mc.getAdminV().delOpinion(curr_proj, curr_verNum, Integer.toString(opinionNum))) {
							System.out.println("AAAAAAAAAAAAAAAAAAAAAAAA");

							// ((CardLayout)
							// getCards().getLayout()).show(getCards(),
							// "Window-2");
							((CardLayout) getCards().getLayout()).show(getCards(), "Window-6");

							setOList();

						} else {
							JOptionPane.showMessageDialog(null, "You dont have right to delete opinion", "ERROR",
									JOptionPane.WARNING_MESSAGE, null);
						}

						break;
					default:
						break;
					}
				}
			}

			BtnListener btnListener = new BtnListener();
			deleteButton.addActionListener(btnListener);

		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(opinionImage, 0, 0, null);
		}

		public Opinion getOpinion() {
			return this;
		}
	}

	class NoticePanel extends JPanel {

		Image noticeProfileImage, noticeImage;
		JLabel noticeProfileLabel;
		JTextField nameTxt, dateTxt;
		JTextPane contentTxt, opinionTxt;

		public NoticePanel(String id, String date, String content) {
			Font smallFont = new Font("NanumGothic", Font.PLAIN, 12);
			this.setLayout(null);
			noticeImage = setImage("resource/windows6/notice.png");

			/* notice profile Label */
			noticeProfileLabel = new JLabel("");
			noticeProfileImage = setImage("resource/windows6/noticeProfile.png");
			noticeProfileLabel.setBounds(20, 20, 80, 80);
			noticeProfileLabel.setIcon(new ImageIcon(noticeProfileImage));

			this.add(noticeProfileLabel);

			/* Notice 작성자 */
			nameTxt = new JTextField(id);
			nameTxt.setBounds(45, 100, 40, 20);
			nameTxt.setOpaque(false);
			nameTxt.setEditable(false);
			nameTxt.setBorder(null);
			nameTxt.setFont(smallFont);
			this.add(nameTxt);

			/* Notice 내용 */
			contentTxt = new JTextPane();
			contentTxt.setText(content);
			contentTxt.setBounds(120, 20, 200, 80);
			contentTxt.setOpaque(false);
			contentTxt.setEditable(false);
			contentTxt.setBorder(null);
			contentTxt.setFont(smallFont);
			this.add(contentTxt);

			/* Opinion 작성 시간 */
			dateTxt = new JTextField(date);
			dateTxt.setBounds(250, 95, 100, 20);
			dateTxt.setOpaque(false);
			dateTxt.setEditable(false);
			dateTxt.setBorder(null);
			dateTxt.setFont(smallFont);
			this.add(dateTxt);

		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(noticeImage, 0, 0, null);
		}
	}

	class OpinionListPanel extends JPanel {
		Image bgImage;
		Image nameImage, sendImage;
		JButton nameButton, sendButton;
		JTextField verTxt;
		JTextPane opinionTxt;
		NoticePanel noticePanel;
		String verNum;

		public OpinionListPanel() {

			this.setLayout(null);

			font = new Font("12롯데마트행복Medium", Font.PLAIN, 20);
			bigFont = new Font("12롯데마트행복Medium", Font.PLAIN, 30);
			Font smallFont = new Font("NanumGothic", Font.PLAIN, 12);

			/* background */
			bgImage = setImage("resource/windows6/back.png");

			/* name Button */
			nameButton = new JButton("");
			nameImage = setImage("resource/windows6/name.png");
			nameButton.setBounds(10, 10, nameImage.getWidth(null) + 30, nameImage.getHeight(null) + 20);
			nameButton.setIcon(new ImageIcon(nameImage));
			setButton(nameButton);
			this.add(nameButton);

			/* ver num txt field */
			// !- vernum 받아와야해

			verTxt = new JTextField("2");
			verTxt.setText(verNum);
			verTxt.setBounds(75, 0, 80, 75);
			verTxt.setOpaque(false);
			verTxt.setEditable(false);
			verTxt.setBorder(null);
			verTxt.setFont(bigFont);
			verTxt.setForeground(Color.BLACK);
			this.add(verTxt);

			// ************noticePanel을 넣어야한다.!!!!!!!!!!!!!!!!
			noticePanel = new NoticePanel(mc.getAdminU().getID(), verNum, verNum);
			noticePanel.setBounds(30, 60, 358, 121);
			this.add(noticePanel);

			/* opinion 애들 추가될 panel */
			GridBagConstraints frameConstraints = new GridBagConstraints();

			opinionSet = new JPanel();

			opinionSet.setLayout(new GridLayout(0, 1, 0, 1));
			opinionSet.setBounds(20, 195, 380, 380);

			JScrollPane scrollFrame = new JScrollPane(opinionSet, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollFrame.setBounds(20, 195, 380, 380);
			scrollFrame.setPreferredSize(new Dimension(380, 380));
			scrollFrame.setViewportView(opinionSet);
			scrollFrame.setBorder(null);

			frameConstraints.gridx = 0;
			frameConstraints.gridy = 1;
			frameConstraints.weighty = 1;
			this.add(scrollFrame, frameConstraints);
			scrollFrame.setBackground(new Color(1, 0, 0, 0));
			scrollFrame.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
			scrollFrame.getViewport().setBorder(null);
			scrollFrame.setViewportBorder(null);
			scrollFrame.setOpaque(false);
			scrollFrame.setVisible(true);

			/* 내용 추가할 textfield */
			opinionTxt = new JTextPane();
			opinionTxt.setBounds(20, 580, 300, 70);
			opinionTxt.setOpaque(true);
			opinionTxt.setEditable(true);
			opinionTxt.setFont(smallFont);
			this.add(opinionTxt);

			/* Send Button */
			sendButton = new JButton("");
			sendImage = setImage("resource/windows6/send.png");
			sendButton.setBounds(330, 580, sendImage.getWidth(null), sendImage.getHeight(null));
			sendButton.setIcon(new ImageIcon(sendImage));
			setButton(sendButton);
			this.add(sendButton);

			/* Button Listener */
			class BtnListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e) {
					Object source = e.getSource();
					if (source.equals(opinionListPanel.sendButton)) {

						// #opinionNum, opinionID는 서버에서 set.
						model.Opinion opinion = new model.Opinion(opinionListPanel.opinionTxt.getText());
						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Date date = new Date();
						opinion.setDate(dateFormat.format(date));

						// addOpinion
						mc.getAdminV().addOpinion(opinion, curr_proj, curr_verNum);
						setOList();

					} else if (source.equals(opinionListPanel.nameButton)) {
						setVList();
						((CardLayout) getCards().getLayout()).show(getCards(), "Window-5");
					}
				}

			}

			BtnListener btnListener = new BtnListener();
			sendButton.addActionListener(btnListener);
			nameButton.addActionListener(btnListener);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bgImage, 0, 0, null);
		}

	}

	public void setButton(JButton button) {
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBorderPainted(false);
	}

	public void setPList() {
		int count = 0;

		Set<String> prjList = mc.getAdminP().rcvProjectList();
		// **
		projectSet.removeAll();
		for (String str : prjList) {
			count++;

			Project tmpProject = new Project(str, count);
			tmpProject.setPreferredSize(new Dimension(360, 140));
			projectSet.add(tmpProject);
			projectSet.revalidate();
			projectSet.repaint();
			projectListPanel.revalidate();

		}
		projectAddPanel.projectTxt.setText("");
	}

	public void setVList() {
		LinkedList<model.Version> vList = mc.getAdminV().rcvVerList(curr_proj);
		// #version 작게, 크게 그리기 비교.
		if (vList.size() == 0) {
			last_verNum = "0.0";
		} else {
			last_verNum = vList.getLast().getVerNum();
		}

		versionSet.removeAll();
		for (model.Version str : vList) {
			Version tmpVer = new Version(str.getVerNum(), Integer.toString(str.getCtgNum()),
					Integer.toString(str.getCaseNum()), Integer.toString(str.getChoiceNum()));

			// tmpVer
			versionSet.add(tmpVer);
			tmpVer.setPreferredSize(new Dimension(360, 140));
			tmpVer.contentTxt.revalidate();
			tmpVer.contentTxt.repaint();
			versionSet.revalidate();
			versionSet.repaint();
			verListPanel.revalidate();
		}
		fileAddPanel.pathButton.setText("");

	}

	public void setOList() {
		LinkedList<model.Opinion> oList = mc.getAdminV().rcvOpinionList(curr_proj, curr_verNum);
		System.out.println(oList.size());
		opinionSet.removeAll();
		for (model.Opinion opi : oList) {
			if (opi.getOpinionNum() == 0) {
				// NoticePanel np = new NoticePanel(opi.getOpinionID(),
				// opi.getDate(), opi.getContent());
				// noticePanel.noticeTxt.setText(opi.getContent());
				opinionListPanel.verTxt.setText(curr_verNum);
				opinionListPanel.noticePanel.contentTxt.setText(opi.getContent());
				opinionListPanel.noticePanel.dateTxt.setText(opi.getDate());
				opinionListPanel.noticePanel.nameTxt.setText(opi.getOpinionID());

				opinionSet.repaint();
				opinionListPanel.revalidate();

			} else {
				Opinion tmpOpi = new Opinion(opi.getOpinionID(), opi.getDate(), opi.getContent(), opi.getOpinionNum());
				opinionSet.add(tmpOpi);
				opinionSet.revalidate();
				opinionSet.repaint();
				opinionListPanel.revalidate();
			}
		}
		opinionListPanel.nameButton.setText(curr_verNum);
		opinionListPanel.opinionTxt.setText("");
	}

	public class ContentPanel extends JPanel {

		Image contentImage;
		JLabel ctgNum, choiceNum, caseNum, ctgTxt, choiceTxt, caseTxt;

		public ContentPanel() {

			this.setLayout(null);
			font = new Font("12롯데마트행복Medium", Font.PLAIN, 18);

			contentImage = setImage("resource/windows8/content.png");
			this.setBounds(15, 60, contentImage.getWidth(null), contentImage.getHeight(null));

			/* Category Num */
			ctgNum = new JLabel("Category");
			ctgNum.setBounds(60, 40, 100, 20);
			ctgNum.setFont(font);

			ctgTxt = new JLabel("");
			ctgTxt.setBounds(260, 40, 40, 20);
			ctgTxt.setFont(font);
			ctgTxt.setHorizontalAlignment(JTextField.RIGHT);
			ctgTxt.setText("13");
			// !-category num 받아와서 set

			this.add(ctgNum);
			this.add(ctgTxt);

			/* choice Num */
			choiceNum = new JLabel("Choice");
			choiceNum.setBounds(60, 100, 100, 20);
			choiceNum.setFont(font);
			choiceTxt = new JLabel("");
			choiceTxt.setBounds(260, 100, 40, 20);
			choiceTxt.setFont(font);
			choiceTxt.setHorizontalAlignment(JTextField.RIGHT);
			choiceTxt.setText("100");
			this.add(choiceNum);
			this.add(choiceTxt);

			/* test case */
			caseNum = new JLabel("Test Case");
			caseNum.setBounds(60, 160, 100, 20);
			caseNum.setFont(font);
			caseTxt = new JLabel("");
			caseTxt.setBounds(260, 160, 40, 20);
			caseTxt.setFont(font);
			caseTxt.setHorizontalAlignment(JTextField.RIGHT);
			caseTxt.setText("300");
			this.add(caseNum);
			this.add(caseTxt);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(contentImage, 0, 0, null);
		}

	}

}
