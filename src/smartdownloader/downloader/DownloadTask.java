package smartdownloader.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import smartdownloader.downloader.model.DownloadEntry;
import smartdownloader.downloader.model.DownloadProgress;
import smartdownloader.downloader.model.DownloadRequest;
import smartdownloader.downloader.model.DownloadTaskListener;

public class DownloadTask implements Runnable {

    private static final int TIMEOUT = 5000;
    private static final int BLOCK_SIZE = 2048;

    private final DownloadEntry entry;
    private final DownloadTaskListener listener;

    public DownloadTask(DownloadEntry entry, DownloadTaskListener listener) {
        this.entry = entry;
        this.listener = listener;
    }

    @Override
    public void run() {

        DownloadRequest request = entry.getRequest();
        long downloadId = entry.getDownloadId();

        try {

            String url = request.link;
            String path = request.downloadPath;
            path = path.endsWith("/") ? path : path + "/";

            listener.onTaskStarted(downloadId);

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.0.5) Gecko/2008120122 Firefox/3.0.5");
            connection.setRequestProperty("Accept", "*/*");
            connection.connect();

            String fileName = new java.io.File(url).getName().split("\\?")[0];

            File file = new File(path + fileName);
            int version = 1;
            while (file.exists()) {

                int endIndex = fileName.lastIndexOf(".");
                String name = fileName.substring(0, endIndex) + "(" + version + ")";
                String extension = fileName.substring(endIndex, fileName.length());

                file = new File(path + name + extension);
                version++;
            }

            FileOutputStream out = new FileOutputStream(file);

            int fileSize = connection.getContentLength();

            System.out.println("File Size = " + fileSize);

            double total = 0.0;

            InputStream input = connection.getInputStream();

            byte buffer[] = new byte[BLOCK_SIZE];
            int length;

            while ((length = input.read(buffer, 0, BLOCK_SIZE)) != -1) {
                total = total + length;
                out.write(buffer, 0, length);
                int progress = (int) ((total / fileSize) * 100);
                String speed = "";

                double downloaded = 0;
                double remaining = 0;

                listener.onProgress(downloadId, new DownloadProgress(progress, speed, total, downloaded, remaining));

            }

            input.close();
            out.close();

            connection.disconnect();

            listener.onTaskCompleted(downloadId, true);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error -> " + e.getMessage());
            listener.onTaskCompleted(downloadId, false);
        }
    }
}
