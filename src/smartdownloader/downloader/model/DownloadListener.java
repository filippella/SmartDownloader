package smartdownloader.downloader.model;

public interface DownloadListener {

    void onStart(long downloadId);

    void onProgress(long downloadId, DownloadProgress progress);

    void onComplete(long downloadId, boolean success);
}
