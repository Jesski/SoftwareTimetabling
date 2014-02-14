import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.print.PrinterException;

import java.io.*;

import java.text.MessageFormat;
import java.util.*;

public class Window extends JFrame implements MouseListener, ActionListener{

	private JFrame frame;
	private JMenuItem menuItem;
	
	public Window()
	{
		frame = new JFrame("Window");
		
		//Create the menu bar
		JMenuBar menuBar = new JMenuBar();
		
		//Build first menu
		JMenu menu = new JMenu("Menu");
		menu.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menu);
		
		//First group of JMenuItems
		JMenu submenu = new JMenu("Module");
		submenu.setMnemonic(KeyEvent.VK_S);
		
		menuItem = new JMenuItem("View Module");
		menuItem.addActionListener(this);
		submenu.add(menuItem);
		
		menuItem = new JMenuItem("Create Module");
		menuItem.addActionListener(this);
		submenu.add(menuItem);
		
		menu.add(submenu);
		
		submenu = new JMenu("Schedule");
		submenu.setMnemonic(KeyEvent.VK_S);
		
		menuItem = new JMenuItem("View Schedule");
		menuItem.addActionListener(this);
		submenu.add(menuItem);
		
		menuItem = new JMenuItem("Create Schedule");
		menuItem.addActionListener(this);
		submenu.add(menuItem);
		
		menu.add(submenu);
		
		//Second group
		menu.addSeparator();
		menuItem = new JMenuItem("Load Student");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Load Schedule");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Load Module");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		
		
		
		
		frame.setJMenuBar(menuBar);
		
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setSize(800, 600);
		frame.setVisible(true);
	}
	
	/*--- Event Action ---*/
	@Override
	public void actionPerformed(ActionEvent a) {
		
		if(a.getActionCommand().equals("View Module"))
		{
			System.out.println("View Module");
		}
		else if(a.getActionCommand().equals("Create Module"))
		{
			System.out.println("Create Module");
		}
		else if(a.getActionCommand().equals("View Schedule"))
		{
			System.out.println("View Schedule");
		}
		else if(a.getActionCommand().equals("Create Schedule"))
		{
			System.out.println("Create Schedule");
		}
		else if(a.getActionCommand().equals("Load Student"))
		{
			System.out.println("Load Student");
		}
		else if(a.getActionCommand().equals("Load Schedule"))
		{
			System.out.println("Load Schedule");
		}
		else if(a.getActionCommand().equals("Load Module"))
		{
			System.out.println("Load Module");
		}
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
