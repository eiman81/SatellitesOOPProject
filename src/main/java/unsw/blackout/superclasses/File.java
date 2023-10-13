package unsw.blackout.superclasses;

public class File {
    private String fileName;
    private String content;
    private int fileSize;
    private boolean isFileComplete;
    private String sender;
    private String receiver;
    private double progress;

    public File(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
        this.isFileComplete = true;
        this.fileSize = content.length();
    }

    public File(String fileName, String content, Integer fileSize, String receiver, String sender, double progress,
            boolean isComplete) {
        this.fileName = fileName;
        this.content = content;
        this.fileSize = fileSize;
        this.sender = sender;
        this.receiver = receiver;
        this.progress = progress;
        this.isFileComplete = isComplete;
    }

    public File() {
        this.fileName = "";
        this.content = "";
        this.isFileComplete = false;
        this.fileSize = 0;
    }

    /**
     * getters and setters
     */
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

    public double getProgress() {
        return progress;
    }

}
