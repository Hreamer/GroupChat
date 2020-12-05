public class Daemon extends Thread {
    public void run() {
        while (true) {
            for (int i = 0; i < Client.getConvosForRefresh().size(); i++) {
                String currentTitleToRefresh = Client.getConvosForRefresh().get(i).getTitle();
                Client.textArea.setText(Client.getConversation(currentTitleToRefresh));
                System.out.println("Been refreshed" + Client.getConvosForRefresh().get(i).getTitle());
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}
