package smartdownloader.downloader.model;

public class DownloadProgress {

    public final int progress;
    public final String speed;
    public final double total;
    public final double downloaded;
    public final double remaining;

    public DownloadProgress(int progress, String speed, double total, double downloaded, double remaining) {
        this.progress = progress;
        this.speed = speed;
        this.total = total;
        this.downloaded = downloaded;
        this.remaining = remaining;
    }
}
