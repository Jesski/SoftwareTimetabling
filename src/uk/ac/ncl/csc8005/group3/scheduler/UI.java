import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTextPane;
import java.awt.Font;


public class UI {

	private JFrame frame;
	private JMenuItem menuItem;
	JPanel panelMain, panelViewModule, panelCreateModule;
	private JTable table;
	private JTable table_1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/*--- Menu Items---*/
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenu mntmModule = new JMenu("Module");
		menuItem = new JMenuItem("View Module");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelViewModule.setVisible(true);
				panelMain.setVisible(false);
			}
		});
		mntmModule.add(menuItem);
		menuItem = new JMenuItem("Create Module");
		mntmModule.add(menuItem);
		mnMenu.add(mntmModule);
		
		JMenu mntmSchedule = new JMenu("Schedule");
		menuItem = new JMenuItem("View Schedule");
		mntmSchedule.add(menuItem);
		menuItem = new JMenuItem("Create Schedule");
		mntmSchedule.add(menuItem);
		mnMenu.add(mntmSchedule);
		
		JSeparator separator = new JSeparator();
		mnMenu.add(separator);
		
		JMenuItem mntmLoadStudent = new JMenuItem("Load Student");
		mnMenu.add(mntmLoadStudent);
		
		JMenuItem mntmLoadModule = new JMenuItem("Load Module");
		mnMenu.add(mntmLoadModule);
		
		JMenuItem mntmLoadSchedule = new JMenuItem("Load Schedule");
		mnMenu.add(mntmLoadSchedule);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		/*-----------------------------------------------------------*/
		
		/*--- Panel ---*/
		panelMain = new JPanel();
		frame.getContentPane().add(panelMain, "name_1392496925082617000");
		panelMain.setLayout(null);
		panelMain.setVisible(true);
		
		JLabel lblNewcastleTimetabling = new JLabel("Newcastle Timetabling");
		lblNewcastleTimetabling.setBounds(30, 58, 143, 16);
		panelMain.add(lblNewcastleTimetabling);
		
		panelViewModule = new JPanel();
		frame.getContentPane().add(panelViewModule, "name_1392496928211593000");
		panelViewModule.setLayout(null);
		
		JLabel lblViewModule = new JLabel("View Module");
		lblViewModule.setBounds(291, 22, 79, 16);
		panelViewModule.add(lblViewModule);
		
		//View Module Table
		String[] columnNames = {"Code", "Title","Date", "Time", "Length", "Room"};
		/*Object[][] data = {
				{"CSC8001","Programming and Data Sreuctures","13-Jan-14", "14.00", "3h", "BSB"},
				{"CSC8010","Computer Environments","17-Jan-14", "14.00", "1h 30m", "BSB"}
			  };
		*/
		table = new JTable();
		table.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		table.setEnabled(false);
		table.setBackground(Color.WHITE);
		table.setForeground(Color.BLACK);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"CSC8001", "Programming and Data Sreuctures", "13-Jan-14", "14.00", "3h", "BSB"},
				{"CSC8010", "Computer Environments", "17-Jan-14", "14.00", "1h 30m", "BSB"},
			},
			new String[] {
				"Code", "Title", "Date", "Time", "Length", "Room"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, Object.class, Object.class, Object.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(1).setPreferredWidth(230);
		table.setBounds(73, 96, 550, 200);
		table.setRowHeight(25);
		table.setVisible(true);
		
		panelViewModule.add(table);
		
		table_1 = new JTable();
		table_1.setShowHorizontalLines(false);
		table_1.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		table_1.setEnabled(false);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{"Code", "Title", "Date", "Time", "Length", "Location"},
			},
			new String[] {
				"c0", "c1", "c2", "c3", "c4", "c5"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Object.class, Object.class, Object.class, Object.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table_1.getColumnModel().getColumn(1).setPreferredWidth(230);
		table_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		table_1.setBackground(Color.ORANGE);
		table_1.setBounds(73, 71, 550, 26);
		panelViewModule.add(table_1);
		panelViewModule.setVisible(false);
		
		panelCreateModule = new JPanel();
		frame.getContentPane().add(panelCreateModule, "name_1392496931060524000");
		panelCreateModule.setVisible(false);
	}
}
