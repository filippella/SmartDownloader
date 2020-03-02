package smartdownloader.downloader.model;

public class DownloadEntry {

    private final DownloadRequest request;
    private final long downloadId;
    private DownloadState state = DownloadState.NONE;

    public DownloadEntry(DownloadRequest request, long downloadId) {
        this.request = request;
        this.downloadId = downloadId;
    }

    public void setState(DownloadState state) {
        this.state = state;
    }

    public DownloadRequest getRequest() {
        return request;
    }

    public DownloadState getState() {
        return state;
    }

    public long getDownloadId() {
        return downloadId;
    }
}
