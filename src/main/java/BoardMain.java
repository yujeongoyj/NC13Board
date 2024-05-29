import connector.ConnectionMaker;
import connector.MySqlConnectionMaker;
import controller.UserController;
import viewer.UserViewer;

public class BoardMain {
    public static void main(String[] args) {
        UserViewer userViewer = new UserViewer();
        userViewer.showIndex();
    }
}
