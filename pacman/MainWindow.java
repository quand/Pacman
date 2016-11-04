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
	//главный метод, запускает окно
    public static void main(String[] args) 
	{
        // Точка входа, создаем экземпляр главного окна
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
        // Инициализируем свойства окна
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Pac-Man");
        //параметры окна
        frame.setBounds(50, 50, COLUMNS_COUNT*CELL_WIDTH+8, (ROW_COUNT)*CELL_HEIGHT+15);
        // Приложение должно завершиться после закрытия окна
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Добавляем верхнее меню для старта, паузы игры
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
		//инициализируем объект, а затем добавляем его
        final JMenuItem startMenuItem = new JMenuItem("Start");
        menuBar.add(startMenuItem);
        final JMenuItem pauseMenuItem = new JMenuItem("Pause");
        pauseMenuItem.setEnabled(false);
        menuBar.add(pauseMenuItem);
		//---------------------------------------------------
		//простой создание окна выше^
		
		
		
        // Создаем вьюшку и добавляем ее в окно
        BaseView view = new BaseView();
        frame.getContentPane().add(view);
		
		//пока не обработанный кусок кода!!!!!(начало)
		//---------------------------------------------------
        // Создаем мэнеджер управляющий логикой игры
        final GameManager gameManager = new GameManager(view);

        // Подписываем менеджер на события клавиатуры
        frame.addKeyListener(gameManager);
		//---------------------------------------------------
		//пока не обработанный кусок кода!!!!!(конец)
		
        // Подписываемся на клики по меню(начало)
		//запускаем нашу игру(менеджер игры), меняем видимость кнопок
        startMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameManager.start();
                startMenuItem.setEnabled(false);
                pauseMenuItem.setEnabled(true);
            }
        });
		//стопарим игру(менеджер игры), и меняем видимость кнопок  
        pauseMenuItem.addActionListener(new ActionListener() 
		{
            @Override
            public void actionPerformed(ActionEvent e) {
                gameManager.stop();
                startMenuItem.setEnabled(true);
                pauseMenuItem.setEnabled(false);
            }
        });
		// Подписываемся на клики по меню(конец)
    }
}