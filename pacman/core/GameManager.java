package pacman.core;

import pacman.core.models.GameState;
import pacman.core.views.BaseView;
import pacman.world.WorldManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GameManager implements BaseView.EventsListener, KeyListener {

    // =============================================================================================
    // CONSTANTS
    // =============================================================================================
    private static final int WORLD_UPDATE_INTERVAL_MILLIS = 30;

    // =============================================================================================
    // FIELDS
    // =============================================================================================
    private final BaseView view;
    // менеджеры отвечающие за свои части игры
   // private BackgroundManager backgroundManager;
    private WorldManager worldManager;
    // Состояние игры
    private GameState state;
    private Timer worldTimer;

    // =============================================================================================
    // CONSTRUCTOR
    // =============================================================================================
    public GameManager(BaseView view) {
        this.view = view;
        view.setEventsListener(this);
        //backgroundManager = new BackgroundManager();
        worldManager = new WorldManager();
        worldTimer = new Timer(WORLD_UPDATE_INTERVAL_MILLIS, worldTimerTask);
        state = GameState.IDLE;
    }

    // =============================================================================================
    // BASE VIEW HANDLERS
    // =============================================================================================
    @Override
    public void onDraw(Graphics g) {
        if (state == GameState.RUNNING) {
            //backgroundManager.render(g);
            worldManager.render(g);
        }
    }

    @Override
    public void onResize(int width, int height) {
        //backgroundManager.resize(width, height);
        worldManager.resize(width, height);
    }
    // =============================================================================================
    // METHODS
    // =============================================================================================
    /**
     * Запустить игру
     */
    public void start() {
        state = GameState.RUNNING;
        worldManager.init();
        worldTimer.start();
    }

    /**
     * Приостановить игру
     */
    public void stop() {
        state = GameState.PAUSED;
        worldTimer.stop();
        if (view != null) {
            view.repaint();
        }
    }
    // =============================================================================================
    // UPDATING
    // =============================================================================================
    private ActionListener worldTimerTask = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            update();
            render();
        }
    };

    /**
     * Пересчет мира, положения столкновения и прочая физика
     */
    private void update() {
        worldManager.update();
    }

    /**
     * Отрисовка мира
     */
    private void render() {
        // Сообщаем вьюшке, что она должна перерисоваться. В ответ на это будет вызван метод render(),
        // в котором мы и должны рисовать свои шарики, квадратики, whatever
        view.repaint();
    }

    // =============================================================================================
    // KEYBOARD EVENT HANDLERS
    // =============================================================================================
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        worldManager.keyPressed(keyEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        worldManager.keyReleased(keyEvent);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }
}
