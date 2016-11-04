package pacman.core.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class BaseView extends JPanel {
	//������� ����������
    private EventsListener eventsListener;
	//����������� ��� ����������
    public void setEventsListener(EventsListener eventsListener) {
        this.eventsListener = eventsListener;
    }

    // �����������
    public BaseView() 
	{
        setBackground(Color.BLACK);
        // ������������� �� ������� �������� ������ JPanel
        addComponentListener(new ComponentListener() 
		{
            @Override
            public void componentResized(ComponentEvent e) {
                // ��� ��������� ������� JPanel �������� �� ���� ������ � ������� EventsListener'�
                if (eventsListener != null) {
                    // ����� ������ � ������ ������ ����� � ���������� ���� ������� �������
                    int width = e.getComponent().getWidth();
                    int height = e.getComponent().getHeight();
                    eventsListener.onResize(width, height);
                }
            }

            // ��������� ������� ��� �� ���������, ������ �� ������������� ������
            //������ �����
			@Override
            public void componentMoved(ComponentEvent e) {
            }
			//������ �����
            @Override
            public void componentShown(ComponentEvent e) {
            }
			//������ �����
            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });
    }

    // ���������� �������� ��� ����������� JPanel, 
	//����� ������������ ��� ��������� ����� ��������
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // �������� ������ ��� ���� �������� �������
        if (eventsListener != null) {
            eventsListener.onDraw(g);
        }
        g.dispose();
    }

    // ��������� ��� �������� ��������� ������
    public interface EventsListener {
        void onDraw(Graphics g);

        void onResize(int width, int height);
    }
}
