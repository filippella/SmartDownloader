package smartdownloader.downloader;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import smartdownloader.downloader.model.DownloadEntry;
import smartdownloader.downloader.model.DownloadListener;
import smartdownloader.downloader.model.DownloadProgress;
import smartdownloader.downloader.model.DownloadRequest;
import smartdownloader.downloader.model.DownloadTaskListener;

public final class DownloadManager implements DownloadTaskListener {

    private final Map<Long, DownloadTask> taskMap = new LinkedHashMap<>();
    private final List<DownloadListener> listeners = new LinkedList<>();
    private final ThreadPoolExecutor taskExecutor = new ThreadPoolExecutor(5, 6,
            60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    private static final class InstanceHolder {

        private static DownloadManager sInstance = new DownloadManager();
    }

    public static DownloadManager get() {
        return InstanceHolder.sInstance;
    }

    public boolean addListener(DownloadListener listener) {
        return !listeners.contains(listener) && listeners.add(listener);
    }

    public long addRequest(DownloadRequest request) {
        long downloadId = System.currentTimeMillis() + taskMap.size();
        taskMap.put(downloadId, new DownloadTask(new DownloadEntry(request, downloadId), this));
        return downloadId;
    }

    public boolean start() {
        boolean started = false;
        for (Map.Entry<Long, DownloadTask> taskEntry : taskMap.entrySet()) {
            taskExecutor.submit(taskEntry.getValue());
            started = true;
        }
        return started;
    }

    public void cancelAll() {
        Set<Long> downloadIds = taskMap.keySet();
        for (Long downloadId : downloadIds) {
            cancel(downloadId);
        }
    }

    public boolean cancel(long downloadId) {
        boolean stopped = false;
        DownloadTask task = taskMap.get(downloadId);
        if (task != null) {
            stopped = taskExecutor.remove(task);
        }
        return stopped;
    }

    @Override
    public void onTaskStarted(long downloadId) {
        for (DownloadListener listener : listeners) {
            listener.onStart(downloadId);
        }
    }

    @Override
    public void onProgress(long downloadId, DownloadProgress progress) {
        for (DownloadListener listener : listeners) {
            listener.onProgress(downloadId, progress);
        }
    }

    @Override
    public void onTaskCompleted(long downloadId, boolean success) {
        DownloadTask task = taskMap.remove(downloadId);
        System.out.println("Removed task -> " + downloadId + " :: Task completed successfully: " + success);
        for (DownloadListener listener : listeners) {
            listener.onComplete(downloadId, success);
        }
        if (taskMap.isEmpty()) {
            //taskExecutor.shutdown();
            System.out.println("Done!");
        }
    }

    public boolean removeListener(DownloadListener listener) {
        return listeners.remove(listener);
    }

    public void close() {
        taskExecutor.shutdown();
    }
}
