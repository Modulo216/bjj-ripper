package de.jonashackt.springbootvuejs.domain;

import nl.stil4m.transmission.api.domain.TorrentInfo;
import org.springframework.beans.BeanUtils;

public class Torrent {
    private Long id;
    private String name;
    private String downloadDir;
    private Long addedDate;
    private Double uploadRatio;
    private Long status;
    private Long totalSize;
    private Double percentDone;

    public Torrent() {}

    public Torrent(TorrentInfo torrentInfo) {
        BeanUtils.copyProperties(torrentInfo, this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDownloadDir() {
        return downloadDir;
    }

    public void setDownloadDir(String downloadDir) {
        this.downloadDir = downloadDir;
    }

    public Long getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Long addedDate) {
        this.addedDate = addedDate;
    }

    public Double getUploadRatio() {
        return uploadRatio;
    }

    public void setUploadRatio(Double uploadRatio) {
        this.uploadRatio = uploadRatio;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public Double getPercentDone() {
        return percentDone;
    }

    public void setPercentDone(Double percentDone) {
        this.percentDone = percentDone;
    }

    @Override
    public String toString() {
        return "Torrent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", downloadDir='" + downloadDir + '\'' +
                ", addedDate=" + addedDate +
                ", uploadRatio=" + uploadRatio +
                ", status=" + status +
                ", totalSize=" + totalSize +
                ", percentDone=" + percentDone +
                '}';
    }
}
