package com.siongriffiths.plantatlas.imageutils;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by sig2@aber.ac.uk on 08/02/2016.
 */
public class ImageLoader {

    public static final Logger LOGGER = Logger.getLogger(ImageLoader.class);
    private String path = "src/main/resources/static/images";

    //Why do this to me Windows??!
    private static final String toRemove = "src\\main\\resources\\static" ;

    public ImageLoader(String filePath){
        path = filePath;
    }

    public ImageLoader(){
    }

    public List<String> getFiles(){
        List<String> fileList = new ArrayList<>();
        File file = new File(path);

        try {
            List<File> pathList = listFiles(Paths.get(path));
            String filepath = "";

            for(File f : pathList) {
                filepath = f.getPath();
                LOGGER.info(f.getPath());
                fileList.add(filepath.replace(toRemove,""));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    /**
     * http://stackoverflow.com/questions/20987214/recursively-list-all-files-within-a-directory-using-nio-file-directorystream
     * @param path
     * @return
     * @throws IOException
     */
    private List<File> listFiles(Path path) throws IOException {
        Deque<Path> stack = new ArrayDeque<Path>();
        final List<File> files = new LinkedList<>();

        stack.push(path);
        while (!stack.isEmpty()) {
            DirectoryStream<Path> stream = Files.newDirectoryStream(stack.pop());
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    stack.push(entry);
                }
                else {
                    files.add(entry.toFile());
                }
            }
            stream.close();
        }
        return files;
    }


}
