package smartdownloader.downloader.model;

public interface DownloadTaskListener {

    void onTaskStarted(long downloadId);

    void onProgress(long downloadId, DownloadProgress downloadProgress);

    void onTaskCompleted(long downloadId, boolean success);
}
