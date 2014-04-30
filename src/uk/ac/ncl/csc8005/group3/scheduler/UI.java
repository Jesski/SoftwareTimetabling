package uk.ac.ncl.csc8005.group3.scheduler;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTextPane;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Arrays;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

public class UI {
	private JFrame frame;
	private JMenuItem menuItem;
	JPanel panelMain, panelRun, panelViewModule, panelCreateModule, panelEditModule,
	panelViewSchedule, panelCreateSchedule, panelStudentModule, panelLoadStudent,
	panelAddRoom;
	private JTable table, table2;
	private JTable table_1, table_2;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;

	static ArrayList<String> clashedModules = null;

	private String moduleCode;
	private String examLength;
	private String moduleSize;
	private String moduleType;
		
	DatabaseIO db = new DatabaseIO();

	/**
	 * Launch the application.
	 * Create the application.
	 */

	public UI() {
		try {
			db.openDatabase();
		}catch (Exception e) {}

		initialize();
	}

	/**
	 * 
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setBounds(100, 100, 700, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/*--- Menu Items---*/

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		JMenuItem mntmRun = new JMenuItem("Run");
		mntmRun.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				panelCreateModule.setVisible(false);
				panelRun.setVisible(true);
				panelMain.setVisible(false);
				panelViewModule.setVisible(false);
				panelViewSchedule.setVisible(false);
				panelCreateSchedule.setVisible(false);
				panelStudentModule.setVisible(false);
				panelLoadStudent.setVisible(false);
				panelAddRoom.setVisible(false);
				panelEditModule.setVisible(false);
			}

		});

		mnMenu.add(mntmRun);
		JMenu mntmModule = new JMenu("Module");
		menuItem = new JMenuItem("View Module");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelViewModule.setVisible(true);
				panelRun.setVisible(false);
				panelMain.setVisible(false);
				panelCreateModule.setVisible(false);
				panelViewSchedule.setVisible(false);
				panelCreateSchedule.setVisible(false);
				panelStudentModule.setVisible(false);
				panelLoadStudent.setVisible(false);
				panelAddRoom.setVisible(false);
				panelEditModule.setVisible(false);
			}

		});

		mntmModule.add(menuItem);
		menuItem = new JMenuItem("Create Module");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCreateModule.setVisible(true);
				panelRun.setVisible(false);
				panelMain.setVisible(false);
				panelViewModule.setVisible(false);
				panelViewSchedule.setVisible(false);
				panelCreateSchedule.setVisible(false);
				panelStudentModule.setVisible(false);
				panelLoadStudent.setVisible(false);
				panelAddRoom.setVisible(false);
				panelEditModule.setVisible(false);
			}

		});

		mntmModule.add(menuItem);
		mnMenu.add(mntmModule);
		JMenu mntmSchedule = new JMenu("Schedule");
		menuItem = new JMenuItem("View Schedule");
		menuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				panelCreateModule.setVisible(false);
				panelRun.setVisible(false);
				panelMain.setVisible(false);
				panelViewModule.setVisible(false);
				panelViewSchedule.setVisible(true);
				panelCreateSchedule.setVisible(false);
				panelStudentModule.setVisible(false);
				panelLoadStudent.setVisible(false);
				panelAddRoom.setVisible(false);
				panelEditModule.setVisible(false);
			}

		});

		mntmSchedule.add(menuItem);
		menuItem = new JMenuItem("Create Schedule");
		menuItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				panelCreateModule.setVisible(false);
				panelRun.setVisible(false);
				panelMain.setVisible(false);
				panelViewModule.setVisible(false);
				panelViewSchedule.setVisible(false);
				panelCreateSchedule.setVisible(true);
				panelStudentModule.setVisible(false);
				panelLoadStudent.setVisible(false);
				panelAddRoom.setVisible(false);
				panelEditModule.setVisible(false);
			}

		});

		mntmSchedule.add(menuItem);
		mnMenu.add(mntmSchedule);
		JSeparator separator = new JSeparator();
		mnMenu.add(separator);
		JMenuItem mntmAddStudent = new JMenuItem("Add Student");
		mntmAddStudent.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				panelCreateModule.setVisible(false);
				panelRun.setVisible(false);
				panelMain.setVisible(false);
				panelViewModule.setVisible(false);
				panelViewSchedule.setVisible(false);
				panelCreateSchedule.setVisible(false);
				panelStudentModule.setVisible(false);
				panelLoadStudent.setVisible(true);
				panelAddRoom.setVisible(false);
				panelEditModule.setVisible(false);
			}

		});
		mnMenu.add(mntmAddStudent);
		
		JMenuItem mntmStudentModule = new JMenuItem("Student Module");
		mntmStudentModule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCreateModule.setVisible(false);
				panelRun.setVisible(false);
				panelMain.setVisible(false);
				panelViewModule.setVisible(false);
				panelViewSchedule.setVisible(false);
				panelCreateSchedule.setVisible(false);
				panelStudentModule.setVisible(true);
				panelLoadStudent.setVisible(false);
				panelAddRoom.setVisible(false);
				panelEditModule.setVisible(false);
			}
		});
		mnMenu.add(mntmStudentModule);
		
		JMenuItem mntmAddRoom = new JMenuItem("Add Room");
		mntmAddRoom.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				panelCreateModule.setVisible(false);
				panelRun.setVisible(false);
				panelMain.setVisible(false);
				panelViewModule.setVisible(false);
				panelViewSchedule.setVisible(false);
				panelCreateSchedule.setVisible(false);
				panelStudentModule.setVisible(false);
				panelLoadStudent.setVisible(false);
				panelAddRoom.setVisible(true);
				panelEditModule.setVisible(false);

			}

		});
		mnMenu.add(mntmAddRoom);

		frame.getContentPane().setLayout(new CardLayout(0, 0));

		/*------------------------ Run -------------------------------*/

		/*-----------------------------------------------------------------*/

		panelMain = new JPanel();

		frame.getContentPane().add(panelMain, "name_1392496925082617000");

		panelMain.setLayout(null);

		panelMain.setVisible(true);

		JLabel lblNewcastleTimetabling = new JLabel("Newcastle Timetabling");

		lblNewcastleTimetabling.setBounds(40, 272, 143, 16);

		panelMain.add(lblNewcastleTimetabling);

		JPanel panelBackground = new JPanel();

		BufferedImage img = null;

		try {

			img = ImageIO.read(new File("Images/Newcastle_Logo.jpg"));

		} catch (IOException e1) {

			// TODO Auto-generated catch block

			// e1.printStackTrace();

		}

		/*
		 Icon simage = new ImageIcon(img.getScaledInstance(img.getWidth()/7, img.getHeight()/8, Image.SCALE_DEFAULT));//Standard Image
		 JLabel Im = new JLabel(simage);
		 
		 panelBackground.add(Im);
		 
		 panelBackground.setBounds(6, 6, 688, 194);
		 
		 panelMain.add(panelBackground);
		 */

		/*------------------------------------------------------------------------------------------------------------------*/

		/*------------------------------------------ View Module -----------------------------------------------------------*/

		panelRun = new JPanel();
		frame.getContentPane().add(panelRun, "name_1398683175495015000");
		panelRun.setLayout(null);
		JLabel lblExam = new JLabel("Exam");
		lblExam.setBounds(166, 107, 61, 16);
		panelRun.add(lblExam);
		JLabel lblPeriod = new JLabel("period");
		lblPeriod.setBounds(239, 107, 61, 16);
		panelRun.add(lblPeriod);
		JLabel lblStart = new JLabel("start");
		lblStart.setBounds(239, 148, 61, 16);
		panelRun.add(lblStart);
		JLabel lblEnd = new JLabel("end");
		lblEnd.setBounds(239, 190, 61, 16);
		panelRun.add(lblEnd);
		final JDateChooser dateChooser_2 = new JDateChooser();
		dateChooser_2.setBounds(366, 136, 123, 28);
		panelRun.add(dateChooser_2);
		final JDateChooser dateChooser_3 = new JDateChooser();
		dateChooser_3.setBounds(366, 178, 123, 28);
		panelRun.add(dateChooser_3);
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				System.out.println("Test");
				String startDate = ((JTextField) dateChooser_2.getDateEditor().getUiComponent()).getText();

				String endDate = ((JTextField) dateChooser_3.getDateEditor().getUiComponent()).getText();
				
				DateFormat format= new SimpleDateFormat("dd-MMMM-yyyy");
				Date startDate2 = new Date();
				Date endDate2 = new Date();
				
				
				try {
					System.out.println("Test2 "+startDate);
					startDate2 = format.parse(startDate);
					endDate2 = format.parse(endDate);

				}
				catch(Exception e)
				{

				}

				Scheduler scheduler = new Scheduler();
				ArrayList<Module> modules= new ArrayList<Module>(db.getModules());
				ArrayList<Room> rooms= new ArrayList<Room>(db.getRooms());
				
				int examPeriodLength = (int)( (endDate2.getTime() - startDate2.getTime())/ (1000 * 60 * 60 * 24) );
				System.out.println(endDate2.getTime());
				System.out.println(startDate2.getTime());
				System.out.println(examPeriodLength);
              
				Calendar cal = Calendar.getInstance();
				cal.setTime(startDate2);
				try{
				db.openDatabase();
				}catch(Exception e){}
				
				boolean successful = false;
				try
				{
					db.writeAllToDB(scheduler.generateAndReturnSchedule(db.getModules(), db.getRooms(), examPeriodLength),cal);
					successful=true;
				}
				catch(IllegalArgumentException e)
				{
					JOptionPane.showMessageDialog(null, e.getMessage());
					successful=false;
				}
				catch(Exception e2){
					JOptionPane.showMessageDialog(null, "Sorry, there is some error");
					successful=false;
				}
				finally{
					if (successful==true){
						JOptionPane.showMessageDialog(null,"Generate Schedule is successful!");
					}
				}
			}

		});

		btnRun.setBounds(372, 283, 117, 29);

		panelRun.add(btnRun);

		panelViewModule = new JPanel();

		frame.getContentPane().add(panelViewModule, "name_1392496928211593000");

		panelViewModule.setLayout(null);

		JLabel lbModule = new JLabel("Module:");

		lbModule.setBounds(73, 71, 50, 16);

		panelViewModule.add(lbModule);

		JLabel label = new JLabel("View Module");

		label.setBounds(272, 22, 79, 16);

		panelViewModule.add(label);

		JButton btnEditDetails = new JButton("Edit details");
		btnEditDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("Check view module");
				textField_6.setText(moduleCode);
				panelViewModule.setVisible(false);
				panelEditModule.setVisible(true);
			}
		});

		btnEditDetails.setBounds(506, 66, 117, 29);

		panelViewModule.add(btnEditDetails);

		System.out.println("CHECK");

		System.out.println("----------------------------");
		
		String[] modulesString = Arrays.copyOf(db.getModuletitles().toArray(), db.getModuletitles().size(), String[].class);
		

		// View Module Table

		String[] columnNames = {"Code", "Number of Student", "Room Type"};

		table = new JTable();
		table.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		table.setEnabled(false);
		table.setBackground(Color.WHITE);
		table.setForeground(Color.BLACK);
				
		panelViewModule.add(table);

		table_1 = new JTable();
		table_1.setShowHorizontalLines(false);
		table_1.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		table_1.setEnabled(false);
		table_1.setModel(new DefaultTableModel(

				new Object[][] {

				{"Code", "Number of Student", "Room Type"},

				},

				new String[] {"c1", "c2", "c3"}

				) {

					Class[] columnTypes = new Class[] {

					String.class, Object.class, Object.class,
							Object.class, Object.class

					};

					public Class getColumnClass(int columnIndex) {

						return columnTypes[columnIndex];

					}

				});

				table_1.getColumnModel().getColumn(1).setPreferredWidth(230);
				table_1.setBorder(new LineBorder(new Color(0, 0, 0)));
				table_1.setBackground(Color.ORANGE);
				table_1.setBounds(73, 115, 550, 26);
				panelViewModule.add(table_1);

		final JComboBox comboBox = new JComboBox(modulesString);
		comboBox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	
		        System.out.println("comboBox: " +comboBox.getSelectedItem().toString());
		        
		        moduleCode = comboBox.getSelectedItem().toString();
		        
		        table.setModel(new DefaultTableModel(

						new Object[][] {

								{ moduleCode, db.numberOfStudents(moduleCode), db.roomType(moduleCode) }

						},

						new String[] {"Code", "Number of Student", "Room Type"}

						) {

							Class[] columnTypes = new Class[] {

							String.class, String.class, Object.class, Object.class,
									Object.class, String.class

							};

							public Class getColumnClass(int columnIndex) {

								return columnTypes[columnIndex];

							}

						});

						table.getColumnModel().getColumn(1).setPreferredWidth(230);
						table.setBounds(73, 139, 550, 200);
						table.setRowHeight(25);
						table.setVisible(true);
		        
		    }
		});
		comboBox.setBounds(135, 67, 120, 27);
		panelViewModule.add(comboBox);

		
		/*------------------------------------------------------------------------------------------------------------------*/

		/*----------------------------------------- Create Module ----------------------------------------------------------*/

		panelCreateModule = new JPanel();

		frame.getContentPane().add(panelCreateModule,
				"name_1392496931060524000");

		panelCreateModule.setLayout(null);

		JLabel lblCreateModule = new JLabel("Create Module");

		lblCreateModule.setBounds(289, 40, 90, 16);

		panelCreateModule.add(lblCreateModule);

		JLabel lblModuleCode = new JLabel("Module Code:");

		lblModuleCode.setBounds(191, 107, 86, 16);

		panelCreateModule.add(lblModuleCode);

		JLabel lblRoomType = new JLabel("Room Type:");

		lblRoomType.setBounds(191, 135, 86, 16);

		panelCreateModule.add(lblRoomType);

		JLabel lblLengthOfExam = new JLabel("Length of Exam:");

		lblLengthOfExam.setBounds(191, 163, 109, 16);

		panelCreateModule.add(lblLengthOfExam);

		

		JComboBox comboBox_1 = new JComboBox();

		comboBox_1.setBounds(435, 103, 80, 27);

		panelCreateModule.add(comboBox_1);

		JComboBox comboBox_2 = new JComboBox();

		comboBox_2.setBounds(435, 131, 80, 27);

		panelCreateModule.add(comboBox_2);

		

		JButton btnAdd = new JButton("Add");

		btnAdd.setBounds(398, 292, 117, 29);

		panelCreateModule.add(btnAdd);

		JButton btnReset = new JButton("Reset");

		btnReset.setBounds(183, 292, 80, 29);

		panelCreateModule.add(btnReset);

		

		
		textField_5 = new JTextField();

		textField_5.setBounds(435, 157, 80, 28);

		panelCreateModule.add(textField_5);

		textField_5.setColumns(10);

		/*------------------------------------------------------------------------------------------------------------------*/

		/*----------------------------------------- View Schedule ----------------------------------------------------------*/

		System.out.println("Check 2 ----");

		// System.out.println(s.toString());

		// s.scheduleModule(new Module(db.getModules())));

		panelViewSchedule = new JPanel();

		frame.getContentPane().add(panelViewSchedule,
				"name_1394971129929447000");

		panelViewSchedule.setLayout(null);

		JLabel label_1 = new JLabel("Start date:");

		label_1.setBounds(186, 139, 71, 16);

		panelViewSchedule.add(label_1);

		JLabel label_2 = new JLabel("End date:");

		label_2.setBounds(186, 184, 61, 16);

		panelViewSchedule.add(label_2);

		JLabel label_3 = new JLabel("Time exam start:");

		label_3.setBounds(186, 227, 112, 16);

		panelViewSchedule.add(label_3);

		JLabel label_4 = new JLabel("Time exam end:");

		label_4.setBounds(186, 269, 112, 16);

		panelViewSchedule.add(label_4);

		textField_1 = new JTextField();

		textField_1.setBounds(428, 127, 134, 28);

		panelViewSchedule.add(textField_1);

		textField_1.setColumns(10);

		textField_2 = new JTextField();

		textField_2.setBounds(428, 172, 134, 28);

		panelViewSchedule.add(textField_2);

		textField_2.setColumns(10);

		textField_3 = new JTextField();

		textField_3.setBounds(428, 221, 134, 28);

		panelViewSchedule.add(textField_3);

		textField_3.setColumns(10);

		textField_4 = new JTextField();

		textField_4.setBounds(428, 263, 134, 28);

		panelViewSchedule.add(textField_4);

		textField_4.setColumns(10);

		JLabel lblViewSchedule = new JLabel("View Schedule");

		lblViewSchedule.setBounds(319, 78, 89, 16);

		panelViewSchedule.add(lblViewSchedule);

		/*------------------------------------------------------------------------------------------------------------------*/

		/*---------------------------------------- Create Schedule ---------------------------------------------------------*/

		panelCreateSchedule = new JPanel();

		frame.getContentPane().add(panelCreateSchedule,
				"name_1394971515747404000");

		panelCreateSchedule.setLayout(null);

		JLabel lblCreateSchedule = new JLabel("Create Schedule");

		lblCreateSchedule.setBounds(305, 67, 105, 16);

		panelCreateSchedule.add(lblCreateSchedule);

		JLabel lblNewLabel = new JLabel("Start date:");

		lblNewLabel.setBounds(170, 135, 71, 16);

		panelCreateSchedule.add(lblNewLabel);

		JLabel lblEndDate = new JLabel("End date:");

		lblEndDate.setBounds(170, 180, 61, 16);

		panelCreateSchedule.add(lblEndDate);

		JLabel lblTimeExamStart = new JLabel("Time exam start:");

		lblTimeExamStart.setBounds(170, 223, 112, 16);

		panelCreateSchedule.add(lblTimeExamStart);

		JLabel lblTimeExamEnd = new JLabel("Time exam end:");

		lblTimeExamEnd.setBounds(170, 265, 112, 16);

		panelCreateSchedule.add(lblTimeExamEnd);

		JDateChooser dateChooser = new JDateChooser();

		dateChooser.setBounds(373, 123, 123, 28);

		panelCreateSchedule.add(dateChooser);

		JDateChooser dateChooser_1 = new JDateChooser();

		dateChooser_1.setBounds(373, 168, 123, 28);

		panelCreateSchedule.add(dateChooser_1);

		String[] examTime = { "...", "9 A.M.", "10 A.M.", "11 A.M.", "12 A.M.",

		"13 P.M.", "14 P.M.", "15 P.M.", "16 P.M.", "17 P.M." };

		JComboBox comboBox_examTimeStart = new JComboBox(examTime);

		comboBox_examTimeStart.setBounds(373, 219, 123, 27);

		panelCreateSchedule.add(comboBox_examTimeStart);

		JComboBox comboBox_comboBox_examTimeEnd = new JComboBox(examTime);

		comboBox_comboBox_examTimeEnd.setBounds(373, 261, 123, 27);

		panelCreateSchedule.add(comboBox_comboBox_examTimeEnd);

		JButton btnReset_1 = new JButton("Reset");

		btnReset_1.setBounds(170, 354, 117, 29);

		panelCreateSchedule.add(btnReset_1);

		JButton btnOk = new JButton("Ok");

		btnOk.setBounds(379, 354, 117, 29);

		panelCreateSchedule.add(btnOk);

		/*------------------------------------------------------------------------------------------------------------------*/

		/*------------------------------------------ Student Modules -----------------------------------------------------------*/
		panelStudentModule = new JPanel();
		frame.getContentPane().add(panelStudentModule, "name_1395058344668467000");
		panelStudentModule.setLayout(null);
		
		JLabel lblStudentId_1 = new JLabel("Student ID:");
		lblStudentId_1.setBounds(185, 146, 81, 16);
		panelStudentModule.add(lblStudentId_1);
		
		textField_16 = new JTextField();
		textField_16.setBounds(299, 140, 134, 28);
		panelStudentModule.add(textField_16);
		textField_16.setColumns(10);
		
		
		final JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(200, 173, 250, 200);
		panelStudentModule.add(textArea);
			
		JButton btnView = new JButton("View");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				textArea.setText(db.query1(Integer.parseInt(textField_16.getText())).toString());
			}
		});
		btnView.setBounds(476, 141, 117, 29);
		panelStudentModule.add(btnView);
		
		

		/*------------------------------------------------------------------------------------------------------------------*/

		/*------------------------------------------ Load Student ----------------------------------------------------------*/
		panelLoadStudent = new JPanel();
		frame.getContentPane().add(panelLoadStudent, "name_1395063512959039000");
		panelLoadStudent.setLayout(null);
		
		JLabel lblLoadStudent = new JLabel("Load Student");
		lblLoadStudent.setBounds(322, 58, 93, 16);
		panelLoadStudent.add(lblLoadStudent);
		
		JLabel lblStudentId = new JLabel("Student ID:");
		lblStudentId.setBounds(228, 141, 85, 16);
		panelLoadStudent.add(lblStudentId);
		
		textField = new JTextField();
		textField.setBounds(387, 135, 134, 28);
		panelLoadStudent.add(textField);
		textField.setColumns(10);
		
		JButton btnAdd_1 = new JButton("Add");
		btnAdd_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String stuID = textField.getText();
				System.out.println("Student check: "+stuID);
				
				db.writeToInputDB(stuID);
				
			}
		});
		btnAdd_1.setBounds(298, 236, 117, 29);
		panelLoadStudent.add(btnAdd_1);

		/*------------------------------------------------------------------------------------------------------------------*/

		/*----------------------------------------- Add Room ----------------------------------------------------------*/
		panelAddRoom = new JPanel();
		frame.getContentPane().add(panelAddRoom, "name_1395063531440464000");
		panelAddRoom.setLayout(null);
		
		JLabel lblAddRoom = new JLabel("Add Room");
		lblAddRoom.setBounds(301, 87, 78, 16);
		panelAddRoom.add(lblAddRoom);
		
		JLabel lblRoomNumber = new JLabel("Room Number");
		lblRoomNumber.setBounds(82, 153, 96, 16);
		panelAddRoom.add(lblRoomNumber);
		
		JLabel lblRoomType_1 = new JLabel("Room Type");
		lblRoomType_1.setBounds(203, 153, 86, 16);
		panelAddRoom.add(lblRoomType_1);
		
		JLabel lblRoomStart = new JLabel("Room Start");
		lblRoomStart.setBounds(301, 153, 78, 16);
		panelAddRoom.add(lblRoomStart);
		
		JLabel lblFireBreak = new JLabel("Fire Break");
		lblFireBreak.setBounds(391, 153, 61, 16);
		panelAddRoom.add(lblFireBreak);
		
		JLabel lblCapacity = new JLabel("Capacity");
		lblCapacity.setBounds(476, 153, 61, 16);
		panelAddRoom.add(lblCapacity);
		
		JLabel lblRoomEnd = new JLabel("Room End");
		lblRoomEnd.setBounds(549, 153, 78, 16);
		panelAddRoom.add(lblRoomEnd);
		
		textField_10 = new JTextField();
		textField_10.setBounds(82, 183, 86, 28);
		panelAddRoom.add(textField_10);
		textField_10.setColumns(10);
		
		textField_11 = new JTextField();
		textField_11.setBounds(203, 181, 71, 28);
		panelAddRoom.add(textField_11);
		textField_11.setColumns(10);
		
		textField_12 = new JTextField();
		textField_12.setBounds(301, 183, 71, 28);
		panelAddRoom.add(textField_12);
		textField_12.setColumns(10);
		
		textField_13 = new JTextField();
		textField_13.setBounds(391, 183, 61, 28);
		panelAddRoom.add(textField_13);
		textField_13.setColumns(10);
		
		textField_14 = new JTextField();
		textField_14.setBounds(476, 183, 61, 28);
		panelAddRoom.add(textField_14);
		textField_14.setColumns(10);
		
		textField_15 = new JTextField();
		textField_15.setBounds(549, 181, 71, 28);
		panelAddRoom.add(textField_15);
		textField_15.setColumns(10);
		
		JButton btnAdd_2 = new JButton("Add");
		btnAdd_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try
				{
					if(db.addRoom(Integer.parseInt(textField_10.getText()), textField_11.getText(), 
							Integer.parseInt(textField_12.getText()), Integer.parseInt(textField_13.getText()), 
							Integer.parseInt(textField_14.getText()), Integer.parseInt(textField_15.getText())))
					{
						JOptionPane.showMessageDialog(null, "Add room, successful!");
					}
				}
				catch(Exception roomE)
				{
					JOptionPane.showMessageDialog(null, "Sorry, there is some error!");
				}
			}
		});
		btnAdd_2.setBounds(274, 267, 117, 29);
		panelAddRoom.add(btnAdd_2);
		
		// addRoom(int roomNumber, String roomType, int roomStart,int roomFireBreak, int capacity, int roomEnd)
		
		
		/*------------------------------------------------------------------------------------------------------------------*/

		/*----------------------------------------- Edit Module ----------------------------------------------------------*/
		panelEditModule = new JPanel();
		frame.getContentPane().add(panelEditModule, "name_1398770634005047000");
		panelEditModule.setLayout(null);
		
		textField_6 = new JTextField();
		
		textField_6.setBounds(60, 164, 134, 28);
		panelEditModule.add(textField_6);
		textField_6.setColumns(10);
		
		JLabel lblModuleCode_1 = new JLabel("Module Code");
		lblModuleCode_1.setBounds(81, 136, 95, 16);
		panelEditModule.add(lblModuleCode_1);
		
		JLabel lblExamLength = new JLabel("Exam Length");
		lblExamLength.setBounds(242, 136, 95, 16);
		panelEditModule.add(lblExamLength);
		
		textField_7 = new JTextField();
		textField_7.setBounds(223, 164, 134, 28);
		panelEditModule.add(textField_7);
		textField_7.setColumns(10);
		
		JLabel lblSize = new JLabel("Size");
		lblSize.setBounds(381, 136, 61, 16);
		panelEditModule.add(lblSize);
		
		textField_8 = new JTextField();
		textField_8.setBounds(369, 164, 61, 28);
		panelEditModule.add(textField_8);
		textField_8.setColumns(10);
		
		JLabel lblType = new JLabel("Type");
		lblType.setBounds(460, 136, 61, 16);
		panelEditModule.add(lblType);
		
		textField_9 = new JTextField();
		textField_9.setBounds(443, 164, 68, 28);
		panelEditModule.add(textField_9);
		textField_9.setColumns(10);
		
		JButton btnDone = new JButton("Done");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try
				{
					if(db.writeToModuleTable(moduleCode, Double.parseDouble(textField_7.getText()), Integer.parseInt(textField_8.getText()), textField_9.getText()))
					{
						JOptionPane.showMessageDialog(null, "Edit module, successful!");
					}
				}
				catch(Exception editModuleE)
				{
					JOptionPane.showMessageDialog(null, "Sorry, there is some error!");
				}
			}
		});
		btnDone.setBounds(223, 261, 117, 29);
		panelEditModule.add(btnDone);
		
	}

}