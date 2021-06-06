package codes.picture;

import java.io.File;

public class InputPicture {
    File file;

    //get fileaddress and create file:
    public InputPicture(String fileAddress) {
        this.file = new File(fileAddress);
    }

    public InputPicture(File file) {
        this.file = file;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}