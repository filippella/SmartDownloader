package smartdownloader.downloader.model;

public class DownloadRequest {

    public final String link;
    public final String downloadPath;

    public DownloadRequest(String link, String path) {
        this.link = link;
        this.downloadPath = path;
    }


//    private final List<String> linkList = new LinkedList<>();

//    private DownloadRequest(Builder builder){
//        downloadPath = builder.downloadPath;
//        linkList.addAll(builder.linkList);
//    }
//
//    public String getDownloadPath() {
//        return downloadPath;
//    }
//
//    public List<String> getLinkList() {
//        return linkList;
//    }
//
//    public  static final class Builder {
//
//        private final String downloadPath;
//        private final List<String> linkList = new LinkedList<>();
//
//        public Builder(String path) {
//            downloadPath = path;
//        }
//
//        public Builder addLink(String link) {
//            linkList.add(link);
//            return Builder.this;
//        }
//
//        public Builder addLinks(List<String> links) {
//            linkList.addAll(links);
//            return Builder.this;
//        }
//
//        public DownloadRequest build() {
//            return new DownloadRequest(Builder.this);
//        }
//    }
}
