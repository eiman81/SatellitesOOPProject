package unsw.blackout;

public class File {
    private String fileName;
    private String content;
    private int fileSize;
    private boolean isFileComplete;

    public File(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
        this.isFileComplete = true;
        this.fileSize = content.length();
    }

    public String getFileName() {
        return fileName;
    }

    public String getContent() {
        return content;
    }

    public int getFileSize() {
        return fileSize;
    }

    public boolean isFileComplete() {
        return isFileComplete;
    }

}
