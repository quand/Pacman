package pacman;

import pacman.core.GameManager;
import pacman.core.views.BaseView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static pacman.world.WorldManager.CELL_HEIGHT;
import static pacman.world.WorldManager.CELL_WIDTH;

public class       MainWindow {
	//������� �����, ��������� ����
    public static void main(String[] args) 
	{
        // ����� �����, ������� ��������� �������� ����
        EventQueue.invokeLater(new Runnable() 
		{
            public void run() {
                new MainWindow();
            }
        });
    }
    private static final int COLUMNS_COUNT = 19;
    private static final int ROW_COUNT = 23;
    private MainWindow() {
        // �������������� �������� ����
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Pac-Man");
        //��������� ����
        frame.setBounds(50, 50, COLUMNS_COUNT*CELL_WIDTH+8, (ROW_COUNT)*CELL_HEIGHT+15);
        // ���������� ������ ����������� ����� �������� ����
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // ��������� ������� ���� ��� ������, ����� ����
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
		//�������������� ������, � ����� ��������� ���
        final JMenuItem startMenuItem = new JMenuItem("Start");
        menuBar.add(startMenuItem);
        final JMenuItem pauseMenuItem = new JMenuItem("Pause");
        pauseMenuItem.setEnabled(false);
        menuBar.add(pauseMenuItem);
		//---------------------------------------------------
		//������� �������� ���� ����^
		
		
		
        // ������� ������ � ��������� �� � ����
        BaseView view = new BaseView();
        frame.getContentPane().add(view);
		
		//���� �� ������������ ����� ����!!!!!(������)
		//---------------------------------------------------
        // ������� �������� ����������� ������� ����
        final GameManager gameManager = new GameManager(view);

        // ����������� �������� �� ������� ����������
        frame.addKeyListener(gameManager);
		//---------------------------------------------------
		//���� �� ������������ ����� ����!!!!!(�����)
		
        // ������������� �� ����� �� ����(������)
		//��������� ���� ����(�������� ����), ������ ��������� ������
        startMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameManager.start();
                startMenuItem.setEnabled(false);
                pauseMenuItem.setEnabled(true);
            }
        });
		//�������� ����(�������� ����), � ������ ��������� ������  
        pauseMenuItem.addActionListener(new ActionListener() 
		{
            @Override
            public void actionPerformed(ActionEvent e) {
                gameManager.stop();
                startMenuItem.setEnabled(true);
                pauseMenuItem.setEnabled(false);
            }
        });
		// ������������� �� ����� �� ����(�����)
    }
}