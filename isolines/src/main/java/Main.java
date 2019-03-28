import controller.Controller;
import model.IsolineManagerImpl;
import view.MainWindow;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow(500, 500);
        Controller controller = new Controller();
        IsolineManagerImpl isolineManager = new IsolineManagerImpl(500, 500);

        controller.setIsolineManager(isolineManager);
        isolineManager.addObserver(mainWindow);
        mainWindow.setLogicController(controller);

        isolineManager.setGraphicArea(-50, 50, -50, 50);
        isolineManager.setColorsRGB(new int[]{Color.YELLOW.getRGB(), Color.WHITE.getRGB(), Color.MAGENTA.getRGB(), Color.CYAN.getRGB(), Color.RED.getRGB(), Color.GREEN.getRGB(), Color.GRAY.getRGB(), Color.BLACK.getRGB()});
        controller.createGraphic();
        mainWindow.setVisible(true);
    }
}
