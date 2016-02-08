package com.siongriffiths.plantatlas.image;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sig2@aber.ac.uk on 08/02/2016.
 */
public class ImageLoader {

    private String path = "G:\\UNI\\diss";

    public ImageLoader(String filePath){
        path = filePath;
    }

    public ImageLoader(){
    }

    public int countFiles(){
        int retValue = 0;
        try {
            retValue = listFiles(Paths.get(path)).size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retValue;
    }

    /**
     * http://stackoverflow.com/questions/20987214/recursively-list-all-files-within-a-directory-using-nio-file-directorystream
     * @param path
     * @return
     * @throws IOException
     */
    private List<Path> listFiles(Path path) throws IOException {
        Deque<Path> stack = new ArrayDeque<Path>();
        final List<Path> files = new LinkedList<>();

        stack.push(path);
        while (!stack.isEmpty()) {
            DirectoryStream<Path> stream = Files.newDirectoryStream(stack.pop());
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    stack.push(entry);
                }
                else {
                    files.add(entry);
                }
            }
            stream.close();
        }
        return files;
    }


}
