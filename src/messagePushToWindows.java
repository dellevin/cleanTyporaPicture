import java.awt.*;

public class messagePushToWindows {
    public static void main(String[] args) throws AWTException {
        if (SystemTray.isSupported()) {//如果支持系统托盘
            //回调
            messagePushToWindows nd = new messagePushToWindows();
            nd.displayTray();

        } else {
            System.err.println("System tray not supported!");
        }
    }

    public void displayTray() throws AWTException {
        //生成系统托盘实例
        SystemTray tray = SystemTray.getSystemTray();

        //如果这个icon是个文件的话
        //Image image = Toolkit.getDefaultToolkit().createImage("messageCenter.png");
        //备选方案（如果图标位于类路径上）
        Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("messageCenter.png"));
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //如果需要，让系统调整图像大小
        trayIcon.setImageAutoSize(true);
        //设置托盘图标的工具提示文本
        trayIcon.setToolTip("系统托盘图标样例");
        tray.add(trayIcon);

        trayIcon.displayMessage("通知题目", "通知内容通知内容通知内容通知内容通知内容通知内容通知内容通知内容通知内容", TrayIcon.MessageType.NONE);
    }
}
