/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartdownloader.downloader.model;

/**
 *
 * @author filippo
 */
public class DownloadItem {
    
    public final DownloadRequest request;
    public final long downloadId;

    public DownloadItem(DownloadRequest request, long downloadId) {
        this.request = request;
        this.downloadId = downloadId;
    }

    @Override
    public String toString() {
        return request.link;
    }
}
