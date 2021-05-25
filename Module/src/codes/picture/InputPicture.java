package codes.picture;

import java.io.File;

public class InputPicture {
    File file;

    //get fileaddress and create file:
    public InputPicture(String fileAddress){
        this.file=new File(fileAddress);
    }

    public InputPicture() {

    }

    public Picture getFile() {
        Picture pic=new Picture(this.file);
        return pic;
    }

    public void setFile(File file) {
        this.file = file;
    }
}