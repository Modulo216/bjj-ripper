package de.jonashackt.springbootvuejs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class VFile {
    private int id;
    private String name;
    private String type;
    private long size = 0;
    private List<VFile> children;
    private String path;

    protected VFile() {}

    public VFile(int id, File file) throws IOException {
        this.id = id;
        this.name = file.getName();
        this.type = Files.probeContentType(file.toPath());
        this.size = file.length() / 1024;
    }

    public VFile(int id, File file, List<VFile> children) throws IOException {
        this.id = id;
        this.name = file.getName();
        this.children = children;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }
    public List<VFile> getChildren() {
        return children;
    }
    public void setChildren(List<VFile> children) {
        this.children = children;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "VFile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                ", children=" + children +
                '}';
    }
}
